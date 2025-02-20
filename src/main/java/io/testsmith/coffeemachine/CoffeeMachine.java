package io.testsmith.coffeemachine;

import io.testsmith.coffeemachine.enums.CoffeeType;
import io.testsmith.coffeemachine.exceptions.InsufficientBeansException;
import io.testsmith.coffeemachine.exceptions.InsufficientMilkException;
import io.testsmith.coffeemachine.exceptions.InsufficientWaterException;
import io.testsmith.coffeemachine.models.BeanReservoir;
import io.testsmith.coffeemachine.models.MilkReservoir;
import io.testsmith.coffeemachine.models.WaterTank;

import java.util.HashMap;
import java.util.Map;

public class CoffeeMachine {
    private final WaterTank waterTank;
    private final BeanReservoir beanReservoir;
    private final MilkReservoir milkReservoir;
    private boolean powerOn;
    private final Map<CoffeeType, Integer> brewCounts;

    public CoffeeMachine(WaterTank waterTank, BeanReservoir beanReservoir, MilkReservoir milkReservoir) {
        this.waterTank = waterTank;
        this.beanReservoir = beanReservoir;
        this.milkReservoir = milkReservoir;
        this.powerOn = false;
        this.brewCounts = new HashMap<>();

        // Initialize brew counts
        for (CoffeeType type : CoffeeType.values()) {
            brewCounts.put(type, 0);
        }
    }

    public void powerOn() {
        this.powerOn = true;
    }

    public void powerOff() {
        this.powerOn = false;
    }

    public boolean isPoweredOn() {
        return powerOn;
    }

    public int getBrewCount(CoffeeType type) {
        return brewCounts.getOrDefault(type, 0);
    }

    public String brew(CoffeeType type) {
        if (!powerOn) {
            throw new IllegalStateException("Coffee Machine is OFF. Please turn it ON before brewing.");
        }

        if (waterTank.getLevel() < type.waterRequired) {
            throw new InsufficientWaterException("Not enough water!");
        }
        if (beanReservoir.getLevel() < type.beansRequired) {
            throw new InsufficientBeansException("Not enough beans!");
        }
        if (milkReservoir.getLevel() < type.milkRequired) {
            throw new InsufficientMilkException("Not enough milk!");
        }

        waterTank.use(type.waterRequired);
        beanReservoir.use(type.beansRequired);
        milkReservoir.use(type.milkRequired);

        // Track brew count
        brewCounts.put(type, brewCounts.get(type) + 1);

        return "Your " + type.name().toLowerCase() + " is ready!";
    }
}