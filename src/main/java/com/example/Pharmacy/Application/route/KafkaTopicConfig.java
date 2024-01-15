package com.example.Pharmacy.Application.route;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

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
    public NewTopic emailTopic() {
        Map<String, String> topicConfig = new HashMap<>();
        topicConfig.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");
        topicConfig.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824");

        return new NewTopic("emailTopic", 2, (short) 1).configs(topicConfig);
    }

    @Bean
    public NewTopic paymentTopic() {
        Map<String, String> topicConfig = new HashMap<>();
        topicConfig.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        topicConfig.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");

        return new NewTopic("paymentTopic", 2, (short) 1).configs(topicConfig);
    }

}
