package io.src.model.Network.DTO;

import io.src.model.Enums.FarmPosition;

import java.util.List;

public class PlayerDTO {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public FarmPosition getFarmPosition() {
        return farmPosition;
    }

    public void setFarmPosition(FarmPosition farmPosition) {
        this.farmPosition = farmPosition;
    }

    private String username;
    private FarmPosition farmPosition;
    private int gold;

    // getters & setters
}
