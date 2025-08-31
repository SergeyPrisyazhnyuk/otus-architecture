package ru.otus.starshipbattletests.interpreter.interpreter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.UObject;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.core.SourceCodeGenerator;
import ru.otus.starshipbattle.interpreter.command.InterpretCommand;
import ru.otus.starshipbattle.interpreter.interpreter.Interpreter;
import ru.otus.starshipbattle.model.Movable;
import ru.otus.starshipbattle.scopes.InitCommand;

import java.util.function.Function;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InterpretCommandTest {

    private String gameId;
    @Mock
    private Interpreter interpreter;

    @BeforeEach
    void initialize() {

        try {
            new InitCommand().execute();
        } catch (RuntimeException e) {
            log.info("Scope already exists");
        }

        SourceCodeGenerator sourceCodeGenerator = new SourceCodeGenerator();
        sourceCodeGenerator.generateAdapterClassFromInterface(Movable.class);

        gameId = "game";
        ((Command) IoC.resolve("IoC.Register", "Interpreter.Command.Execute", (Function<Object[], Object>) args -> {
            if (gameId.equals(args[0])) {
                return interpreter.interpret((UObject) args[1]);
            } else {
                return null;
            }
        })).execute();

    }

    @Test
    public void callInterpreterAndAddCommandToQueueTest() {
        UObject order = mock(UObject.class);
        Command command = mock(Command.class);
        when(interpreter.interpret(refEq(order))).thenReturn(command);
        new InterpretCommand(gameId, order).execute();
        verify(command, times(1)).execute();
    }

}
