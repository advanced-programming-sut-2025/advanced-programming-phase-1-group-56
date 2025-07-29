package io.src;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.src.controller.GameMenuController.GameController;
import io.src.controller.MenuController.LoginMenuController;
import io.src.model.App;
import io.src.model.Game;
import io.src.view.AppView;
import io.src.view.GameMenus.GameView;

public class StardewValley extends com.badlogic.gdx.Game {
    private Game game;
    private GameView gameView;
    private static SpriteBatch batch;

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
//        GameController gameController = new GameController(this);
//        gameController.init();
//        gameController.run();
        gameView = new GameView(game);
        setScreen(gameView);
        batch = new SpriteBatch();
        LoginMenuController loginMenuController = new LoginMenuController(this);
        loginMenuController.init();
        loginMenuController.run();
//        new AppView().run();
//        Game game = App.getCurrentUser().getCurrentGame();
//        if (game == null) {
//            System.out.println("no game");
//            return;
//        }
//        this.game = game;
//        GameController gameController = new GameController(this);
//        gameController.init();
//        gameController.run();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
}
