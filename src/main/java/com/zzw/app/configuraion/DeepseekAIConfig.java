package com.zzw.app.configuraion;

import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.observation.ObservationRegistry;
import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.observation.ChatClientObservationConvention;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.*;

@Configuration
public class DeepseekAIConfig {

    @Value("${spring.ai.see.urls:}")
    private List<String> seeUrls;

    @Bean
    public TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }

    @Bean
    public OpenAiApi openAiApi(@Value("${spring.ai.openai.base-url}") String baseUrl,
                               @Value("${spring.ai.openai.api-key}") String apikey) {
        return OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apikey)
                .build();
    }


    @Bean("pgVectorDataSource")
    public DataSource pgVectorDataSource(@Value("${spring.ai.vectorstore.pgvector.datasource.driver-class-name}") String driverClassName,
                                         @Value("${spring.ai.vectorstore.pgvector.datasource.url}") String url,
                                         @Value("${spring.ai.vectorstore.pgvector.datasource.username}") String username,
                                         @Value("${spring.ai.vectorstore.pgvector.datasource.password}") String password,
                                         @Value("${spring.ai.vectorstore.pgvector.datasource.hikari.maximum-pool-size:5}") int maximumPoolSize,
                                         @Value("${spring.ai.vectorstore.pgvector.datasource.hikari.minimum-idle:2}") int minimumIdle,
                                         @Value("${spring.ai.vectorstore.pgvector.datasource.hikari.idle-timeout:30000}") long idleTimeout,
                                         @Value("${spring.ai.vectorstore.pgvector.datasource.hikari.connection-timeout:30000}") long connectionTimeout) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        // 连接池配置
        dataSource.setMaximumPoolSize(maximumPoolSize);
        dataSource.setMinimumIdle(minimumIdle);
        dataSource.setIdleTimeout(idleTimeout);
        dataSource.setConnectionTimeout(connectionTimeout);
        // 确保在启动时连接数据库
        dataSource.setInitializationFailTimeout(1);  // 设置为1ms，如果连接失败则快速失败
        dataSource.setConnectionTestQuery("SELECT 1"); // 简单的连接测试查询
        dataSource.setAutoCommit(true);
        dataSource.setPoolName("PgVectorHikariPool");
        return dataSource;
    }

    @Bean("pgVectorJdbcTemplate")
    public JdbcTemplate pgVectorJdbcTemplate(@Qualifier("pgVectorDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean("vectorStore")
    public PgVectorStore pgVectorStore(@Qualifier("pgVectorJdbcTemplate") JdbcTemplate jdbcTemplate,
                                       @Value("${spring.ai.ollama.base-url:}") String ollamaURL,
                                       @Value("${spring.ai.ollama.embedding-model:}") String modelEmbed,
                                       @Value("${spring.ai.ollama.embedding.options.num-batch:}") int numBatch) {
        OllamaApi ollamaApi = new OllamaApi(ollamaURL); // 或你的 IP

        OllamaOptions options = OllamaOptions.builder()
                .model(modelEmbed)
                .numBatch(numBatch)
                .build();

        OllamaEmbeddingModel embeddingModel = OllamaEmbeddingModel.builder().ollamaApi(ollamaApi).defaultOptions(options).build();
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .vectorTableName("vector_store_deepseek")
                .build();
    }

    @Bean("syncMcpToolCallbackProvider")
    public SyncMcpToolCallbackProvider syncMcpToolCallbackProvider(List<McpSyncClient> mcpClients) {
//        mcpClients.remove(0);

        // 用于记录 name 和其对应的 index
        Map<String, Integer> nameToIndexMap = new HashMap<>();
        // 用于记录重复的 index
        Set<Integer> duplicateIndices = new HashSet<>();

        // 遍历 mcpClients 列表
        for (int i = 0; i < mcpClients.size(); i++) {
            String name = mcpClients.get(i).getServerInfo().name();
            if (nameToIndexMap.containsKey(name)) {
                // 如果 name 已经存在，记录当前 index 为重复
                duplicateIndices.add(i);
            } else {
                // 否则，记录 name 和 index
                nameToIndexMap.put(name, i);
            }
        }

        // 删除重复的元素，从后往前删除以避免影响索引
        List<Integer> sortedIndices = new ArrayList<>(duplicateIndices);
        sortedIndices.sort(Collections.reverseOrder());
        for (int index : sortedIndices) {
            mcpClients.remove(index);
        }

        return new SyncMcpToolCallbackProvider(mcpClients);
    }

    @Bean
    public  OpenAiChatOptions options(){
        return OpenAiChatOptions.builder()
                .model("deepseek-chat")
                .build();
    }


    @Bean
    public ChatClient chatClient(@Qualifier("syncMcpToolCallbackProvider") ToolCallbackProvider syncMcpToolCallbackProvider,
                                 OpenAiApi openAiApi,
                                 OpenAiChatOptions options,
                                 ChatMemory chatMemory) {
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(options)
                .build();



        return ChatClient.builder(chatModel)
                .defaultTemplateRenderer(new NoOpTemplateRenderer())
                .defaultTools(syncMcpToolCallbackProvider)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),
                        new SimpleLoggerAdvisor())
                .build();

    }


}
