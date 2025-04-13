package io.testsmith.coffeemachine;

import io.testsmith.coffeemachine.enums.CoffeeType;
import io.testsmith.coffeemachine.exceptions.InsufficientBeansException;
import io.testsmith.coffeemachine.exceptions.InsufficientMilkException;
import io.testsmith.coffeemachine.exceptions.InsufficientWaterException;
import io.testsmith.coffeemachine.models.BeanReservoir;
import io.testsmith.coffeemachine.models.MilkReservoir;
import io.testsmith.coffeemachine.models.WaterTank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoffeeMachineTest {
    private CoffeeMachine coffeeMachine;
    private WaterTank waterTank;
    private BeanReservoir beanReservoir;
    private MilkReservoir milkReservoir;

    @BeforeEach
    void setup() {
        waterTank = mock(WaterTank.class);
        beanReservoir = mock(BeanReservoir.class);
        milkReservoir = mock(MilkReservoir.class);
        coffeeMachine = new CoffeeMachine(waterTank, beanReservoir, milkReservoir);
    }

    @Test
    void testPowerOnOffFeature() {
        assertFalse(coffeeMachine.isPoweredOn());

        coffeeMachine.powerOn();
        assertTrue(coffeeMachine.isPoweredOn());

        coffeeMachine.powerOff();
        assertFalse(coffeeMachine.isPoweredOn());
    }

    @Test
    void testReservoir_UseExactAvailableAmount() {
        BeanReservoir reservoir = new BeanReservoir(50);
        reservoir.use(50); // This should succeed
        assertEquals(0, reservoir.getLevel());
    }

    @Test
    void testBrewFailsWhenMachineIsOff() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(0);

        assertThrows(IllegalStateException.class, () -> coffeeMachine.brew(CoffeeType.COFFEE));
    }

    @Test
    void testBrewSucceedsWhenMachineIsOn() {
        coffeeMachine.powerOn();
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(0);

        String result = coffeeMachine.brew(CoffeeType.COFFEE);
        assertEquals("Your coffee is ready!", result);
    }

    @Test
    void testBrewCountTracking() {
        coffeeMachine.powerOn();

        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(0);

        assertEquals(0, coffeeMachine.getBrewCount(CoffeeType.COFFEE));

        coffeeMachine.brew(CoffeeType.COFFEE);
        assertEquals(1, coffeeMachine.getBrewCount(CoffeeType.COFFEE));

        coffeeMachine.brew(CoffeeType.COFFEE);
        assertEquals(2, coffeeMachine.getBrewCount(CoffeeType.COFFEE));
    }

    @Test
    void testBrewCountTracksDifferentCoffeeTypes() {
        coffeeMachine.powerOn();

        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(200);

        coffeeMachine.brew(CoffeeType.LATTE);
        coffeeMachine.brew(CoffeeType.CAPPUCCINO);
        coffeeMachine.brew(CoffeeType.CAPPUCCINO);

        assertEquals(1, coffeeMachine.getBrewCount(CoffeeType.LATTE));
        assertEquals(2, coffeeMachine.getBrewCount(CoffeeType.CAPPUCCINO));
        assertEquals(0, coffeeMachine.getBrewCount(CoffeeType.ESPRESSO));
    }

    @Test
    void testBrewEspresso_Success() {
        when(waterTank.getLevel()).thenReturn(200);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(0);

        coffeeMachine.powerOn();
        String result = coffeeMachine.brew(CoffeeType.ESPRESSO);

        assertEquals("Your espresso is ready!", result);
    }

    @Test
    void testBrewLatte_Success() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(150);

        coffeeMachine.powerOn();
        String result = coffeeMachine.brew(CoffeeType.LATTE);

        assertEquals("Your latte is ready!", result);
    }

    @Test
    void testBrewCappuccino_Success() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(200);

        coffeeMachine.powerOn();
        String result = coffeeMachine.brew(CoffeeType.CAPPUCCINO);

        assertEquals("Your cappuccino is ready!", result);
    }

    @Test
    void testBrewMacchiato_Success() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(100);

        coffeeMachine.powerOn();
        String result = coffeeMachine.brew(CoffeeType.MACCHIATO);

        assertEquals("Your macchiato is ready!", result);
    }

    @Test
    void testBrewCoffee_FailsDueToWater() {
        when(waterTank.getLevel()).thenReturn(100);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(0);

        coffeeMachine.powerOn();
        assertThrows(InsufficientWaterException.class, () -> coffeeMachine.brew(CoffeeType.COFFEE));
    }

    @Test
    void testBrewCoffee_FailsDueToBeans() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(10);
        when(milkReservoir.getLevel()).thenReturn(0);

        coffeeMachine.powerOn();
        assertThrows(InsufficientBeansException.class, () -> coffeeMachine.brew(CoffeeType.COFFEE));
    }

    @Test
    void testBrewLatte_FailsDueToMilk() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(50);
        coffeeMachine.powerOn();

        assertThrows(InsufficientMilkException.class, () -> coffeeMachine.brew(CoffeeType.LATTE));
    }

    @Test
    void testWaterTank_UseAndRefill() {
        WaterTank tank = new WaterTank(500);
        tank.use(100);
        assertEquals(400, tank.getLevel());

        tank.refill(200);
        assertEquals(500, tank.getLevel());
    }

    @Test
    void testBeanReservoir_UseAndRefill() {
        BeanReservoir reservoir = new BeanReservoir(100);
        reservoir.use(20);
        assertEquals(80, reservoir.getLevel());

        reservoir.refill(50);
        assertEquals(100, reservoir.getLevel());
    }

    @Test
    void testMilkReservoir_UseAndRefill() {
        MilkReservoir reservoir = new MilkReservoir(300);
        reservoir.use(150);
        assertEquals(150, reservoir.getLevel());

        reservoir.refill(100);
        assertEquals(250, reservoir.getLevel());
    }

    @Test
    void testReservoir_UseMoreThanAvailable() {
        BeanReservoir reservoir = new BeanReservoir(50);
        assertThrows(IllegalArgumentException.class, () -> reservoir.use(60));
    }

    @Test
    void testReservoir_RefillBeyondCapacity() {
        MilkReservoir reservoir = new MilkReservoir(200);
        reservoir.refill(100);
        assertEquals(200, reservoir.getLevel());
    }
}