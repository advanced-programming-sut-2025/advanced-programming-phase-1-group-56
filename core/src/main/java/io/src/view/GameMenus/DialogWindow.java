package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class DialogWindow extends Window {
    private Label speakerLabel, textLabel;

    public DialogWindow(Skin skin) {
        super("", skin);
        speakerLabel = new Label("", skin, "title");
        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        add(speakerLabel).left().row();
        add(textLabel).width(300).height(100);
        pack();
        setVisible(false);
    }

    public void showDialog(String speaker, String text) {
        speakerLabel.setText(speaker);
        textLabel.setText(text);
        pack();
        setPosition(
            (Gdx.graphics.getWidth() - getWidth()) / 2,
            50  // پایین صفحه
        );
        setVisible(true);
    }

    public void hideDialog() {
        setVisible(false);
    }
}
