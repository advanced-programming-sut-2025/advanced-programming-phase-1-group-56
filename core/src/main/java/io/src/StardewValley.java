package io.src;


import io.src.controller.GameMenuController.GameController;
import io.src.model.App;
import io.src.model.Game;
import io.src.view.AppView;

public class StardewValley extends com.badlogic.gdx.Game {
    private Game game;

    public StardewValley() {
//        this.game = game;
    }

    @Override
    public void create() {
        new AppView().run();
        Game game = App.getCurrentUser().getCurrentGame();
        if (game == null) {
            System.out.println("no game");
            return;
        }
        this.game = game;
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
