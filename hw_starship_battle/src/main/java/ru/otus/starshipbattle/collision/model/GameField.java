package ru.otus.starshipbattle.collision.model;

import java.util.Map;

public interface GameField {
    Map<String, Area> getAreas(Location location);

    void checkCollisions();
}
