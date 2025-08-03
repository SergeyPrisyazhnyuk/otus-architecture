package ru.otus.starshipbattletests.collision.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.collision.command.CheckCollisionCommand;
import ru.otus.starshipbattle.collision.model.GameObject;
import ru.otus.starshipbattle.collision.model.Location;
import ru.otus.starshipbattle.collision.model.impl.GameAreaImpl;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.scopes.InitCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
public class GameAreaImplTest {

    @BeforeEach
    void initialize() {
        try {
            new InitCommand().execute();
        } catch (RuntimeException e) {
            log.info("Scope already exists");
        }
    }

    @Test
    public void addRemoveObjectAndUpdateCheckCollisionsCommandTest() {
        List<CheckCollisionInfo> checkCollisionCommands = new ArrayList<>();

        ((Command) IoC.resolve("IoC.Register", "CheckCollisionCommand", (Function<Object[], Object>) args -> {
            GameObject object1 = (GameObject) args[0];
            GameObject object2 = (GameObject) args[1];
            Command command = spy(new CheckCollisionCommand(object1, object2));
            checkCollisionCommands.add(new CheckCollisionInfo(command, object1, object2));
            return command;
        })).execute();

        String id = "1";
        Location location = mock(Location.class);
        GameAreaImpl gameArea = new GameAreaImpl(id, location);

        GameObject object1 = mock(GameObject.class);
        when(object1.getId()).thenReturn("100");
        GameObject object2 = mock(GameObject.class);
        when(object2.getId()).thenReturn("200");
        GameObject object3 = mock(GameObject.class);
        when(object3.getId()).thenReturn("300");

        gameArea.addObject(object1);
        assertThat(checkCollisionCommands).isEmpty();

        gameArea.addObject(object2);
        assertThat(checkCollisionCommands.size()).isEqualTo(1);
        assertThat(checkCollisionCommands.get(0).obj1).isEqualTo(object1);
        assertThat(checkCollisionCommands.get(0).obj2).isEqualTo(object2);
        gameArea.checkCollisions();
        verify(checkCollisionCommands.get(0).command, times(1)).execute();

        gameArea.addObject(object3);
        assertThat(checkCollisionCommands.size()).isEqualTo(4);
        assertThat(checkCollisionCommands.get(1).obj1).isEqualTo(object1);
        assertThat(checkCollisionCommands.get(1).obj2).isEqualTo(object2);
        assertThat(checkCollisionCommands.get(2).obj1).isEqualTo(object1);
        assertThat(checkCollisionCommands.get(2).obj2).isEqualTo(object3);
        assertThat(checkCollisionCommands.get(3).obj1).isEqualTo(object2);
        assertThat(checkCollisionCommands.get(3).obj2).isEqualTo(object3);
        gameArea.checkCollisions();
        verify(checkCollisionCommands.get(0).command, times(1)).execute();
        verify(checkCollisionCommands.get(1).command, times(1)).execute();
        verify(checkCollisionCommands.get(2).command, times(1)).execute();
        verify(checkCollisionCommands.get(3).command, times(1)).execute();

        gameArea.removeObject(object2);
        assertThat(checkCollisionCommands.size()).isEqualTo(5);
        assertThat(checkCollisionCommands.get(4).obj1).isEqualTo(object1);
        assertThat(checkCollisionCommands.get(4).obj2).isEqualTo(object3);
        gameArea.checkCollisions();
        verify(checkCollisionCommands.get(0).command, times(1)).execute();
        verify(checkCollisionCommands.get(1).command, times(1)).execute();
        verify(checkCollisionCommands.get(2).command, times(1)).execute();
        verify(checkCollisionCommands.get(3).command, times(1)).execute();
        verify(checkCollisionCommands.get(4).command, times(1)).execute();
    }

    @Builder
    private static class CheckCollisionInfo {
        Command command;
        GameObject obj1;
        GameObject obj2;

    }

}
