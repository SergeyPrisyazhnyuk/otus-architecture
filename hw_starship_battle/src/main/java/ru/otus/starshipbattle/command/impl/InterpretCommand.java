package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;

@RequiredArgsConstructor
public class InterpretCommand implements Command {
    private final String gameId;
    private final String objectId;
    private final String commandId;
    private final Object[] args;
    @Override
    public void execute() {
        Object iocScope = IoC.resolve("IoC.Scope.Get", gameId);
        Command set = IoC.resolve("IoC.Scope.Current.Set", iocScope);
        set.execute();
        Object gameObject = IoC.resolve(objectId);
        Command command = IoC.resolve(commandId, gameObject, args[0]);
        IoC.resolve("CommandsQueue.add", command);
    }
}
