package model.Enums.Registery;

import java.util.Arrays;
import java.util.List;

import model.GameObject.NPC.NpcProduct;
import model.items.Item;

public enum StoreType {
    BLACKSMITH("Clint",    9,  16, Arrays.asList(
            new NpcProduct(new Item("ore",   99, true), 150, 10),
            new NpcProduct(new Item("stone", 999, true), 100, 20)
    )),
    JOJA_MART("Morris",   9,  23, Arrays.asList(
            new NpcProduct(new Item("seeds",      30, true),  30, 50),
            new NpcProduct(new Item("fertilizer",100, true), 100, 20)
    )),
    GENERAL_STORE("Pierre",  9,  17, Arrays.asList(
            new NpcProduct(new Item("corn_seeds", 150, true), 150, 30),
            new NpcProduct(new Item("backpack_upgrade", 2000, false), 2000, 1)
    )),
    CARPENTER("Robin",     9,  20, Arrays.asList(
            new NpcProduct(new Item("wood",   10, true), 10, 100),
            new NpcProduct(new Item("stone",  20, true), 20, 100)
    )),
    FISH_SHOP("Willy",    9,  17, Arrays.asList(
            new NpcProduct(new Item("bait",        5, true),   5, 100),
            new NpcProduct(new Item("fishing_rod",500, false), 500,  5)
    )),
    RANCH("Marnie",    9,  16, Arrays.asList(
            new NpcProduct(new Item("egg",   50, true), 50, 20),
            new NpcProduct(new Item("cow", 1500, false), 1500, 3)
    )),
    SALOON("Gus",     12,  24, Arrays.asList(
            new NpcProduct(new Item("meal",         100, false), 100, 30),
            new NpcProduct(new Item("egg_o_matic", 2500, false), 2500, 1)
    ));

    private final String ownerName;
    private final int openingHour;
    private final int closingHour;
    private final List<NpcProduct> products;

    StoreType(String ownerName,
              int openingHour,
              int closingHour,
              List<NpcProduct> products) {
        this.ownerName   = ownerName;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.products    = products;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getOpeningHour() {
        return openingHour;
    }

    public int getClosingHour() {
        return closingHour;
    }

    public List<NpcProduct> getProducts() {
        return products;
    }

    /*
      اگر لازم است یک NPC واقعی برای صاحب بسازید:
     */
//    public NPC createOwnerNpc() {
//        return new NPC();
//    }
}
