package model.Enums.Registery;

public enum ForageableItem {
    COMMON_MUSHROOM("Common Mushroom", "Special", 40, 38),
    DAFFODIL("Daffodil", "Spring", 30, 0),
    DANDELION("Dandelion", "Spring", 40, 25),
    LEEK("Leek", "Spring", 60, 40),
    MOREL("Morel", "Spring", 150, 20),
    SALMONBERRY("Salmonberry", "Spring", 5, 25),
    SPRING_ONION("Spring Onion", "Spring", 8, 13),
    WILD_HORSERADISH("Wild Horseradish", "Spring", 50, 13),
    FIDDLEHEAD_FERN("Fiddlehead Fern", "Summer", 90, 25),
    GRAPE("Grape", "Summer", 80, 38),
    RED_MUSHROOM("Red Mushroom", "Summer", 75, -50),
    SPICE_BERRY("Spice Berry", "Summer", 80, 25),
    SWEET_PEA("Sweet Pea", "Summer", 50, 0),
    BLACKBERRY("Blackberry", "Fall", 25, 25),
    CHANTERELLE("Chanterelle", "Fall", 160, 75),
    HAZELNUT("Hazelnut", "Fall", 40, 38),
    PURPLE_MUSHROOM("Purple Mushroom", "Fall", 90, 30),
    WILD_PLUM("Wild Plum", "Fall", 80, 25),
    CROCUS("Crocus", "Winter", 60, 0),
    CRYSTAL_FRUIT("Crystal Fruit", "Winter", 150, 63),
    HOLLY("Holly", "Winter", 80, -37),
    SNOW_YAM("Snow Yam", "Winter", 100, 30),
    WINTER_ROOT("Winter Root", "Winter", 70, 25);

    public final String name;
    public final String season;
    public final int baseSellPrice;
    public final int energy;

    ForageableItem(String name, String season, int baseSellPrice, int energy) {
        this.name = name;
        this.season = season;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
    }
}
