package ru.otus.starshipbattletests.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.ChangeVelocityCommand;
import ru.otus.starshipbattle.model.ChangeVelocity;
import ru.otus.starshipbattle.model.impl.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChangeVelocityCommandTest {
    @Mock
    private ChangeVelocity changeVelocity;

    @Captor
    ArgumentCaptor<Vector> interceptNewVelocity;

    private Command command;

    @BeforeEach
    void setUpTest() {
        command = new ChangeVelocityCommand(changeVelocity);
    }

    @Test
    void testChangeVelocityCommand() {
        when(changeVelocity.getVelocity()).thenReturn(new Vector(100, 0));
        when(changeVelocity.getDirection()).thenReturn(1);
        when(changeVelocity.getDirectionsNumbers()).thenReturn(4);
        doNothing().when(changeVelocity).setVelocity(interceptNewVelocity.capture());

        command.execute();

        Vector expectedVelocity = new Vector(0, 100);
        Vector actualVelocity = interceptNewVelocity.getValue();

        assertEquals(expectedVelocity, actualVelocity);
    }

}
