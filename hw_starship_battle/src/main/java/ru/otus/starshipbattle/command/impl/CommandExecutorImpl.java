package ru.otus.starshipbattle.command.impl;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.command.CommandExecutor;
import ru.otus.starshipbattle.exception.handler.CommandExceptionHandler;

import java.util.concurrent.BlockingQueue;

import static java.util.Optional.ofNullable;

public class CommandExecutorImpl implements CommandExecutor {
    private final BlockingQueue<Command> commands;
    private final CommandExceptionHandler exceptionHandler;
    private final Thread thread;
    private Runnable behaviour;
    private volatile boolean stopped = false;

    public CommandExecutorImpl(BlockingQueue<Command> commands, CommandExceptionHandler exceptionHandler) {
        this.commands = commands;
        this.exceptionHandler = exceptionHandler;
        behaviour = () -> {
            Command cmd = this.commands.poll();
            try {
                ofNullable(cmd).ifPresent(Command::execute);
            } catch (Exception e) {
                this.exceptionHandler.handle(e, cmd).execute();
            }
        };
        this.thread = new Thread(() -> {
            while (!stopped) {
                behaviour.run();
            }
        });
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public void updateBehaviour(Runnable action) {
        behaviour = action;
    }
}