package ru.otus.starshipbattle.scopes;

import lombok.RequiredArgsConstructor;
import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;

import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public class RegisterDependencyCommand implements Command {

    private final String dependency;
    private final Function<Object[], Object> dependencyResolverStrategy;

    @Override
    public void execute() {
        Map<String, Function<Object[], Object>> currentScope = IoC.resolve("IoC.Scope.Current");
        currentScope.put(dependency, dependencyResolverStrategy);
    }
}
