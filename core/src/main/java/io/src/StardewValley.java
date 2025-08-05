package io.src;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import io.src.controller.GameMenuController.GameController;
import io.src.controller.MenuController.LoginMenuController;
import io.src.controller.MenuController.MainMenuController;
import io.src.model.App;
import io.src.model.Game;
import io.src.model.User;
import io.src.view.AppView;
import io.src.view.GameMenus.GameView;

import java.io.*;

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
        App.setStardewValley(this);
        setScreen(gameView);


//        batch = new SpriteBatch();
//        Gson gson = new Gson();
//        User user = null;
//
//        if (new File("assets\\StayLoggedIn.json").exists()) {
//            try (Reader reader = new FileReader("assets\\StayLoggedIn.json")) {
//                user = gson.fromJson(reader, User.class);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if (user == null) {
//            LoginMenuController loginMenuController = new LoginMenuController(this);
//            loginMenuController.init();
//            loginMenuController.run();
//        } else {
//            MainMenuController mainMenuController = new MainMenuController(this);
//            mainMenuController.init();
//            mainMenuController.run();
//        }
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

    public GameView getGameView() {
        return gameView;
    }
}
