//package io.src.view.InnerMenus;
//
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//
//public class AnimalNameMiniMenu extends Window {
//
//    private final TextField nameTextField;
//    private final Button okButton;
//    private final Button exitButton;
//
//    public interface OnOkListener {
//        void onOk(String name);
//    }
//
//    public AnimalNameMiniMenu(Skin skin, OnOkListener okListener) {
//        super("", skin, "default4");
//
//        Window window = new Window("", skin, "default3");
//
//        nameTextField = new TextField("", skin);
//        Label nameLabel = new Label("Name", skin);
//
//        okButton = new Button(skin, "okButton");
//        exitButton = new Button(skin, "closeButton");
//
//        okButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (okListener != null) {
//                    okListener.onOk(getNameText());
//                }
//            }
//        });
//
//        exitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                setVisible(false);
//            }
//        });
//
//        window.add(nameLabel);
//        window.add(nameTextField).row();
//        window.add(okButton);
//
//        window.setSize(400, 200);
//
//        add(exitButton).right().row();
//        add(window);
//        exitButton.toFront();
//
//        setSize(window.getWidth() + 50, window.getHeight() + 20);
//    }
//
//    public String getNameText() {
//        return nameTextField.getText();
//    }
//}


package io.src.view.InnerMenus;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class AnimalNameDialog extends Dialog {

    public interface OnOkListener {
        void onOk(String name);
    }

    private final TextField nameTextField;
    private final OnOkListener okListener;

    public AnimalNameDialog(Skin skin, OnOkListener okListener) {
        super("Name your animal", skin);
        this.okListener = okListener;

        setModal(true);
        setMovable(false);

        Table content = getContentTable();
        content.pad(12);

        content.add(new Label("Name:", skin)).left();
        nameTextField = new TextField("", skin);
        nameTextField.setAlignment(Align.left);
        content.add(nameTextField).width(220).row();

        // دکمه‌ها: OK مقدار true، Cancel مقدار false برمی‌گرداند
        button("OK ", true);
        button(" Cancel", false);

        pack();
    }


    public void result(Object object) {
        if (object instanceof Boolean && (Boolean) object) {
            String name = nameTextField.getText().trim();
            if (name.isEmpty()) name = "Unnamed";
            if (okListener != null) okListener.onOk(name);
        }
        // بعد از بستن، فوکوس رو برگردون
        if (getStage() != null) {
            getStage().setKeyboardFocus(null);
            getStage().setScrollFocus(null);
        }
    }

    public void showCentered(Stage stage) {
        show(stage); // این متد dialog را به stage اضافه می‌کند و modal می‌کند
        // فوکس صفحه‌کلید روی فیلد
        stage.setKeyboardFocus(nameTextField);
        // مرکز کردن (اختیاری ولی مفید)
        float x = (stage.getViewport().getWorldWidth() - getWidth()) / 2f;
        float y = (stage.getViewport().getWorldHeight() - getHeight()) / 2f;
        setPosition(x, y);
    }
}
