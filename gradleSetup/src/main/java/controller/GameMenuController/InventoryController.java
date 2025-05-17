package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Result;
import model.Slot;
import model.items.Item;

public class InventoryController extends CommandController {
    public static Result inventoryShow() {
        StringBuilder output = new StringBuilder();
        int counter = 1;
        for (Slot slot : App.getMe().getInventory().getSlots()) {
            output.append(counter).append("-Name: '").append(slot.getItem().getName()).append("' Count:\n")
                    .append(slot.getQuantity()).append("\n");
            counter++;
        }
        return new Result(true, output.toString());
    }

    public static Result manageInventoryTrash(String itemStr, String amountStr) {
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            return new Result(false, e.getMessage());
        }
        if (amount < 1) {
            return new Result(false, "amount must be greater than 0");
        }
        Item item = App.getMe().getInventory().findItemByName(itemStr);
        if (item == null) {
            return new Result(false, "item not found in you inventory");
        }

        if (App.getMe().getInventory().countItem(item) < amount) {
            return new Result(false, "you do not have enough items " +
                    App.getMe().getInventory().countItem(item) + "<" + amount);
        }

        int moneyToAdd = amount * item.getFinalPrice() * App.getMe().getCurrentTrashcan().getReturnPercent();
        App.getMe().addGold(moneyToAdd);
        App.getMe().getInventory().remove(item, amount);

        return new Result(true, "You have trashed: " + itemStr + "*" + amount +
                "\ntotal cash gets: " + moneyToAdd);
    }
}
