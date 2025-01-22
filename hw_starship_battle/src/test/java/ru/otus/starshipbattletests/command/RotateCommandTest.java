package ru.otus.starshipbattletests.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.starshipbattle.command.impl.RotateCommand;
import ru.otus.starshipbattle.model.Rotable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RotateCommandTest {

    @Mock
    private Rotable rotable;

    @Captor
    private ArgumentCaptor<Integer> getNewPosition;

    private RotateCommand rotateCommand;
    private int startDirection;
    private int angularVelocity;
    private int directionNumbers;


    @BeforeEach
    public void initRotateCommandTest() {
        rotateCommand = new RotateCommand(rotable);

        startDirection = 0;
        angularVelocity = 360;
        directionNumbers = 360;
    }

    /*
    * Поворот объекта вокруг оси
    * */
    @Test
    public void whenRotateAroundAxisThenOk() {
        when(rotable.getDirection()).thenReturn(startDirection);
        when(rotable.getAngularVelocity()).thenReturn(angularVelocity);
        when(rotable.getDirectionsNumber()).thenReturn(directionNumbers);

        doNothing().when(rotable).setDirection(getNewPosition.capture());

        rotateCommand.execute();

        assertEquals(0, getNewPosition.getValue());
    }


}
