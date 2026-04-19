package org.lg.engine.core.service.manager;

import com.alibaba.fastjson.JSON;
import org.lg.engine.core.client.enumerate.ServiceTypeEnum;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.ServiceParam;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.ReServiceModelMapper;
import org.lg.engine.core.db.model.ReServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 三方服务接口
 */
@Component
public class ServiceModelHelper {

    private static final Logger logger = LoggerFactory.getLogger(ServiceModelHelper.class);

    private static ReServiceModelMapper reServiceModelMapper;

    @Autowired
    public ServiceModelHelper(ReServiceModelMapper reServiceModelMapper) {
        ServiceModelHelper.reServiceModelMapper = reServiceModelMapper;
    }

    /**
     * 获取人员
     */
    public static List<Assignee> getAssignee(Long id, List<ServiceParam> params) {
        if (id == null) {
            return new ArrayList<>(0);
        }
        ReServiceModel reServiceModel = reServiceModelMapper.selectByPrimaryKey(id);
        if (reServiceModel == null) {
            return new ArrayList<>(0);
        }
        //获取服务地址
        String url = reServiceModel.getUrl();
        if (Utils.isEmpty(url)) {
            return new ArrayList<>(0);
        }

        Map<String, Object> paramsMap = getStringObjectMap(params);

        Object o = HttpClientHelper.doPost(url, paramsMap, ServiceTypeEnum.USER);
        if (o == null) {
            return new ArrayList<>(0);
        }
        try {
            return JSON.parseArray(o.toString(), Assignee.class);
        } catch (Exception e) {
            logger.error("调用人员接口错误,接口：{}", url, e);
            return new ArrayList<>(0);
        }
    }

    /**
     * 判断条件
     */
    public static Boolean getBoolean(Long id, List<ServiceParam> params) {
        if (id == null) {
            return false;
        }
        ReServiceModel reServiceModel = reServiceModelMapper.selectByPrimaryKey(id);
        if (reServiceModel == null) {
            return false;
        }
        //获取服务地址
        String url = reServiceModel.getUrl();
        if (Utils.isEmpty(url)) {
            return false;
        }

        Map<String, Object> paramsMap = getStringObjectMap(params);

        Object o = HttpClientHelper.doPost(url, paramsMap, ServiceTypeEnum.CONDITION);
        if (o == null) {
            return false;
        }
        try {
            return (Boolean) o;
        } catch (Exception e) {
            logger.error("调用人员接口错误,接口：{}", url, e);
            return false;
        }
    }

    /**
     * 调用接口
     */
    public static void callService(Long id, List<ServiceParam> params) {
        if (id == null) {
            return;
        }
        ReServiceModel reServiceModel = reServiceModelMapper.selectByPrimaryKey(id);
        if (reServiceModel == null) {
            return;
        }
        //获取服务地址
        String url = reServiceModel.getUrl();
        if (Utils.isEmpty(url)) {
            return;
        }
        try {
            Map<String, Object> paramsMap = getStringObjectMap(params);
            HttpClientHelper.doPost(url, paramsMap, ServiceTypeEnum.CONDITION);
        } catch (Exception e) {
            logger.error("调用接口错误,接口：{}", url, e);
        }
    }


    private static Map<String, Object> getStringObjectMap(List<ServiceParam> params) {

        Map<String, Object> paramsMap = new HashMap<>();

        if (Utils.isNotEmpty(params)) {
            for (ServiceParam param : params) {
                if (param == null) {
                    continue;
                }
                String fieldKey = param.getFieldKey();
                String fieldValue = param.getFieldValue();
                if (Utils.isEmpty(fieldKey) || Utils.isEmpty(fieldValue)) {
                    continue;
                }
                paramsMap.put(fieldKey, fieldValue);
            }
        }
        return paramsMap;
    }
}
