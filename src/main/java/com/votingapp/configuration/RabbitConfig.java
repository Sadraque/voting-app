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

    @Value("${rabbit.queues.email.queue}")
    public String EMAIL_QUEUE;

    @Value("${rabbit.queues.vote.routingKey}")
    public String VOTE_ROUTING_KEY;

    @Value("${rabbit.queues.email.routingKey}")
    public String EMAIL_ROUTING_KEY;

    @Value("${rabbit.user}")
    public String USER;

    @Value("${rabbit.password}")
    public String PASSWORD;

    @Value("${rabbit.host}")
    public String HOST;

    @Value("${rabbit.health}")
    public String HEALTH;

    public final String DEFAULT_EXCHANGE = "default_exchange";

    @Bean
    public Queue voteQueue() {
        return new Queue(VOTE_QUEUE, true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public Binding voteBinding(final TopicExchange topicExchange) {
        return BindingBuilder
                .bind(voteQueue())
                .to(topicExchange)
                .with(VOTE_ROUTING_KEY);
    }

    @Bean
    public Binding emailBinding(final TopicExchange topicExchange) {
        return BindingBuilder
                .bind(emailQueue())
                .to(topicExchange)
                .with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public TopicExchange defaultExchange() {
        return new TopicExchange(DEFAULT_EXCHANGE);
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
