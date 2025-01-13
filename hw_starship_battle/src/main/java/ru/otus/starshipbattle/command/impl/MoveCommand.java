package ru.otus.starshipbattle.command.impl;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.model.Movable;
import ru.otus.starshipbattle.model.impl.Vector;

public class MoveCommand implements Command {

    private final Movable movable;

    public MoveCommand(Movable movable) {
        this.movable = movable;
    }

    @Override
    public void execute() {
        movable.setPosition( new Vector(
                movable.getPosition().x() + movable.getVelocity().x(),
                movable.getPosition().y() + movable.getVelocity().y()
        ));
    }
}
