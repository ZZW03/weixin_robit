package com.zzw.trigger.websocket.impl;



import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.zzw.api.model.response.Response;
import com.zzw.domain.WxMessage.model.req.MessageRequest;
import com.zzw.domain.WxMessage.model.res.DataItem;
import com.zzw.domain.WxMessage.model.res.WebSocketMessage;
import com.zzw.infrastuction.adapter.respository.WXMessageRepository;
import jakarta.annotation.PreDestroy;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.*;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
@Component
@Slf4j
public class MyWebSocketClientWeixin {

    private Session session;

    private final StringBuilder partialMessageBuffer = new StringBuilder();

    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Value("${spring.weixin.authCode:}")
    private String key;

    @Value("${spring.weixin.connect:}")
    private String websocketConnect;

    @Autowired
    WXMessageRepository WXMessageRepository;

    @Value("${spring.weixin.currentUserName}")
    private String currentUserName;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        log.info("[WebSocket] 连接已经打开");
//        startHeartbeat();
    }

    // 如果你服务端支持分段消息，这样写
    @OnMessage
    public void onMessage(String message, boolean last) {
        partialMessageBuffer.append(message);
        if (last) {
            log.info("[WebSocket] 收到了消息：{}",JSON.toJSONString(partialMessageBuffer));
            WebSocketMessage webSocketMessage = JSONObject.parseObject(partialMessageBuffer.toString(), WebSocketMessage.class);
            if (webSocketMessage.getMsgType() == 1 && !webSocketMessage.getFromUserName().getStr().equals(currentUserName)) {
                MessageRequest messageRequest = webSocketMessage.toMessageRequest();
                Response<List<DataItem>> listResponse = WXMessageRepository.sendMessage(messageRequest);
                log.info("[WebSocket] 回复用户{},内容为{},收到的返回{}",messageRequest.getMsgItem().get(0).getToUserName(),
                        messageRequest.getMsgItem().get(0).getTextContent(), JSON.toJSONString(listResponse));
            }
            partialMessageBuffer.setLength(0); // 清空缓冲
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
       log.info("[WebSocket] 已经关闭，原因是:{}",reason);
        reconnectWithDelay();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
       log.error("[WebSocket] 发生了错误,原因是{}",throwable.getMessage());
       throwable.getStackTrace();
    }

    @PreDestroy
    public void cleanup() {
        if (session != null && session.isOpen()) {
            try {
               log.info("[WebSocket] 应用关闭，主动关闭连接");
                session.close();
            } catch (Exception e) {
                log.error("[WebSocket] 关闭连接异常: {}", e.getMessage());
            }finally {
                stopHeartbeat();
            }
        }
    }

    public void startHeartbeat() {
        scheduler.scheduleAtFixedRate(() -> {
            if (session != null && session.isOpen()) {
                try {
                    session.getAsyncRemote().sendText("ping");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    public void stopHeartbeat() {
        scheduler.shutdownNow();
    }

    public void reconnectWithDelay() {
        scheduler.schedule(() -> {
            try {
                log.info("[WebSocket] 尝试重新连接...");
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.setDefaultMaxTextMessageBufferSize(512 * 1024);
                container.setDefaultMaxBinaryMessageBufferSize(512 * 1024);
                container.connectToServer(this, URI.create(websocketConnect + key));
            } catch (Exception e) {
                log.error("[WebSocket] 重连失败", e);
                // 递归重试（建议加入最大重试次数）
                reconnectWithDelay();
            }
        }, 5, TimeUnit.SECONDS); // 5秒后重试
    }



}