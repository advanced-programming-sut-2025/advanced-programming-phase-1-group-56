package io.src.model.items;


import io.src.model.Enums.BackPackType;
import io.src.model.Slot;

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

    public void add(Item item, int quantity) {
        for (Slot slot : slots) {
            if (quantity <= 0) {
                break;
            }
            if (item.getName().equalsIgnoreCase(slot.getItem().getName())) {
                if (quantity <= item.maxStackSize - slot.getQuantity()) {
                    slot.setQuantity(quantity + slot.getQuantity());
                    quantity = 0;
                } else {
                    quantity -= item.maxStackSize - slot.getQuantity();
                    slot.setQuantity(item.maxStackSize);
                }
            }
        }
        while (slots.size() < capacity) {
            if (quantity <= 0)
                break;

            if (quantity <= item.maxStackSize) {
                slots.add(new Slot(item, quantity));
                quantity = 0;
            } else {
                slots.add(new Slot(item, item.maxStackSize));
                quantity -= item.maxStackSize;
            }
        }

        slots.removeIf(slot -> slot.getQuantity() == 0);

    }

    public void remove(Item item, int quantity) {
        int remaining = quantity;

        Iterator<Slot> it = slots.iterator();
        while (it.hasNext() && remaining > 0) {
            Slot slot = it.next();
            if (item.equals(slot.getItem())) {
                int slotQty = slot.getQuantity();

                if (slotQty > remaining) {
                    slot.setQuantity(slotQty - remaining);
                    remaining = 0;
                } else {
                    remaining -= slotQty;
                    it.remove();
                }
            }
        }
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
