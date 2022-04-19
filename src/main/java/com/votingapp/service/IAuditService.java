package com.votingapp.service;

import com.votingapp.domain.Audit;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IAuditService {

    Audit createAudit(final String electionId);

    Audit findAuditById(final String electionId);

    Optional<Audit> findLastAuditByElectionId(String electionId);
}
