package com.votingapp.service;

import com.votingapp.domain.Election;
import com.votingapp.domain.dto.ElectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IElectionService {

    Election createElection(final ElectionDTO electionDTO);

    void openElection(final String electionId);

    void closeElection(final String electionId);

    Page<Election> findAllElections(final Pageable pageable);

    Election updateElection(final Election election);

    List<Election> findAllToOpen(final Date reference);

    List<Election> findAllToClose(final Date reference);
}
