package ru.otus.starshipbattle.interpreter.interpreter;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.impl.UObject;

public interface Interpreter {
    Command interpret(UObject object);
}
