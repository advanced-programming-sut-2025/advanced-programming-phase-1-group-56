package model.GameObject;

import model.Enums.Animals.AnimalType;
import model.Enums.Items.EtcType;
import model.GameObject.LivingEntity;
import model.MapModule.Buildings.AnimalHouse;
import model.MapModule.Position;
import model.items.AnimalProduct;
import model.items.Saleable;

import java.util.ArrayList;
import java.util.Arrays;

public class Animal extends LivingEntity implements Saleable {
    private String name;
    private AnimalType animalInfo;
    private final ArrayList<AnimalProduct> dailyProducts = new ArrayList<>();
    private int friendship = 0;
    private boolean isFed = false;
    private boolean isCaressed = false;
    private String nickName;
    AnimalHouse house;

    public Animal(Position position, String name, AnimalType animalInfo) {
        super(position,true);
        this.name = name;
        this.animalInfo = animalInfo;
    }

    public void pet() {
        friendship += 15;
    }

    public int getFriendship() {
        return friendship;
    }

    public void addFriendShip(int countFriendShip) {
        friendship += countFriendShip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EtcType> getProducts() {
        return new ArrayList<>(Arrays.asList(animalInfo.getProducts()));
    }

    public ArrayList<AnimalProduct> getDailyProducts() { return dailyProducts; }

    public boolean getIsFed() {
        return isFed;
    }

    public void setFed(boolean fed) {
        isFed = fed;
    }

    public boolean getIsCaressed() {
        return isCaressed;
    }

    public void setCaressed(boolean caressed) {
        isCaressed = caressed;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void produceProductOnDay(){//TODO

    }

    public AnimalType getAnimalInfo() {
        return animalInfo;
    }

    public void setAnimalInfo(AnimalType animalInfo) {
        this.animalInfo = animalInfo;
    }

    @Override
    public String getName() {
        return name;
    }
}
