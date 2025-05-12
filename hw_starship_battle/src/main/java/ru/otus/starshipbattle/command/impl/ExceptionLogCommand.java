package ru.otus.starshipbattle.command.impl;

import lombok.extern.slf4j.Slf4j;
import ru.otus.starshipbattle.command.Command;

@Slf4j
public class ExceptionLogCommand implements Command {
    /**
    * 4 Реализовать Команду, которая записывает информацию о выброшенном исключении в лог
    **/

    private final Exception exception;

    public ExceptionLogCommand(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void execute() {
        log.error("Command execute exception", exception);
    }
}
