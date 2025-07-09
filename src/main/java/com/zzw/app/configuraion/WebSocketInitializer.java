package com.zzw.app.configuraion;

import com.zzw.trigger.websocket.impl.MyWebSocketClientQQ;
import com.zzw.trigger.websocket.impl.WebsocketFactoryImpl;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketInitializer implements CommandLineRunner {

    private final MyWebSocketClientQQ client;

    public WebSocketInitializer(MyWebSocketClientQQ client) {
        this.client = client;
    }

    @Override
    public void run(String... args) {
        WebsocketFactoryImpl.getInstance().startClient(client);
    }
}
