package ru.otus.starshipbattle.state.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;
import ru.otus.starshipbattle.state.State;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

@RequiredArgsConstructor
public class MoveToState implements State {
    private final BlockingQueue<Command> queue;
    private final BlockingQueue<Command> otherQueue;

    @Override
    public State next() {
        Command command = queue.poll();
        while (command != null) {
            CountDownLatch countDownLatch = IoC.resolve("Latch");
            countDownLatch.countDown();
            otherQueue.add(command);
            command = queue.poll();
        }
        return null;
    }
}
