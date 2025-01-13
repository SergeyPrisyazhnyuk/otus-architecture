package ru.otus.starshipbattletests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.starshipbattle.command.impl.MoveCommand;
import ru.otus.starshipbattle.model.Movable;
import ru.otus.starshipbattle.model.impl.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MoveCommandTest {

    @Mock
    private Movable movable;

    @Captor
    private ArgumentCaptor<Vector> getNewPosition;

    private MoveCommand moveCommand;
    private Vector startPosition;
    private Vector startVelocity;
    private Vector endPosition;


    @BeforeEach
    public void initMoveCommandTest() {
        moveCommand = new MoveCommand(movable);
        startPosition = new Vector(12, 5);
        startVelocity = new Vector(-7, 3);
        endPosition = new Vector(5, 8);
    }

    /*
    * Для объекта, находящегося в точке (12, 5) и движущегося со скоростью (-7, 3) движение меняет положение объекта на (5, 8)
    * */
    @Test
    public void whenAllDataForExecuteMoveExistsTest() {
        when(movable.getPosition()).thenReturn(startPosition);
        when(movable.getVelocity()).thenReturn(startVelocity);
        doNothing().when(movable).setPosition(getNewPosition.capture());

        moveCommand.execute();
        Vector newPosition = getNewPosition.getValue();

        assertEquals(endPosition, newPosition);
    }

    /*
    * Попытка сдвинуть объект, у которого невозможно прочитать положение в пространстве, приводит к ошибке
    * */
    @Test
    public void whenUnknownPositionForExecuteMoveThenExceptionTest() {
        when(movable.getPosition()).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> moveCommand.execute());
    }

    /*
     * Попытка сдвинуть объект, у которого невозможно прочитать значение мгновенной скорости, приводит к ошибке
     * */
    @Test
    public void whenUnknownVelocityForExecuteMoveThenExceptionTest() {
        when(movable.getPosition()).thenReturn(startPosition);
        when(movable.getVelocity()).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> moveCommand.execute());
    }

    /*
    * Попытка сдвинуть объект, у которого невозможно изменить положение в пространстве, приводит к ошибке
    * */
    @Test
    public void whenImmovableObjectForExecuteMoveThenExceptionTest() {
        when(movable.getPosition()).thenReturn(startPosition);
        when(movable.getVelocity()).thenReturn(startVelocity);
        doThrow(IllegalArgumentException.class).when(movable).setPosition(any());

        assertThrows(IllegalArgumentException.class, () -> moveCommand.execute());
    }

}
