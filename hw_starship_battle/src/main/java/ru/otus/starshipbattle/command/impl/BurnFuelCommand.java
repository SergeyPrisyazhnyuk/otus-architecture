package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.model.ConsumeFuel;

@RequiredArgsConstructor
public class BurnFuelCommand implements Command {

    private final ConsumeFuel consumeFuel;

    @Override
    public void execute() {
        consumeFuel.setRemaining(consumeFuel.getRemaining() - consumeFuel.getConsumption());
    }
}
