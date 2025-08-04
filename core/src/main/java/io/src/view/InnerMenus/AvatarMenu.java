package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AvatarMenu extends Window {
    public AvatarMenu(Skin skin, AvatarSelectionListener listener) {
        super("", skin);

        int avatarScale = 4;
        Image avatarBackground = new Image(new Texture("AVATAR/final/avatarBack.png"));
        Image avatarImage = new Image(new Texture("AVATAR/final/avatar1.1.1.png"));
        avatarImage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                listener.onAvatarSelected(1);
            }
        });
        add(avatarBackground).center();
        add(avatarImage).width(avatarImage.getWidth() * avatarScale).height(avatarImage.getHeight() * avatarScale).expand().center().pad(10).row();
        setSize(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 1.2f);
        setPosition(Gdx.graphics.getWidth() / 2f - (getWidth() / 2), Gdx.graphics.getHeight() / 2f - (getHeight() / 2));
        setMovable(false);
        setModal(true);
    }

    public interface AvatarSelectionListener {
        void onAvatarSelected(int avatarID);
    }
}
