package com.votingapp.service;

import com.votingapp.domain.Election;
import com.votingapp.domain.dto.ElectionDTO;
import org.springframework.stereotype.Service;

@Service
public interface IElectionService {

    Election createElection(ElectionDTO electionDTO);

    Election startElection();

    Election closeElection();
}
