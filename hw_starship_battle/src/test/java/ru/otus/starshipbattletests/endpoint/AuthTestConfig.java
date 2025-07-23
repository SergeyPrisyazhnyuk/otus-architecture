package ru.otus.starshipbattletests.endpoint;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.otus.gameauth.impl.GameManager;
import ru.otus.gameauth.impl.JwtService;
import ru.otus.gameauth.repository.GameRepository;
import ru.otus.gameauth.service.GameAuthorizationService;
import ru.otus.gameauth.service.GameAuthorizerService;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class AuthTestConfig {
    @Bean
    public GameAuthorizerService register() {
        GameRepository gameRepository = mock(GameRepository.class);
        GameAuthorizationService gameAuthorizationService = new GameManager(gameRepository);
        return new JwtService(gameAuthorizationService);
    }
}
