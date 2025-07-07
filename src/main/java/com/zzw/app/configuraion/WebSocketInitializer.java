package com.zzw.app.configuraion;

import com.zzw.trigger.websocket.MyWebSocketClient;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class WebSocketInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(WebSocketInitializer.class);

    @Value("${spring.weixin.key:}")
    private String key;

    private  final MyWebSocketClient client;

    public WebSocketInitializer(MyWebSocketClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) {
        try {
            String url = "ws://49.235.216.182:1238/ws/GetSyncMsg?key=" + key;
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(client, URI.create(url));
            log.info("连接成功");
        } catch (Exception e) {
            log.info("连接失败了，请重试：{}",e.getMessage());
        }
    }
}
