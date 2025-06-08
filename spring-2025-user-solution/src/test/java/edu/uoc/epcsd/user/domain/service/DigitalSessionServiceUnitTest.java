package edu.uoc.epcsd.user.domain.service;

import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.User;
import edu.uoc.epcsd.user.domain.exception.UserNotFoundException;
import edu.uoc.epcsd.user.domain.repository.DigitalSessionRepository;
import edu.uoc.epcsd.user.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


public class DigitalSessionServiceUnitTest {

    private DigitalSessionRepository digitalSessionRepository;
    private UserRepository userRepository;
    private DigitalSessionService digitalSessionService;

    @BeforeEach
    void setUp() {
        digitalSessionRepository = Mockito.mock(DigitalSessionRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        digitalSessionService = new DigitalSessionServiceImpl(digitalSessionRepository, userRepository);
    }

    @Test
    void findDigitalSessionByUser() {

        Long userId = 1L;
        List<DigitalSession> expectedSessions = List.of(
                DigitalSession.builder().id(1L).userId(userId).build(),
                DigitalSession.builder().id(2L).userId(userId).build()
        );

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(User.builder().id(userId).build()));
        when(digitalSessionRepository.findDigitalSessionByUser(userId)).thenReturn(expectedSessions);

        List<DigitalSession> actualSessions = digitalSessionService.findDigitalSessionByUser(userId);

        assertThat(actualSessions).isEqualTo(expectedSessions);

    }

    @Test
    void findDigitalSessionByUserNotFound() {

        Long userId = 999L;

        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            digitalSessionService.findDigitalSessionByUser(userId);
        });

    }



}
