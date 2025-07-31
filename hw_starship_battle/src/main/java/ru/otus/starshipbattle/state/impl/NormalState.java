package ru.otus.starshipbattle.state.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.state.State;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class NormalState implements State {
    private final BlockingQueue<Command> queue;

    @Override
    public State next() {
        CountDownLatch countDownLatch = IoC.resolve("Latch");
        countDownLatch.countDown();
        ofNullable(queue.poll()).ifPresent(Command::execute);
        return null;
    }
}
