package model.items;


import model.Enums.BackPackType;
import model.Slot;

import java.util.ArrayList;
import java.util.HashMap;

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
            if (slot.getItem().getName().equals(item.getName())) {
                if (slot.getQuantity() + quantity < item.getMaxStackSize())
                    slot.setQuantity(slot.getQuantity() + quantity);
                else if (slot.getQuantity() + quantity > item.getMaxStackSize()) {
                    int tmpQuantity = quantity;

                    int spaceLeft = item.getMaxStackSize() - slot.getQuantity();
                    slot.setQuantity(item.getMaxStackSize());
                    tmpQuantity -= spaceLeft;

                    while (tmpQuantity > 0) {
                        if (slots.size() == capacity) {
                            return;
                        }

                        int addAmount = Math.min(tmpQuantity, item.getMaxStackSize());
                        Slot newSlot = new Slot(item, addAmount);
                        slots.add(newSlot);
                        tmpQuantity -= addAmount;
                    }
                    return;
                } else {
                    slot.setQuantity(slot.getQuantity() + quantity);
                }
            } else {
                int tmpQuantity = quantity;
                while (tmpQuantity > 0) {
                    if (slots.size() == capacity) {
                        return;
                    }
                    int addAmount = Math.min(tmpQuantity, item.getMaxStackSize());
                    Slot newSlot = new Slot(item, addAmount);
                    slots.add(newSlot);
                    tmpQuantity -= addAmount;
                }
            }
        }
    }

    public void remove(Item item, int quantity) {
        int tmpQuantity = quantity;
        for (Slot slot : slots) {
            if (slot.getItem().getName().equals(item.getName())) {
                if(slot.getQuantity() >= tmpQuantity) {
                    slot.setQuantity(slot.getQuantity() - tmpQuantity);
                    if(slot.getQuantity()==0){
                        slots.remove(slot);
                    }
                    return;
                } else if(slot.getQuantity() < tmpQuantity) {
                    tmpQuantity -= slot.getQuantity();
                    slots.remove(slot);
                }
            }
        }
    }

    public int countItem(Item item){
        int sum = 0;
        for (Slot slot : slots) {
            if (slot.getItem().getName().equals(item.getName())) {
                sum += slot.getQuantity();
            }
        }
        return sum;
    }

    public boolean hasItem(Item item){
        return countItem(item)!=0;
    }


    public ArrayList<Slot> getSlots(){
        return slots;
    }


    public BackPackType getBackPackType() {
        return backPackType;
    }

    public int maxItemMayBeAdded(Item item){
        int max = 0;
        max +=  (capacity - slots.size()) * item.getMaxStackSize();//for empty slots
        for (Slot slot : slots) {// for semi full slots of same item
            if (slot.getItem().getName().equals(item.getName())) {
                max += item.maxStackSize - slot.getQuantity();
            }
        }
        return max;
    }
    public boolean canAddItem(Item item, int quantity) {
        return quantity <=  maxItemMayBeAdded(item);
    }

    public Item findItemByName(String itemName) {
        for (Slot slot : slots) {
            if (slot.getItem().getName().equals(itemName)) {
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
                if( countItem(slot.getItem())> max){
                    max = countItem(slot.getItem());
                    item = slot.getItem();
                }
            }
        }
        return null;
    }

    public Slot findSlotByName(String itemName) {
        for (Slot slot : slots) {
            if (slot.getItem().getName().equals(itemName)) {
                return slot;
            }
        }
        return null;
    }
}