package com.brytech.product_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic notificationEventsTopic() {
        Map<String, String> topicConfig = new HashMap<>();
        topicConfig.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, "86400000"); // 24 hours retention
        topicConfig.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824"); // I GB segment size

        return new NewTopic("product_events", 4, (short) 3) // 3 replicas
                .configs(topicConfig);
    }
}
