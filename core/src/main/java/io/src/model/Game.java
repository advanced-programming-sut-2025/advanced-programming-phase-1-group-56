package io.src.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.google.gson.annotations.Expose;
import io.src.model.Activities.Trade;
import io.src.model.MapModule.Buildings.Store;
import io.src.model.States.WeatherState;
import io.src.model.TimeSystem.TimeSystem;
import io.src.model.MapModule.GameMap;
import java.util.ArrayList;
import java.util.UUID;


public class Game {
    private String gameId;
    private ArrayList<String> usersId = new ArrayList<>();
    private ArrayList<Player> players;
    private GameMap gameMap;
    private TimeSystem timeSystem;
    private OrthographicCamera camera; // Add camera field

    public void setWeatherState(WeatherState weatherState) {
        this.weatherState = weatherState;
    }

    public void setTimeSystem(TimeSystem timeSystem) {
        this.timeSystem = timeSystem;
    }

    private WeatherState weatherState;
    private String starterPlayerUserId;
    private String currentPlayerUserId;
    @Expose(serialize = false, deserialize = false)
    private Player starterPlayer;
    @Expose(serialize = false, deserialize = false)
    private Player currentPlayer;
    private boolean PendingDelete;

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private final ArrayList<Trade> allTrades = new ArrayList<>();

    public Game(ArrayList<Player> players, GameMap GameMap, TimeSystem timeSystem, WeatherState weatherState) {
        this.players = players;
        this.gameMap = GameMap;
        this.timeSystem = timeSystem;
        this.weatherState = weatherState;
        currentPlayer = players.get(0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(currentPlayer.getPosition().getX(), currentPlayer.getPosition().getY(), 0);
        //TODO HANDLE GAME ID SYSTEM
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public GameMap getGameMap() {
        return gameMap;
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
            if (player.getUser().getUsername().equals(user.getUsername())) {
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

    public <T extends Store> T findStoreByClass(Class<T> storeClass) {
        for (Store store : gameMap.getPelikanTown().getStores()) {
            if (storeClass.isInstance(store)) {
                return storeClass.cast(store);
            }
        }
        return null; // یا throw new IllegalArgumentException(...)
    }
    public void update(float deltaTime) {

        float playerX = currentPlayer.getPosition().getX() * 16;
        float playerY = currentPlayer.getPosition().getY() * 16;

        float camX = camera.position.x;
        float camY = camera.position.y;

        float viewHalfWidth = camera.viewportWidth / 2;
        float viewHalfHeight = camera.viewportHeight / 2;

        float border = 32; // 2-tile margin from edge

        // Horizontal movement
        if (playerX < camX - viewHalfWidth + border) {
            camX = playerX + viewHalfWidth - border;
        } else if (playerX > camX + viewHalfWidth - border) {
            camX = playerX - viewHalfWidth + border;
        }

        // Vertical movement
        if (playerY < camY - viewHalfHeight + border) {
            camY = playerY + viewHalfHeight - border;
        } else if (playerY > camY + viewHalfHeight - border) {
            camY = playerY - viewHalfHeight + border;
        }
        //correct map width and height
        camX = Math.max(viewHalfWidth, Math.min(camX, 80 * 16 - viewHalfWidth));
        camY = Math.max(viewHalfHeight, Math.min(camY, 65 * 16 - viewHalfHeight));

        camera.position.set(camX, camY, 0);
        camera.update();
    }

    public OrthographicCamera getCamera(){
        return camera;
    }
}
