package model.Enums;

import java.util.ArrayList;
import java.util.Arrays;

import model.GameObject.NPC.NPC;
import model.GameObject.NPC.NpcRequest;
import model.MapModule.Position;
import model.items.Item;

public enum NPCs {
    SEBASTIAN(new NPC(
            "Sebastian",
            new ArrayList<>(Arrays.asList(
                    new NpcRequest(new Item("Iron", 99, true),           50, new Item("Diamond", 1, false),        2),
                    new NpcRequest(new Item("Pumpkin Pie", 1, true),      1, new Item("Gold",    1, false),     5000),
                    new NpcRequest(new Item("Coconut",     5, true),      5, new Item("Friendship Point", 1, false), 1)
            )),
            new ArrayList<>(Arrays.asList(
                    new Item("Wool",       999, true),
                    new Item("Pumpkin Pie",   1, true),
                    new Item("Pizza",        1, false)
            )),
            new Position(10,10)
    )),

    ABIGAIL(new NPC(
            "Abigail",
            new ArrayList<>(Arrays.asList(
                    new NpcRequest(new Item("Stone",     999, true),   150, new Item("Quartz", 1, false),       50),
                    new NpcRequest(new Item("Pumpkin",      1, true),     1, new Item("Gold",   1, false),      500),
                    new NpcRequest(new Item("Any Fruit",  12, true),    12, new Item("Gold",   1, false),      750)
            )),
            new ArrayList<>(Arrays.asList(
                    new Item("Stone",     999, true),
                    new Item("Iron Ore",  999, true),
                    new Item("Coffee",    999, true)
            )),
            new Position(15,15)
    )),

    HARVEY(new NPC(
            "Harvey",
            new ArrayList<>(Arrays.asList(
                    new NpcRequest(new Item("Salmon",   1, true),       1, new Item("Friendship Point", 1, false),  1),
                    new NpcRequest(new Item("Wine",     1, false),      1, new Item("Salad",           1, false),  5),
                    new NpcRequest(new Item("Wood",    10, true),      10, new Item("Gold",             1, false),500)
            )),
            new ArrayList<>(Arrays.asList(
                    new Item("Coffee", 999, true),
                    new Item("Pickles",999, true),
                    new Item("Wine",    1,   false)
            )),
            new Position(20,20)
    )),

    LEAH(new NPC(
            "Leah",
            new ArrayList<>(Arrays.asList(
                    new NpcRequest(new Item("Salad",          1, false),  1, new Item("Dinner Salmon",1, false),     1),
                    new NpcRequest(new Item("Deluxe Scarecrow",3, false),  3, new Item("Wood",           999, true),   200),
                    new NpcRequest(new Item("Wood",           999, true),  80, new Item("Gold",              1, false),1000)
            )),
            new ArrayList<>(Arrays.asList(
                    new Item("Salad",   1, false),
                    new Item("Grapes", 999, true),
                    new Item("Wine",     1, false)
            )),
            new Position(25,25)
    )),

    ROBIN(new NPC(
            "Robin",
            new ArrayList<>(Arrays.asList(
                    new NpcRequest(new Item("Iron Bar",    1, true),   10, new Item("Bee House",      1, false),  3),
                    new NpcRequest(new Item("Wood",       999, true),1000, new Item("Gold",            1, false),25000),
                    new NpcRequest(new Item("Spaghetti",   1, false),   1, new Item("Friendship Point",1, false),  1)
            )),
            new ArrayList<>(Arrays.asList(
                    new Item("Spaghetti", 1, false),
                    new Item("Wood",     999, true),
                    new Item("Iron Bar",  1, true)
            )),
            new Position(30,30)
    ));

    private final NPC npc;

    NPCs(NPC npc) {
        this.npc = npc;
    }

    public NPC getNpc() {
        return npc;
    }
}