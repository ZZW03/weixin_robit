package com.zzw.trigger.websocket;

import jakarta.websocket.ClientEndpointConfig;

public interface WebSocketFactory {

    void startClient(AbstractWebSocketEndpoint client);

    void shutUpClient(AbstractWebSocketEndpoint client );

}
