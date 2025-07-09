package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;

@RequiredArgsConstructor
public class HardStopCommand implements Command {
    private final CommandExecutor commandExecutor;

    @Override
    public void execute() {
        commandExecutor.stop();
    }
}
