package model.GameObject.Animal;

public class AnimalProduct {
    private String name;
    private int price;
    private int productionInterval;
    private int chance;


    public AnimalProduct(String name, int price, int productionInterval,int chance) {
        this.name = name;
        this.price = price;
        this.productionInterval = productionInterval;
        this.chance = chance;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getProductionInterval() { return productionInterval; }

}
