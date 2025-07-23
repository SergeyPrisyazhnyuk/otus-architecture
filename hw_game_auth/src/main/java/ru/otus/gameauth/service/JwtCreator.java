package ru.otus.gameauth.service;

public interface JwtCreator {
    String create(String userId, String gameId);
}
