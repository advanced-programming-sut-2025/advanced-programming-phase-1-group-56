package model.Enums.Items;

import model.App;

public enum Ore implements ItemType {
    COPPER_ORE("Copper Ore", "A common ore that can be smelted into bars.", 75, -1),
    IRON_ORE("Iron Ore", "A fairly common ore that can be smelted into bars.", 150, -1),
    COAL("Coal", "A combustible rock that is useful for crafting and smelting.", 150, -1),
    GOLD_ORE("Gold Ore", "A precious ore that can be smelted into bars.", 400, -1),

    ANY_ORE("Any Ore", "A precious ore that can be smelted into bars.", 0, -1);

    private final String name;
    private final String description;
    private final int price;
    private final int dailyLimit; // -1 means unlimited

    Ore(String name, String description, int price, int dailyLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public int getDailyLimit() { return dailyLimit; }
}
