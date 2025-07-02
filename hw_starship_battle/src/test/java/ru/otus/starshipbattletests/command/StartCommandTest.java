package ru.otus.starshipbattletests.command;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;
import ru.otus.starshipbattle.command.impl.CommandExecutorImpl;
import ru.otus.starshipbattle.command.impl.StartCommand;
import ru.otus.starshipbattle.exception.handler.CommandExceptionHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.*;

public class StartCommandTest {

    @Test
    void startTest() throws InterruptedException {
        BlockingQueue<Command> queue = new ArrayBlockingQueue<>(10);
        CommandExceptionHandler exceptionHandler = mock(CommandExceptionHandler.class);
        CommandExecutor commandExecutor = new CommandExecutorImpl(queue, exceptionHandler);

        Command mockCommand = mock(Command.class);
        queue.add(mockCommand);

        Command command = new StartCommand(commandExecutor);
        command.execute();
        sleep(1000);

        verify(mockCommand, times(1)).execute();
    }
}
