package com.zzw.infrastuction.util;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

public class HttpUtils {



    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * GET 请求
     * @param url 请求地址
     * @param headers 请求头（可为 null）
     * @param responseType 返回类型
     */
    public static <T> T get(String url, Map<String, String> headers, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::add);
        }
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return response.getBody();
    }

    /**
     * POST 请求
     * @param url 请求地址
     * @param headers 请求头（可为 null）
     * @param body 请求体
     * @param responseType 返回类型
     */
    public static <T> T post(String url, Map<String, String> headers, Object body, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); // 默认 JSON
        if (headers != null) {
            headers.forEach(httpHeaders::add);
        }
        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<T> response = restTemplate.postForEntity(url, entity, responseType);
        return response.getBody();
    }
}
