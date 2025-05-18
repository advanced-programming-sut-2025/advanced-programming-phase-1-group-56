package controller.GameMenuController.ShopMenuControllers;

import model.App;
import model.Enums.Animals.AnimalType;
import model.GameObject.Animal;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Buildings.*;
import model.MapModule.GameLocations.Farm;
import model.MapModule.Position;
import model.Player;
import model.Result;

import java.util.Optional;
import java.util.regex.Matcher;

public class MarniesRanchController implements ShopController {
    public static Result showAllProducts() {
        return ShopController.showAllProducts(
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(MarniesRanch.class).getDailyProductList());
    }

    public static Result showAllAvailableProducts() {
        return ShopController.showAllAvailableProducts(
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(MarniesRanch.class).getDailyProductList());
    }

    public static Result PurchaseProduct(Matcher matcher) {
        return ShopController.purchaseProductFromList(matcher,
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(MarniesRanch.class).getDailyProductList());
    }

    public static Result buyAnimal(Matcher matcher) {
        // Parse arguments
        String typeName = matcher.group(1).trim().trim();   // e.g. "COW"
        String nickName = matcher.group(2).trim().trim();                            // unique name

        // Validate nickname
        if (nickName.isEmpty()) {
            return new Result(false, "You must provide a unique name for your animal.");
        }
        // Prevent duplicate names
        Player me = App.getMe();
        boolean nameTaken = me.getAnimals().stream()
                .anyMatch(a -> a.getNickName().equalsIgnoreCase(nickName));
        if (nameTaken) {
            return new Result(false, "That name is already used by another animal.");
        }

        MarniesRanch shop = App.getCurrentUser()
                .getCurrentGame()
                .findStoreByClass(MarniesRanch.class);
        AnimalType animalType = AnimalType.findAnimalTypeByName(typeName);
        if (animalType == null) {
            return new Result(false, "Unknown animal type: " + typeName);
        }

        NpcProduct prod = shop.findProductByAnimalType(animalType);
        if (prod == null) {
            return new Result(false, "Marnie does not sell a " + typeName + ".");
        }

        if (prod.getRemainingStock() <= 0) {
            return new Result(false, typeName + " is out of stock today.");
        }

        // Find a building with capacity
        Farm farm = me.getPlayerFarm();
        Optional<Building> optHome = findBuildingWithCapacity(animalType);
        if (optHome.isEmpty()) {
            return new Result(false,
                    "No available " + animalType.getRequiredBuildingType() +
                            " has free capacity. Build one first.");
        }
        AnimalHouse home = (AnimalHouse) optHome.get();

        // Check gold
        int price = prod.getPrice();
        if (me.getGold() < price) {
            return new Result(false,
                    "Not enough gold. You have " + me.getGold() +
                            "g but need " + price + "g.");
        }

        // Deduct costs and update store
        me.addGold(-price);
        prod.setRemainingStock(prod.getRemainingStock() - 1);

        // Instantiate and place the animal
        Animal newAnimal = new Animal(new Position(3, 3), nickName, animalType);
        home.getAnimals().add(newAnimal);
        me.getAnimals().add(newAnimal);

        return new Result(true,
                "Successfully purchased " + nickName +
                        " the " + animalType + " and placed in " +
                        home.getType() + ".");
    }

    public static Optional<Building> findBuildingWithCapacity(AnimalType type) {
        return App.getMe().getPlayerFarm().getBuildings().stream()
                .filter(b -> b instanceof Barn barn &&
                        barn.getType().getCapacity() - barn.getCurrentAnimalCount() > 0).findFirst();
    }

    public static Result ExitShop(){
        return ShopController.exitShopMenu(MarniesRanch.class);
    }

}
