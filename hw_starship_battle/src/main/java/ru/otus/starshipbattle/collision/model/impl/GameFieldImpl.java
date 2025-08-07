package ru.otus.starshipbattle.collision.model.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.collision.model.Area;
import ru.otus.starshipbattle.collision.model.GameField;
import ru.otus.starshipbattle.collision.model.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GameFieldImpl implements GameField {

    private final List<Area> areasList;
    private final GameField nextLocation;

    @Override
    public Map<String, Area> getAreas(Location location) {
        Map<String, Area> areaMap = new HashMap<>();
        areasList.forEach(area -> {
            if (area.checkLocationIntersection(location)) {
                areaMap.put(area.getId(), area);
            }
        });
        if (nextLocation != null) {
            areaMap.putAll(nextLocation.getAreas(location));
        }
        return areaMap;
    }

    @Override
    public void checkCollisions() {
        areasList.forEach(Area::checkCollisions);
        if (nextLocation != null) {
            nextLocation.checkCollisions();
        }
    }
}
