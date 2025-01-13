package ru.otus.starshipbattle.command.impl;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.model.Rotable;


public class RotateCommand implements Command {

    private final Rotable rotable;

    public RotateCommand(Rotable rotable) {
        this.rotable = rotable;
    }

    @Override
    public void execute() {
        rotable.setDirection(
                (rotable.getDirection() +
                        rotable.getAngularVelocity())
                % rotable.getDirectionsNumber()
        );
    }
}
