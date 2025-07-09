package com.zzw.trigger.websocket.impl;

import com.zzw.trigger.websocket.AbstractWebSocketEndpoint;
import com.zzw.trigger.websocket.EndpointProcess;
import com.zzw.trigger.websocket.WebSocketFactory;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.WebSocketContainer;

import java.io.IOException;
import java.net.URI;

public class WebsocketFactoryImpl implements WebSocketFactory {

    private static class Holder {
        private static final WebsocketFactoryImpl INSTANCE = new WebsocketFactoryImpl();
    }

    public static WebsocketFactoryImpl getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void startClient(AbstractWebSocketEndpoint client) {
        String url = client.getConnectURL() ;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(256 * 1024);
        container.setDefaultMaxBinaryMessageBufferSize(256 * 1024);
        try {
            container.connectToServer(client,client.getConfig(), URI.create(url));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutUpClient(AbstractWebSocketEndpoint client) {
        client.shutUpClient();
    }
}
