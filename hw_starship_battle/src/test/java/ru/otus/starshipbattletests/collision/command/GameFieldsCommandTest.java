package ru.otus.starshipbattletests.collision.command;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.starshipbattle.collision.command.GameFieldsCommand;
import ru.otus.starshipbattle.collision.model.GameField;
import ru.otus.starshipbattle.collision.model.Location;
import ru.otus.starshipbattle.collision.model.impl.GameAreaImpl;
import ru.otus.starshipbattle.collision.model.impl.GameFieldImpl;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.scopes.InitCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Slf4j
public class GameFieldsCommandTest {
    @BeforeEach
    void initialize() {
        try {
            new InitCommand().execute();
        } catch (RuntimeException e) {
            log.info("Scope already exists");
        }
    }

    @Test
    void registerGameFieldsTest() {
        int[] shifts = new int[] {2, 0, 1};
        int areaSize = 10;
        Location location = mock(Location.class);
        List<GameField> expected = new ArrayList<>();
        GameField gameField1 = spy(new GameFieldImpl(List.of(new GameAreaImpl("1", mock(Location.class)), new GameAreaImpl("2", mock(Location.class))), null));
        GameField gameField2 = spy(new GameFieldImpl(List.of(new GameAreaImpl("3", mock(Location.class))), gameField1));
        GameField gameField3 = spy(new GameFieldImpl(List.of(new GameAreaImpl("4", mock(Location.class)), new GameAreaImpl("5", mock(Location.class)), new GameAreaImpl("6", mock(Location.class))), gameField2));
        expected.add(gameField2);
        expected.add(gameField3);
        expected.add(gameField1);
        ((Command) IoC.resolve("IoC.Register", "GameField.Get", (Function<Object[], Object>) args -> {
            int movingArg = (Integer) args[0];
            int areaSizeArg = (Integer) args[1];
            Location locationArg = (Location) args[2];
            if (areaSizeArg == areaSize && locationArg == location) {
                if (movingArg == shifts[0]) {
                    return expected.get(0);
                } else if (movingArg == shifts[1]) {
                    return expected.get(1);
                } else if (movingArg == shifts[2]) {
                    return expected.get(2);
                }
            }
            return null;
        })).execute();

        GameFieldsCommand command = new GameFieldsCommand(shifts, areaSize, location);
        command.execute();
        GameField actual = IoC.resolve("GameField");
        assertThat(actual).isEqualTo(gameField3);
    }

}
