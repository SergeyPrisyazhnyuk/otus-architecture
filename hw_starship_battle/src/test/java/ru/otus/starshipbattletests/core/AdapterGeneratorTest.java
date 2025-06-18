package ru.otus.starshipbattletests.core;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.FinishCommand;
import ru.otus.starshipbattle.command.impl.UObject;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.core.SourceCodeGenerator;
import ru.otus.starshipbattle.model.Movable;
import ru.otus.starshipbattle.model.impl.Vector;
import ru.otus.starshipbattle.scopes.InitCommand;

import java.util.Map;
import java.util.function.Function;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdapterGeneratorTest {
    @Mock
    private UObject uObject;


    @SneakyThrows
    @BeforeEach
    void setup() {
        new InitCommand().execute();
        Map<String, Function<Object[], Object>> scope = IoC.resolve("IoC.Scope.Create");
        Command command = IoC.resolve("IoC.Scope.Current.Set", scope);
        command.execute();

        SourceCodeGenerator sourceCodeGenerator = new SourceCodeGenerator();
        sourceCodeGenerator.generateAdapterClassFromInterface(Movable.class);
    }

    @SneakyThrows
    @AfterEach
    void clear() {
        Command command = IoC.resolve("IoC.Scope.Current.Clear");
        command.execute();
    }

    @SneakyThrows
    @Test
    void GenerateAdapterFinishTest() {

        ((Command) IoC.resolve("IoC.Register", "finish", (Function<Object[], Object>) (Object[] args) ->
                new FinishCommand(uObject))).execute();

        Vector position = new Vector(12, 5);
        Integer velocityMod = 5;

        when(uObject.getProperty("Position")).thenReturn(position);
        when(uObject.getProperty("Velocity")).thenReturn(velocityMod);

        Movable instanceOfClass = IoC.resolve("MovableAdapter", UObject.class, uObject);
        instanceOfClass.finish();

        verify(uObject, times(1)).getProperty("Position");
        verify(uObject, times(1)).getProperty("Velocity");
    }
}
