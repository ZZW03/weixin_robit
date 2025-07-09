package com.zzw.infrastuction.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QQHttpClientUtils {

    @Value("${spring.qq.token}")
    private  String token;

    public  <T> T get(String url ,Class<T> responseType) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return HttpUtils.get(url,headers, responseType);
    }

    public  <T> T post(String url, Object body, Class<T> responseType) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return HttpUtils.post(url,headers,body,responseType);
    }
}
