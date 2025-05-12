package ru.otus.starshipbattle.model;

import ru.otus.starshipbattle.model.impl.Vector;

public interface ChangeVelocity {
    Vector getVelocity();
    void setVelocity(Vector newVelocity);
    int getDirection();
    int getDirectionsNumbers();
}
