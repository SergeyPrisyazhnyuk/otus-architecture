package ru.otus.starshipbattle.command.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.starshipbattle.command.Command;

@Slf4j
@RequiredArgsConstructor
public class FinishCommand implements Command {
    private final UObject uObject;

    @Override
    public void execute() {
        log.info("Finish was called for object with position {} and velocity {} !",
                uObject.getProperty("Position"), uObject.getProperty("Velocity"));
    }
}
