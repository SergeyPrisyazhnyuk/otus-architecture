package ru.otus.starshipbattle.interpreter.model;


import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class Game {
    private final String gameId;
}