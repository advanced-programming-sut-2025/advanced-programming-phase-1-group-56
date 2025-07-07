package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.Animals.AnimalProductQuality;
import io.src.model.Enums.Animals.AnimalType;
import io.src.model.Enums.Items.EtcType;
import io.src.model.Enums.Items.ItemQuality;
import io.src.model.Enums.Items.ToolType;
import io.src.model.Enums.Skills;
import io.src.model.Enums.WeatherAndTime.WeatherType;

import io.src.model.GameObject.Animal;
import io.src.model.MapModule.Buildings.Barn;
import io.src.model.MapModule.Buildings.Building;
import io.src.model.MapModule.Buildings.Coop;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.Slot;
import io.src.model.items.AnimalProduct;
import io.src.model.MapModule.Position;
import io.src.model.Result;
import io.src.model.items.Etc;
import io.src.model.items.Item;

import java.util.Random;
import java.util.regex.Matcher;

public class HusbandryController extends CommandController {
    public static Result addAnimal(int x , int y, String name){
        AnimalType animal = AnimalType.findAnimalTypeByName(name);
        if(animal == null){
            return new Result(false, "AnimalType not found");
        }
        Animal animal1 = new Animal(new Position(x,y),"mn",animal);
        App.getMe().addAnimals(animal1);
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).setFixedObject(animal1);
        return new Result(true,"Added Animal " + animal1.getName());
    }
    public static Result petting(Matcher matcher) {
        String name = matcher.group(1).trim();
        Animal animal = returnAnimal(name);
        if (animal == null) {
            return new Result(false, "Animal not found");
        }
        Position position = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPosition();
        boolean isExist = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(position.getX() + i, position.getY() + j).getFixedObject() instanceof Animal) {
                    if (((Animal) (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(position.getX() + i, position.getY() + j).getFixedObject())).getNickName().equals(name)) {
                        isExist = true;
                    }
                }
            }
        }
        if (!isExist) {
            return new Result(false, "Animal is not around u!");
        }
        for (Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal2.getNickName().equalsIgnoreCase(name)) {
                animal2.addFriendShip(15);
                animal2.setCaressed(true);
            }
        }
        return new Result(true, "you petting " + animal.getNickName());

    }

    public static Result showInfoOfAnimal() {
        StringBuilder tmpString = new StringBuilder();
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            tmpString.append("animal's Name : ").append(animal.getNickName()).append("\n").append("your friendShip with him : ")
                    .append(animal.getFriendship()).append("\n")
                    .append("cuddled or not : ").append(animal.getIsCaressed())
                    .append("\n").append("eaten or not : ").append(animal.getIsFed()).append("\n");

        }
        if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals().isEmpty()){
            return new Result(false,"there is no animal");
        }
        return new Result(false,tmpString.toString());
    }

    public static Result shepherdAnimals(Matcher matcher) {
        String animalName = matcher.group(1).trim();
        int x = Integer.parseInt(matcher.group(2));
        int y = Integer.parseInt(matcher.group(3));
        Animal animal = returnAnimal(animalName);
        if (App.getCurrentUser()
                .getCurrentGame()
                .getWeatherState()
                .getTodayWeather() != WeatherType.Sunny) {
            return new Result(true, "your animal can't go out in this weather!");
        } else if (animal == null) {
            return new Result(false, "there is no animal with that name");
        } else if (!(App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() instanceof Farm)) {
            return new Result(false, "you are not in the farm!");
        } else if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).getFixedObject() != null || !App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).isWalkable()) {
            return new Result(true, "there is something exist in this tile!");
        }

        for (Building building : ((Farm) App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation()).getBuildings()) {
            if (building instanceof Coop) {
                Coop coop = (Coop) building;
                if (coop.getCapacity() < coop.getAnimals().size() + 1) {
                    return new Result(false, "this coop have not enough Space!");
                } else if (x <= coop.getWidth() + coop.getPosition().getX() && x > coop.getPosition().getX()) {
                    if (y <= coop.getHeight() + coop.getPosition().getY() && y > coop.getPosition().getY()) {
                        animal.setGoOut(false);
                        for (Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
                            if (animal2.getNickName().equalsIgnoreCase(animalName)) {
                                animal2.setPosition(new Position(x, y));
                            }
                        }
                        return new Result(true, "your animal go back to their coop!");
                    }
                }
            } else if (building instanceof Barn) {
                Barn barn = (Barn) building;
                if (barn.getRemainingCapacity() <= 0) {
                    return new Result(false, "this coop have not enough Space!");
                } else if (x <= barn.getWidth() + barn.getPosition().getX() && x > barn.getPosition().getX()) {
                    if (y <= barn.getHeight() + barn.getPosition().getY() && y > barn.getPosition().getY()) {
                        animal.setGoOut(false);
                        for (Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
                            if (animal2.getNickName().equalsIgnoreCase(animalName)) {
                                animal2.setPosition(new Position(x, y));
                            }
                        }
                        return new Result(true, "your animal go back to their barn!");

                    }
                }
            }
        }
        animal.setGoOut(true);
        animal.setFed(true);
        animal.addFriendShip(8);
        for (Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal2.getNickName().equalsIgnoreCase(animalName)) {
                App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(animal2.getPosition().getX(), animal2.getPosition().getY()).setFixedObject(null);
                Animal animal3 = new Animal(new Position(animal2.getPosition().getX(),animal2.getPosition().getY()),animal2.getNickName(),animal2.getAnimalInfo());
                App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).setFixedObject(animal3);
                return new Result(true, animal2.getNickName() + " have been replaced!");
            }
        }
        return null;

    }

    public static Result feedHay(Matcher matcher) {
        String animalName = matcher.group(1).trim();
        Animal animal = returnAnimal(animalName);

        if (animal == null) {
            return new Result(false, "there is no animal with that name");
        }

        for (Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal2.getNickName().equals(animalName)) {
                animal2.setFed(true);
            }
        }
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(new Etc(EtcType.HAY), 1);
        return new Result(true, animal.getNickName() + " ate.");
    }

    public static Result showProduces() {
        StringBuilder tmpString = new StringBuilder();
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal.getDailyProducts().isEmpty()) {
                continue;
            } else {
                tmpString.append("animal's Name : " + animal.getNickName() + "\n");
                for (AnimalProduct animalProduct : animal.getDailyProducts()) {
                    tmpString.append("animal's Product Name : " + animalProduct.getName() + "\n")
                            .append("animal's Product quality : " + animalProduct.getItemQuality() + "\n");
                }
                tmpString.append("------------------------------");
            }
        }
        if (tmpString.isEmpty()) {
            tmpString.append("there is no animal's products ! yoho");
        }
        return new Result(true, tmpString.toString());
    }

    public static Result collectProduce(Matcher matcher) {
        String name = matcher.group(1).trim();
        Animal animal = returnAnimal(name);
        if (animal == null) {
            return new Result(false, "there is no animal with that name");
        } else if (animal.getDailyProducts().isEmpty()) {
            return new Result(false, "this animal haven't Product!");
        }

        if (animal.getAnimalInfo() == AnimalType.COW) {
            boolean isExist1 = false;
            for (Slot slot : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().getSlots()) {
                Item item = slot.getItem();
                if (item.getName().equals(ToolType.MILK_PAIL.getName())) {
                    App.getCurrentUser().getCurrentGame().getCurrentPlayer().setCurrentItem(item);
                    isExist1 = true;
                }
            }
            if (!isExist1) {
                return new Result(false, "you don't have Milk Pail!");
            }
            AnimalProduct animalProduct = animal.getDailyProducts().get(0);
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().add(animalProduct, 1);
            animal.getDailyProducts().remove(animalProduct);
            animal.addFriendShip(5);

        } else if (animal.getAnimalInfo() == AnimalType.GOAT) {
            boolean isExist = false;
            for (Slot slot : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().getSlots()) {
                Item item = slot.getItem();
                if (item.getName().equals(ToolType.MILK_PAIL.getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                return new Result(false, "you don't have Milk Pail!");
            }
            AnimalProduct animalProduct = animal.getDailyProducts().get(0);
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().add(animalProduct, 1);
            animal.getDailyProducts().remove(animalProduct);
            animal.addFriendShip(5);
        } else if (animal.getAnimalInfo() == AnimalType.SHEEP) {
            boolean isExist = false;
            for (Slot slot : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().getSlots()) {
                Item item = slot.getItem();
                if (item.getName().equals(ToolType.SHEAR.getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                return new Result(false, "you don't have Shear!");
            }
            AnimalProduct animalProduct = animal.getDailyProducts().get(0);
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().add(animalProduct, 1);
            animal.getDailyProducts().remove(animalProduct);
            animal.addFriendShip(5);
        } else if (animal.getAnimalInfo() == AnimalType.PIG) {
            if (!animal.isGoOut()) {
                return new Result(false, "your pig is not in the yard!");
            }
            AnimalProduct animalProduct = animal.getDailyProducts().get(0);
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().add(animalProduct, 1);
            animal.getDailyProducts().remove(animalProduct);
        } else {
            AnimalProduct animalProduct = animal.getDailyProducts().get(0);
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().add(animalProduct, 1);
            animal.getDailyProducts().remove(animalProduct);
        }
        App.getMe().getSkillByName(Skills.Farming.toString()).setXp(App.getMe().getSkillByName(Skills.Farming.toString()).getXp()+5);
        return new Result(true, "you get product from animal!");
    }

    public static Result sellAnimal(Matcher matcher) {
        String userName = matcher.group(1).trim();
        Animal animal = returnAnimal(userName);
        if (animal == null) {
            return new Result(false, "there is no animal with that name");
        }
        int sellPrice = (int) (animal.getAnimalInfo().getPrice() * ((double) (animal.getFriendship() / 1000) + 0.3));
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getAnimals()
                .remove(animal);
        if(animal.getHouse() != null){
            animal.getHouse().getAnimals().remove(animal);
        }
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(animal.getPosition().getX(), animal.getPosition().getY()).setFixedObject(null);
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().addGold(sellPrice);
        return new Result(true, "process for Selling animal ...");

    }

    //CHEAT
    public static Result cheatSetFriendship(Matcher matcher) {
        String name = matcher.group(1).trim();
        int amount = Integer.parseInt(matcher.group(2));
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal.getNickName().equals(name)) {
                animal.addFriendShip(amount);
                animal.setCaressed(true);
                return new Result(true, "you have cheated the friendship");
            }
        }
        return null;
    }

    private static Animal returnAnimal(String animalName) {
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal.getNickName().equals(animalName)) {
                return animal;
            }
        }
        return null;
    }

    private static boolean isUniqueAnimalNickName(String uniqueAnimalName) {
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal.getNickName().equals(uniqueAnimalName)) {
                return false;
            }
        }
        return true;
    }

    public static AnimalProduct getProduct(Animal animal) {
        AnimalProduct animalProduct = null;

        EtcType[] products = animal.getAnimalInfo().getProducts();

        if (products.length == 1) {
            animalProduct = new AnimalProduct(products[0]);
//            animalProduct.setQuality();
        } else {
            Random random = new Random();
            double randomInt = random.nextDouble(0.5, 1.5);
            double probability = (animal.getFriendship() + 150 * randomInt) / 1500;

            if (animal.getFriendship() >= 100 && probability > 0.5) {
                animalProduct = new AnimalProduct(products[1]);
            } else {
                animalProduct = new AnimalProduct(products[0]);
            }
        }

        Random random = new Random();
        double rand = random.nextDouble();
        double qualityValue = ((double) animal.getFriendship() / 1000) * (0.5 + 0.5 * rand);

        if (qualityValue > 0.9) {
            animalProduct.setItemQuality(ItemQuality.Iridium);
        } else if (qualityValue > 0.7) {
            animalProduct.setItemQuality(ItemQuality.Gold);
        } else if (qualityValue > 0.5) {
            animalProduct.setItemQuality(ItemQuality.Silver);
        } else {
            animalProduct.setItemQuality(ItemQuality.Normal);
        }

        return animalProduct;
    }


}
