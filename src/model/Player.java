package model;

import model.Enums.BackPackType;
import model.Enums.TrashcanType;
import model.skills.Skill;

import java.util.ArrayList;

public class Player {
    private ArrayList<Skill> skills;
    private ArrayList<ToolRecipes> toolRecipes;
    private ArrayList<FoodRecipes> foodRecipes;
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

    public ArrayList<ToolRecipes> getToolRecipes() {
        return toolRecipes;
    }

    public void addToolRecipes(ToolRecipes toolRecipes) {
        this.toolRecipes.add(toolRecipes);
    }

    public ArrayList<FoodRecipes> getFoodRecipes() {
        return foodRecipes;
    }

    public void setFoodRecipes(FoodRecipes foodRecipes) {
        this.foodRecipes.add(foodRecipes);
    }
}