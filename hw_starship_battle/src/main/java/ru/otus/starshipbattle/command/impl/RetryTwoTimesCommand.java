package ru.otus.starshipbattle.command.impl;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.exception.RetryCommandTwoTimesExceededException;

import java.util.Collection;

public class RetryTwoTimesCommand implements Command{

    /**
     * 9 Реализовать стратегию обработки исключения - повторить два раза, потом записать в лог.
     * Указание: создать новую команду, точно такую же как в пункте 6.
     * Тип этой команды будет показывать, что Команду не удалось выполнить два раза
     */

    private static final int MAX_RETRIES = 2;
    private static int retries = 0;

    private final Command command;
    private final Collection<Command> commandCollection;

    public RetryTwoTimesCommand(Command command, Collection<Command> commandCollection) {
        this.command = command;
        this.commandCollection = commandCollection;
    }

    @Override
    public void execute() {

        if (retries < MAX_RETRIES) {
            try {
                command.execute();
            } catch (Exception e) {
                commandCollection.add(this);
            }
            retries++;
        }
        else {
            throw new RetryCommandTwoTimesExceededException();
        }


        command.execute();
    }
}
