package ru.otus.starshipbattle.command.impl;

public enum UObjectProperties {
    DIRECTION("Direction"),
    DIRECTION_NUMBERS("DirectionNumbers"),
    VELOCITY("Velocity"),

    POSITION("Position"),
    TURN_VELOCITY_DELTA("TurnVelocityDelta"),
    FUEL_VOLUME("FuelVolume"),
    BURN_FUEL_VELOCITY("BurnFuelVelocity");

    private final String value;

    UObjectProperties(String value) {
        this.value = value;
    }
}
