package model.Enums.Items;

import model.Enums.BuffType;
import model.items.Food;

public enum FoodType implements ItemType {

    //food that make with recepie
    FRIED_EGG("Fried Egg", 50,35 , null),
    BAKED_FISH("Baked Fish", 75,100 , null),
    SALAD("Salad", 113,110 , null),
    OMELETTE("Omelette", 100,125 , null),
    PUMPKIN_PIE("Pumpkin Pie", 225,385 , null),
    SPAGHETTI("Spaghetti", 75,120 , null),
    PIZZA("Pizza", 150,300 , null),
    TORTILLA("Tortilla", 50, 50 , null),
    MAKI_ROLL("Maki Roll", 100,100 , null),
    TRIPLE_SHOT_ESPRESSO("Triple Shot Espresso", 200, 450, BuffType.MaxEnergy100),
    COOKIE("Cookie", 90, 140, null),
    HASH_BROWNS("Hash Browns", 90, 120 , BuffType.FarmingBuff),
    PANCAKES("Pancakes", 90, 80 , BuffType.ForagingBuff11),
    FRUIT_SALAD("Fruit Salad", 263,450 , null),
    RED_PLATE("Red Plate", 240, 400 ,  BuffType.MaxEnergy50),
    BREAD("Bread", 50, 60 , null),
    SALMON_DINNER("Salmon Dinner", 125, 300 , null),
    VEGETABLE_MEDLEY("Vegetable Medley", 165, 120 , null),
    FARMERS_LUNCH("Farmer's Lunch", 200, 150 , BuffType.FarmingBuff),
    SURVIVAL_BURGER("Survival Burger", 125, 180 , BuffType.ForagingBuff5),
    DISH_OF_THE_SEA("Dish O' The Sea", 150, 220 , BuffType.FishingBuff5),
    SEAFORM_PUDDING("Seaform Pudding", 175, 300 , BuffType.FishingBuff10),
    MINERS_TREAT("Miner's Treat", 125, 200 ,  BuffType.MiningBuff),


    //crop
    BLUE_JAZZ("Blue Jazz", 45, 50,null),
    CARROT("Carrot", 75, 35 , null),
    CAULIFLOWER("Cauliflower", 75,175 , null),
    COFFEE_BEAN("Coffee Bean", 0, 15 , null),
    GARLIC("Garlic", 20, 60 , null),
    GREEN_BEAN("Green Bean", 25, 40 , null),
    KALE("Kale", 50, 110 , null),
    PARSNIP("Parsnip", 25, 35 , null),
    POTATO("Potato", 25, 80 , null),
    RHUBARB("Rhubarb", 0, 220 , null),
    STRAWBERRY("Strawberry", 50, 120 , null),
    TULIP("Tulip", 45, 30 , null),
    UNMILLED_RICE("Unmilled Rice", 3, 30 , null),
    BLUEBERRY("Blueberry", 25, 50 , null),
    CORN("Corn", 25, 50 , null),
    HOPS("Hops", 45, 25 , null),
    HOT_PEPPER("Hot Pepper", 13, 40 , null),
    MELON("Melon", 113, 250 , null),
    POPPY("Poppy", 45, 140 , null),
    RADISH("Radish", 45, 90 , null),
    RED_CABBAGE("Red Cabbage", 75, 260 , null),
    STARFRUIT("Starfruit", 125, 750 , null),
    SUMMER_SPANGLE("Summer Spangle", 45, 90 , null),
    SUMMER_SQUASH("Summer Squash", 63, 45 , null),
    SUNFLOWER("Sunflower", 45, 80 , null),
    TOMATO("Tomato", 20, 60 , null),
    WHEAT("Wheat", 0, 25 , null),
    AMARANTH("Amaranth", 50, 150 , null),
    ARTICHOKE("Artichoke", 30, 160 , null),
    BEET("Beet", 30, 100 , null),
    BOK_CHOY("Bok Choy", 25, 80 , null),
    BROCCOLI("Broccoli", 63, 70 , null),
    CRANBERRIES("Cranberries", 38, 75 , null),
    EGGPLANT("Eggplant", 20, 60 , null),
    FAIRY_ROSE("Fairy Rose", 45, 290 , null),
    GRAPE("Grape", 38, 80 , null),
    PUMPKIN("Pumpkin", 0, 320 , null),
    YAM("Yam", 45, 160 , null),
    SWEET_GEM_BERRY("Sweet Gem Berry", 0, 3000 , null),
    POWDER_MELON("Powder Melon", 63, 60 , null),
    ANCIENT_FRUIT("Ancient Fruit", 0, 550 , null),

