package com.votingapp.producer;

import com.votingapp.domain.dto.VoteDTO;

public interface IVoteProducer {

    void sendToQueue(VoteDTO payload);

}
