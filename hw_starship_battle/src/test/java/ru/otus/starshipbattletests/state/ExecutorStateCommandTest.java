package ru.otus.starshipbattletests.state;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;
import ru.otus.starshipbattle.command.impl.HardStopCommand;
import ru.otus.starshipbattle.command.impl.MoveToCommand;
import ru.otus.starshipbattle.command.impl.RunCommand;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.scopes.InitCommand;
import ru.otus.starshipbattle.state.CommandExecutorState;

import java.util.concurrent.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@Slf4j
public class ExecutorStateCommandTest {
    @BeforeEach
    void init() {

        try {
            new InitCommand().execute();
        } catch (RuntimeException e) {
            log.info("Scope already exists");
        }
        Function<Object[], Object> func = args -> Executors.newFixedThreadPool(5);
        ((Command) IoC.resolve("IoC.Register", "ExecutorService", func)).execute();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Function<Object[], Object> funcL = args -> countDownLatch;
        ((Command) IoC.resolve("IoC.Register", "Latch", funcL)).execute();
    }

    @Test
    void stateMachineStopTest() throws InterruptedException {
        BlockingQueue<Command> queue = new ArrayBlockingQueue<>(10);

        CommandExecutor commandExecutor = new CommandExecutorState();

        Command moveTo = new HardStopCommand(commandExecutor);
        queue.add(moveTo);

        Command mock1 = mock(Command.class);
        queue.add(mock1);

        Command mock2 = mock(Command.class);
        queue.add(mock2);

        Command mock3 = mock(Command.class);
        queue.add(mock3);

        new RunCommand(commandExecutor, queue).execute();

        CountDownLatch countDownLatch = IoC.resolve("Latch");
        countDownLatch.await(3, TimeUnit.SECONDS);
        assertEquals(2, countDownLatch.getCount());
    }

    @Test
    void stateMachineMoveToTest() throws InterruptedException {
        BlockingQueue<Command> queue = new ArrayBlockingQueue<>(10);
        BlockingQueue<Command> newQueue = new ArrayBlockingQueue<>(10, true);

        Command moveTo = new MoveToCommand(queue, newQueue);
        queue.add(moveTo);

        Command mock1 = mock(Command.class);
        queue.add(mock1);

        Command mock2 = mock(Command.class);
        queue.add(mock2);

        Command mock3 = mock(Command.class);
        queue.add(mock3);

        CommandExecutor commandExecutor = new CommandExecutorState();

        new RunCommand(commandExecutor, queue).execute();
        CountDownLatch countDownLatch = IoC.resolve("Latch");
        countDownLatch.await(3, TimeUnit.SECONDS);
        assertEquals(0, countDownLatch.getCount());
    }
}
