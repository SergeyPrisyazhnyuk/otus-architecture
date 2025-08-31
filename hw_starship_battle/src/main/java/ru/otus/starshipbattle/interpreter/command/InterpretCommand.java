package ru.otus.starshipbattle.interpreter.command;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.UObject;
import ru.otus.starshipbattle.core.IoC;

@RequiredArgsConstructor
public class InterpretCommand implements Command {

    private final String gameId;
    private final UObject order;

    @Override
    public void execute() {
        Command command = IoC.resolve("Interpreter.Command.Execute", gameId, order);
        command.execute();
    }

}