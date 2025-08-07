package ru.otus.starshipbattle.collision.command;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.collision.model.Area;
import ru.otus.starshipbattle.collision.model.GameField;
import ru.otus.starshipbattle.collision.model.GameObject;
import ru.otus.starshipbattle.collision.model.Location;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;

import java.util.Map;

@RequiredArgsConstructor
public class ChangeAreaCommand implements Command {

    private final GameObject gameObject;
    private final Location previousLocation;


    @Override
    public void execute() {
        GameField gameField = IoC.resolve("GameField");
        Map<String, Area> previousAreas;
        if (previousLocation != null) {
            previousAreas = gameField.getAreas(previousLocation);
        } else {
            previousAreas = null;
        }
        Map<String, Area> currentAreas = gameField.getAreas(gameObject.getLocation());

        if (previousAreas != null) {
            previousAreas.forEach((key, value) -> {
                if (!currentAreas.containsKey(key)) {
                    value.removeObject(gameObject);
                }
            });
        }
        currentAreas.forEach((key, value) -> {
            if (previousAreas == null || !previousAreas.containsKey(key)) {
                value.addObject(gameObject);
            }
        });
    }
}
