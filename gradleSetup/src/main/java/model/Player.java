package model;

import model.Activities.CookFood;
import model.Activities.CraftTool;
import model.Activities.Friendship;
import model.Enums.FarmPosition;
import model.Enums.Items.BackPackType;
import model.Enums.Items.TrashcanType;
import model.GameObject.Animal.Animal;
import model.GameObject.LivingEntity;
import model.GameObject.NPC.NPC;

import model.MapModule.Buildings.Building;
import model.MapModule.GameLocations.Farm;
import model.MapModule.GameLocations.GameLocation;
import model.MapModule.Position;
import model.States.Energy;
import model.items.Inventory;
import model.items.Item;
import model.skills.*;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Player {
    //Identity
//    private String name;
    private final UUID userId;
    @Expose(serialize = false, deserialize = false)
    private User user;

    //Activities
    private ArrayList<Skill> skills = new ArrayList<>();
    private final ArrayList<CraftTool> toolRecipes = new ArrayList<>();
    private final ArrayList<CookFood> foodRecipes = new ArrayList<>();

    //Map Authorities
    private FarmPosition farmPosition;//TODO

    @Expose(serialize = false, deserialize = false)
    private Farm playerFarm;//TODO
    @Expose(serialize = false, deserialize = false)
    private Building defaultHome;
    private final ArrayList<Friendship> friendShips =new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private final ArrayList<NPC> npc =new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private final ArrayList<Animal> animals =new ArrayList<>();
//    private final ArrayList<Building> building = new ArrayList<>();

    //Authorities
    private Inventory inventory;
    private Item currentItem;
    private BackPackType currentBackpack;
    private TrashcanType currentTrashcan;

    //status
    private boolean fainted;
    private Energy energy;
    private int gold;
    private Position position;
    private GameLocation currentGameLocation;


    private final ArrayList<UUID> myTrades= new ArrayList<>();
    private final ArrayList<UUID> receivedTrades= new ArrayList<>();
    private final ArrayList<UUID> endedTradesHistory= new ArrayList<>();



    public Player(User user) {
        this.user = user;
        this.userId = user.getUserId();
        //id ok
        FarmingSkill farmingSkill = new FarmingSkill(0);
        ForagingSkill foragingSkill = new ForagingSkill(0);
        MiningSkill miningSkill = new MiningSkill(0);
        FishingSkill fishingSkill = new FishingSkill(0);
        skills.add(farmingSkill);
        skills.add(foragingSkill);
        skills.add(miningSkill);
        skills.add(fishingSkill);
        //skill ok
        //map auth should add with setters
        //ok

        inventory = new Inventory(currentBackpack);
        currentTrashcan = TrashcanType.initialTrashcan;
        currentBackpack = BackPackType.InitialBackpack;
        this.currentItem = null;
        //auth ok

        this.energy = new Energy(200);
        this.fainted = false;
        this.gold = 0;
        this.position = new Position(0, 0);
        //TODO set current GL with setter
        //status ok
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

    public void subtractEnergy(int energy) {
        this.energy.setEnergy(this.energy.getEnergy() - energy);
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

    public ArrayList<Friendship> getFriendShips() {
        return friendShips;
    }

    public void addFriendShips(Friendship friendShip) {
        this.friendShips.add(friendShip);
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

    public void addAnimals(Animal animals) {
        this.animals.add(animals);
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
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

    public UUID getPlayerID(){
        return userId;
    }

    public ArrayList<UUID> getEndedTradesHistory() {
        return endedTradesHistory;
    }

    public ArrayList<UUID> getReceivedTrades() {
        return receivedTrades;
    }

    public ArrayList<UUID> getMyTrades() {
        return myTrades;
    }

    public ArrayList<NPC> getNpc() {
        return npc;
    }

    public GameLocation getCurrentGameLocation() {
        return currentGameLocation;
    }

    public void setCurrentGameLocation(GameLocation currentGameLocation) {
        this.currentGameLocation = currentGameLocation;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}