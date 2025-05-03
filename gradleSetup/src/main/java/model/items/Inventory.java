package model.items;

import model.Slot;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private ArrayList<Slot> slots;
    private int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
    }


    public void updateCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void add(Item item, int quantity) {
        for (Slot slot : slots) {
            if (slot.getItem().equals(item)) {
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
            if (slot.getItem().equals(item)) {
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
            if (slot.getItem().equals(item)) {
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


}