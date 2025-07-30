package ru.otus.starshipbattle.state;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;
import ru.otus.starshipbattle.core.IoC;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;

public class CommandExecutorState implements CommandExecutor {
    private Future<?> future;
    private volatile boolean stopped = false;
    private Runnable behaviour = () -> {
        State state = IoC.resolve("State");
        while (state != null && !stopped) {
            State finalState = state;
            Function<Object[], Object> func = (args) -> finalState.next();
            ((Command) IoC.resolve("IoC.Register", "State", func)).execute();
            state = IoC.resolve("State");
            System.out.println();
        }
    };

    @Override
    public void start() {
        ExecutorService executorService = IoC.resolve("ExecutorService");
        future = executorService.submit(behaviour);
    }

    @Override
    public void stop() {
        ExecutorService executorService = IoC.resolve("ExecutorService");
        updateBehaviour(() -> {});
    }

    @Override
    public void updateBehaviour(Runnable action) {
        stopped = true;
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        stopped = false;
        behaviour = action;
        ExecutorService executorService = IoC.resolve("ExecutorService");
        future = executorService.submit(behaviour);
    }
}
