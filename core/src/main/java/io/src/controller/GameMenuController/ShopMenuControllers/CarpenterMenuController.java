package io.src.controller.GameMenuController.ShopMenuControllers;

import io.src.model.App;
import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.Enums.Items.EtcType;
import io.src.model.Enums.Items.MineralItemType;
import io.src.model.Enums.TileType;
import io.src.model.Game;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.GameObject.ShippingBar;
import io.src.model.GameObject.Well;
import io.src.model.MapModule.Buildings.*;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.Position;
import io.src.model.Player;
import io.src.model.Result;
import io.src.model.items.Etc;
import io.src.model.items.Item;
import io.src.model.items.Mineral;

import java.util.regex.Matcher;

public class CarpenterMenuController implements ShopController {
    public static Result showAllProducts() {
        return ShopController.showAllProducts(
            App.getCurrentUser().getCurrentGame().
                findStoreByClass(CarpentersShop.class).getDailyProductList());
    }

    public static Result showAllAvailableProducts() {
        return ShopController.showAllAvailableProducts(
            App.getCurrentUser().getCurrentGame().
                findStoreByClass(CarpentersShop.class).getDailyProductList());
    }

    public static Result PurchaseProduct(Matcher matcher) {
        return ShopController.purchaseProductFromList(matcher.group(1), matcher.group(2),
            App.getCurrentUser().getCurrentGame().
                findStoreByClass(CarpentersShop.class).getDailyProductList());
    }

    public static Result BuildABuilding(String name, BuildingType buildingType, int x, int y) {
        name = name.toUpperCase();
        Farm farm = App.getMe().getPlayerFarm();

        // 2. lookup type

        if (buildingType == null) {
            return new Result(false, "Unknown Building Type: " + name);
        }


        if (!farm.isWithinBounds(x, y, buildingType.getWidth(), buildingType.getHeight())) {
            return new Result(false, "Position (" + x + "," + y + ") is outside the farm.");
        }

        // 3. collision check
        for (int dx = 0; dx < buildingType.getWidth(); dx++) {
            for (int dy = 0; dy < buildingType.getHeight(); dy++) {
                if (!farm.getTileByPosition(x + dx, y + dy).isWalkable() ||
                    farm.getTileByPosition(x + dx, y + dy).getFixedObject() != null) {
                    return new Result(false, "Cannot build: space occupied at (" + (x + dx) + "," + (y + dy) + ").");
                }
            }
        }
        Game thisGame = App.getCurrentUser().getCurrentGame();
        Player me = App.getMe();
        NpcProduct product = thisGame.findStoreByClass(CarpentersShop.class).findBuildingByType(buildingType);
        if (product == null) {
            return new Result(false, "Shop doesn't has this type of building type ");
        }
        if (product.getRemainingStock() == 0) {
            return new Result(false, "this building is out of stock today");
        }

        // 4. resources check
        int needWood = buildingType.getWoodCount();
        int needStone = buildingType.getStoneCount();
        if (me.getInventory().countItem(new Etc(EtcType.WOOD)) < needWood ||
            me.getInventory().countItem(new Mineral(MineralItemType.STONE)) < needStone) {
            return new Result(false, "Not enough materials: need " +
                needWood + " wood, " + needStone + " stone.");
        }
        if (me.getGold() < product.getPrice()) {
            return new Result(false, "Not enough gold: need " + product.getPrice() + "g.");
        }

        // 5. construct & place
        switch (buildingType) {
            case BuildingType.BARN:
            case BuildingType.BIG_BARN:
            case BuildingType.DELUXE_BARN: {
                Barn newBarn = new Barn(new Position(x, y),  buildingType);
                farm.getTileByPosition(x, y).setFixedObject(newBarn);
                for (int i = x; i < x +  buildingType.getWidth(); i++) {
                    for (int j = y; j < y +  buildingType.getHeight(); j++) {
                        if (i==x && j==y) continue;
                        farm.getTileByPosition(i,j).setWalkable(newBarn.isWalkable());
                    }
                }
                App.getMe().getPlayerFarm().getBuildings().add(newBarn);
                App.getMe().getPlayerFarm().getBuildings().add(newBarn);
                App.getMe().getPlayerFarm().getGameObjects().add(newBarn);
                App.getMe().getPlayerFarm().getTileByPosition(newBarn.getDoorPosition()).setWalkable(true);
                App.getMe().getPlayerFarm().getTileByPosition(newBarn.getDoorPosition()).setTileType(TileType.Wrapper);
            }
            break;
            case BuildingType.COOP:
            case BuildingType.BIG_COOP:
            case BuildingType.DELUXE_COOP: {
                Coop newCoop = new Coop(new Position(x, y), buildingType);
                farm.getTileByPosition(x, y).setFixedObject(newCoop);
                for (int i = x; i < x + buildingType.getWidth(); i++) {
                    for (int j = y; j < y + buildingType.getHeight(); j++) {
                        if (i==x && j==y) continue;
                        farm.getTileByPosition(i, j).setWalkable(newCoop.isWalkable());
                    }
                }
                App.getMe().getPlayerFarm().getBuildings().add(newCoop);
                App.getMe().getPlayerFarm().getBuildings().add(newCoop);
                App.getMe().getPlayerFarm().getGameObjects().add(newCoop);
                App.getMe().getPlayerFarm().getTileByPosition(newCoop.getDoorPosition()).setWalkable(true);
                App.getMe().getPlayerFarm().getTileByPosition(newCoop.getDoorPosition()).setTileType(TileType.Wrapper);
            }
            break;
            case BuildingType.WELL: {
                Well newWell = new Well(new Position(x, y));
                for (int i = x; i < x + buildingType.getWidth(); i++) {
                    for (int j = y; j < y + buildingType.getHeight(); j++) {
                        farm.getTileByPosition(i, j).setFixedObject(newWell);
                        farm.getTileByPosition(i, j).setTileType(TileType.Water);
                    }
                }
                App.getMe().getPlayerFarm().getGameObjects().add(newWell);

            }
            break;
            case BuildingType.SHIPPING_BIN: {
                ShippingBar newBin = new ShippingBar(new Position(x, y), farm);
                for (int i = x; i < x + buildingType.getWidth(); i++) {
                    for (int j = y; j < y + buildingType.getHeight(); j++) {
                        farm.getTileByPosition(i, j).setFixedObject(newBin);
                    }
                }
                App.getMe().getPlayerFarm().getGameObjects().add(newBin);
            }
            break;
        }

        Item wood = App.getMe().getInventory().findItemByName(new Etc(EtcType.WOOD).getName());
        Item stone = App.getMe().getInventory().findItemByName(new Mineral(MineralItemType.STONE).getName());
        me.getInventory().remove(wood, needWood);
        me.getInventory().remove(stone, needStone);
        me.addGold(-product.getPrice());
        product.setRemainingStock(product.getRemainingStock() - 1);

        return new Result(true, name + " built at (" + x + "," + y + ").");
    }

    public static Result ExitShop() {
        return ShopController.exitShopMenu(CarpentersShop.class);
    }
}

