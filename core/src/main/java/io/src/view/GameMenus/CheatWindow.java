package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.src.model.App;
import io.src.model.Result;

public class CheatWindow extends Window {

    private final Label cheatLabel;
    private final TextField cheatInputField;
    private Skin skin;
    private InputProcessor previousProcessor;


    public CheatWindow(Skin skin) {
        super("", skin);
        this.skin = skin;
        setSize(300, 130);

        cheatLabel = new Label("cheat BOX", skin);
        cheatLabel.setAlignment(Align.center);
        this.add(cheatLabel).colspan(1).center().padTop(10).row();

        cheatInputField = new TextField("", skin);
        cheatInputField.setAlignment(Align.center);
        this.add(cheatInputField).width(250).height(50).padBottom(10);


        cheatInputField.setTextFieldListener((textField, c) -> {
            if (c == '\r' || c == '\n') {
                String command = cheatInputField.getText();
                executeCheat(command);
            }
        });

        cheatInputField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    hideDialog(getStage());
                    return true;
                }
                return false;
            }
        });


        setPosition(100, 100);
    }

    private void executeCheat(String command) {
        Result result = App.getCurrentMenu().checkCommand(App.getScanner(), command);

        //CLOSING WINDOW
        cheatInputField.setText("");
        getStage().unfocus(cheatInputField);
        setVisible(false);


        //RESULT
        WarningWindow resultWindow = App.getStardewValley().getGameView().getWarningWindow();
        resultWindow.setVisible(true);
        resultWindow.showDialog("Cheat Result:" + ((result.isSuccess()) ? "Success" : "Failure"),
            result.message(), 400);
    }


    public void showWithFocus(Stage stage) {
        Gdx.app.postRunnable(() -> {
            this.setVisible(true);
            cheatInputField.setDisabled(false);
            stage.setKeyboardFocus(cheatInputField);
            cheatInputField.setText("");
        });
    }

    public void hideDialog(Stage stage) {
        this.setVisible(false);
        stage.unfocus(cheatInputField);
    }

    public Actor getTextField() {
        return cheatInputField;
    }
}
