package com.votingapp.repository.impl;

import com.votingapp.domain.Election;
import com.votingapp.repository.ElectionCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ElectionCustomRepositoryImpl implements ElectionCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Election> findAllToOpen(Date reference) {
        return mongoTemplate.find(Query.query(
                Criteria.where("startAutomatically").is(true)
                        .and("started").is(false)
                        .and("startDate").lt(reference)),
                Election.class);
    }

    @Override
    public List<Election> findAllToClose(Date reference) {
        return mongoTemplate.find(Query.query(
                        Criteria.where("closed").is(false)
                                .and("endAutomatically").is(true)
                                .and("endDate").lt(reference)),
                Election.class);
    }
}
