package ru.otus.starshipbattle.model;

import ru.otus.starshipbattle.model.impl.Vector;

public interface Movable {
    Vector getPosition();
    void setPosition(Vector newVector);
    Vector getVelocity();
}
