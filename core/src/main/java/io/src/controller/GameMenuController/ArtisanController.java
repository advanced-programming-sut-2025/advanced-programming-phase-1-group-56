package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.*;
import io.src.model.Enums.Items.*;
import io.src.model.GameObject.ArtesianMachine;
import io.src.model.MapModule.Tile;
import io.src.model.items.Artesian;
import io.src.model.items.ArtisanGood;
import io.src.model.items.Fish;
import io.src.model.items.Item;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ArtisanController extends CommandController {
    public static Result useArtisan(Matcher matcher) {
        String machineName = matcher.group(1).trim();
        String productName = matcher.group(2).trim();
        String[] productsName = productName.split(" ");
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                Tile tile = player.getCurrentGameLocation().getTileByPosition(
                    (int)player.getPosition().getX() - 1 + i,
                    (int)player.getPosition().getY() - 1 + j
                );
                if (tile == null) continue;

                if (tile.getFixedObject() instanceof ArtesianMachine artesianMachine) {
                    if (artesianMachine.getArtisanMachineType().getName().equals(machineName)) {
                        for (ArtisanGoodType product : artesianMachine.getArtisanMachineType().getProducts()) {
                            for (String str : productsName) {
                                if (player.getInventory().findItemByName(str) == null) {
                                    return new Result(false, "this item doesn't fount in your inventory.");
                                }

                                boolean found2 = false;
                                for (Slot ingredient : product.getIngredients()) {
                                    if (ingredient.getItem().getName().equals("Any Fish")) {
                                        if (FishType.fromName(str) != null) {
                                            found2 = true;
                                        }
                                    } else if (ingredient.getItem().getName().equals("Any Fruit")) {
                                        if (FruitType.fromName(str) != null) {
                                            found2 = true;
                                        }
                                    } else if (ingredient.getItem().getName().equals("Any Ore")) {
                                        ArrayList<MineralItemType> Ores = new ArrayList<>();
                                        Ores.add(MineralItemType.COPPER_ORE);
                                        Ores.add(MineralItemType.IRON_ORE);
                                        Ores.add(MineralItemType.GOLD_ORE);
                                        Ores.add(MineralItemType.IRIDIUM_ORE);
                                        for (MineralItemType ore : Ores) {
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
                }
            }
        }
        return new Result(false, "you dont near to such artisan machine");
    }

    public static Result getArtisan(Matcher matcher) {
        String machineName = matcher.group(1).trim();
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        player.getInventory().add(new ArtisanGood(ArtisanGoodType.SMOKED_FISH), 1);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int x = (int)player.getPosition().getX() + dx;
                int y = (int)player.getPosition().getY() + dy;

                Tile tile = player.getCurrentGameLocation().getTileByPosition(x, y);
                if (tile == null) continue;


                if (tile.getFixedObject() instanceof ArtesianMachine artesianMachine) {
                    if (artesianMachine.getArtisanMachineType().getName().equals(machineName)) {
                        if (artesianMachine.getArtisanGood() == null) {
                            return new Result(false, "the product isn't ready yet");
                        }
                        player.getInventory().add(artesianMachine.getArtisanGood(), 1);
                        artesianMachine.setArtisanGood(null);
                        return new Result(true, "the product was added to your inventory");
                    }
                }
            }
        }

        return new Result(false, "oh baby");
    }

}
