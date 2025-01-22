package ru.otus.starshipbattletests.handler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.RetryCommand;
import ru.otus.starshipbattle.exception.handler.impl.RetryCommandHandler;

import java.util.ArrayList;
import java.util.List;

public class RetryCommandHandlerTest {

    @Test
    void testRetryCommandHandler() {
        List<Command> commandList = new ArrayList<>();

        Command command = () -> {};
        new RetryCommandHandler(commandList).handle(new RuntimeException("RetryCommandHandler"), command).execute();

        assertEquals(1, commandList.size());
        assertEquals(RetryCommand.class, commandList.get(0).getClass());
    }
}
