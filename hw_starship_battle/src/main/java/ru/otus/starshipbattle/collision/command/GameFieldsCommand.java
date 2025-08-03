package ru.otus.starshipbattle.collision.command;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.collision.model.GameField;
import ru.otus.starshipbattle.collision.model.Location;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@RequiredArgsConstructor
public class GameFieldsCommand implements Command {

    private final int[] movings;
    private final int areaSize;
    private final Location location;

    @Override
    public void execute() {
        AtomicReference<GameField> gameFieldNext = new AtomicReference<>();
        Arrays.stream(movings).boxed().sorted(Collections.reverseOrder()).forEach(move -> {
            gameFieldNext.set(IoC.resolve("GameField.Get", move, areaSize, location, gameFieldNext.get()));
        });

        ((Command) IoC.resolve("IoC.Register", "GameField", (Function<Object[], Object>) args -> gameFieldNext.get())).execute();
    }
}
