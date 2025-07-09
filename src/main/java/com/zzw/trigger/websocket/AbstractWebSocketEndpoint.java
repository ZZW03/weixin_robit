package com.zzw.trigger.websocket;

import com.zzw.trigger.websocket.impl.WebsocketFactoryImpl;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class AbstractWebSocketEndpoint extends Endpoint implements EndpointProcess{

    public AtomicLong lastHeartbeatTime = new AtomicLong(0);

    public AtomicBoolean isHeartbeatRunning = new AtomicBoolean(false);

    public Long heartbeatTimeout ;

    public Session session ;

    public ScheduledExecutorService heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();

    public abstract void sendHeartbeat();

    @Override
    public  void startHeartbeat() {
        if (isHeartbeatRunning.get()) {
            return;
        }

        if (heartbeatExecutor.isShutdown()){
            heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
        }

        isHeartbeatRunning.set(true);
        lastHeartbeatTime.set(System.currentTimeMillis());

        // 定时发送心跳包
        heartbeatExecutor.scheduleAtFixedRate(() -> {
            try {
                if (session != null && session.isOpen()) {
                    sendHeartbeat();
                    checkHeartbeatTimeout(); // 每次发送后检查超时
                }
            } catch (Exception e) {
                log.error("id为{}的websocketClient,心跳任务执行异常", session.getId());
            }
        }, 0, 30000, TimeUnit.MILLISECONDS);
    }

    public void stopHeartbeat(){
        heartbeatExecutor.shutdownNow();
    }

    public void startClient() {
        WebsocketFactoryImpl.getInstance().startClient(this);
    }


    public  void shutUpClient(){
        try {
            session.close();
            log.info("关闭Id为{}的WebSocket：",session.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkHeartbeatTimeout() {
        if (System.currentTimeMillis() - lastHeartbeatTime.get() > heartbeatTimeout) {
            startClient();
        }
    }

}
