package io.src.model.Enums;

import io.src.model.App;
import io.src.model.Enums.Items.*;
import io.src.model.Enums.NpcDialogs.*;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.GameObject.NPC.NpcFriendship;
import io.src.model.GameObject.NPC.NpcRequest;
import io.src.model.MapModule.Position;
import io.src.model.Player;
import io.src.model.items.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum NpcType {
    SEBASTIAN(
        "Sebastian",
        new ArrayList<>(Arrays.asList(
            new NpcRequest(new Mineral(MineralItemType.IRON_ORE), 50, new Mineral(MineralItemType.DIAMOND), 2),
            new NpcRequest(new Food(FoodType.PUMPKIN_PIE), 1, EtcType.Money, 5000),
            new NpcRequest(new Mineral(MineralItemType.STONE), 150, new Mineral(MineralItemType.QUARTZ), 50)
        )),
        7,
        new ArrayList<>(Arrays.asList(
            new Etc(EtcType.WOOL),
            new Food(FoodType.PUMPKIN_PIE),
            new Food(FoodType.PIZZA)
        )),
        SebastianPrompt.class,
        "U+1F9D9",
        "Sebastian",
        Arrays.asList(
            new Position(67, 57),
            new Position(81, 57),
            new Position(96, 56),
            new Position(88, 44),
            new Position(81, 32)
        )
    ),

    ABIGAIL(
        "Abigail",
        new ArrayList<>(Arrays.asList(
            new NpcRequest(new Etc(EtcType.GOLD_BAR), 1, EtcType.NPC_FRIENDSHIP_XP, 200),
            new NpcRequest(new Food(FoodType.PUMPKIN), 1, EtcType.Money, 500),
            new NpcRequest(new Food(FoodType.WHEAT), 50, (EtcType.IRIDIUM_SPRINKLER), 1)
        )),
        9,
        new ArrayList<>(Arrays.asList(
            new Mineral(MineralItemType.STONE),
            new Mineral(MineralItemType.IRON_ORE),
            new Food(FoodType.COFFEE)
        )),
        AbigailPrompt.class,
        "U+1F9DB",
        "Haley",
        Arrays.asList(
            new Position(20, 30),
            new Position(40, 50),
            new Position(60, 70),
            new Position(80, 90),
            new Position(100, 110)
        )
    ),

    HARVEY(
        "Harvey",
        new ArrayList<>(Arrays.asList(
            new NpcRequest(new Etc(EtcType.ANY_PLANT), 12, EtcType.Money, 750),
            new NpcRequest(new Fish(FishType.Salmon), 1, EtcType.NPC_FRIENDSHIP_XP, 200),
            new NpcRequest(new Food(FoodType.WINE), 1, new Food(FoodType.SALAD), 5)
        )),
        11,
        new ArrayList<>(Arrays.asList(
            new Food(FoodType.COFFEE),
            new Food(FoodType.PICKLES),
            new Food(FoodType.WINE)
        )),
        HarveyPrompt.class,
        "U+1F9DF",
        "Alex",
        Arrays.asList(
            new Position(27, 18),
            new Position(27, 28),
            new Position(41, 28),
            new Position(49, 35),
            new Position(60, 40)
        )
    ),

    LEAH(
        "Leah",
        new ArrayList<>(Arrays.asList(
            new NpcRequest(new Etc(EtcType.WOOD), 10, EtcType.Money, 500),
            new NpcRequest(new Fish(FishType.Salmon), 1, FoodRecipesList.SALMON_DINNER, 1),
            new NpcRequest(new Etc(EtcType.WOOD), 200, new Etc(EtcType.DELUXE_SCARE_CROW), 3)
        )),
        13,
        new ArrayList<>(Arrays.asList(
            new Food(FoodType.SALAD),
            new Food(FoodType.GRAPE),
            new Food(FoodType.WINE)
        )),
        LeahPrompt.class,
        "U+1F9DD",
        "Elliot",
        Arrays.asList(
            new Position(31, 38),
            new Position(26, 54),
            new Position(21, 64),
            new Position(14, 81),
            new Position(16, 96)
        )
    ),

    ROBIN(
        "Robin",
        new ArrayList<>(Arrays.asList(
            new NpcRequest(new Etc(EtcType.WOOD), 1000, EtcType.Money, 1000),
            new NpcRequest(new Etc(EtcType.IRON_BAR), 10, new Artesian(ArtisanMachineItemType.BEE_HOUSE), 3),
            new NpcRequest(new Etc(EtcType.WOOD), 1000, EtcType.Money, 25000)
        )),
        120,
        new ArrayList<>(Arrays.asList(
            new Food(FoodType.SPAGHETTI),
            new Etc(EtcType.WOOD),
            new Etc(EtcType.IRON_BAR)
        )),
        RobinPrompt.class,
        "U+1F9DA",
        "Robin",
        Arrays.asList(
            new Position(92, 16),
            new Position(73, 16),
            new Position(47, 23),
            new Position(42, 24),
            new Position(45, 8)
        )
    );

    private final String name;
    private final ArrayList<NpcRequest> requests;
    private final int daysForSecond;
    private final ArrayList<Item> favoriteItems;
    private final Class<? extends NpcPrompt> promptClass;
    private final String icon;
    private final String assetName;
    private final List<Position> pathPoints; // مسیر نقاط راه رفتن

    NpcType(String name,
            ArrayList<NpcRequest> requests,
            int daysForSecond,
            ArrayList<Item> favoriteItems,
            Class<? extends NpcPrompt> promptClass,
            String icon,
            String assetName,
            List<Position> pathPoints) {
        this.name = name;
        this.requests = requests;
        this.daysForSecond = daysForSecond;
        this.favoriteItems = favoriteItems;
        this.promptClass = promptClass;
        this.icon = icon;
        this.assetName = assetName;
        this.pathPoints = pathPoints;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }

    public boolean hasAtlas() {
        return assetName.toLowerCase().contains("atlas");
    }

    public Class<? extends NpcPrompt> getPromptClass() {
        return promptClass;
    }

    public ArrayList<Item> getFavoriteItems() {
        return favoriteItems;
    }

    public ArrayList<NpcRequest> getRequests() {
        return requests;
    }

    public int getDaysForSecond() {
        return daysForSecond;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    /**
     * مسیر نقطه‌های مشخص‌شده برای حرکت این NPC در طول روز.
     */
    public List<Position> getPathPoints() {
        return pathPoints;
    }

    public NPC getNPC(Position position) {
        NPC npcToAdd = new NPC(position, this);

        // تنظیم مسیر اولیه از pathPoints
//        for (Position p : pathPoints) {
//            npcToAdd.addPathNode(p); // فرض می‌کنیم در NPC متد addPathNode پیاده است
//        }
        for (Player player : App.getCurrentUser().getCurrentGame().getPlayers()) {
            NpcFriendship friendship = new NpcFriendship(player, npcToAdd);
            npcToAdd.addFriendship(friendship);
            player.getNpcFriendShips().add(friendship);
        }
        return npcToAdd;
    }
}




//public enum NpcType {
//    SEBASTIAN(
//        "Sebastian",
//        new ArrayList<>(Arrays.asList(
//            new NpcRequest(new Mineral(MineralItemType.IRON_ORE), 50, new Mineral(MineralItemType.DIAMOND), 2),
//            new NpcRequest(new Food(FoodType.PUMPKIN_PIE), 1, EtcType.Money, 5000),
//            new NpcRequest(new Mineral(MineralItemType.STONE), 150, new Mineral(MineralItemType.QUARTZ), 50)
//        )),
//        7,
//        new ArrayList<>(Arrays.asList(
//            new Etc(EtcType.WOOL),
//            new Food(FoodType.PUMPKIN_PIE),
//            new Food(FoodType.PIZZA)
//        )),
//        SebastianPrompt.class,
//        "U+1F9D9",
//        "Sebastian_Atlas"),
//
//    ABIGAIL(
//        "Abigail",
//        new ArrayList<>(Arrays.asList(
//            new NpcRequest(new Etc(EtcType.GOLD_BAR), 1, EtcType.NPC_FRIENDSHIP_XP, 200),
//            new NpcRequest(new Food(FoodType.PUMPKIN), 1, EtcType.Money, 500),
//            new NpcRequest(new Food(FoodType.WHEAT), 50, (EtcType.IRIDIUM_SPRINKLER), 1)
//        )),
//        9,
//        new ArrayList<>(Arrays.asList(
//            new Mineral(MineralItemType.STONE),
//            new Mineral(MineralItemType.IRON_ORE),
//            new Food(FoodType.COFFEE)
//        )),
//        AbigailPrompt.class,
//        "U+1F9DB"
//        , "Abigail_Atlas"),
//
//    HARVEY(
//        "Alex",///
//        new ArrayList<>(Arrays.asList(
//            new NpcRequest(new Etc(EtcType.ANY_PLANT), 12, EtcType.Money, 750),
//            new NpcRequest(new Fish(FishType.Salmon), 1, EtcType.NPC_FRIENDSHIP_XP, 200),
//            new NpcRequest(new Food(FoodType.WINE), 1, new Food(FoodType.SALAD), 5)
//        )),
//        11,
//        new ArrayList<>(Arrays.asList(
//            new Food(FoodType.COFFEE),
//            new Food(FoodType.PICKLES),
//            new Food(FoodType.WINE)
//        )),
//        HarveyPrompt.class,
//        "U+1F9DF"
//        , "Harvey_Atlas"),
//
//    LEAH(
//        "Leah",
//        new ArrayList<>(Arrays.asList(
//            new NpcRequest(new Etc(EtcType.WOOD), 10, EtcType.Money, 500),
//            new NpcRequest(new Fish(FishType.Salmon), 1, FoodRecipesList.SALMON_DINNER, 1),
//            new NpcRequest(new Etc(EtcType.WOOD), 200, new Etc(EtcType.DELUXE_SCARE_CROW), 3)
//        )),
//        13,
//        new ArrayList<>(Arrays.asList(
//            new Food(FoodType.SALAD),
//            new Food(FoodType.GRAPE),
//            new Food(FoodType.WINE)
//        )),
//        LeahPrompt.class,
//        "U+1F9DD"
//        , "Leah_Atlas"),
//
//    ROBIN(
//        "Robin",
//        new ArrayList<>(Arrays.asList(
//            new NpcRequest(new Etc(EtcType.WOOD), 1000, EtcType.Money, 1000),
//            new NpcRequest(new Etc(EtcType.IRON_BAR), 10, new Artesian(ArtisanMachineItemType.BEE_HOUSE), 3),
//            new NpcRequest(new Etc(EtcType.WOOD), 1000, EtcType.Money, 25000)
//        )),
//        120,
//        new ArrayList<>(Arrays.asList(
//            new Food(FoodType.SPAGHETTI),
//            new Etc(EtcType.WOOD),
//            new Etc(EtcType.IRON_BAR)
//        )),
//        RobinPrompt.class,
//        "U+1F9DA"
//        , "Robin_Atlas");
//
//    private final String name;
//    private final ArrayList<NpcRequest> requests;
//    private final int daysForSecond;
//    private final ArrayList<Item> favoriteItems;
//    private final Class<? extends NpcPrompt> promptClass;
//    private final String icon;
//    private final String assetName;
//
//
//    NpcType(String name, ArrayList<NpcRequest> requests, int daysForSecond, ArrayList<Item> favoriteItems, Class<? extends NpcPrompt> promptClass, String icon, String assetName) {
//        this.name = name;
//        this.requests = requests;
//        this.daysForSecond = daysForSecond;
//        this.favoriteItems = favoriteItems;
//        this.promptClass = promptClass;
//        this.icon = icon;
//        this.assetName = assetName;
//    }
//
//
//    @Nullable
//    public String getAssetName() {
//        return switch (assetName) {
//            case "null" -> null;
//            case "" -> name.replace(" ", "_");
//            default -> assetName;
//        };
//    }
//
//    public boolean hasAtlas() {
//        return assetName.toLowerCase().contains("atlas");
//    }
//
//    public Class<? extends NpcPrompt> getPromptClass() {
//        return promptClass;
//    }
//
//    public ArrayList<Item> getFavoriteItems() {
//        return favoriteItems;
//    }
//
//    public ArrayList<NpcRequest> getRequests() {
//        return requests;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getIcon() {
//        return icon;
//    }
//
//
//    public NPC getNPC(Position position) {
//        NPC npcToAdd = new NPC(position, this);
//        for (Player player : App.getCurrentUser().getCurrentGame().getPlayers()) {
//            NpcFriendship friendship = new NpcFriendship(player, npcToAdd);
//            npcToAdd.addFriendship(friendship);
//            player.getNpcFriendShips().add(friendship);
//        }
//        return npcToAdd;
//    }
//
//    public int getDaysForSecond() {
//        return daysForSecond;
//    }
//}
