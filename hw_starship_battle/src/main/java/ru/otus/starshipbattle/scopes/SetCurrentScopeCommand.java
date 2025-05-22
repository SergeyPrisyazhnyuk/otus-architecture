package ru.otus.starshipbattle.scopes;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;

@RequiredArgsConstructor
public class SetCurrentScopeCommand implements Command {
    private final Object scope;

    @Override
    public void execute() {
        InitCommand.setCurrentScopes(scope);
    }
}
