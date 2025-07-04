
package controller.GameMenuController.ShopMenuControllers;

import model.App;
import model.Enums.Items.EtcType;
import model.Enums.Items.TrashcanType;
import model.Enums.Stores.BlackSmithProducts;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Buildings.Blacksmith;
import model.Player;
import model.Result;
import model.items.Item;
import model.items.Tool;

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
        return ShopController.purchaseProductFromList(matcher,
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(Blacksmith.class).getDailyProductList());
    }

    public static Result upgradeTools(String toolName) {
        Player me = App.getMe();
        boolean isTrashCan = toolName.toLowerCase().contains("trash can")||
                toolName.toLowerCase().contains("trash_can");
        Tool tool = null;
        EtcType toolMaterial;

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

        //finding product
        String keyword = isTrashCan ? "TRASH_CAN" : "TOOL";
        NpcProduct npcProduct = findUpgradeProduct(keyword, toolMaterial.name());
        if (npcProduct == null) {
            return new Result(false, "Error finding upgrade option in shop."
                    + keyword + "     " + toolMaterial.name());
        }

        if(npcProduct.getRemainingStock()==0){
            return new Result(false, "daily stock ended comeback tomorrow.");
        }


        int price = npcProduct.getPrice();
        if (me.getGold() < price) {
            return new Result(false, "You don't have enough money.");
        }
        // upgrade commit
        npcProduct.setRemainingStock(npcProduct.getRemainingStock() - 1);
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

    private static NpcProduct findUpgradeProduct(String keyword, String oreName) {
        for (NpcProduct p : BlackSmithProducts.getProducts(BlackSmithProducts.class)) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase()) && p.getSaleable().getName().equalsIgnoreCase(oreName)) {
                return p;
            }
        }
        return null;
    }

    public static Result ExitShop(){
        return ShopController.exitShopMenu(Blacksmith.class);
    }

}
