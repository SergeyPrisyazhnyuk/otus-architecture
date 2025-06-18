package ru.otus.starshipbattletests.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.scopes.InitCommand;

import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IoCTest {

    @BeforeEach
    void initialize() {
        new InitCommand().execute();
        Map<String, Function<Object[], Object>> scope = IoC.resolve("IoC.Scope.Create");
        Command command = IoC.resolve("IoC.Scope.Current.Set", scope);
        command.execute();
    }

    @AfterEach
    void clear() {
        Command command = IoC.resolve("IoC.Scope.Current.Clear");
        command.execute();
    }

    @Test
    void IocShouldUpdateResolveDependencyStrategyTest() {
        Function<Object[], Object> function = args -> (Integer) args[0] + (Integer) args[1] + (Integer) args[2];
        Command command = IoC.resolve("IoC.Register", "someDependency", function);
        command.execute();

        assertEquals(6, (Integer) IoC.resolve("someDependency", 1, 2, 3));
    }
}
