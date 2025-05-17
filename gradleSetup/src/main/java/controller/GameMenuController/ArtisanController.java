package controller.GameMenuController;

import controller.CommandController;
import model.*;
import model.Enums.Items.ArtisanGoodType;
import model.Enums.Items.FishType;
import model.Enums.Items.FruitType;
import model.Enums.Items.Ore;
import model.GameObject.ArtesianMachine;
import model.items.Artesian;
import model.items.ArtisanGood;
import model.items.Fish;
import model.items.Item;

import java.util.regex.Matcher;

public class ArtisanController extends CommandController {
    public static Result useArtisan(Matcher matcher) {
        String machineName = matcher.group(1);
        String productName = matcher.group(2);
        String[] productsName = productName.split(" ");
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() - 1 + i, player.getPosition().getY() - 1 + j).getFixedObject() instanceof ArtesianMachine artesianMachine) {
                    if (artesianMachine.getArtisanMachineType().getName().equals(machineName)) {
                        for (ArtisanGoodType product : artesianMachine.getArtisanMachineType().getProducts()) {
//                            boolean found = false;
                            for (String str : productsName) {
                                if (player.getInventory().findItemByName(str) == null) {
                                    return new Result(false, "this item doesn't fount in your inventory.");
                                }
                                boolean found2 = false;
                                for (Slot ingredient : product.getIngredients()) {
                                    if (ingredient.getItem().getName().equals("Any Fish")) {
                                        for (FishType fishType : FishType.values()) {
                                            if (fishType.getName().equals(str)) {
                                                found2 = true;
                                            }
                                        }
                                    } else if (ingredient.getItem().getName().equals("Any Fruit")) {
                                        for (FruitType fruitType : FruitType.values()) {
                                            if (fruitType.getName().equals(str)) {
                                                found2 = true;
                                            }
                                        }
                                    } else if (ingredient.getItem().getName().equals("Any Ore")) {
                                        for (Ore ore : Ore.values()) {
                                            if (ore.getName().equals(str)) {
                                                found2 = true;
                                            }
                                        }
                                    } else if (ingredient.getItem().getName().equals(str)) {
                                        found2 = true;
                                    }
                                }
                                if (!found2) {
                                    return new Result(false, "you input the wrong items for this artisan machine");
                                }
                            }

                            artesianMachine.startMakeArtisanGood(product);
                            player.subtractEnergy(product.getEnergy());
                            return new Result(true, "start preparing artisan product");
                        }
                    }
                    return new Result(false, "you dont near any" + machineName);
                }
//                return new Result(false , "you dont near any artisan machine");
            }
        }
        return new Result(false, "you dont near to such artisan machine");
    }

    public static Result getArtisan(Matcher matcher) {
        String machineName = matcher.group(1);
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() - 1 + i, player.getPosition().getY() - 1 + i).getFixedObject() instanceof ArtesianMachine artesianMachine) {
                    if (artesianMachine.getArtisanMachineType().getName().equals(machineName)) {
                        if (artesianMachine.getArtisanGood() == null) {
                            return new Result(false, "the product doesn't ready");
                        }
                        player.getInventory().add(artesianMachine.getArtisanGood(), 1);
                        artesianMachine.setArtisanGood(null);
                        return new Result(true, "the product added to your inventory");

                    }
                    return new Result(false, "this artisan machine doesn't exist");
                }
            }
        }
        return new Result(false, "you dont near a artisan machine");
    }


}
