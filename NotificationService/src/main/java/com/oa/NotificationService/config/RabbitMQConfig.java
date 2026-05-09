package com.oa.NotificationService.config;

import com.fasterxml.jackson.databind.ObjectMapper; // Ensure this is imported

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "notification_queue";
    public static final String EXCHANGE = "notification_exchange";
    public static final String ROUTING_KEY = "notification_routing_key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    // Crucial: This tells RabbitMQ to use JSON for the DTO instead of Java Serialization
    @Bean
    public MessageConverter converter(ObjectMapper objectMapper) {
        // This uses the non-deprecated constructor
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}