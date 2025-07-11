package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimerWindow extends Window {
    private Label timeLabel;
    public TimerWindow(Skin skin) {
        super("Time / Date", skin);
        timeLabel = new Label("00:00", skin);
        add(timeLabel).pad(5);
        pack();
        setPosition(Gdx.graphics.getWidth() - getWidth() - 10, Gdx.graphics.getHeight() - getHeight() - 10);
        setMovable(false);
    }
    public void update(LocalTime time) {
        timeLabel.setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
