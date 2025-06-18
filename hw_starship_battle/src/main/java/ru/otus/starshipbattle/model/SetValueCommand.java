package ru.otus.starshipbattle.model;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.UObject;

@RequiredArgsConstructor
public class SetValueCommand implements Command {
    private final UObject o;
    private final String key;
    private final Object value;

    @Override
    public void execute() {
        o.setProperty(key, value);
    }
}
