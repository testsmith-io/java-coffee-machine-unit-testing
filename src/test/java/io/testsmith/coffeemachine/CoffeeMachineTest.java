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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void testBrewCoffee_Success() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(0);

        String result = coffeeMachine.brew(CoffeeType.COFFEE);

        assertEquals("Your coffee is ready!", result);
        verify(waterTank).use(200);
        verify(beanReservoir).use(20);
    }

    @Test
    void testBrewEspresso_Success() {
        when(waterTank.getLevel()).thenReturn(200);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(0);

        String result = coffeeMachine.brew(CoffeeType.ESPRESSO);

        assertEquals("Your espresso is ready!", result);
    }

    @Test
    void testBrewLatte_Success() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(150);

        String result = coffeeMachine.brew(CoffeeType.LATTE);

        assertEquals("Your latte is ready!", result);
    }

    @Test
    void testBrewCappuccino_Success() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(200);

        String result = coffeeMachine.brew(CoffeeType.CAPPUCCINO);

        assertEquals("Your cappuccino is ready!", result);
    }

    @Test
    void testBrewMacchiato_Success() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(100);

        String result = coffeeMachine.brew(CoffeeType.MACCHIATO);

        assertEquals("Your macchiato is ready!", result);
    }

    @Test
    void testBrewCoffee_FailsDueToWater() {
        when(waterTank.getLevel()).thenReturn(100);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(0);

        assertThrows(InsufficientWaterException.class, () -> coffeeMachine.brew(CoffeeType.COFFEE));
    }

    @Test
    void testBrewCoffee_FailsDueToBeans() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(10);
        when(milkReservoir.getLevel()).thenReturn(0);

        assertThrows(InsufficientBeansException.class, () -> coffeeMachine.brew(CoffeeType.COFFEE));
    }

    @Test
    void testBrewLatte_FailsDueToMilk() {
        when(waterTank.getLevel()).thenReturn(300);
        when(beanReservoir.getLevel()).thenReturn(50);
        when(milkReservoir.getLevel()).thenReturn(50);

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