package ru.otus.starshipbattletests.collision.command;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.collision.command.ChangeAreaCommand;
import ru.otus.starshipbattle.collision.model.Area;
import ru.otus.starshipbattle.collision.model.GameField;
import ru.otus.starshipbattle.collision.model.GameObject;
import ru.otus.starshipbattle.collision.model.Location;
import ru.otus.starshipbattle.collision.model.impl.GameAreaImpl;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.scopes.InitCommand;

import java.util.Map;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
public class ChangeAreaCommandTest {

    @BeforeEach
    void initialize() {
        try {
            new InitCommand().execute();
        } catch (RuntimeException e) {
            log.info("Scope already exists");
        }
    }

    @Test
    void removeFromOldAddToNewWhenAreaChangedTest() {
        Location previousLocation = mock(Location.class);
        Location currentLocation = mock(Location.class);

        GameObject obj = mock(GameObject.class);
        when(obj.getId()).thenReturn("100");
        when(obj.getLocation()).thenReturn(currentLocation);

        GameAreaImpl area1 = mock(GameAreaImpl.class);
        GameAreaImpl area2 = mock(GameAreaImpl.class);
        GameAreaImpl area3 = mock(GameAreaImpl.class);
        when(area1.getId()).thenReturn("1");
        when(area2.getId()).thenReturn("2");
        when(area3.getId()).thenReturn("3");

        Map<String, Area> previousAreas = Map.of(area1.getId(), area1, area2.getId(), area2);
        Map<String, Area> currentAreas = Map.of(area2.getId(), area2, area3.getId(), area3);

        GameField gameField = mock(GameField.class);
        when(gameField.getAreas(eq(previousLocation))).thenReturn(previousAreas);
        when(gameField.getAreas(eq(currentLocation))).thenReturn(currentAreas);
        ((Command) IoC.resolve("IoC.Register", "GameField", (Function<Object[], Object>) args -> gameField)).execute();

        ChangeAreaCommand command = new ChangeAreaCommand(obj, previousLocation);
        command.execute();
        verify(area1, times(1)).removeObject(eq(obj));
        verify(area3, times(1)).addObject(eq(obj));
        verify(area1, times(0)).addObject(any());
        verify(area3, times(0)).removeObject(any());
        verify(area2, times(0)).addObject(any());
        verify(area2, times(0)).removeObject(any());
    }

    @Test
    void addToNewAreasIfPreviousIsNullTest() {
        Location currentLocation = mock(Location.class);

        GameObject obj = mock(GameObject.class);
        when(obj.getId()).thenReturn("100");
        when(obj.getLocation()).thenReturn(currentLocation);

        GameAreaImpl area1 = mock(GameAreaImpl.class);
        when(area1.getId()).thenReturn("1");

        Map<String, Area> currentAreas = Map.of(area1.getId(), area1);

        GameField gameMap = mock(GameField.class);
        when(gameMap.getAreas(eq(currentLocation))).thenReturn(currentAreas);
        ((Command) IoC.resolve("IoC.Register", "GameField", (Function<Object[], Object>) args -> gameMap)).execute();

        ChangeAreaCommand command = new ChangeAreaCommand(obj, null);
        command.execute();
        verify(area1, times(1)).addObject(eq(obj));
        verify(area1, times(0)).removeObject(any());
    }

}
