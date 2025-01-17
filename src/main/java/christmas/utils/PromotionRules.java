package christmas.utils;

public enum PromotionRules {
    MINIMUM_PRICE(10_000),
    MAXIMUM_ORDER_COUNT(20),
    MINIMUM_ORDER_COUNT(1),
    START_DATE(1),
    END_DATE(31),
    CHRISTMAS_DATE(25),
    VALID_MENU_LENGTH(2),
    MENU_ORDER(0),
    COUNT_ORDER(1),
    TOTAL_BENEFIT_20000(20_000),
    TOTAL_BENEFIT_10000(10_000),
    TOTAL_BENEFIT_5000(5_000),
    STAR_DISCOUNT(1_000),
    GIVEAWAY_CONDITION(120_000),
    ACCUMULATE_DISCOUNT_PRICE(100),
    DISCOUNT_START_PRICE(1_000),
    EVENT_PERIOD_DISCOUNT(2_023);

    private final int value;

    PromotionRules(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

