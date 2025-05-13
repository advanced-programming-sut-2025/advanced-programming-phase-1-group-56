package model.GameObject.Animal;

import model.Enums.Animals.AnimalType;
import model.GameObject.LivingEntity;
import model.MapModule.Position;
import model.items.AnimalProduct;

import java.util.ArrayList;

public class Animal extends LivingEntity {
    private String name;
    private AnimalType animalInfo;
    private int friendship = 0;
    private int health;//TODO
    private int hungaryBar;//TODO
    private ArrayList<AnimalProduct> products;
    private boolean isFed = false;
    private boolean isCaressed = false;
    private String nickName;

    public Animal(Position position, boolean walkable, String name, AnimalType animalInfo) {
        super(position,walkable);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AnimalProduct> getProducts() {
        return products;
    }

    public void addProducts(AnimalProduct products) {
        this.products.add(products);
    }

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

}
