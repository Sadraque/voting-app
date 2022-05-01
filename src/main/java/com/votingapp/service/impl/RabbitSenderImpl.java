package com.votingapp.service.impl;

import com.votingapp.domain.dto.RabbitPayloadDTO;
import com.votingapp.service.IRabbitTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log
@Service
@RequiredArgsConstructor
public class RabbitSenderImpl implements IRabbitTemplate {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendToQueue(RabbitPayloadDTO payload, String exchange, String routingKey) {
        try {
            payload.setId(generateId());

            rabbitTemplate.convertAndSend(exchange,
                    routingKey,
                    payload);

        } catch (Exception e) {
            log.severe("Error sending payload to queue. Exception: " +e.getMessage());
        }
    }

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
