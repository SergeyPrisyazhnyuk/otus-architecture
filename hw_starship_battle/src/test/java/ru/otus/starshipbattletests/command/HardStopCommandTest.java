package ru.otus.starshipbattletests.command;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;
import ru.otus.starshipbattle.command.impl.CommandExecutorImpl;
import ru.otus.starshipbattle.command.impl.HardStopCommand;
import ru.otus.starshipbattle.command.impl.StartCommand;
import ru.otus.starshipbattle.exception.handler.CommandExceptionHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.*;

public class HardStopCommandTest {

    @Test
    void hardStopCommandTest() throws InterruptedException {
        BlockingQueue<Command> queue = new ArrayBlockingQueue<>(10);
        CommandExceptionHandler exceptionHandler = mock(CommandExceptionHandler.class);
        CommandExecutor commandExecutor = new CommandExecutorImpl(queue, exceptionHandler);

        Command mockCommand1 = mock(Command.class);
        Command stopCommand = new HardStopCommand(commandExecutor);
        Command mockCommand2 = mock(Command.class);
        Command mockCommand3 = mock(Command.class);
        queue.add(mockCommand1);
        queue.add(stopCommand);
        queue.add(mockCommand2);
        queue.add(mockCommand3);

        Command command = new StartCommand(commandExecutor);
        command.execute();

        sleep(1000);

        verify(mockCommand1, times(1)).execute();
        verify(mockCommand2, times(0)).execute();
        verify(mockCommand3, times(0)).execute();
    }
}
