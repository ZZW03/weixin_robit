package com.zzw.infrastuction.adapter.respository;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPObject;
import com.alibaba.fastjson2.TypeReference;
import com.zzw.api.model.response.Response;
import com.zzw.domain.Message.adapter.MessageOperation;
import com.zzw.domain.Message.model.req.MessageRequest;
import com.zzw.domain.Message.model.req.MsgItem;
import com.zzw.domain.Message.model.res.DataItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class MessageRepository implements MessageOperation {

    @Value("${spring.weixin.key:}")
    private  String key;



    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Response<List<DataItem>> sendMessage(MessageRequest request) {
        String url = "http://49.235.216.182:1238/message/SendTextMessage?key=15b1e540-2b4e-473b-9d13-3e03bb86958e";
        if (request.getMsgItem().size() == 0){
            return Response.error("没收到消息");
        }
        MsgItem msg = request.getMsgItem().get(0);
        // 构造消息体
        msg.setAtWxIDList(Collections.singletonList(""));  // 实际应替换为真实wxID
        msg.setImageContent("");
        msg.setMsgType(0);  // 0=文本消息
        msg.setTextContent("你好，来自 Java 的消息！");
        msg.setToUserName("wxid_xxx");  // 替换为目标用户wxid

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMsgItem(Collections.singletonList(msg));

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MessageRequest> entity = new HttpEntity<>(messageRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        String body = response.getBody();
        log.info("body:{}",body);
        return JSONObject.parseObject(body, new TypeReference<Response<List<DataItem>>>() {});

    }

}
