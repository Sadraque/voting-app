package com.votingapp.service;

import com.votingapp.ObjectBuilder;
import com.votingapp.domain.Candidate;
import com.votingapp.domain.Election;
import com.votingapp.domain.User;
import com.votingapp.domain.Vote;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.exception.InvalidVoteException;
import com.votingapp.repository.CandidateRepository;
import com.votingapp.repository.ElectionRepository;
import com.votingapp.repository.UserRepository;
import com.votingapp.repository.VoteRepository;
import com.votingapp.service.impl.VoteServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.votingapp.ObjectBuilder.buildCandidate;
import static com.votingapp.ObjectBuilder.buildVote;

public class VoteServiceTest {

    @Mock
    private VoteRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private ElectionRepository electionRepository;

    @InjectMocks
    private VoteServiceImpl underTest;

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
    @DisplayName("Find Vote by id")
    public void when_FindVoteById_Then_ReturnVote() {
        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.ofNullable(buildVote()));

        Assertions.assertNotNull(underTest.findVoteById("vote-1"));
    }

    @Test
    @DisplayName("Find Vote by id throws EntityNotFoundException")
    public void when_FindVoteById_Then_ThrowsEntityNotFoundException() {
        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> underTest.findVoteById("vote-1"));
    }


    @Test
    @DisplayName("Find all Votes")
    public void when_FindAllVotes_Then_ReturnVotes() {
        Mockito.when(repository.findAll(Pageable.unpaged()))
                        .thenReturn(new PageImpl<>(
                                List.of(ObjectBuilder.buildVote(),
                                        ObjectBuilder.buildVote())));

        Assertions.assertNotNull(underTest.findAllVotes(Pageable.unpaged()));
        Assertions.assertTrue(underTest.findAllVotes(Pageable.unpaged()).getSize() > 1);
    }

    @Test
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

        Mockito.when(repository.existsByUserId(Mockito.anyString()))
                .thenReturn(false);

        Mockito.when(repository.save(Mockito.any(Vote.class)))
                .thenReturn(ObjectBuilder.buildVote());

        Assertions.assertNotNull(underTest.createVote(ObjectBuilder.buildVoteDTO()));
    }

    @Test
    @DisplayName("Create one Vote throws Exception")
    public void when_CreateOneVote_Then_ThrowsExceptions() {
        //EntityNotFoundException - Election not found
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());
        Exception entityNotFoundException = Assertions.assertThrows(EntityNotFoundException.class, () -> underTest.createVote(ObjectBuilder.buildVoteDTO()));
        Assertions.assertEquals(String.format("%s not found", Election.class.getSimpleName()), entityNotFoundException.getMessage());

        //InvalidVoteException - Election closed
        Election election = ObjectBuilder.buildElection();
        election.setStarted(false);
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(election));
        Exception invalidVoteException = Assertions.assertThrows(InvalidVoteException.class, () -> underTest.createVote(ObjectBuilder.buildVoteDTO()));
        Assertions.assertEquals("Invalid vote. Election it is closed", invalidVoteException.getMessage());


        //EntityNotFoundException - User not found
        Mockito.when(userRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());
        election.setStarted(true);
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(election));
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(election));
        Exception entityNotFoundException2 = Assertions.assertThrows(EntityNotFoundException.class, () -> underTest.createVote(ObjectBuilder.buildVoteDTO()));
        Assertions.assertEquals(String.format("%s not found", User.class.getSimpleName()), entityNotFoundException2.getMessage());

        //EntityNotFoundException - Candidate not found
        election.setStarted(true);
        Mockito.when(candidateRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(ObjectBuilder.buildUser()));
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(election));
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(election));
        Exception entityNotFoundException3 = Assertions.assertThrows(EntityNotFoundException.class, () -> underTest.createVote(ObjectBuilder.buildVoteDTO()));
        Assertions.assertEquals(String.format("%s not found", Candidate.class.getSimpleName()), entityNotFoundException3.getMessage());

        //InvalidVoteException - User already voted
        election.setStarted(true);
        Mockito.when(repository.existsByUserId(Mockito.anyString()))
                .thenReturn(true);
        Mockito.when(candidateRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(buildCandidate()));
        Mockito.when(userRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(ObjectBuilder.buildUser()));
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(election));
        Mockito.when(electionRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(election));
        Exception entityNotFoundException4 = Assertions.assertThrows(InvalidVoteException.class, () -> underTest.createVote(ObjectBuilder.buildVoteDTO()));
        Assertions.assertEquals("Invalid vote. User already voted", entityNotFoundException4.getMessage());
    }
}
