package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.io.File;
import java.util.ArrayList;

public class AvatarMenu extends Window {
    public AvatarMenu(Skin skin, AvatarSelectionListener listener) {
        super("", skin);

        // Load textures
        Texture backgroundTex = new Texture("AVATAR/final/avatarBack.png");
        Texture avatarTex = new Texture("AVATAR/final/avatar1.1.1.png");

        // Create images
        Image avatarBackground = new Image(backgroundTex);
        Image avatarImage = new Image(avatarTex);

        // Set scaling behavior (important)
        avatarBackground.setScaling(Scaling.stretch);
        avatarImage.setScaling(Scaling.stretch);

        // Sizes
        float backgroundWidth = backgroundTex.getWidth();
        float backgroundHeight = backgroundTex.getHeight();

        float avatarWidth = avatarTex.getWidth() * 4;
        float avatarHeight = avatarTex.getHeight() * 4;

        // Wrap each image in a container and set size separately
        Container<Image> backgroundContainer = new Container<>(avatarBackground);
        backgroundContainer.size(backgroundWidth, backgroundHeight);

        Container<Image> avatarContainer = new Container<>(avatarImage);
        avatarContainer.size(avatarWidth, avatarHeight);

        // Center the avatar inside its smaller container
        avatarContainer.align(Align.center);

        // Create a stack and add background (big), then image (small)
        Stack avatarStack = new Stack();
        avatarStack.add(backgroundContainer);
        avatarStack.add(avatarContainer);

        // Add to the window
        add(avatarStack).pad(10).row();

        TextField nameField = new TextField("", skin);
        TextField farmNameField = new TextField("", skin);
        TextField favoriteThingsField = new TextField("", skin);
        add(nameField).pad(10).row();
        add(farmNameField).pad(10).row();
        add(favoriteThingsField).pad(10).row();

        Label nameLabel = new Label("Name:", skin);
        Label farmNameLabel = new Label("farm name:", skin);
        Label favoriteThingsLabel = new Label("favorite thing:", skin);
        add(nameLabel).pad(10).row();
        add(farmNameLabel).pad(10).row();
        add(favoriteThingsLabel).pad(10).row();

        // Set size of window
        setSize(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 1.2f);
        setPosition(
            (Gdx.graphics.getWidth() - getWidth()) / 2f,
            (Gdx.graphics.getHeight() - getHeight()) / 2f
        );

        setMovable(false);
        setModal(true);
    }

    public interface AvatarSelectionListener {
        void onAvatarSelected(int avatarID);
    }
}
