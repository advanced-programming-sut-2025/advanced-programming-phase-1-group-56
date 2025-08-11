package io.src.view.InnerMenus;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import io.src.model.App;
import io.src.model.User;

public class ProfileMenu extends Window {
    public ProfileMenu(Skin skin) {
        super("", skin);
        User targetUser = App.getCurrentUser();

    }
}
