package com.votingapp.consumer;

import com.votingapp.domain.dto.RabbitPayloadDTO;
import com.votingapp.domain.dto.VoteDTO;
import com.votingapp.service.IVoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log
public class VoteConsumer {

    private final IVoteService service;

    @RabbitListener(queues = "${rabbit.queues.vote.queue}")
    public void consumer(@Payload RabbitPayloadDTO payload) {
        log.info("[*] consuming " +payload);
        service.createVote((VoteDTO) payload);
    }
}
