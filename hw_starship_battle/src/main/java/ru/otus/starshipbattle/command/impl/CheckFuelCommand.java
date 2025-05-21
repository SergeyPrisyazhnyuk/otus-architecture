package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.exception.CommandException;
import ru.otus.starshipbattle.model.ConsumeFuel;

@RequiredArgsConstructor
public class CheckFuelCommand implements Command {

    private final ConsumeFuel consumeFuel;

    @Override
    public void execute() {
        if (consumeFuel.getRemaining() < consumeFuel.getConsumption()) {
            throw new CommandException("Low fuel!");
        }
    }
}
