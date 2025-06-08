package ru.otus.starshipbattle.core;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateIocResolveDependencyStrategyCommand implements Command {
    private final BiFunction<String, Object[], Object> updater;

    @Override
    public void execute() {
        IoC.setStrategy(updater);
    }
}
