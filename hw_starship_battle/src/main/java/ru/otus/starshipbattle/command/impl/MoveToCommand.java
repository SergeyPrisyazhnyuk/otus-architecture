package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.state.State;
import ru.otus.starshipbattle.state.impl.MoveToState;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

@RequiredArgsConstructor
public class MoveToCommand implements Command {
    private final BlockingQueue<Command> queue;
    private final BlockingQueue<Command> newQueue;

    @Override
    public void execute() {
        CommandExecutor commandExecutor = IoC.resolve("CommandExecutor");
        ExecutorService executorService = IoC.resolve("ExecutorService");
        executorService.execute(() -> commandExecutor.updateBehaviour(() -> {
            Function<Object[], Object> func = args -> new MoveToState(queue, newQueue);
            ((Command) IoC.resolve("IoC.Register", "State", func)).execute();
            State state = IoC.resolve("State");
            while (state != null) {
                State finalState = state;
                Function<Object[], Object> funcNext = args -> finalState.next();
                ((Command) IoC.resolve("IoC.Register", "State", funcNext)).execute();
                state = IoC.resolve("State");
            }
        }));
    }
}
