package org.lg.upone.login.common.utils;

import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.utils.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 从网关获取数据
 *
 * @author hezhenyang on 2021/7/16
 */
public class ReqUtils {
    /**
     * 返回操作人
     *
     * @param httpServletRequest 入参
     * @return 操作人
     */
    public static Operator getOperator(HttpServletRequest httpServletRequest) {
        Operator operator = new Operator();
//        Map<String, Object> objectmap = LoginContext.get();
//        Object o = objectmap.get("uid");
//        if (o == null) {
//            throw new WfException(WfExceptionCode.NOT_LOGIN_ERROR.getCode(), WfExceptionCode.NOT_LOGIN_ERROR.getMsg());
//        }
//        //用户Id校验
//        if (BaasLoginContext.getUid() == null) {
//            throw new WfException(WfExceptionCode.USER_ID_BLANK.getCode(), WfExceptionCode.USER_ID_BLANK.getMsg());
//        }
//        operator.setId(Long.toString(BaasLoginContext.getUid()));
//        //组织Id校验
//        if (BaasLoginContext.getOrgId() == null) {
//            throw new WfException(WfExceptionCode.USER_NAME_BLANK.getCode(), WfExceptionCode.USER_NAME_BLANK.getMsg());
//        }
//        operator.setOrgId(Long.toString(BaasLoginContext.getOrgId()));
//        //组织名称校验
//        if (BaasLoginContext.getName() == null) {
//            throw new WfException(WfExceptionCode.ORG_ID_BLANK.getCode(), WfExceptionCode.ORG_ID_BLANK.getMsg());
//        }
//        operator.setName(BaasLoginContext.getName());
//
//        operator.setMobile( BaasLoginContext.getMobile());
//        String uid = getFromReq(httpServletRequest, "uid");
//        Assert.notNull(uid, WfExceptionCode.USER_ID_BLANK.getMsg(),WfExceptionCode.USER_ID_BLANK.getCode());
//        operator.setId(uid);
//
//        String name = getFromReq(httpServletRequest, "name");
//        Assert.notNull(name, WfExceptionCode.USER_NAME_BLANK.getMsg(),WfExceptionCode.USER_NAME_BLANK.getCode());
//        operator.setName(name);
//
//        String orgId = getFromReq(httpServletRequest, "orgId");
//        Assert.notNull(orgId, WfExceptionCode.ORG_ID_BLANK.getMsg(),WfExceptionCode.ORG_ID_BLANK.getCode());
//        operator.setOrgId(orgId);
        return operator;
    }


    /**
     * 查找request中存储的数据
     *
     * @param request 请求体
     * @param key     数据key
     * @return request中存储的数据
     */
    public static String getFromReq(HttpServletRequest request, String key) {
        String codeKey = request.getHeader(key);
        if (Utils.isEmpty(codeKey)) {
            codeKey = (String) request.getAttribute(key);
        }
        if (Utils.isEmpty(codeKey)) {
            codeKey = request.getParameter(key);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return codeKey;
    }
}
