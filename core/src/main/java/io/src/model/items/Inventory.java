package io.src.model.items;


import com.badlogic.gdx.math.Interpolation;
import io.src.model.Enums.BackPackType;
import io.src.model.Slot;
import io.src.view.GameMenus.GameView;
import io.src.view.GameMenus.InventoryBar;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.Iterator;

public class Inventory {
    private final ArrayList<Slot> slots = new ArrayList<>();
    private BackPackType backPackType;
    private int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
    }

    public Inventory(BackPackType backPackType) {
        this.backPackType = backPackType;
        this.capacity = backPackType.getCapacity();
    }

    public void setBackPackType(BackPackType backPackType) {
        this.backPackType = backPackType;
        this.capacity = backPackType.getCapacity();
    }

    public void updateCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean add(Item item, int quantity) {
        if (item == null || quantity <= 0) return false;

        for (Slot slot : slots) {
            if (quantity <= 0) break;

            Item slotItem = slot.getItem();
            if (slotItem != null && item.getName().equalsIgnoreCase(slotItem.getName())) {
                int space = item.maxStackSize - slot.getQuantity();
                if (space > 0) {
                    int toAdd = Math.min(space, quantity);
                    slot.setQuantity(slot.getQuantity() + toAdd);
                    quantity -= toAdd;
                }
            }
        }
        for (Slot slot : slots) {
            if (quantity <= 0) break;

            if (slot.getItem() == null || slot.getQuantity() == 0) {
                int toAdd = Math.min(item.maxStackSize, quantity);
                slot.setItem(item);
                slot.setQuantity(toAdd);
                quantity -= toAdd;
            }
        }

        while (slots.size() < capacity && quantity > 0) {
            int toAdd = Math.min(item.maxStackSize, quantity);
            slots.add(new Slot(item, toAdd));
            quantity -= toAdd;
        }
        slots.removeIf(slot -> slot.getItem() == null || slot.getQuantity() == 0);

        refreshAllUI();
        return quantity == 0;
    }


    public void remove(Item item, int quantity) {
        if (item == null || quantity <= 0) return;

        int remaining = quantity;

        for (int i = 0; i < slots.size() && remaining > 0; i++) {
            Slot slot = slots.get(i);
            Item slotItem = slot.getItem();

            if (slotItem != null && item.equals(slotItem)) {
                int slotQty = slot.getQuantity();

                if (slotQty > remaining) {
                    slot.setQuantity(slotQty - remaining);
                    remaining = 0;
                } else {
                    remaining -= slotQty;
                    slot.setItem(null);
                    slot.setQuantity(0);
                }
            }
        }
        slots.removeIf(slot -> slot.getItem() == null || slot.getQuantity() == 0);

        refreshAllUI();
    }

    private void refreshAllUI() {
        if (GameView.getInventoryBar() != null) GameView.getInventoryBar().refreshInventory();
        if (GameView.getInvWindow() != null) GameView.getInvWindow().refreshInventory();
        if (GameView.foodWindow() != null) GameView.foodWindow().refreshInventory();
        if (GameView.getCraftingWindow() != null) GameView.getCraftingWindow().refreshInventory();
    }




    public int countItem(Item item) {
        int sum = 0;
        for (Slot slot : slots) {
            if (slot.getItem().getName().equalsIgnoreCase(item.getName())) {
                sum += slot.getQuantity();
            }
        }
        return sum;
    }

    public boolean hasItem(Item item) {
        return countItem(item) != 0;
    }


    public ArrayList<Slot> getSlots() {
        return slots;
    }


    public BackPackType getBackPackType() {
        return backPackType;
    }

    public int maxItemMayBeAdded(Item item) {
        int max = 0;
        max += (capacity - slots.size()) * item.getMaxStackSize();//for empty slots
        for (Slot slot : slots) {// for semi full slots of same item
            if (slot.getItem().getName().equalsIgnoreCase(item.getName())) {
                max += item.maxStackSize - slot.getQuantity();
            }
        }
        return max;
    }

    public boolean canAddItem(Item item, int quantity) {
        return quantity <= maxItemMayBeAdded(item);
    }

    public Item findItemByName(String itemName) {
        for (Slot slot : slots) {
            if (slot.getItem().getName().equalsIgnoreCase(itemName)) {
                return slot.getItem();
            }
        }

        return null;
    }

    public Item findItemByPartOfName(String partOfName) {
        int max = 0;
        Item item = null;
        for (Slot slot : slots) {
            if (slot.getItem().getName().contains(partOfName)) {
                if (countItem(slot.getItem()) > max) {
                    max = countItem(slot.getItem());
                    item = slot.getItem();
                    return item;
                }
            }
        }
        return null;
    }

    public Slot findSlotByName(String itemName) {
        for (Slot slot : slots) {
            if (slot.getItem().getName().equalsIgnoreCase(itemName)) {
                return slot;
            }
        }
        return null;
    }
}
