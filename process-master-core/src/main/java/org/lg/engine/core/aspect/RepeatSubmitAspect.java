package org.lg.engine.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class RepeatSubmitAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(org.lg.engine.core.aspect.RepeatSubmit)")
    public void repeatSubmit() {
    }

    @Around("repeatSubmit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //同一个人和链接，1s内只能提交一次

//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
//                .getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取防重复提交注解
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
        if (annotation == null) {
            return joinPoint.proceed();
        }
        String methodPath = method.getDeclaringClass().getName() + method.getName();
        Object[] args = joinPoint.getArgs();

        String operatorId = "";
        if (Utils.isNotEmpty(args)) {
            Object arg = args[0];
            if (arg != null) {
                BaseWfRequest wfRequest = (BaseWfRequest) args[0];
                Operator operator = wfRequest.getOperator();
                Assert.isTrue(operator != null,
                        WfExceptionCode.ILLEGAL_USER_EMPTY.getMsg(),
                        WfExceptionCode.ILLEGAL_USER_EMPTY.getCode()
                );
                operatorId = operator.getId() + operator.getDeptId() + operator.getOrgId();
            }
        }

        //通过前缀 + methodPath + token 来生成redis上的 key
        String redisKey = "repeat_submit_key:"
                .concat("-")
                .concat(methodPath)
                .concat("-")
                .concat(operatorId);

        if (redisTemplate.hasKey(redisKey)) {
            return null;
        }
//        Assert.isTrue(!redisTemplate.hasKey(redisKey),
//                WfExceptionCode.REPEAT_SUBMIT.getMsg(),
//                WfExceptionCode.REPEAT_SUBMIT.getCode()
//        );

        redisTemplate.opsForValue().set(redisKey, redisKey, annotation.expireTime(), TimeUnit.SECONDS);
        try {
            //正常执行方法并返回
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            redisTemplate.delete(redisKey);
            throw throwable;
        }
    }
}
