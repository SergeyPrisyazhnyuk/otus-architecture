package ru.otus.starshipbattle.command.impl;

import ru.otus.starshipbattle.command.Command;

public class RetryCommand implements Command{
    /**
     * 6 Реализовать Команду, которая повторяет Команду, выбросившую исключение
     */

    private final Command command;

    public RetryCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        command.execute();
    }
}
