package ru.otus.gameauth.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import ru.otus.gameauth.model.Game;
import ru.otus.gameauth.repository.GameRepository;
import ru.otus.gameauth.service.GameAuthorizationService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceTest {
    private static GameRepository gameRepository;
    private static JwtService jwtService;

    @BeforeAll
    public static void init() {
        gameRepository = mock(GameRepository.class);
        GameAuthorizationService gameAuthorizationService = new GameManager(gameRepository);
        jwtService = new JwtService(gameAuthorizationService);
    }

    @Test
    void createTokenTest() {
        when(gameRepository.findById("testGame"))
                .thenReturn(Game.builder()
                        .id(UUID.randomUUID().toString())
                        .gamers(List.of("testGamer"))
                        .build());
//        System.out.println("KEYS!!! " + jwtService.create("testGamer", "testGame"));
        assertTrue(StringUtils.isNotBlank(jwtService.create("testGamer", "testGame")));
    }
}
