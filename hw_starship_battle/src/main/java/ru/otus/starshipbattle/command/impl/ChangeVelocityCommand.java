package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.model.ChangeVelocity;
import ru.otus.starshipbattle.model.impl.Vector;

@RequiredArgsConstructor
public class ChangeVelocityCommand implements Command {

    private final ChangeVelocity changeVelocity;

    @Override
    public void execute() {
        Vector currentVelocity = changeVelocity.getVelocity();
        int angleDegrees = (360 * changeVelocity.getDirection() /
                changeVelocity.getDirectionsNumbers());
        double angle = Math.toRadians(angleDegrees);
        double angleVelocity = Math.sqrt(Math.pow(currentVelocity.x(), 2) +
                Math.pow(currentVelocity.y(), 2));

        Vector newVelocity = new Vector(
                (int) (angleVelocity * Math.cos(angle)),
                (int) (angleVelocity * Math.sin(angle))
        );

        changeVelocity.setVelocity(newVelocity);
    }
}
