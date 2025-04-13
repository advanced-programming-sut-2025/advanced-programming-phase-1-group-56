package model;

import model.Activities.CookFood;
import model.Activities.CraftTool;
import model.Enums.Items.BackPackType;
import model.Enums.Items.TrashcanType;
import model.GameObject.LivingEntity;
import model.Locations.Building;
import model.States.Energy;
import model.items.Inventory;
import model.skills.Skill;

import java.util.ArrayList;

public class Player {
    private ArrayList<Skill> skills;
    private ArrayList<CraftTool> toolRecipes;
    private ArrayList<CookFood> foodRecipes;
    private ArrayList<Building> building;
    private Building defaultHome;
    private ArrayList<LivingEntity> livingEntities;
    private Inventory inventory;
    private Energy energy;
    private BackPackType currentBackpack;
    private TrashcanType currentTrashcan;
    private Inventory playerInventory;


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

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public void setPlayerInventory(Inventory playerInventory) {
        this.playerInventory = playerInventory;
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


    public ArrayList<LivingEntity> getLivingEntities() {
        return livingEntities;
    }

    public void addLivingEntities(LivingEntity livingEntities) {
        this.livingEntities.add(livingEntities);
    }

    public ArrayList<Building> getBuilding() {
        return building;
    }

    public void addBuilding(Building building) {
        this.building.add(building);
    }
}