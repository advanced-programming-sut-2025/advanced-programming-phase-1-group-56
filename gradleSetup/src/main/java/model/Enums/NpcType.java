package model.Enums;

import model.App;
import model.Enums.Items.*;
import model.Enums.NpcDialogs.*;
import model.Enums.Recepies.FoodRecipesList;
import model.GameObject.NPC.NPC;
import model.GameObject.NPC.NpcFriendship;
import model.GameObject.NPC.NpcRequest;
import model.MapModule.Position;
import model.Player;
import model.items.*;

import java.util.ArrayList;
import java.util.Arrays;

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
            SebastianPrompt.class
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
            AbigailPrompt.class
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
            HarveyPrompt.class
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
            LeahPrompt.class
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
            RobinPrompt.class
    );

    private final String name;
    private final ArrayList<NpcRequest> requests;
    private final int daysForSecond;
    private final ArrayList<Item> favoriteItems;
    private final Class<? extends NpcPrompt> promptClass;

    NpcType(String name, ArrayList<NpcRequest> requests, int daysForSecond, ArrayList<Item> favoriteItems, Class<? extends NpcPrompt> promptClass) {
        this.name = name;
        this.requests = requests;
        this.daysForSecond = daysForSecond;
        this.favoriteItems = favoriteItems;
        this.promptClass = promptClass;
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

    public String getName() {
        return name;
    }

    public NPC getNPC(Position position) {
        NPC npcToAdd = new NPC(position, this);
        for (Player player : App.getCurrentUser().getCurrentGame().getPlayers()) {
            NpcFriendship friendship = new NpcFriendship(player, npcToAdd);
            npcToAdd.addFriendship(friendship);
            player.getNpcFriendShips().add(friendship);
        }
        return npcToAdd;
    }

    public int getDaysForSecond() {
        return daysForSecond;
    }
}