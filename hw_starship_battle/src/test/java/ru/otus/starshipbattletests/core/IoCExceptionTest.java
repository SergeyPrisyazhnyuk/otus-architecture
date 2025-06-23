package ru.otus.starshipbattletests.core;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.core.IoC;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IoCExceptionTest {

    @Test
    void IocShouldThrowArgumentExceptionIfDependencyIsNotFound() {
        assertThrows(RuntimeException.class, () -> IoC.resolve("IoC.Register"));
    }
}
