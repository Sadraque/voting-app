package com.votingapp.service;

import com.votingapp.domain.Election;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.repository.*;
import com.votingapp.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.votingapp.ObjectBuilder.*;

public class AuditServiceTest {

    @Mock
    private AuditRepository repository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private ElectionRepository electionRepository;

    @InjectMocks
    private AuditServiceImpl underTest;

    private AutoCloseable closeable;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void afterEach() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Find Audit by id")
    public void when_FindAuditById_Then_ReturnAudit() {
        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.of(buildAudit()));

        Assertions.assertNotNull(underTest.findAuditById("vote-1"));
    }

    @Test
    @DisplayName("Find Vote by id throws EntityNotFoundException")
    public void when_FindAuditById_Then_ThrowsEntityNotFoundException() {
        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> underTest.findAuditById("vote-1"));
    }

    @Test
    @DisplayName("Find last Audit by electionId")
    public void when_FindLastAuditByElectionId_Then_ReturnAudit() {
        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.of(buildAudit()));

        Assertions.assertNotNull(underTest.findLastAuditByElectionId("vote-1"));
    }

/*    @Test
    @DisplayName("Create one Vote")
    public void when_CreateOneVote_Then_ReturnVote() {
        Election election = ObjectBuilder.buildElection();
        election.setStarted(true);
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(election));

        Mockito.when(userRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(ObjectBuilder.buildUser()));

        Mockito.when(candidateRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(ObjectBuilder.buildCandidate()));

        Mockito.when(repository.existsByUser(Mockito.any(User.class)))
                .thenReturn(false);

        Mockito.when(repository.save(Mockito.any(Vote.class)))
                .thenReturn(ObjectBuilder.buildVote());

        Assertions.assertNotNull(underTest.createVote(ObjectBuilder.buildVoteDTO()));
    }*/

    @Test
    @DisplayName("Create one Audit throws Exception")
    public void when_CreateOneAudit_Then_ThrowsExceptions() {

        //EntityNotFoundException - Election not found
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Exception entityNotFoundException =
                Assertions.assertThrows(EntityNotFoundException.class,
                        () -> underTest.createAudit("election-1"));

        Assertions.assertEquals(String.format("%s not found", Election.class.getSimpleName()), entityNotFoundException.getMessage());
    }
}
