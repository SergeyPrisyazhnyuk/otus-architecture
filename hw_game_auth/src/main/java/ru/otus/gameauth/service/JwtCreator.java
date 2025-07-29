package ru.otus.gameauth.service;

public interface JwtCreator {
    String create(String gamerId, String gameId);
}
