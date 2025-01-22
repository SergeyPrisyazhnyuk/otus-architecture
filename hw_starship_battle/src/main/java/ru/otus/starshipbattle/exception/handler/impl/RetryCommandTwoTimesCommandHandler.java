package ru.otus.starshipbattle.exception.handler.impl;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.RetryTwoTimesCommand;
import ru.otus.starshipbattle.exception.handler.CommandExceptionHandler;

import java.util.Collection;

public class RetryCommandTwoTimesCommandHandler implements CommandExceptionHandler {

    /**
     * 9 Реализовать стратегию обработки исключения - повторить два раза, потом записать в лог.
     * Указание: создать новую команду, точно такую же как в пункте 6.
     * Тип этой команды будет показывать, что Команду не удалось выполнить два раза
     */


    private final Collection<Command> commandCollection;

    public RetryCommandTwoTimesCommandHandler(Collection<Command> commandCollection) {
        this.commandCollection = commandCollection;
    }

    @Override
    public Command handle(Exception e, Command c) {
        return () -> commandCollection.add(new RetryTwoTimesCommand(c, commandCollection));
    }

}
