package com.votingapp.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbit.queues.vote.queue}")
    public String VOTE_QUEUE;

    @Value("${rabbit.queues.vote.exchange}")
    public String VOTE_EXCHANGE;

    @Value("${rabbit.queues.vote.routingKey}")
    public String VOTE_ROUTING_KEY;

    @Bean
    public Queue queue() {
        return new Queue(VOTE_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(VOTE_EXCHANGE);
    }

    @Bean
    public Binding binding(final Queue queue, final TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(VOTE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
