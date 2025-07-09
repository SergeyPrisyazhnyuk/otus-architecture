package ru.otus.starshipbattle.command;

public interface CommandExecutor {
    void start();

    void stop();

    void updateBehaviour(Runnable action);
}
