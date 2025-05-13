package model.GameObject;

import com.google.gson.annotations.Expose;
import model.App;
import model.Enums.BackPackType;
import model.MapModule.GameLocations.Farm;
import model.MapModule.Position;
import model.Slot;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;
import model.items.Inventory;

import java.util.ArrayList;

public class ShippingBar extends GameObject implements TimeObserver {
    private final Inventory inventory = new Inventory(BackPackType.InitialBackpack);
    @Expose(serialize = false,deserialize = false)
    private Farm farm;


    public ShippingBar(Position position,Farm farm) {
        super(false, position);
        this.farm = farm;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }


    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay) {
            for (Slot slot : inventory.getSlots()) {
                int sumPrice = slot.getQuantity() * slot.getItem().getPrice();
                farm.getPlayer().addGold(sumPrice);
            }
            inventory.getSlots().clear();
        }
    }
}
