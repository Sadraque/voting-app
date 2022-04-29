package com.votingapp.repository;

import com.votingapp.domain.Election;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ElectionCustomRepository {

    List<Election> findAllToOpen(Date reference);

    List<Election> findAllToClose(Date reference);
}
