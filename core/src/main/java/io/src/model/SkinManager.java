package io.src.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinManager {
    private static SkinManager instance;
    private static Skin skin;

    public static SkinManager getInstance() {
        if (instance == null)
            instance = new SkinManager();
        return instance;
    }

    public Skin getSkin(String path) {
        return new Skin(Gdx.files.internal(path));
    }

    public void dispose() {
        if (skin != null) skin.dispose();
    }
}
