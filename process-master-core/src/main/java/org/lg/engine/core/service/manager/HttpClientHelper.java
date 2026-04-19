package org.lg.engine.core.service.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.lg.engine.core.client.enumerate.ServiceTypeEnum;
import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class HttpClientHelper {

    private static final Logger log = LoggerFactory.getLogger(HttpClientHelper.class);

    public static Object doPost(String url, Map<String, Object> params, ServiceTypeEnum serviceTypeEnum) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        ObjectMapper mapper = new ObjectMapper();
        CloseableHttpResponse response = null;
        try {
            String json = mapper.writeValueAsString(params);

            StringEntity input = new StringEntity(json);
            input.setContentType("application/json");
            httpPost.setEntity(input);

            response = httpClient.execute(httpPost);

            // 检查HTTP响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) { // 200 OK
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 获取并打印出响应体内容
                    //{"code":200,"data":false,"success":true}
                    String result = EntityUtils.toString(entity);
                    if (Utils.isNotEmpty(result)) {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (jsonObject != null) {
                            Integer code = jsonObject.getInteger("code");
                            if (!code.equals(HttpStatus.SC_OK)) {
                                log.error("注册接口服务异常，地址：{}，返回：{}", url, JSON.toJSONString(response));
                                return null;
                            }
                            if (serviceTypeEnum.equals(ServiceTypeEnum.CONDITION)) {
                                return jsonObject.getBoolean("data");
                            }
                            if (serviceTypeEnum.equals(ServiceTypeEnum.USER)) {
                                return jsonObject.get("data");
                            }
                            throw new WfException("不支持的接口调用类型" + serviceTypeEnum);
                        }
                    }
                } else {
                    log.error("注册接口服务返回空，地址：{}，返回：{}", url, JSON.toJSONString(response));
                }
            } else {
                log.error("注册接口服务异常，地址：{}，返回：{}", url, JSON.toJSONString(response));
            }
        } catch (Exception e) {
            log.error("调用注册接口服务发生错误，地址：{}", url, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return null;
    }
}