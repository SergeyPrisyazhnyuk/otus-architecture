package ru.otus.starshipbattletests.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.BurnFuelCommand;
import ru.otus.starshipbattle.command.impl.CheckFuelCommand;
import ru.otus.starshipbattle.command.impl.MacroCommand;
import ru.otus.starshipbattle.command.impl.MoveCommand;
import ru.otus.starshipbattle.exception.CommandException;
import ru.otus.starshipbattle.model.ConsumeFuel;
import ru.otus.starshipbattle.model.Movable;
import ru.otus.starshipbattle.model.impl.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MacroCommandTest {
    @Mock
    private Movable movable;
    @Mock
    private ConsumeFuel consumeFuel;

    private Command macroCommand;

    @BeforeEach
    void initTest() {
        macroCommand = new MacroCommand(List.of(
                new CheckFuelCommand(consumeFuel), new MoveCommand(movable), new BurnFuelCommand(consumeFuel)));

        when(movable.getPosition()).thenReturn(new Vector(0, 0));
        when(movable.getVelocity()).thenReturn(new Vector(1, 0));
        doNothing().when(movable).setPosition(any());

        when(consumeFuel.getConsumption()).thenReturn(1);
        doNothing().when(consumeFuel).setRemaining(anyInt());
    }

    @Test
    public void testMacroCommandTestFuelEnough() {
        when(consumeFuel.getRemaining()).thenReturn(10);
        macroCommand.execute();
    }

    @Test
    public void testMacroCommandTestFuelNotEnought() {
        when(consumeFuel.getRemaining()).thenReturn(0);

        assertThrows(CommandException.class, macroCommand::execute);
    }
}
