package ru.otus.starshipbattle.interpreter.command;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.UObject;

@RequiredArgsConstructor
public class FireCommand implements Command {

    private final UObject object;
    private final int fireDirection;

    @Override
    public void execute() {
        object.setProperty("fireDirection", fireDirection);
    }
}
