package ru.otus.starshipbattle.interpreter.command;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.UObject;

@RequiredArgsConstructor
public class EndMoveCommand implements Command {

    private final UObject object;

    @Override
    public void execute() {
        object.setProperty("velocity", 0);
    }
}