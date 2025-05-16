package controller.GameMenuController.ShopMenuControllers;

import model.App;
import model.Enums.Buildings.BuildingType;
import model.Enums.Items.EtcType;
import model.Enums.Items.MineralItemType;
import model.Enums.Stores.BlackSmithProducts;
import model.Enums.Stores.CarpenterShopProducts;
import model.Game;
import model.GameObject.NPC.NpcProduct;
import model.GameObject.ShippingBar;
import model.GameObject.Well;
import model.MapModule.Buildings.*;
import model.MapModule.GameLocations.Farm;
import model.MapModule.GameLocations.GameLocation;
import model.MapModule.Position;
import model.Player;
import model.Result;
import model.items.Etc;
import model.items.Item;
import model.items.Mineral;

import java.util.ArrayList;
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
        return ShopController.purchaseProductFromList(matcher,
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(CarpentersShop.class).getDailyProductList());
    }

    public static Result BuildABuilding(Matcher matcher) {
        String name = matcher.group(1).toUpperCase();
        String[] coords = matcher.group(2).split(",");
        int x, y;
        try {
            x = Integer.parseInt(coords[0].trim());
            y = Integer.parseInt(coords[1].trim());
        } catch (Exception e) {
            return new Result(false, "Coordinates must be integers (x,y).");
        }

        Farm farm = App.getMe().getPlayerFarm();

        // 2. lookup type
        BuildingType type = BuildingType.getTypeByName(name);

        if (type == null) {
            return new Result(false, "Unknown Building Type: " + name);
        }


        if (!farm.isWithinBounds(x, y, type.getWidth(), type.getHeight())) {
            return new Result(false, "Position (" + x + "," + y + ") is outside the farm.");
        }

        // 3. collision check
        for (int dx = 0; dx < type.getWidth(); dx++) {
            for (int dy = 0; dy < type.getHeight(); dy++) {
                if (!farm.getTileByPosition(x + dx, y + dy).isWalkable() ||
                        farm.getTileByPosition(x + dx, y + dy).getFixedObject() != null) {
                    return new Result(false, "Cannot build: space occupied at (" + (x + dx) + "," + (y + dy) + ").");
                }
            }
        }
        Game thisGame = App.getCurrentUser().getCurrentGame();
        Player me = App.getMe();
        NpcProduct product = thisGame.findStoreByClass(CarpentersShop.class).findBuildingByType(type);
        if (product == null) {
            return new Result(false, "Shop doesn't has this type of building type ");
        }
        if (product.getRemainingStock() == 0) {
            return new Result(false, "this building is out of stock today");
        }

        // 4. resources check
        int needWood = type.getWoodCount();
        int needStone = type.getStoneCount();
        if (me.getInventory().countItem(new Etc(EtcType.WOOD)) < needWood ||
                me.getInventory().countItem(new Mineral(MineralItemType.STONE)) < needStone) {
            return new Result(false, "Not enough materials: need " +
                    needWood + " wood, " + needStone + " stone.");
        }
        if (me.getGold() < product.getPrice()) {
            return new Result(false, "Not enough gold: need " + product.getPrice() + "g.");
        }

        // 5. construct & place
        switch (type) {
            case BuildingType.BARN:
            case BuildingType.BIG_BARN:
            case BuildingType.DELUXE_BARN: {
                Barn newBarn = new Barn(new Position(x, y), type);
                for (int i = x; i < x + type.getWidth(); i++) {
                    for (int j = y; j < y + type.getHeight(); j++) {
                        farm.getTileByPosition(i, j).setFixedObject(newBarn);
                    }
                }
                App.getMe().getPlayerFarm().getBuildings().add(newBarn);
            }
            break;
            case BuildingType.COOP:
            case BuildingType.BIG_COOP:
            case BuildingType.DELUXE_COOP: {
                Coop newCoop = new Coop(new Position(x, y), type);
                for (int i = x; i < x + type.getWidth(); i++) {
                    for (int j = y; j < y + type.getHeight(); j++) {
                        farm.getTileByPosition(i, j).setFixedObject(newCoop);
                    }
                }
                App.getMe().getPlayerFarm().getBuildings().add(newCoop);
            }
            break;
            case BuildingType.WELL: {
                Well newWell = new Well(new Position(x, y));
                for (int i = x; i < x + type.getWidth(); i++) {
                    for (int j = y; j < y + type.getHeight(); j++) {
                        farm.getTileByPosition(i, j).setFixedObject(newWell);
                    }
                }
//                                App.getMe().getPlayerFarm().getBuildings().add(newBin);
            }
            break;
            case BuildingType.SHIPPING_BIN: {
                ShippingBar newBin = new ShippingBar(new Position(x, y), farm);
                for (int i = x; i < x + type.getWidth(); i++) {
                    for (int j = y; j < y + type.getHeight(); j++) {
                        farm.getTileByPosition(i, j).setFixedObject(newBin);
                    }
                }
//                App.getMe().getPlayerFarm().getBuildings().add(newBin);
            }
            break;
        }

        me.getInventory().remove(new Etc(EtcType.WOOD), needWood);
        me.getInventory().remove(new Mineral(MineralItemType.STONE), needStone);
        me.addGold(-product.getPrice());
        product.setRemainingStock(product.getRemainingStock() - 1);


        return new Result(true, name + " built at (" + x + "," + y + ").");
    }

    public static Result ExitShop(){
        return ShopController.exitShopMenu(CarpentersShop.class);
    }
}

