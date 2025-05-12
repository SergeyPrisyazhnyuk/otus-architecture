package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;

import java.util.Collection;

@RequiredArgsConstructor
public class MacroCommand implements Command {

    private final Collection<Command> commands;

    @Override
    public void execute() {
        commands.forEach(Command::execute);
    }
}
