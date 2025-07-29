package ru.otus.gameauth.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.gameauth.model.Game;
import ru.otus.gameauth.repository.GameRepository;
import ru.otus.gameauth.service.GameAuthorizationService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GameManager implements GameAuthorizationService {

    private final GameRepository gameRepository;
    private static final List<String> GAMERS_ROLES = List.of("GAMER");

    @Override
    public List<String> getGamersRoles(String userId, String gameId) {
        Game game = gameRepository.findById(gameId);
        return game.getGamers().contains(userId) ? new ArrayList<>(GAMERS_ROLES) : List.of();
    }

    public String createGame(List<String> gamers) {
        Game game = Game.builder()
                .id(UUID.randomUUID().toString())
                .gamers(gamers)
                .build();
        return gameRepository.save(game).getId();
    }
}
