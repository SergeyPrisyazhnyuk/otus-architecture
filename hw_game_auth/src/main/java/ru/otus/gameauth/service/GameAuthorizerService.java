package ru.otus.gameauth.service;

public interface GameAuthorizerService {
    boolean authorize(String token, String gameId, String role);
}
