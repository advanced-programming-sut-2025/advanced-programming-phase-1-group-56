package io.src.model.Network.DTO;

import java.util.List;

public class GameDTO {
    private String gameId;
    private List<PlayerDTO> players;
    private GameMapDTO gameMap;
    private TimeSystemDTO timeSystem;
    private WeatherStateDTO weatherState;
    private String starterPlayerUserId;
    private String currentPlayerUserId;

    public String getCurrentPlayerUserId() {
        return currentPlayerUserId;
    }

    public void setCurrentPlayerUserId(String currentPlayerUserId) {
        this.currentPlayerUserId = currentPlayerUserId;
    }

    public WeatherStateDTO getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(WeatherStateDTO weatherState) {
        this.weatherState = weatherState;
    }

    public String getStarterPlayerUserId() {
        return starterPlayerUserId;
    }

    public void setStarterPlayerUserId(String starterPlayerUserId) {
        this.starterPlayerUserId = starterPlayerUserId;
    }

    public TimeSystemDTO getTimeSystem() {
        return timeSystem;
    }

    public void setTimeSystem(TimeSystemDTO timeSystem) {
        this.timeSystem = timeSystem;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public GameMapDTO getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMapDTO gameMap) {
        this.gameMap = gameMap;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
