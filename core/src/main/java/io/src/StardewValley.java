package io.src;


import io.src.controller.GameMenuController.GameController;
import io.src.model.Game;

public class StardewValley extends com.badlogic.gdx.Game {
    private Game game;

    public StardewValley(Game game) {
        this.game = game;
    }

    @Override
    public void create() {
        GameController gameController = new GameController(this);
        gameController.init();
        gameController.run();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
