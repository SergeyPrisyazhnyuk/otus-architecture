package ru.otus.starshipbattle.scopes;

public interface DependencyResolver {
    <T> T resolve(String key, Object... args);
}
