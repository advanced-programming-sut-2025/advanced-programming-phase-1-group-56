package io.src.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinManager {
    private static SkinManager instance;
    private static Skin skin;

    public static SkinManager getInstance() {
        if (instance == null) {
            instance = new SkinManager();
            skin = new Skin(Gdx.files.internal("skin1/menu_Skin_v0.0.1.json"));
        }
        return instance;
    }

    public Skin getSkin1() {
        return skin;
    }

    public void dispose() {
        if (skin != null) skin.dispose();
    }
}
