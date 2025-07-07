package com.zzw.trigger.websocket;



import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.*;
import jakarta.websocket.Session;
import org.springframework.stereotype.Component;

@ClientEndpoint
@Component
public class MyWebSocketClient {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("xin 收到消息: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("[WebSocket] 连接关闭，原因: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("[WebSocket] 发生错误: " + throwable.getMessage());
    }

}
