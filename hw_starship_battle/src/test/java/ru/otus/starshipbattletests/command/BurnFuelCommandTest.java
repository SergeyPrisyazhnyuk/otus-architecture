package ru.otus.starshipbattletests.command;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.BurnFuelCommand;
import ru.otus.starshipbattle.model.ConsumeFuel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BurnFuelCommandTest {

    @Mock
    private ConsumeFuel consumeFuel;

    @Captor
    private ArgumentCaptor<Integer> interceptValueFuel;

    private Command command;

    @BeforeEach
    void initTest() {
        command = new BurnFuelCommand(consumeFuel);
    }

    @Test
    public void testBurnFuel() {
        when(consumeFuel.getRemaining()).thenReturn(10);
        when(consumeFuel.getConsumption()).thenReturn(5);
        doNothing().when(consumeFuel).setRemaining(interceptValueFuel.capture());

        command.execute();

        assertEquals(5, interceptValueFuel.getValue());
    }


}
