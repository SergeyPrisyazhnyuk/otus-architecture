package ru.otus.starshipbattle.exception.handler;

import ru.otus.starshipbattle.command.Command;

public interface CommandExceptionHandler {
    Command handle(Exception e, Command c);
}
