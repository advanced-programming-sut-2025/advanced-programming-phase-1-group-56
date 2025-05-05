package model;

import com.google.gson.annotations.Expose;
import model.Activities.Trade;
import model.States.WeatherState;
import model.TimeSystem.TimeSystem;

import java.util.ArrayList;
import java.util.UUID;

import model.Locations.Map;

public class Game {
    private String gameId;
    private ArrayList<String> usersId = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private final Map map;
    private final TimeSystem timeSystem;
    private final WeatherState weatherState;
    private String starterPlayerUserId;
    private String currentPlayerUserId;
    @Expose(serialize = false, deserialize = false)
    private Player starterPlayer;
    @Expose(serialize = false, deserialize = false)
    private Player currentPlayer;
    private boolean PendingDelete;

    private final ArrayList<Trade> allTrades = new ArrayList<>();

    public Game(ArrayList<Player> players, Map map, TimeSystem timeSystem, WeatherState weatherState) {
        this.players = players;
        this.map = map;
        this.timeSystem = timeSystem;
        this.weatherState = weatherState;

        //TODO HANDLE GAME ID SYSTEM
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Map getMap() {
        return map;
    }

    public TimeSystem getTimeSystem() {
        return timeSystem;
    }

    public WeatherState getWeatherState() {
        return weatherState;
    }

    public ArrayList<String> getUsersId() {
        return usersId;
    }

    public void setUsersId(ArrayList<String> usersId) {
        this.usersId = usersId;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isPendingDelete() {
        return PendingDelete;
    }

    public void setPendingDelete(boolean pendingDelete) {
        PendingDelete = pendingDelete;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Player getPlayerByUser(User user) {
        for (Player player : players) {
            if (player.getUser().equals(user)) {
                return player;
            }
        }
        return null;
    }

    public Player getStarterPlayer() {
        return starterPlayer;
    }

    public void setStarterPlayer(Player starterPlayer) {
        this.starterPlayer = starterPlayer;
    }

    public ArrayList<Trade> getAllTrades() {
        return allTrades;
    }

    public Trade findTradeById(UUID id) {
        for (Trade trade : allTrades) {
            if(id.equals(trade.getTradeID()))
            {
                return trade;
            }
        }
        return null;
    }

    public Player findPlayerById(UUID playerID) {
        for (Player player : players) {
            if(player.getPlayerID().equals(playerID))
                return player;
        }
        return null;
    }
}