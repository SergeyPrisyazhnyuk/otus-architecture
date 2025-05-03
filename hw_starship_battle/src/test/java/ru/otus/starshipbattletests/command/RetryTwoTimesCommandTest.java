package ru.otus.starshipbattletests.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.RetryTwoTimesCommand;
import ru.otus.starshipbattle.exception.handler.impl.RetryCommandTwoTimesCommandHandler;

import java.util.ArrayList;
import java.util.List;

public class RetryTwoTimesCommandTest {

    @Test
    void testRetryTwoTimesCommand() {
        List<Command> commandList = new ArrayList<>();

        Command command = () -> {};
        new RetryCommandTwoTimesCommandHandler(commandList).handle(new RuntimeException("RetryTwoTimesCommandTest"), command).execute();

        assertEquals(1, commandList.size());
        Command commandRetry = commandList.get(0);
        assertEquals(RetryTwoTimesCommand.class, commandRetry.getClass());
    }

}
