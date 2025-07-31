package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.state.impl.NormalState;

import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

@RequiredArgsConstructor
public class RunCommand implements Command {
    private final CommandExecutor commandExecutor;
    private final BlockingQueue<Command> queue;

    @Override
    public void execute() {
        Function<Object[], Object> func = args -> new NormalState(queue);
        ((Command) IoC.resolve("IoC.Register", "State", func)).execute();
        Function<Object[], Object> funcC = args -> commandExecutor;
        ((Command) IoC.resolve("IoC.Register", "CommandExecutor", funcC)).execute();
        commandExecutor.start();
    }

}
