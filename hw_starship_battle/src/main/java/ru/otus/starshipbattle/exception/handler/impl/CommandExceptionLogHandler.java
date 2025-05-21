package ru.otus.starshipbattle.exception.handler.impl;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.ExceptionLogCommand;
import ru.otus.starshipbattle.exception.handler.CommandExceptionHandler;

import java.util.Collection;

public class CommandExceptionLogHandler implements CommandExceptionHandler {
    /**
     * 5 Реализовать обработчик исключения, который ставит Команду, пишущую в лог в очередь Команд
     **/

    private final Collection<Command> commandCollection;

    public CommandExceptionLogHandler(Collection<Command> commandCollection) {
        this.commandCollection = commandCollection;
    }

    @Override
    public Command handle(Exception e, Command c) {
        return () -> commandCollection.add(new ExceptionLogCommand(e));
    }
}
