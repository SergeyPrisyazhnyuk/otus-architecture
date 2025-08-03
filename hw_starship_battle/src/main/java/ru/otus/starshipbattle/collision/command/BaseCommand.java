package ru.otus.starshipbattle.collision.command;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.exception.CommandException;

import java.util.List;

@RequiredArgsConstructor
public class BaseCommand implements Command {
    private final List<Command> commands;

    @Override
    public void execute() {
        try {
            commands.forEach(Command::execute);
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
    }
}
