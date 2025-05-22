package ru.otus.starshipbattle.scopes;

import ru.otus.starshipbattle.command.Command;
import ru.otus.starshipbattle.core.IoC;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InitCommand implements Command {

    private static final ThreadLocal<Object> currentScopes = new ThreadLocal<>();

    private static final Map<String, Function<Object[], Object>> ROOT_SCOPE = new ConcurrentHashMap<>();

    private boolean alreadyExecutesSuccessfully = false;

    static void setCurrentScopes(Object scope) {
        currentScopes.set(scope);
    }

    @Override
    public void execute() {

        if (alreadyExecutesSuccessfully) {
            return;
        }

        synchronized (ROOT_SCOPE) {
            ROOT_SCOPE.put("IoC.Scope.Current.Set", (args) -> new SetCurrentScopeCommand(args[0]));

            ROOT_SCOPE.put("IoC.Scope.Current.Clear", (args) -> new ClearCurrentScopeCommand());

            ROOT_SCOPE.put("IoC.Scope.Current", (args) -> currentScopes.get() != null
                    ? currentScopes.get()
                    : ROOT_SCOPE
            );

            ROOT_SCOPE.put("IoC.Scope.Parent",
                    (args) -> {
                        throw new RuntimeException("The root scope has no a parent scope.");
                    });

            ROOT_SCOPE.put("IoC.Scope.Create.Empty",
                    (args) -> new HashMap<String, Function<Object[], Object>>());

            ROOT_SCOPE.put("IoC.Scope.Create",
                    (args) -> {
                        Map<String, Function<Object[], Object>> creatingScope =
                                IoC.resolve("IoC.Scope.Create.Empty");

                        if (args.length > 0) {
                            Object parentScope = args[0];
                            creatingScope.put("IoC.Scope.Parent", (Object[] arg) -> parentScope);
                        } else {
                            Map<String, Function<Object[], Object>> parentScope =
                                    IoC.resolve("IoC.Scope.Current");
                            creatingScope.put("IoC.Scope.Parent", (Object[] arg) -> parentScope);
                        }
                        return creatingScope;
                    });

            ROOT_SCOPE.put("IoC.Register",
                    (args) -> new RegisterDependencyCommand((String) args[0],
                            (Function<Object[], Object>) args[1]));


            BiFunction<String, Object[], Object> oldStrategy =
                    (String dependency, Object[] arg) -> {
                        var scope = currentScopes.get() != null ? currentScopes.get() : ROOT_SCOPE;
                        DependencyResolver dependencyResolver = new DependencyResolverImpl(scope);

                        return dependencyResolver.resolve(dependency, arg);
                    };
            Command command = IoC.resolve("Update Ioc Resolve Dependency Strategy", oldStrategy);
            command.execute();

            alreadyExecutesSuccessfully = true;

        }

    }
}
