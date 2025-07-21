package io.src;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.src.controller.GameMenuController.GameController;

public class Main extends Game {
    private static Main main;
    private static SpriteBatch Batch;

    public static Main getMain() {
        if (main == null) {
            main = new Main();
            Batch = new SpriteBatch();
        }
        return main;
    }

    @Override
    public void create() {
        GameController gameController = new GameController();
        gameController.init();
        gameController.run();

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public SpriteBatch getBatch() {
        return Batch;
    }
}
