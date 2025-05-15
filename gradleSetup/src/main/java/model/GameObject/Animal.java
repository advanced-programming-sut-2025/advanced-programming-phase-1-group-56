package model.GameObject;

import model.App;
import model.Enums.Animals.AnimalType;
import model.GameObject.LivingEntity;
import model.MapModule.Position;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;
import model.items.AnimalProduct;

import java.util.ArrayList;

public class Animal extends LivingEntity implements TimeObserver {
    private String name;
    private AnimalType animalInfo;
    private int friendship = 0;
    private final ArrayList<AnimalProduct> dailyProducts = new ArrayList<>();
    private boolean isFed = false;
    private boolean isCaressed = false;
    private String nickName;

    public Animal(Position position, boolean walkable, String name, AnimalType animalInfo) {
        super(position,walkable);
        this.name = name;
        this.animalInfo = animalInfo;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    public void pet() {
        friendship += 15;
    }

    public int getFriendship() {
        return friendship;
    }

    public void addFriendShip(int countFriendShip) {
        if(friendship + countFriendShip > 1000){
            this.friendship = 1000;
        }else{
            friendship += countFriendShip;
        }
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

    public AnimalType getAnimalInfo() {
        return animalInfo;
    }

    public void setAnimalInfo(AnimalType animalInfo) {
        this.animalInfo = animalInfo;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {

    }
    public class Animal extends LivingEntity implements Saleable {
        private String name;
        private AnimalType animalInfo;

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
