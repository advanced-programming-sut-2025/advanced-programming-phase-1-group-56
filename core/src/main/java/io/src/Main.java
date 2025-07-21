package io.src;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.src.controller.GameMenuController.GameController;
import io.src.view.AppView;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;

    public Main getMain() {
        if (main == null){
            main = new Main();
        }
        return main;
    }

    @Override
    public void create() {
        GameController gameController = new GameController(this);
        gameController.init();
        gameController.run();

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
