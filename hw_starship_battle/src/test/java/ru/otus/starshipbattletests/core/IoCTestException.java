package ru.otus.starshipbattletests.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.scopes.InitCommand;

import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IoCTestException {

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
    void IocShouldThrowArgumentExceptionIfDependencyIsNotFound() {
        assertThrows(RuntimeException.class, () -> IoC.resolve("IoC.Register"));
    }
}
