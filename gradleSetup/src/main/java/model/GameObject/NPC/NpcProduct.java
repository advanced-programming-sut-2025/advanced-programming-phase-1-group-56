package model.GameObject.NPC;

import model.Enums.WeatherAndTime.Seasons;
import model.items.Saleable;

public class NpcProduct{
    private final String name;
    private final Saleable saleable;
    private final String description;
    private final int price;
    private final int outOfSeasonPrice;
    private final Seasons[] seasons;
    private final int dailyStock;
    private int remainingStock;

    public NpcProduct(String name,Saleable saleable, String description, int price, int outOfSeasonPrice, Seasons[] seasons, int dailyStock) {
        this.name = name;
        this.saleable = saleable;
        this.description = description;
        this.price = price;
        this.outOfSeasonPrice = outOfSeasonPrice;
        this.seasons = seasons;
        this.dailyStock = dailyStock;
        remainingStock = dailyStock;
    }

    public int getRemainingStock() {
        return remainingStock;
    }

    public void setRemainingStock(int remainingStock) {
        this.remainingStock = remainingStock;
    }

    public void changeRemainingStock(int amount) {
        this.remainingStock += amount;
    }

    public int getDailyStock() {
        return dailyStock;
    }

    public Seasons[] getSeasons() {
        return seasons;
    }

    public int getOutOfSeasonPrice() {
        return outOfSeasonPrice;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Saleable getSaleable() {
        return saleable;
    }
}
