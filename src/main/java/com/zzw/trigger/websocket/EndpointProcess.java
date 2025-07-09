package com.zzw.trigger.websocket;

import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.Session;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public interface EndpointProcess {


    void startClient();

    void shutUpClient();

    ClientEndpointConfig getConfig();

    void startHeartbeat();

    void stopHeartbeat();

    void sendHeartbeat();

    String getConnectURL();
}
