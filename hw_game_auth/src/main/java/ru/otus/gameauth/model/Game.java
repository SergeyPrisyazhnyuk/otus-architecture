package ru.otus.gameauth.model;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
public class Game {
    private final String id;
    private final List<String> gamers;
}
