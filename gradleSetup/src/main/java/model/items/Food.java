package model.items;

import java.util.HashMap;

public class Food extends Item {
    private int energy;
    private int price;
    private String buff;

    public Food(String name, int maxStackSize,int energy, int price, String buff) {
        super(name,maxStackSize,true);
        this.name = name;
        this.energy = energy;
        this.price = price;
        this.buff = buff;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public int getPrice() {
        return price;
    }

    public String getBuff() {
        return buff;
    }

    public void setBuff(String buff) {
        this.buff = buff;
    }
}
