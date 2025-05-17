package model.Enums.Items;

import model.items.Saleable;

import java.util.ArrayList;

public interface ItemType extends Saleable {

    public static ItemType FindItemTypeByName(ItemType[] list, String name) {
        for (ItemType itemType : list) {
            if (itemType.getName().equalsIgnoreCase(name)) {
                return itemType;
            }
        }
        return null;
    }
}
