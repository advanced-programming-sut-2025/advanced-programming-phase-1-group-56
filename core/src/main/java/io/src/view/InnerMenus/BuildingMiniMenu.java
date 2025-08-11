package io.src.view.InnerMenus;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class BuildingMiniMenu extends Window {

    private final TextField xTextField;
    private final TextField yTextField;
    private final Button okButton;
    private final Button exitButton;

    public interface OnOkListener {
        void onOk(int x, int y);
    }

    public BuildingMiniMenu(Skin skin, OnOkListener okListener) {
        super("", skin, "default4");

        Window window = new Window("", skin, "default3");

        xTextField = new TextField("", skin);
        yTextField = new TextField("", skin);

        Label xLabel = new Label("x", skin);
        Label yLabel = new Label("y", skin);

        okButton = new Button(skin, "okButton");
        exitButton = new Button(skin, "closeButton");

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                if (okListener != null) {
                    okListener.onOk(getXValue(), getYValue());
                }
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                setVisible(false);
            }
        });

        window.add(xLabel).padBottom(10);
        window.add(xTextField).padBottom(10).padLeft(5).row();
        window.add(yLabel).padBottom(10);
        window.add(yTextField).padBottom(10).padLeft(5).row();
        window.add(okButton).right();

        window.setSize(500, 300);

        add(exitButton).right().row();
        add(window);
        exitButton.toFront();

        setMovable(false);
        setSize(window.getWidth() + 50, window.getHeight() + 20);
    }

    public int getXValue() {
        try {
            return Integer.parseInt(xTextField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getYValue() {
        try {
            return Integer.parseInt(yTextField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
