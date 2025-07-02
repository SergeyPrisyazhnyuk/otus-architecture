package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;

import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class SoftStopCommand implements Command {
    private final CommandExecutor commandExecutor;
    private final BlockingQueue<Command> commands;

    @Override
    public void execute() {
        if (!commands.isEmpty()) {
            commands.add(new SoftStopCommand(commandExecutor, commands));
        } else {
            commandExecutor.stop();
        }
    }
}
