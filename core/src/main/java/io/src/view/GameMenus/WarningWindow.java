package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class WarningWindow extends Window {
    private final Label speakerLabel;
    private final Label textLabel;
    private int remainingTime = 300;

    public WarningWindow(Skin skin) {
        super("", skin);

        speakerLabel = new Label("", skin);
        speakerLabel.setAlignment(Align.left);

        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        textLabel.setAlignment(Align.left);

        add(speakerLabel).expandX().left().padBottom(5).row();
        add(textLabel).width(700).height(100).left().top();

        pack();

        setVisible(false);
    }

    public void showDialog(String speaker, String text, int remainingTime) {
        this.remainingTime = remainingTime;
        speakerLabel.setText(speaker);
        textLabel.setText(text);

        // (Optional) اگه خواستی با طول متن عرض بده ولی مراقب باش زیاد نپره
        float width = Math.max(300, Math.min(text.length() * 10, 700)); // حداکثر 600
        this.setWidth(width);
        textLabel.setWidth(width);
        speakerLabel.setWidth(width);

        pack();
        setPosition(
            (Gdx.graphics.getWidth() - getWidth()) / 2,
            50
        );
        setVisible(true);
        getColor().a = 0;
        addAction(fadeIn(0.5f));
    }

    public void hideDialog() {
        addAction(sequence(
            fadeOut(2f),
            run(() -> setVisible(false))
        ));
        remainingTime = 1000;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int hit_box) {
        this.remainingTime = hit_box;
        if (hit_box < 0) {
            hideDialog();
        }
    }
}
