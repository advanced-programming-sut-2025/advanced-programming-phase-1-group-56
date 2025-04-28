package model;

import java.util.ArrayList;
import java.util.Map;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Map map;

    Game(ArrayList<Player> players, Map map) {
        this.players = players;
        this.map = map;
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

    public void setMap(Map map) {
        this.map = map;
    }
}
