package io.src.model;

import io.src.model.Activities.*;
import io.src.model.Enums.BackPackType;
import io.src.model.Enums.Direction;
import io.src.model.Enums.FarmPosition;

import io.src.model.Enums.Items.TrashcanType;

import io.src.model.Enums.Recepies.CraftingRecipesList;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.Enums.Skills;
import io.src.model.GameObject.Animal;
import io.src.model.GameObject.NPC.NPC;

import io.src.model.GameObject.NPC.NpcFriendship;
import io.src.model.MapModule.Buildings.Building;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;

import io.src.model.States.Energy;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;
import io.src.model.items.Inventory;
import io.src.model.items.Item;
import io.src.model.skills.*;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.UUID;

public class Player implements TimeObserver {
    //Identity
//    private String name;
    private final UUID userId;
    public static final int BODY_WIDTH = 16;
    public static final int BODY_HEIGHT = 32;
    @Expose(serialize = false, deserialize = false)
    private User user;
    private boolean gender;
    private double energyUsage = 0;
    private int movingDirection = 0;
    private Direction currentDirection = Direction.DOWN;
    private Direction lastDirection = Direction.DOWN;
    //Activities
    private ArrayList<Skill> skills = new ArrayList<>();
    private final ArrayList<CraftingRecipesList> toolRecipes = new ArrayList<>();
    private final ArrayList<FoodRecipesList> foodRecipes = new ArrayList<>();

    //Map Authorities
    private FarmPosition farmPosition;//TODO

    @Expose(serialize = false, deserialize = false)
    private Farm playerFarm;//TODO
    @Expose(serialize = false, deserialize = false)
    private Building defaultHome;
//    private final ArrayList<Building> building = new ArrayList<>();

    //Authorities
    private Inventory inventory;
    private BackPackType currentBackpack = BackPackType.InitialBackpack;
    private TrashcanType currentTrashcan = TrashcanType.initialTrashcan;
    private Item currentItem;
    private int selectedSlot = 0;

    //status
    private boolean fainted = false;
    private Energy energy;
    private int gold;
    private Position position;
    private GameLocation currentGameLocation;
    private Buff currentBuff = null;
    private boolean interactWithPartnerToday;

    //connections
    private final ArrayList<UUID> myTrades = new ArrayList<>();
    private final ArrayList<UUID> receivedTrades = new ArrayList<>();
    private final ArrayList<UUID> endedTradesHistory = new ArrayList<>();

    @Expose(serialize = false, deserialize = false)
    private final ArrayList<NpcFriendship> npcFriendships = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private final ArrayList<Animal> animals = new ArrayList<>();

    private NPC lastMeetedNpc = null;
    //doesn't need expose because duplicate won't make any trouble
    private final ArrayList<Friendship> friendShips = new ArrayList<>();
    private final ArrayList<Message> messages = new ArrayList<>();
    private final ArrayList<Gift> gifts = new ArrayList<>();
    private final ArrayList<Gift> marryRequests = new ArrayList<>();
    private double maxEnergy = 200;

    private Player partner = null;


    private float speed = 6.25f;
    private float vx = 0, vy = 0;

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void update(float delta) {
        tryMove(vx * delta, vy * delta);
        subtractEnergy(delta / 3);
    }

    public boolean tryMove(float dx, float dy) {
        position.ChangePosition(dx, dy);

//        if (newX < 0 || newX >= tiles.length || newY < 0 || newY >= tiles[0].length) return false;

//        if (tiles[newX][newY] != TileDescriptionId.WATER) {
//            playerPosition.first += dx;
//            playerPosition.second += dy;
//            return true;
//        }

        return false;
    }

    public Player(User user) {
        this.user = user;
        this.userId = user.getUserId();
        //id ok
        currentBackpack = BackPackType.InitialBackpack;
        FarmingSkill farmingSkill = new FarmingSkill(Skills.Farming, 0);
        ForagingSkill foragingSkill = new ForagingSkill(Skills.Foraging, 0);
        MiningSkill miningSkill = new MiningSkill(Skills.Mining, 0);
        FishingSkill fishingSkill = new FishingSkill(Skills.Fishing, 0);
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
        this.position = new Position(64, 41);
        //TODO set current GL with setter
        //status ok
        this.gender = user.getGender();
//        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
        interactWithPartnerToday = false;
    }

    //    public Direction getMovingDirection() {
//        return movingDirection;
//    }
    public Direction getLastDirection() {
        return lastDirection;
    }

    //    public void setMovingDirection(Direction direction) {
//        this.movingDirection = direction;
//    }
    public void setMovingDirection(Direction d) {
        if (d != null) {
            currentDirection = d;
            lastDirection = d;
        }
    }

