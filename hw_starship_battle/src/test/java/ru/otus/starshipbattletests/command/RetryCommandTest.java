package ru.otus.starshipbattletests.command;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.RetryCommand;

import static org.mockito.Mockito.*;

public class RetryCommandTest {

    @Test
    public void retryCommandTest() {
        Command retryCommand = mock(Command.class);
        doNothing().when(retryCommand).execute();

        new RetryCommand(retryCommand).execute();

        verify(retryCommand, times(1)).execute();
    }
}
