package io.testsmith.coffeemachine.models;

public abstract class AbstractReservoir implements Reservoir {
    protected int level;
    protected final int capacity;

    public AbstractReservoir(int capacity) {
        this.capacity = capacity;
        this.level = capacity;
    }

    @Override
    public void use(int amount) {
        if (amount > level) {
            throw new IllegalArgumentException("Not enough resources!");
        }
        level -= amount;
    }

    @Override
    public void refill(int amount) {
        level = Math.min(level + amount, capacity);
    }

    @Override
    public int getLevel() {
        return level;
    }
}