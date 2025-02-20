package io.testsmith.coffeemachine.models;

public interface Reservoir {
    void use(int amount);
    void refill(int amount);
    int getLevel();
}