    public boolean isMoving() {
        return vx != 0 || vy != 0;
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

    public void toggleUnlimitedEnergy() {
        energy.toggleUnlimited();
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


    public ArrayList<CraftingRecipesList> getToolRecipes() {
        return toolRecipes;
    }

    public void addToolRecipes(CraftingRecipesList toolRecipes) {
        this.toolRecipes.add(toolRecipes);
    }

    public ArrayList<FoodRecipesList> getFoodRecipes() {
        return foodRecipes;
    }

    public void addFoodRecipes(FoodRecipesList foodRecipes) {
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
        return playerFarm.getDefaultHome();
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
        if (partner == null)
            return gold;
        else return gold + partner.getGold();
    }

    public void addGold(int gold) {
        if (partner == null) {
            this.gold += gold;
        } else if (this.gold + gold < 0) {
            //gold is negative
            gold += this.gold;
            this.gold = 0;
            this.partner.gold += gold;
            //gold = -100 this.gold = 20
            //gold += 20 --> -80
            //this.gold = 0
            //partner.gold += -80 tick
        }
    }

    public UUID getPlayerID() {
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

    public ArrayList<NpcFriendship> getNpcFriendShips() {
        return npcFriendships;
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


    public Position getPixelPosition() {
        float pX = position.getX() * 16;
        float pY = position.getY() * 16;
        return new Position(pX, pY);
    }

    public void setPosition(Position position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }


    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Friendship findFriendshipByPlayer(Player player) {
        for (Friendship friendship : friendShips) {
            if (friendship.getPlayer().equals(player)) {
                return friendship;
            }
        }
        return null;
    }

    public Gift findGiftById(String id) {
        for (Gift gift : gifts) {
            if (gift.getGiftID().equals(UUID.fromString(id))) {
                return gift;
            }
        }
        return null;
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public UUID getUserId() {
        return userId;
    }


    public ArrayList<Gift> getMarryRequests() {
        return marryRequests;
    }

    public Player getPartner() {
        return partner;
    }

    public void setPartner(Player partner) {
        this.partner = partner;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public NPC getLastMeetedNpc() {
        return lastMeetedNpc;
    }

    public void setLastMeetedNpc(NPC lastMeetedNpc) {
        this.lastMeetedNpc = lastMeetedNpc;
    }


    public Buff getCurrentBuff() {
        return currentBuff;
    }

    public void setCurrentBuff(Buff currentBuff) {
        if (this.currentBuff != null) {// old buff != null
            this.currentBuff.manageBuff1(this);//disable old buff
        }
        this.currentBuff = currentBuff;
        if (currentBuff != null) {//new buff != null
            this.currentBuff.manageBuff(this);//enable new buff
        }
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            interactWithPartnerToday = false;
            if (fainted) {
                fainted = false;
                energy.setEnergy((int) (energy.getMaxEnergy() * 0.75));
            } else {
                energy.setEnergy(energy.getMaxEnergy());
            }
        } else {
            if (getCurrentBuff() != null && getCurrentBuff().getRemainingTime() == 0) {
                this.setCurrentBuff(null);
            }
        }
    }

    public boolean isInteractWithPartnerToday() {
        return interactWithPartnerToday;
    }

    public void setInteractWithPartnerToday(boolean interactWithPartnerToday) {
        this.interactWithPartnerToday = interactWithPartnerToday;
    }

    public void addEnergy(int amount) {
        if (energy.getEnergy() + amount > maxEnergy) {
            energy.setEnergy(maxEnergy);
        }
        energy.setEnergy(energy.getEnergy() + amount);
    }

    public void subtractEnergy(double amount) {
        energy.setEnergy(Math.max((energy.getEnergy() - amount), 0));
        if (energy.getEnergy() <= 0) {
            fainted = true;
        }
        App.getMe().setEnergyUsage(App.getMe().getEnergyUsage() + amount);
    }

    public Skill getSkillByName(String skillName) {
        for (Skill skill : skills) {
            if (skill.getName().equalsIgnoreCase(skillName)) {
                return skill;
            }
        }
        return null;
    }

    public double getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public double getEnergyUsage() {
        return energyUsage;
    }

    public void setEnergyUsage(double energyUsage) {
        this.energyUsage = energyUsage;
    }

    public void teleportToHome() {
        this.setCurrentGameLocation(this.getPlayerFarm());
        this.setPosition(new Position(getDefaultHome().getPosition().getX() + 5, getDefaultHome().getPosition().getY() + 12));
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }
}
