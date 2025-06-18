package ru.otus.starshipbattle.core;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.impl.UObject;
import ru.otus.starshipbattle.model.Movable;
import ru.otus.starshipbattle.model.impl.Vector;

@RequiredArgsConstructor
public class MovableAdapter implements Movable {
    protected final UObject o;

    @Override
    public Vector getPosition() {
        return (Vector) o.getProperty("Position");
    }

    @Override
    public void setPosition(Vector newValue) {
        o.setProperty("Position", newValue);
    }

    @Override
    public Vector getVelocity() {
        int d = (int) o.getProperty("Direction");
        int n = (int) o.getProperty("DirectionsNumber");
        double v = (double) o.getProperty("Velocity");
        return new Vector(
                (int) v * (int) Math.cos((double) (d / 360 * n)),
                (int) v * (int) Math.sin((double) (d / 360 * n)));
    }

    @Override
    public void finish() throws Exception {}
}
