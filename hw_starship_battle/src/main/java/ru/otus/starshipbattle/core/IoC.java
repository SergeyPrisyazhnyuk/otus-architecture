package ru.otus.starshipbattle.core;

import java.util.function.BiFunction;

public class IoC {

    private static BiFunction<String, Object[], Object> strategy = (dependency, args) -> {
        if ("Update Ioc Resolve Dependency Strategy".equals(dependency)) {
            return new UpdateIocResolveDependencyStrategyCommand((BiFunction<String, Object[], Object>) args[0]);
        } else {
            throw new IllegalArgumentException(String.format("Dependency %s is not found.", dependency));
        }
    };

    public static <T> T resolve(String dependency, Object... args) {
        return (T) strategy.apply(dependency, args);
    }

    static void setStrategy(BiFunction<String, Object[], Object> newStrategy) {
        strategy = newStrategy;
    }

}
