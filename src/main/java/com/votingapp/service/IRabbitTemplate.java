package com.votingapp.service;

import com.votingapp.domain.dto.RabbitPayloadDTO;
import org.springframework.messaging.handler.annotation.Payload;

public interface IRabbitTemplate {

    void sendToQueue(final @Payload RabbitPayloadDTO payloadDTO, String exchange, String routingKey);

    String generateId();
}
