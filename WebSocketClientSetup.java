@Override
public void run(String... args) {
    try {
        String url = websocketConnect;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(256 * 1024);
        container.setDefaultMaxBinaryMessageBufferSize(256 * 1024);

        // 设置自定义 Header，使用配置中的 Bearer token
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Authorization", Collections.singletonList("Bearer " + key));

        ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
                .configurator(new ClientEndpointConfig.Configurator() {
                    @Override
                    public void beforeRequest(Map<String, List<String>> requestHeaders) {
                        requestHeaders.putAll(headers);
                    }
                })
                .build();

        // 修改此处，确保类型正确
        container.connectToServer((Class<? extends Endpoint>) MyWebSocketClientQQ.class, config, URI.create(url));
        log.info("WebSocket 连接成功：{}", url);

    } catch (DeploymentException | IOException e) {
        log.error("WebSocket 连接失败：{}", e.getMessage(), e);
    }
}