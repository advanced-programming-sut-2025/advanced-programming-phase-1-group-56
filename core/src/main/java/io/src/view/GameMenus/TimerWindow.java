package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import io.src.model.App;
import io.src.model.Enums.WeatherAndTime.DayOfWeek;
import io.src.model.GameAssetManager;
import io.src.model.TimeSystem.TimeSystem;

public class TimerWindow extends Group {

    private Label dayLabel;
    private Label timeLabel;
    private Label goldLabel;
    private Image arrowPointer;
    private Image clockFrame;
    private Skin skin;

    public TimerWindow() {
        skin = GameAssetManager.getGameAssetManager().getSkin();

        clockFrame = new Image(GameAssetManager.getGameAssetManager().getClock());
        clockFrame.setScale(4f, 4f);
        clockFrame.setPosition(80, 10);


        arrowPointer = new Image(GameAssetManager.getGameAssetManager().getClockCursor());
        arrowPointer.setScale(2.5f, 3f);
        arrowPointer.setOrigin(Align.bottom);
        arrowPointer.setPosition(164, 160);

        TimeSystem timeSystem = App.getCurrentUser().getCurrentGame().getTimeSystem();
        int day = timeSystem.getDateTime().getDay();
        int hour = timeSystem.getDateTime().getHour();
        DayOfWeek dayOfWeek = timeSystem.getDateTime().getDayOfWeek();

        dayLabel = new Label(String.format("%s,%02d", dayOfWeek.name(), day), skin);
        dayLabel.setPosition(180, 188);

        goldLabel = new Label(String.format("%d",App.getMe().getGold()),skin);
        goldLabel.setPosition(240, 40);

        timeLabel = new Label(String.format(" %02d:00 ", hour), skin);
        timeLabel.setPosition(210, 94);

        int gold = App.getMe().getGold();
        String goldStr = String.valueOf(gold);
        float startX = 264;
        float y = 24;
        float spacing = 22;
        int len = goldStr.length();
        startX  += (8-len)*spacing;

        for (int i = 0; i < goldStr.length(); i++) {
            char digit = goldStr.charAt(i);
            Label digitLabel = new Label(String.valueOf(digit), skin);
            digitLabel.setPosition(startX + i * spacing, y);
            addActor(digitLabel);
        }

        addActor(clockFrame);
        addActor(arrowPointer);
        addActor(dayLabel);
        addActor(timeLabel);

        setPosition(
            Gdx.graphics.getWidth() - 370,
            Gdx.graphics.getHeight() - 260
        );

        updateTime();
    }

    public void updateTime() {
        TimeSystem timeSystem = App.getCurrentUser().getCurrentGame().getTimeSystem();
        int hour = timeSystem.getDateTime().getHour();
        int day = timeSystem.getDateTime().getDay();
        DayOfWeek dayOfWeek = timeSystem.getDateTime().getDayOfWeek();

        timeLabel.setText(String.format(" %02d:00 ", hour));
        dayLabel.setText(String.format(" %s,%02d ", dayOfWeek.name(), day));

        int totalMinutes = hour * 60;
        int start = 9 * 60;
        int end = 22 * 60;

        totalMinutes = Math.max(start, Math.min(end, totalMinutes));
        float progress = (totalMinutes - start) / 780f;
        float angle = progress * 180f;

        arrowPointer.setRotation(180 - angle);
    }

    public void updateGold() {
        int gold = App.getMe().getGold();
        String goldStr = String.valueOf(gold);
        goldLabel.remove();
        goldLabel = new Label(goldStr,skin);
        goldLabel.setPosition(260, 24);
        addActor(goldLabel);
    }
}
