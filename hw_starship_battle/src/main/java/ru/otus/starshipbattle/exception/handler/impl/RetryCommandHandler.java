package ru.otus.starshipbattle.exception.handler.impl;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.RetryCommand;
import ru.otus.starshipbattle.exception.handler.CommandExceptionHandler;

import java.util.Collection;

public class RetryCommandHandler implements CommandExceptionHandler {
    /**
     * 7 Реализовать обработчик исключения, который ставит в очередь Команду - повторитель команды, выбросившей исключение
     **/

    private final Collection<Command> commandCollection;

    public RetryCommandHandler(Collection<Command> commandCollection) {
        this.commandCollection = commandCollection;
    }

    @Override
    public Command handle(Exception e, Command c) {
        return () -> commandCollection.add(new RetryCommand(c));
    }
}
