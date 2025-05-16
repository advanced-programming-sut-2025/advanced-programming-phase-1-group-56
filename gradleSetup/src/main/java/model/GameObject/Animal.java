package model.GameObject;

import controller.GameMenuController.FarmingController;
import controller.GameMenuController.HusbandryController;
import model.App;
import model.Enums.Animals.AnimalType;
import model.Enums.Items.EtcType;
import model.GameObject.LivingEntity;
import model.MapModule.Buildings.AnimalHouse;
import model.MapModule.Position;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;
import model.items.AnimalProduct;
import model.items.Saleable;

import java.util.ArrayList;
import java.util.Arrays;

public class Animal extends LivingEntity implements Saleable, TimeObserver {
    private String name;
    private AnimalType animalInfo;
    private final ArrayList<AnimalProduct> dailyProducts = new ArrayList<>();
    private int friendship = 0;
    private boolean isFed;
    private boolean isCaressed;
    private String nickName;
    private boolean goOut;
    private AnimalHouse house;
    private int produce;

    public Animal(Position position, String name, AnimalType animalInfo) {
        super(position,true);
        this.name = name;
        this.animalInfo = animalInfo;
        this.isFed = false;
        this.isCaressed = false;
        this.goOut = false;
        this.produce = 0;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
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

    public AnimalHouse getHouse() {
        return house;
    }

    public void setHouse(AnimalHouse house) {
        this.house = house;
    }

    public boolean isGoOut() {
        return goOut;
    }

    public void setGoOut(boolean goOut) {
        this.goOut = goOut;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            if(isFed){
                produce++;
            }if (produce == animalInfo.getProductionInterval()){
                dailyProducts.clear();
                dailyProducts.add(HusbandryController.getProduct(this));
                produce = 0;
            }

            if(!isFed){
                friendship -= 20;
            }
            if(goOut){
                friendship -= 20;
            }
            if(!isCaressed){
                friendship -= 10;
            }

        }
    }

    public int getProduce() {
        return produce;
    }

    public void setProduce(int produce) {
        this.produce = produce;
    }



}
