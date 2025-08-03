package ru.otus.starshipbattle.collision.model.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.collision.command.BaseCommand;
import ru.otus.starshipbattle.collision.model.Area;
import ru.otus.starshipbattle.collision.model.GameObject;
import ru.otus.starshipbattle.collision.model.Location;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class GameAreaImpl implements Area {

    private final String id;
    private final Location location;
    private final Map<String, GameObject> objects;
    private volatile Command checkCollisionsCommand = () -> {
    };

    public GameAreaImpl(String id, Location location) {
        this.id = id;
        this.location = location;
        this.objects = new ConcurrentHashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void addObject(GameObject object) {
        objects.put(object.getId(), object);
        updateCheckCollisionCommand();
    }

    @Override
    public void removeObject(GameObject object) {
        objects.remove(object.getId());
        updateCheckCollisionCommand();
    }

    @Override
    public Set<GameObject> getObjects() {
        return Set.of((GameObject) objects.values());
    }

    @Override
    public void checkCollisions() {
        checkCollisionsCommand.execute();
    }

    @Override
    public boolean checkLocationIntersection(Location location) {
        return this.location.checkLocationIntersection(location);
    }

    private void updateCheckCollisionCommand() {

        List<Command> checkCollisionsCommands = new ArrayList<>();
        List<GameObject> objectsList = objects.values().stream().sorted(Comparator.comparing(GameObject::getId)).toList();
        for (int i = 0; i < objectsList.size(); i++) {
            for (int j = i + 1; j < objectsList.size(); j++) {
                Command command = IoC.resolve("CheckCollisionCommand", objectsList.get(i), objectsList.get(j));
                checkCollisionsCommands.add(command);
            }
        }
        // записывает макрокоманду проверки коллизий объектов
        this.checkCollisionsCommand = new BaseCommand(checkCollisionsCommands);
    }
}
