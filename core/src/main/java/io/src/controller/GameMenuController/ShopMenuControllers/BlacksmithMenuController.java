
package io.src.controller.GameMenuController.ShopMenuControllers;

import io.src.model.App;
import io.src.model.Enums.Items.EtcType;
import io.src.model.Enums.Items.ToolType;
import io.src.model.Enums.Items.TrashcanType;
import io.src.model.Enums.Stores.BlackSmithProducts;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.Buildings.Blacksmith;
import io.src.model.Player;
import io.src.model.Result;
import io.src.model.items.Item;
import io.src.model.items.Tool;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class BlacksmithMenuController implements ShopController {
    public static Result showAllProducts() {
        return ShopController.showAllProducts(
            App.getCurrentUser().getCurrentGame().
                findStoreByClass(Blacksmith.class).getDailyProductList());
    }

    public static Result showAllAvailableProducts() {
        return ShopController.showAllAvailableProducts(
            App.getCurrentUser().getCurrentGame().
                findStoreByClass(Blacksmith.class).getDailyProductList());
    }

    public static Result PurchaseProduct(Matcher matcher) {
        return ShopController.purchaseProductFromList(matcher.group(1), matcher.group(2),
            App.getCurrentUser().getCurrentGame().
                findStoreByClass(Blacksmith.class).getDailyProductList());
    }

    public static Result upgradeTools(NpcProduct selectedProduct, ArrayList<NpcProduct> products) {
        EtcType toolMaterial = null;
        if (selectedProduct.getSaleable() instanceof ToolType toolType) {
            toolMaterial = toolType.getToolMaterial().getOre();
        } else if (selectedProduct.getSaleable() instanceof TrashcanType trashcanType) {
            toolMaterial = trashcanType.getMaterial().getOre();
        }
        String toolName = selectedProduct.getSaleable().getName();
        System.out.println(toolName);

        Player me = App.getMe();
        boolean isTrashCan = toolName.toLowerCase().contains("trashcan") ||
            toolName.toLowerCase().contains("trash_can") || toolName.toLowerCase().contains("trash can");
        Tool tool = null;

        if (!isTrashCan) {
            Item it = me.getInventory().findItemByName(toolName.trim());
            if (!(it instanceof Tool)) {
                return new Result(false, it == null
                    ? "You don't have such item in your inventory."
                    : "That item is not a tool.");
            }
            tool = (Tool) it;
            if (tool.getToolType().getNextToolType() == null) {
                return new Result(false, "This tool is already at max level.");
            }
            toolMaterial = tool.getToolType().getNextToolType().getToolMaterial().getOre();
        } else {
            TrashcanType current = me.getCurrentTrashcan();
            if (current.getNextTrashcanType() == null) {
                return new Result(false, "This trashcan is already at max level.");
            }
            toolMaterial = current.getNextTrashcanType().getMaterial().getOre();
        }

        // barr
        Item bar = me.getInventory().findItemByName(toolMaterial.name());
        if (bar == null || me.getInventory().countItem(bar) < 5) {
            return new Result(false, "You need at least 5 " + toolMaterial.name() + " bars.");
        }


        if (selectedProduct.getRemainingStock() == 0) {
            return new Result(false, "daily stock ended comeback tomorrow.");
        }


        int price = selectedProduct.getPrice();
        if (me.getGold() < price) {
            return new Result(false, "You don't have enough money.");
        }
        //update remaining Stock
        for (NpcProduct product : products) {
            if (selectedProduct.getSaleable() instanceof ToolType && product.getSaleable() instanceof ToolType toolType && toolMaterial.equals(toolType.getToolMaterial().getOre())) {
                product.setRemainingStock(product.getRemainingStock() - 1);
            } else if (selectedProduct.getSaleable() instanceof TrashcanType && product.getSaleable() instanceof TrashcanType trashcanType && toolMaterial.equals(trashcanType.getMaterial().getOre())) {
                product.setRemainingStock(product.getRemainingStock() - 1);
            }
        }
        // upgrade commit
        me.addGold(-price);
        me.getInventory().remove(bar, 5);

        if (isTrashCan) {
            me.setCurrentTrashcan(me.getCurrentTrashcan().getNextTrashcanType());
        } else {
            Tool.upgrade(tool);
        }

        return new Result(true, "Successfully upgraded your " + (isTrashCan ? "trashcan." : "tool."
            + "to " + tool.getToolType().getName() + tool.getToolType().getToolMaterial().getName()));
    }

    public static Result ExitShop() {
        return ShopController.exitShopMenu(Blacksmith.class);
    }

}
