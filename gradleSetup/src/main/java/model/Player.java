package model;

import model.Activities.CookFood;
import model.Activities.CraftTool;
import model.Activities.Friendship;
import model.Enums.FarmPosition;
import model.Enums.Items.BackPackType;
import model.Enums.Items.TrashcanType;
import model.GameObject.Animal.Animal;
import model.GameObject.NPC.NPC;
import model.Locations.Building;
import model.Locations.Farm;
import model.States.Energy;
import model.items.Inventory;
import model.items.Item;
import model.skills.*;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.UUID;

public class Player {
    //identities
    private final UUID playerID;
    private String name;

    //ArrayLists
    private ArrayList<Skill> skills = new ArrayList<>();
    private final ArrayList<CraftTool> toolRecipes = new ArrayList<>();
    private final ArrayList<CookFood> foodRecipes = new ArrayList<>();
    private final ArrayList<Building> building = new ArrayList<>();
    private final ArrayList<Friendship> friendShips =new ArrayList<>();
    private final ArrayList<Animal> animals =new ArrayList<>();
    private final ArrayList<NPC> npc =new ArrayList<>();

    //inventories:
    private Inventory inventory;
    private Item currentItem;
    private BackPackType currentBackpack;
    private TrashcanType currentTrashcan;
    private int gold = 0;


    //status
    private Energy energy;
    private boolean fainted;

    //gameStatus
    private FarmPosition farmPosition;
    private Building defaultHome;
    private String userId;
    @Expose(serialize = false, deserialize = false)
    private Farm playerFarm;
    @Expose(serialize = false, deserialize = false)
    private User user;


    //Trades
    private final ArrayList<UUID> myTrades= new ArrayList<>();
    private final ArrayList<UUID> receivedTrades= new ArrayList<>();
    private final ArrayList<UUID> endedTradesHistory= new ArrayList<>();

    public Player(User user) {
        this.playerID = UUID.randomUUID();
        FarmingSkill farmingSkill = new FarmingSkill(0);
        ForagingSkill foragingSkill = new ForagingSkill(0);
        MiningSkill miningSkill = new MiningSkill(0);
        FishingSkill fishingSkill = new FishingSkill(0);
        skills.add(farmingSkill);
        skills.add(foragingSkill);
        skills.add(miningSkill);
        skills.add(fishingSkill);
        energy = new Energy(150);
        currentBackpack = BackPackType.InitialBackpack;
        inventory = new Inventory(currentBackpack.getCapacity());
        currentTrashcan = TrashcanType.initialTrashcan;
        this.user = user;
        this.userId = user.getUserId();
        this.fainted = false;
        this.currentItem = null;
    }


    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public BackPackType getCurrentBackpack() {
        return currentBackpack;
    }

    public void setCurrentBackpack(BackPackType currentBackpack) {
        this.currentBackpack = currentBackpack;
    }

    public TrashcanType getCurrentTrashcan() {
        return currentTrashcan;
    }

    public void setCurrentTrashcan(TrashcanType currentTrashcan) {
        this.currentTrashcan = currentTrashcan;
    }


    public ArrayList<CraftTool> getToolRecipes() {
        return toolRecipes;
    }

    public void addToolRecipes(CraftTool toolRecipes) {
        this.toolRecipes.add(toolRecipes);
    }

    public ArrayList<CookFood> getFoodRecipes() {
        return foodRecipes;
    }

    public void addFoodRecipes(CookFood foodRecipes) {
        this.foodRecipes.add(foodRecipes);
    }


    public ArrayList<NPC> getNpc() {
        return npc;
    }

    public void addNpc(NPC npc) {
        this.npc.add(npc);
    }

    public ArrayList<Building> getBuilding() {
        return building;
    }

    public void addBuilding(Building building) {
        this.building.add(building);
    }

    public ArrayList<Friendship> getFriendShips() {
        return friendShips;
    }

    public void addFriendShips(Friendship friendShip) {
        this.friendShips.add(friendShip);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Farm getPlayerFarm() {
        return playerFarm;
    }

    public void setPlayerFarm(Farm playerFarm) {
        this.playerFarm = playerFarm;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFainted() {
        return fainted;
    }

    public void setFainted(boolean fainted) {
        this.fainted = fainted;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public Building getDefaultHome() {
        return defaultHome;
    }

    public void setDefaultHome(Building defaultHome) {
        this.defaultHome = defaultHome;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }
    public void addAnimals(Animal animals) {
        this.animals.add(animals);
    }
    public FarmPosition getFarmPosition() {
        return farmPosition;
    }

    public void setFarmPosition(FarmPosition farmPosition) {
        this.farmPosition = farmPosition;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public void subtractGold(int gold) {
        this.gold -= gold;
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}