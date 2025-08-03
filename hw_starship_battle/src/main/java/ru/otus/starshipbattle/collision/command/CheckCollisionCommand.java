package ru.otus.starshipbattle.collision.command;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.collision.model.GameObject;
import ru.otus.starshipbattle.command.Command;

@RequiredArgsConstructor
public class CheckCollisionCommand implements Command {

    private final GameObject gameObject1;
    private final GameObject gameObject2;

    @Override
    public void execute() {

    }
}
