package ru.otus.starshipbattle.collision.model;

import java.util.Set;

public interface Area {
    String getId();

    void addObject(GameObject object);

    void removeObject(GameObject object);

    Set<GameObject> getObjects();

    void checkCollisions();

    boolean checkLocationIntersection(Location location);
}
