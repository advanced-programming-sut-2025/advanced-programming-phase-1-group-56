package io.src;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.src.controller.GameMenuController.GameController;
import io.src.controller.MenuController.LoginMenuController;

public class Main extends Game {
    private static Main main;
    private SpriteBatch batch;

    public static Main getMain() {
        return main;
    }

    @Override
    public void create() {
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(displayMode);
        main = this;
        batch = new SpriteBatch();
        LoginMenuController controller = new LoginMenuController();
        controller.init();
        controller.run();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
