package io.testsmith.coffeemachine;

import io.testsmith.coffeemachine.enums.CoffeeType;
import io.testsmith.coffeemachine.exceptions.InsufficientBeansException;
import io.testsmith.coffeemachine.exceptions.InsufficientMilkException;
import io.testsmith.coffeemachine.exceptions.InsufficientWaterException;
import io.testsmith.coffeemachine.models.BeanReservoir;
import io.testsmith.coffeemachine.models.MilkReservoir;
import io.testsmith.coffeemachine.models.WaterTank;

public class CoffeeMachine {
    private final WaterTank waterTank;
    private final BeanReservoir beanReservoir;
    private final MilkReservoir milkReservoir;

    public CoffeeMachine(WaterTank waterTank, BeanReservoir beanReservoir, MilkReservoir milkReservoir) {
        this.waterTank = waterTank;
        this.beanReservoir = beanReservoir;
        this.milkReservoir = milkReservoir;
    }

    public String brew(CoffeeType type) {
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

        return "Your " + type.name().toLowerCase() + " is ready!";
    }
}