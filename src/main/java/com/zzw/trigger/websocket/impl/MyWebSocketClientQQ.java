package com.zzw.trigger.websocket.impl;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zzw.api.model.response.Response;
import com.zzw.domain.QQMessage.model.req.QQSimpleSendMessage;
import com.zzw.domain.QQMessage.model.resp.QQSendResponse;
import com.zzw.domain.QQMessage.model.websockekResp.QQPrivateMessage;
import com.zzw.infrastuction.adapter.respository.QQMessageRepository;
import com.zzw.infrastuction.dao.MessageRecordMapper;
import com.zzw.infrastuction.dao.po.MessageRecord;
import com.zzw.trigger.websocket.AbstractWebSocketEndpoint;
import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MyWebSocketClientQQ extends AbstractWebSocketEndpoint implements MessageHandler.Whole<String> {


    @Value("${spring.qq.token:}")
    private String key;

    @Value("${spring.qq.websocket.url:}")
    private String websocketConnect;

    @Autowired
    private QQMessageRepository qqMessageRepository;

    @Autowired
    MessageRecordMapper messageRecordMapper;

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        this.heartbeatTimeout = 30000L;
        log.info("[WebSocket] 连接已经打开");
        session.addMessageHandler(this);
    }

    @Override
    public void onMessage(String json) {

        JSONObject obj = JSONObject.parseObject(json);
        String postType = obj.getString("post_type");
        String messageType = obj.getString("message_type");
        String messageId = obj.getString("message_id");

        if (postType.equals("meta_event")){


        }else if (postType.equals("message")){
            QQPrivateMessage qqPrivateMessage = JSONObject.parseObject(json,QQPrivateMessage.class);
            MessageRecord messageRecord = qqPrivateMessage.toMessageRecord();
            messageRecordMapper.insert(messageRecord);

            //群
            if ("private".equals(messageType)){
                log.info("收到私聊消息:{}",messageId);
                Response<QQSendResponse> qqSendResponseResponse = qqMessageRepository.sendPrivateMessage(qqPrivateMessage);
            }

            //私聊
            if ("group".equals(messageType)){
                log.info("收到群聊消息:{}",messageId);
                Response<QQSendResponse> qqSendResponseResponse = qqMessageRepository.sendGroupMessage(qqPrivateMessage);
            }

        }else{
            log.info("不处理");
        }
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        log.info("id为{}Client,发生了错误",session.getId(),throwable);
        throwable.getStackTrace();
    }


    @Override
    public ClientEndpointConfig getConfig() {
        return  ClientEndpointConfig.Builder.create()
                .configurator(new ClientEndpointConfig.Configurator() {
                    @Override
                    public void beforeRequest(Map<String, List<String>> headers) {
                        // 添加自定义头信息
                        headers.put("Authorization", List.of("Bearer "+ key));
                    }
                }).build();
    }

    public void sendHeartbeat() {
        try {
            String heartbeatMsg = "{\"type\":\"heartbeat\",\"timestamp\":" + System.currentTimeMillis() + "}";
            session.getBasicRemote().sendText(heartbeatMsg);
        } catch (IOException e) {
            log.error("id为{}Client,心跳发送错误",session.getId());
        }
    }

    @Override
    public String getConnectURL(){
        return this.websocketConnect;
    }
}
