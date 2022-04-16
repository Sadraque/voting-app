package com.votingapp.service;

import com.votingapp.domain.Audit;
import org.springframework.stereotype.Service;

@Service
public interface IAuditService {

    Audit createAudit();
}
