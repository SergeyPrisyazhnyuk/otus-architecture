package ru.otus.gameauth.repository;

import ru.otus.gameauth.model.Game;

public interface GameRepository {
    Game save(Game game);
    Game findById(String gameId);
}
