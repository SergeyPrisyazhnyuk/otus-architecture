package ru.otus.starshipbattletests.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.CheckFuelCommand;
import ru.otus.starshipbattle.exception.CommandException;
import ru.otus.starshipbattle.model.ConsumeFuel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckFuelCommandTest {
    @Mock
    private ConsumeFuel consumeFuel;

    private Command command;

    @BeforeEach
    void initTest() {
        command = new CheckFuelCommand(consumeFuel);
    }

    @Test
    public void testCheckFuelNotEnough() {
        when(consumeFuel.getRemaining()).thenReturn(10);
        when(consumeFuel.getConsumption()).thenReturn(20);

        assertThrows(CommandException.class, () -> command.execute());
    }

    @Test
    public void testCheckFuelEnough() {
        when(consumeFuel.getRemaining()).thenReturn(10);
        when(consumeFuel.getConsumption()).thenReturn(5);

        assertDoesNotThrow(command::execute);
    }
}
