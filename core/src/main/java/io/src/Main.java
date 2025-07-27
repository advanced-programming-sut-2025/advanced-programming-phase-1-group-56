package io.src;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.src.controller.GameMenuController.GameController;

public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    public static Main getMain() {
        return main;
    }

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        GameController gameController = new GameController();
        gameController.init();
        gameController.run();

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