    //food
    DAFFODIL("Daffodil", 0, 30 , null),
    DANDELION("Dandelion", 25, 40 , null),
    LEEK("Leek", 40, 60 , null),
    MOREL("Morel", 20, 150 , null),
    SALMON_BERRY("Salmon Berry", 13, 8 , null),
    SPRING_ONION("Spring Onion", 13, 8 , null),
    WILD_HORSERADISH("Wild Horseradish", 13, 50 , null),
    FIDDLE_HEAD_FERN("Fiddle Head Fern", 25, 90 , null),
    RED_MUSHROOM("Red Mushroom", -50, 75 , null),
    SPICE_BERRY("Spice Berry", 25, 80 , null),
    SWEET_PEA("Sweet Pea", 0, 50 , null),
    BLACKBERRY("Black Berry", 25, 25 , null),
    CHANTERELLE("Chanterelle", 75, 160 , null),
    HAZELNUT("Hazelnut", 38, 40 , null),
    PURPLE_MUSHROOM("Purple Mushroom", 30, 90 , null),
    WILD_PLUM("Wild Plum", 25, 80 , null),
    CROCUS("Crocus", 0, 60 , null),
    CRYSTAL_FRUIT("Crystal Fruit", 63, 150 , null),
    HOLLY("Holly", -37, 80 , null),
    SNOW_YAM("Snow Yam", 30, 100 , null),
    WINTER_ROOT("Winter Root", 25, 70 , null),
    BEER("Beer", 50, 200 , null),
    COFFEE("Coffee", 3, 150 , null),
    JOJA_COLA("Joja Cola", 13, 25 , null),
    SUGAR("Sugar", 25, 50 , null),
    WHEAT_FLOUR("Wheat Flour", 13, 50 , null),
    RICE("Rice", 13, 100 , null),
    OIL("Oil", 13, 100 , null),
    VINEGAR("Vinegar", 13, 100 , null),
    TROUT_SOUP("Trout Soup", 100, 100 , null),

    //food that make with Artisan
    HONEY("Honey", 0, 100 , null),
    MAYONNAISE("Mayonnaise", 50, 190 , null),
    DUCK_MAYONNAISE("Duck Mayonnaise", 75, 375 , null),
    DINOSAUR_MAYONNAISE("Dinosaur Mayonnaise", 125, 800 , null),
    TRUFFLE_OIL("Truffle Oil", 38, 1065 , null),
    CHEESE("Cheese", 125, 230 , null),
    GOAT_CHEESE("Goat Cheese", 125, 400 , null),
    MEAD("Mead", 75, 300 , null),
    PALE_ALE("Pale Ale", 50, 300 , null),
    RAISINS("Raisins", 125, 600 , null),
    //Determined based on base ingredient.
    WINE("Wine", 0, 0 , null),
    JUICE("Juice", 0, 0 , null),
    DRIED_MUSHROOMS("Dried Mushrooms", 50, 0 , null),
    DRIED_FRUIT("Dried Fruit", 75, 0 , null),
    PICKLES("Pickles", 0, 0 , null),
    JELLY("Jelly", 0, 0 , null),
    SMOKED_FISH("Smoked Fish", 0, 0 , null);

    private String name;
    private int energy;
    private int price;
    private BuffType buffType;

    FoodType(String name, int energy, int price, BuffType buffType){
        this.name = name;
        this.energy = energy;
        this.price = price;
        this.buffType = buffType;
    }

    public String getName(){
        return name;
    }
    public int getEnergy(){
        return energy;
    }
    public int getPrice(){
        return price;
    }
    public BuffType getBuffType(){
        return buffType;
    }

}
