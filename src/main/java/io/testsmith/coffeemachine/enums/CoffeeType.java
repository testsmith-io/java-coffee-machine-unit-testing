package io.testsmith.coffeemachine.enums;

public enum CoffeeType {
    /**
     * To brew coffee 200ml water, 20gr beans, 0ml milk is required
     */
    COFFEE(200, 20, 0),

    /**
     * To brew espresso 100ml water, 30gr beans, 0ml milk is required
     */
    ESPRESSO(100, 30, 0),

    /**
     * To brew double espresso 150ml water, 40gr beans, 0ml milk is required
     */
    DOUBLE_ESPRESSO(150, 40, 0),

    /**
     * To brew latte 150ml water, 20gr beans, 100ml milk is required
     */
    LATTE(150, 20, 100),

    /**
     * To brew cappuccino 100ml water, 25gr beans, 150ml milk is required
     */
    CAPPUCCINO(100, 25, 150),

    /**
     * To brew machiato 100ml water, 15gr beans, 50ml milk is required
     */
    MACCHIATO(100, 15, 50);

    public final int waterRequired;
    public final int beansRequired;
    public final int milkRequired;

    CoffeeType(int waterRequired, int beansRequired, int milkRequired) {
        this.waterRequired = waterRequired;
        this.beansRequired = beansRequired;
        this.milkRequired = milkRequired;
    }
}