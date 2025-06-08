package ru.otus.starshipbattle.scopes;

import ru.otus.starshipbattle.command.Command;

public class ClearCurrentScopeCommand implements Command {
    @Override
    public void execute() {
        InitCommand.setCurrentScopes(null);
    }
}
