package ru.otus.starshipbattletests.handler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.CommandExceptionLog;
import ru.otus.starshipbattle.exception.handler.impl.CommandExceptionLogHandler;

import java.util.ArrayList;
import java.util.List;

public class CommandExceptionLogHandlerTest {

    @Test
    void testHandleException() {
        List<Command> commandList = new ArrayList<>();

        new CommandExceptionLogHandler(commandList).handle(new RuntimeException("CommandExceptionLogHandlerTest"), null).execute();

        assertEquals(1, commandList.size());
        assertEquals(CommandExceptionLog.class, commandList.get(0).getClass());
    }

}
