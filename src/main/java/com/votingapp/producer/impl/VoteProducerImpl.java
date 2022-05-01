package com.votingapp.producer.impl;

import com.votingapp.configuration.RabbitConfig;
import com.votingapp.domain.dto.VoteDTO;
import com.votingapp.producer.IVoteProducer;
import com.votingapp.service.IRabbitTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteProducerImpl implements IVoteProducer {

    private final IRabbitTemplate rabbitTemplate;
    private final RabbitConfig rabbitConfig;

    @Override
    public void sendToQueue(VoteDTO payload) {
        rabbitTemplate.sendToQueue(payload, rabbitConfig.VOTE_EXCHANGE, rabbitConfig.VOTE_ROUTING_KEY);
    }

}
