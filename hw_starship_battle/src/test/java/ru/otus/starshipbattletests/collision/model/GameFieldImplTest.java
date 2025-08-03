package ru.otus.starshipbattletests.collision.model;

import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.collision.model.Area;
import ru.otus.starshipbattle.collision.model.GameField;
import ru.otus.starshipbattle.collision.model.Location;
import ru.otus.starshipbattle.collision.model.impl.GameAreaImpl;
import ru.otus.starshipbattle.collision.model.impl.GameFieldImpl;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class GameFieldImplTest {

    @Test
    void checkCollisionsForAllGameFields() {
        Area area1 = mock(GameAreaImpl.class);
        Area area2 = mock(GameAreaImpl.class);
        Area area3 = mock(GameAreaImpl.class);
        Area area4 = mock(GameAreaImpl.class);
        Area area5 = mock(GameAreaImpl.class);
        Area area6 = mock(GameAreaImpl.class);
        GameField gameField3 = spy(new GameFieldImpl(List.of(area1, area2), null));
        GameField gameField2 = spy(new GameFieldImpl(List.of(area3), gameField3));
        GameField gameField1 = spy(new GameFieldImpl(List.of(area4, area5, area6), gameField2));

        gameField1.checkCollisions();
        verify(gameField1, times(1)).checkCollisions();
        verify(gameField2, times(1)).checkCollisions();
        verify(gameField3, times(1)).checkCollisions();
        verify(area1, times(1)).checkCollisions();
        verify(area2, times(1)).checkCollisions();
        verify(area3, times(1)).checkCollisions();
        verify(area4, times(1)).checkCollisions();
        verify(area5, times(1)).checkCollisions();
        verify(area6, times(1)).checkCollisions();
    }

    @Test
    void getAreasForAllGameFieldsTest() {
        Location location = mock(Location.class);

        Area area1 = mock(GameAreaImpl.class);
        Area area2 = mock(GameAreaImpl.class);
        Area area3 = mock(GameAreaImpl.class);
        Area area4 = mock(GameAreaImpl.class);
        Area area5 = mock(GameAreaImpl.class);
        Area area6 = mock(GameAreaImpl.class);
        when(area1.getId()).thenReturn("1");
        when(area2.getId()).thenReturn("2");
        when(area3.getId()).thenReturn("3");
        when(area4.getId()).thenReturn("4");
        when(area5.getId()).thenReturn("5");
        when(area6.getId()).thenReturn("6");

        GameField gameField3 = spy(new GameFieldImpl(List.of(area1, area2), null));
        GameField gameField2 = spy(new GameFieldImpl(List.of(area3), gameField3));
        GameField gameField1 = spy(new GameFieldImpl(List.of(area4, area5, area6), gameField2));

        when(area1.checkLocationIntersection(eq(location))).thenReturn(true);
        when(area2.checkLocationIntersection(eq(location))).thenReturn(false);
        when(area3.checkLocationIntersection(eq(location))).thenReturn(true);
        when(area4.checkLocationIntersection(eq(location))).thenReturn(false);
        when(area5.checkLocationIntersection(eq(location))).thenReturn(true);
        when(area6.checkLocationIntersection(eq(location))).thenReturn(false);

        Map<String, Area> areas = gameField1.getAreas(location);
        assertThat(areas).isEqualTo(Map.of(area1.getId(), area1, area3.getId(), area3, area5.getId(), area5));
        verify(gameField1, times(1)).getAreas(eq(location));
        verify(gameField2, times(1)).getAreas(eq(location));
        verify(gameField3, times(1)).getAreas(eq(location));
        verify(area1, times(1)).checkLocationIntersection(eq(location));
        verify(area2, times(1)).checkLocationIntersection(eq(location));
        verify(area3, times(1)).checkLocationIntersection(eq(location));
        verify(area4, times(1)).checkLocationIntersection(eq(location));
        verify(area5, times(1)).checkLocationIntersection(eq(location));
        verify(area6, times(1)).checkLocationIntersection(eq(location));
    }

}
