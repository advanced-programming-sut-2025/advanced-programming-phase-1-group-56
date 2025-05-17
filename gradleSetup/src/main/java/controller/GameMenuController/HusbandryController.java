package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.Animals.AnimalProductQuality;
import model.Enums.Animals.AnimalType;
import model.Enums.Items.EtcType;
import model.Enums.Items.ItemQuality;
import model.Enums.Items.ToolType;
import model.Enums.Skills;
import model.Enums.WeatherAndTime.WeatherType;

import model.GameObject.Animal;
import model.MapModule.Buildings.Barn;
import model.MapModule.Buildings.Building;
import model.MapModule.Buildings.Coop;
import model.MapModule.GameLocations.Farm;
import model.Slot;
import model.items.AnimalProduct;
import model.MapModule.Position;
import model.Result;
import model.items.Etc;
import model.items.Item;

import java.util.Random;
import java.util.regex.Matcher;

public class HusbandryController extends CommandController {

    public static Result petting(Matcher matcher) {
        String name = matcher.group(1);
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
            if (animal2.getName().equalsIgnoreCase(name)) {
                animal2.addFriendShip(15);
                animal2.setCaressed(true);
            }
        }
        return new Result(true, "you petting " + animal.getName());

    }

    public static Result showInfoOfAnimal() {
        StringBuilder tmpString = new StringBuilder();
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            tmpString.append("animal's Name : ").append(animal.getName()).append("\n").append("your friendShip with him : ")
                    .append(animal.getFriendship()).append("\n")
                    .append("cuddled or not : ").append(animal.getIsCaressed())
                    .append("\n").append("eaten or not : ").append(animal.getIsFed()).append("\n");

        }
        return new Result(false,tmpString.toString());
    }

    public static Result shepherdAnimals(Matcher matcher) {
        String animalName = matcher.group(1);
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
                            if (animal2.getName().equalsIgnoreCase(animalName)) {
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
                            if (animal2.getName().equalsIgnoreCase(animalName)) {
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
            if (animal2.getName().equalsIgnoreCase(animalName)) {
                animal2.setPosition(new Position(x, y));
            }
        }
        return new Result(true, "your animal has go out in this weather!");
    }

    public static Result feedHay(Matcher matcher) {
        String animalName = matcher.group(1);
        Animal animal = returnAnimal(animalName);

        if (animal == null) {
            return new Result(false, "there is no animal with that name");
        }

        for (Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal2.getName().equals(animalName)) {
                animal2.setFed(true);
            }
        }
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(new Etc(EtcType.HAY), 1);
        return null;
    }

    public static Result showProduces() {
        StringBuilder tmpString = new StringBuilder();
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if (animal.getDailyProducts().isEmpty()) {
                continue;
            } else {
                tmpString.append("animal's Name : " + animal.getName() + "\n");
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
        String name = matcher.group(1);
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
        String userName = matcher.group(1);
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
        animal.getHouse().getAnimals().remove(animal);
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().addGold(sellPrice);
        return new Result(true, "process for Selling animal ...");
    }

    //CHEAT
    public static Result cheatSetFriendship(Matcher matcher) {
        String name = matcher.group(1);
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
