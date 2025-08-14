//package io.src.view.InnerMenus;
//
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//
//public class BuildingMiniMenu extends Window {
//
//    private final TextField xTextField;
//    private final TextField yTextField;
//    private final Button okButton;
//    private final Button exitButton;
//
//    public interface OnOkListener {
//        void onOk(int x, int y);
//    }
//
//    public BuildingMiniMenu(Skin skin, OnOkListener okListener) {
//        super("", skin, "default4");
//
//        Window window = new Window("", skin, "default3");
//
//        xTextField = new TextField("", skin);
//        yTextField = new TextField("", skin);
//
//        Label xLabel = new Label("x", skin);
//        Label yLabel = new Label("y", skin);
//
//        okButton = new Button(skin, "okButton");
//        exitButton = new Button(skin, "closeButton");
//
//        okButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float xx, float yy) {
//                if (okListener != null) {
//                    okListener.onOk(getXValue(), getYValue());
//                }
//            }
//        });
//
//        exitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float xx, float yy) {
//                setVisible(false);
//            }
//        });
//
//        window.add(xLabel).padBottom(10);
//        window.add(xTextField).padBottom(10).padLeft(5).row();
//        window.add(yLabel).padBottom(10);
//        window.add(yTextField).padBottom(10).padLeft(5).row();
//        window.add(okButton).right();
//
//        window.setSize(500, 300);
//
//        add(exitButton).right().row();
//        add(window);
//        exitButton.toFront();
//
//        setSize(window.getWidth() + 50, window.getHeight() + 20);
//    }
//
//    public int getXValue() {
//        try {
//            return Integer.parseInt(xTextField.getText());
//        } catch (NumberFormatException e) {
//            return 0;
//        }
//    }
//
//    public int getYValue() {
//        try {
//            return Integer.parseInt(yTextField.getText());
//        } catch (NumberFormatException e) {
//            return 0;
//        }
//    }
//}

package io.src.view.InnerMenus;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class BuildingMiniMenu extends Dialog {

    public interface OnOkListener {
        void onOk(int x, int y);
    }

    private final TextField xTextField;
    private final TextField yTextField;
    private final OnOkListener okListener;

    public BuildingMiniMenu(Skin skin, OnOkListener okListener) {
        super("Place Building", skin);
        this.okListener = okListener;

        setModal(true);
        setMovable(false);

        Table content = getContentTable();
        content.pad(12);

        content.add(new Label("X:", skin)).left();
        xTextField = new TextField("", skin);
        xTextField.setAlignment(Align.center);
        content.add(xTextField).width(80).row();

        content.add(new Label("Y:", skin)).left();
        yTextField = new TextField("", skin);
        yTextField.setAlignment(Align.center);
        content.add(yTextField).width(80).row();

        // Buttons: OK returns "ok", Cancel returns "cancel"
        button("OK ", "ok");
        button(" Cancel", "cancel");

        pack();
    }

    @Override
    public void result(Object object) {
        if ("ok".equals(object)) {
            if (okListener != null) okListener.onOk(getXValue(), getYValue());
        }
        // Dialog will hide automatically after result is returned
    }

    private int getXValue() {
        try {
            return Integer.parseInt(xTextField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int getYValue() {
        try {
            return Integer.parseInt(yTextField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void showCentered(Stage stage) {
        show(stage); // adds to stage and starts modal focus handling
        // ensure keyboard focus to first field
        stage.setKeyboardFocus(xTextField);
        // center on stage (optional)
        setPosition((stage.getViewport().getWorldWidth() - getWidth()) / 2f,
            (stage.getViewport().getWorldHeight() - getHeight()) / 2f);
    }
}

