package com.votingapp.repository;

import com.votingapp.domain.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends MongoRepository<Audit, String> {

}
