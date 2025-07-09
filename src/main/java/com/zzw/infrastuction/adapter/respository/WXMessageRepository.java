package com.zzw.infrastuction.adapter.respository;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.zzw.api.model.response.Response;
import com.zzw.domain.WxMessage.adapter.MessageOperation;
import com.zzw.domain.WxMessage.model.req.MessageRequest;
import com.zzw.domain.WxMessage.model.res.DataItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class WXMessageRepository implements MessageOperation {

    @Value("${spring.weixin.authCode:}")
    private String authCode;

    @Value("${spring.weixin.sendMessageURl:}")
    private String sendMessageURl;

    @Value("${spring.weixin.baseUrl:}")
    private String baseUrl;

    @Override
    public Response<List<DataItem>> sendMessage(MessageRequest request) {
        String url = baseUrl + sendMessageURl + "?key=" + authCode;
        if (request.getMsgItem().isEmpty()){
            return Response.error("没收到消息");
        }
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MessageRequest> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        String body = response.getBody();
        log.info("body:{}",body);
        return JSONObject.parseObject(body, new TypeReference<>() {});
    }

}
