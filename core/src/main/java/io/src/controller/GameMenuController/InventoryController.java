package io.src.controller.GameMenuController;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.src.controller.CommandController;
import io.src.model.*;
import io.src.model.Enums.Items.TrashcanType;
import io.src.model.items.Inventory;
import io.src.model.items.Item;
import io.src.view.GameMenus.GameView;

import java.util.ArrayList;

public class InventoryController extends CommandController {
    public static Result inventoryShow() {
        StringBuilder output = new StringBuilder();
        int counter = 1;
        for (Slot slot : App.getMe().getInventory().getSlots()) {
            output.append(counter).append("-Name: '").append(slot.getItem().getName()).append("' Count:")
                .append(slot.getQuantity()).append("\n");
            counter++;
        }
        output.append("\nBaghi mande Puli : ").append(App.getMe().getGold());
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

    public static User returnUser(String username) {
        ArrayList<User> users = App.getUsers();
        if (users == null) return null;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void swapSlots(Inventory inventory, int fromIndex, int toIndex) {
        ArrayList<Slot> slots = inventory.getSlots();
        while (slots.size() <= Math.max(fromIndex, toIndex)) {
            slots.add(new Slot(null, 0));
        }

        Slot temp = slots.get(fromIndex);
        slots.set(fromIndex, slots.get(toIndex));
        slots.set(toIndex, temp);
        GameView.getInventoryBar().refreshInventory();
        GameView.getInvWindow().refreshInventory();
        GameView.foodWindow().refreshInventory();
        GameView.getCraftingWindow().refreshInventory();

    }

    public static Image trashCanImage(Player player) {
        TrashcanType trashcan1 = player.getCurrentTrashcan();
        Image trashCan = null;
        if (trashcan1.equals(TrashcanType.initialTrashcan)) {
            trashCan = new Image(GameAssetManager.getGameAssetManager().getTrashcan1());
        } else if (trashcan1.equals(TrashcanType.copperTrashcan)) {
            trashCan = new Image(GameAssetManager.getGameAssetManager().getTrashcan2());
        } else if (trashcan1.equals(TrashcanType.ironTrashcan)) {
            trashCan = new Image(GameAssetManager.getGameAssetManager().getTrashcan3());
        } else if (trashcan1.equals(TrashcanType.goldTrashcan)) {
            trashCan = new Image(GameAssetManager.getGameAssetManager().getTrashcan4());
        } else if (trashcan1.equals(TrashcanType.iridiumTrashcan)) {
            trashCan = new Image(GameAssetManager.getGameAssetManager().getTrashcan5());

        }
        return trashCan;
    }

    public static  Table showSkillsLevel(int level) {

        Table table = new Table();
        table.defaults().padRight(3f);
        for (int i = 0; i < 10; i++) {
            Stack stack = new Stack();
            Image skillsImage = null;
            if (i <= level && (i + 1) % 5 != 0) {
                skillsImage = new Image(GameAssetManager.getGameAssetManager().getFilledSmallSkillBar());
            } else if (i <= level && (i + 1) % 5 == 0) {
                skillsImage = new Image(GameAssetManager.getGameAssetManager().getFilledBigSkillBar());
            } else if (i > level && (i + 1) % 5 != 0) {
                skillsImage = new Image(GameAssetManager.getGameAssetManager().getEmptySmallSkillBar());
            } else if (i > level && (i + 1) % 5 == 0) {
                skillsImage = new Image(GameAssetManager.getGameAssetManager().getEmptyBigSkillBar());
            }
            stack.add(skillsImage);
            table.add(stack).size(33);
        }
        return table;
    }

    public static void moveItem(Inventory fromInventory, Inventory toInventory, int fromIndex, int toIndex) {
        ArrayList<Slot> fromSlots = fromInventory.getSlots();
        ArrayList<Slot> toSlots = toInventory.getSlots();
        while (fromSlots.size() <= fromIndex) {
            fromSlots.add(new Slot(null, 0));
        }
        while (toSlots.size() <= toIndex) {
            toSlots.add(new Slot(null, 0));
        }

        Slot fromSlot = fromSlots.get(fromIndex);
        Slot toSlot = toSlots.get(toIndex);

        Item fromItem = fromSlot.getItem();
        int fromQty = fromSlot.getQuantity();

        if (fromItem == null || fromQty == 0) return;

        if (toSlot.getItem() == null) {
            toSlot.setItem(fromItem);
            toSlot.setQuantity(fromQty);
            fromSlot.setItem(null);
            fromSlot.setQuantity(0);
        } else if (toSlot.getItem().equals(fromItem)) {
            toSlot.setQuantity(toSlot.getQuantity() + fromQty);
            fromSlot.setItem(null);
            fromSlot.setQuantity(0);
        } else {
            Item tempItem = toSlot.getItem();
            int tempQty = toSlot.getQuantity();
            toSlot.setItem(fromItem);
            toSlot.setQuantity(fromQty);
            fromSlot.setItem(tempItem);
            fromSlot.setQuantity(tempQty);
        }
        GameView.getInventoryBar().refreshInventory();
        GameView.getInvWindow().refreshInventory();
        GameView.foodWindow().refreshInventory();
        GameView.getCraftingWindow().refreshInventory();
    }


}

