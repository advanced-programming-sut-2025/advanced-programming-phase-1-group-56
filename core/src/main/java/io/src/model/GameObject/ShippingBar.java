package io.src.model.GameObject;

import com.badlogic.gdx.Input;
import com.google.gson.annotations.Expose;
import io.src.model.App;
import io.src.model.Clickable;
import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.Position;
import io.src.model.Slot;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;
import io.src.model.items.Inventory;

public class ShippingBar extends GameObject implements TimeObserver, Clickable, SensitiveToPlayer {
    private final Inventory inventory = new Inventory(100);
    @Expose(serialize = false, deserialize = false)
    private Farm farm;
    private final BuildingType type = BuildingType.SHIPPING_BIN;
    private boolean open = false;

    public ShippingBar(Position position, Farm farm) {
        super(false, position);
        this.farm = farm;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }


    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            for (Slot slot : inventory.getSlots()) {
                int sumPrice = slot.getQuantity() * slot.getItem().getFinalPrice();
                if (sumPrice != -1) {
                    farm.getPlayer().addGold(sumPrice);
                }
            }
            inventory.getSlots().clear();
        }
    }

    public int getWidth() {
        return type.getWidth();
    }

    public int getHeight() {
        return type.getHeight();
    }

    @Override
    public String getAssetName() {
        return open ? "Shipping_Bin_Opened" : "Shipping_Bin_Closed";
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean onPlayerGoesNearby(float distance) {
        open = true;
        return true;
    }

    @Override
    public boolean onPlayerGetsFar(float distance) {
        open = false;
        return true;
    }

    @Override
    public boolean onPlayerFocus() {
        return true;
    }

    @Override
    public boolean onPlayerDefocus() {
        return true;
    }

    @Override
    public float getSensitivityDistance() {
        return 2.5f;
    }
}
