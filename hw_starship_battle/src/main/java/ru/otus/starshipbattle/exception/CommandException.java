package ru.otus.starshipbattle.exception;

public class CommandException extends RuntimeException{
    public CommandException(String message) {
        super(message);
    }
}