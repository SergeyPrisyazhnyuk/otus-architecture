package ru.otus.starshipbattletests.interpreter.command;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import ru.otus.starshipbattle.command.impl.UObject;
import ru.otus.starshipbattle.interpreter.exception.InterpretException;
import ru.otus.starshipbattle.interpreter.interpreter.CommandInterpreter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Slf4j
class CommandInterpreterTest {

    private final String gameId1 = "game1";
    private CommandInterpreter interpreter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        interpreter = new CommandInterpreter(gameId1);
    }

    @AfterEach
    void cleanUp() {
        interpreter = null;
    }

    @Test
    void throwIfNotCorrectOrderTest() {
        UObject incorrectOrder = mock(UObject.class);
        when(incorrectOrder.getProperty(eq("action"))).thenReturn("incorrectAction");

        assertThatThrownBy(() -> interpreter.interpret(incorrectOrder))
                .isInstanceOf(InterpretException.class)
                .hasMessageContaining("Action not found");
    }
}