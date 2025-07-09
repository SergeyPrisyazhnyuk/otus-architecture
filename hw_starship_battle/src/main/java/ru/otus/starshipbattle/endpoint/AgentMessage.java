package ru.otus.starshipbattle.endpoint;

public record AgentMessage(String gameId, String objectId, String operationId, Object[] args) {
}