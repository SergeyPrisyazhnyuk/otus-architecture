package ru.otus.starshipbattletests.handler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.RetryTwoTimesCommand;
import ru.otus.starshipbattle.exception.handler.impl.RetryCommandTwoTimesCommandHandler;

import java.util.ArrayList;
import java.util.List;

public class RetryCommandTwoTimesCommandHandlerTest {

    @Test
    void testRetryCommandTwoTimesCommandHandler() {
        List<Command> commandList = new ArrayList<>();

        Command command = () -> {};
        new RetryCommandTwoTimesCommandHandler(commandList).handle(new RuntimeException("RetryCommandTwoTimesCommandHandlerTest"), command).execute();

        assertEquals(1, commandList.size());
        Command retryCommand = commandList.get(0);
        assertEquals(RetryTwoTimesCommand.class, retryCommand.getClass());
    }
}
