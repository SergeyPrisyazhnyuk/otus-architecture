package ru.otus.gameauth.service;

import java.util.List;

public interface GameAuthorizationService {
    List<String> getGamersRoles(String userId, String gameId);
}
