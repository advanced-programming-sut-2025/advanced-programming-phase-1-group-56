package io.src.view.InnerMenus;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class AnimalNameMiniMenu extends Window {

    private final TextField nameTextField;
    private final Button okButton;
    private final Button exitButton;

    public interface OnOkListener {
        void onOk(String name);
    }

    public AnimalNameMiniMenu(Skin skin, OnOkListener okListener) {
        super("", skin, "default4");

        Window window = new Window("", skin, "default3");

        nameTextField = new TextField("", skin);
        Label nameLabel = new Label("Name", skin);

        okButton = new Button(skin, "okButton");
        exitButton = new Button(skin, "closeButton");

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (okListener != null) {
                    okListener.onOk(getNameText());
                }
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });

        window.add(nameLabel);
        window.add(nameTextField).row();
        window.add(okButton);

        window.setSize(400, 200);

        add(exitButton).right().row();
        add(window);
        exitButton.toFront();

        setSize(window.getWidth() + 50, window.getHeight() + 20);
    }

    public String getNameText() {
        return nameTextField.getText();
    }
}
