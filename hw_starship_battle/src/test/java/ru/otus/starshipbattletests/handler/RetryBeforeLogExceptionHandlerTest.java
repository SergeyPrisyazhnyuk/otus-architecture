package ru.otus.starshipbattletests.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.ExceptionLogCommand;
import ru.otus.starshipbattle.command.impl.RetryCommand;
import ru.otus.starshipbattle.exception.handler.impl.RetryBeforeLogExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class RetryBeforeLogExceptionHandlerTest {

    @Test
    void testRetryCommand() {
        List<Command> commandList = new ArrayList<>();
        Command command = mock(Command.class);
        new RetryBeforeLogExceptionHandler(commandList).handle(new RuntimeException("RetryBeforeLogExceptionHandlerTest"),
                command).execute();

        assertEquals(RetryCommand.class, commandList.get(0).getClass());
    }

    @Test
    void testCommandExceptionLog() {
        List<Command> commandList = new ArrayList<>();
        Command command = mock(Command.class);
        commandList.add(command);
        commandList.add(command);

        new RetryBeforeLogExceptionHandler(commandList).handle(new RuntimeException("RetryBeforeLogExceptionHandlerTest"),
                command).execute();

        assertEquals(ExceptionLogCommand.class, commandList.get(2).getClass());
    }

}
