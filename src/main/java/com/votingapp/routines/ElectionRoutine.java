package com.votingapp.routines;

import com.votingapp.domain.Election;
import com.votingapp.service.IElectionService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@Log
public class ElectionRoutine {

    private final IElectionService service;

    @Scheduled(fixedDelayString = "${routine.election.open}")
    public void openElectionsAutomatically() {
        String id = UUID.randomUUID().toString();
        log.info(String.format("[*] starting Routine::openElectionsAutomatically [id: %s]", id));

        try {
            List<Election> elections = service.findAllToOpen(new Date());

            log.info(String.format("[*] elections size::%d [id: %s]", elections.size(), id));

            elections.forEach(election -> {
                election.setStarted(true);
                election.setUpdatedAt(LocalDateTime.now());

                service.updateElection(election);
            });

        } catch (Exception e) {
            log.info(String.format("[*] error::%s [id: %s]", e.getMessage(), id));

        } finally {
            log.info("[*] ending Routine::openElectionsAutomatically");
        }
    }

    @Scheduled(fixedDelayString = "${routine.election.close}")
    public void closeElectionsAutomatically() {
        String id = UUID.randomUUID().toString();
        log.info(String.format("[*] starting Routine::closeElectionsAutomatically [id: %s]", id));

        try {
            List<Election> elections = service.findAllToClose(new Date());

            log.info(String.format("[*] elections size::%d [id: %s]", elections.size(), id));

            elections.forEach(election -> {
                election.setClosed(true);
                election.setUpdatedAt(LocalDateTime.now());

                service.updateElection(election);
            });

        } catch (Exception e) {
            log.info(String.format("[*] error::%s [id: %s]", e.getMessage(), id));

        } finally {
            log.info("[*] ending Routine::closeElectionsAutomatically");
        }
    }
}
