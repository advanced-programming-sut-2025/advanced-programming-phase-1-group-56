package io.src.model.Network.DTO;

import io.src.model.Enums.FarmPosition;
import io.src.model.Enums.GameLocationType;
import io.src.model.MapModule.Buildings.Home;

public class FarmDTO {
    private String farnmapPath;
    private FarmPosition position;
    private String playerUsername;
    private Home defaultHome;
    private GameLocationType gameLocationType;

    // getters & setters

    public FarmPosition getPosition() {
        return position;
    }

    public void setPosition(FarmPosition position) {
        this.position = position;
    }

    public String getFarnmapPath() {
        return farnmapPath;
    }

    public void setFarnmapPath(String farnmapPath) {
        this.farnmapPath = farnmapPath;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public Home getDefaultHome() {
        return defaultHome;
    }

    public void setDefaultHome(Home defaultHome) {
        this.defaultHome = defaultHome;
    }

    public GameLocationType getGameLocationType() {
        return gameLocationType;
    }

    public void setGameLocationType(GameLocationType gameLocationType) {
        this.gameLocationType = gameLocationType;
    }
}
