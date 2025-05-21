package ru.otus.starshipbattle.exception.handler.impl;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.ExceptionLogCommand;
import ru.otus.starshipbattle.command.impl.RetryCommand;
import ru.otus.starshipbattle.exception.handler.CommandExceptionHandler;

import java.util.Collection;

public class RetryBeforeLogExceptionHandler implements CommandExceptionHandler {

    /**
     * 8 С помощью Команд из пункта 4 и пункта 6 реализовать следующую обработку исключений:
     * при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог
     */

    private final Collection<Command> commandCollection;

    public RetryBeforeLogExceptionHandler(Collection<Command> commandCollection) {
        this.commandCollection = commandCollection;
    }

    @Override
    public Command handle(Exception e, Command c) {

        if (commandCollection.isEmpty()) {
            return () -> commandCollection.add(new RetryCommand(c));
        } else {
            return () -> commandCollection.add(new ExceptionLogCommand(e));
        }
    }
}
