package ru.otus.starshipbattle.interpreter.command;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.UObject;

@RequiredArgsConstructor
public class BeginMoveCommand implements Command {

    private final UObject object;
    private final double initialVelocity;

    @Override
    public void execute() {
        object.setProperty("initialVelocity", initialVelocity);
    }
}