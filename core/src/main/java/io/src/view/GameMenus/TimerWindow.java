package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.src.model.App;
import io.src.model.GameAssetManager;
import io.src.model.TimeSystem.TimeSystem;
import io.src.model.Enums.WeatherAndTime.DayOfWeek;

public class TimerWindow extends Group {

    private Label dayLabel;
    private Label timeLabel;
    private Image arrowPointer;
    private Image clockFrame;
    private Image weatherIcon;
    private Image seasonIcon;
    private Skin skin;

    private Texture currentWeatherTexture = null;
    private Texture currentSeasonTexture = null;

    private final Group goldDigitsGroup = new Group();

    public TimerWindow() {
        skin = GameAssetManager.getGameAssetManager().getSkin();

        clockFrame = new Image(GameAssetManager.getGameAssetManager().getClock());
        clockFrame.setScale(4f, 4f);
        clockFrame.setPosition(80, 10);

        arrowPointer = new Image(GameAssetManager.getGameAssetManager().getClockCursor());
        arrowPointer.setScale(2.5f, 3f);
        arrowPointer.setOrigin(Align.bottom);
        arrowPointer.setPosition(164, 160);

        String assetNameWeather = App.getCurrentUser().getCurrentGame().getWeatherState().getCurrentWeather().getAssetName();
        String assetPathWeather = GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetNameWeather);
        if (assetPathWeather != null) {
            currentWeatherTexture = new Texture(Gdx.files.internal(assetPathWeather));
            weatherIcon = new Image(currentWeatherTexture);
        } else {
            weatherIcon = new Image();
        }

        String assetNameSeason = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason().getAssetName();
        String assetPathSeason = GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetNameSeason);
        if (assetPathSeason != null) {
            currentSeasonTexture = new Texture(Gdx.files.internal(assetPathSeason));
            seasonIcon = new Image(currentSeasonTexture);
        } else {
            seasonIcon = new Image();
        }

        weatherIcon.setScale(1.2f, 1.2f);
        weatherIcon.setOrigin(Align.bottom);
        weatherIcon.setPosition(205, 144);

        seasonIcon.setScale(1.2f, 1.2f);
        seasonIcon.setOrigin(Align.bottom);
        seasonIcon.setPosition(303, 146);

        TimeSystem timeSystem = App.getCurrentUser().getCurrentGame().getTimeSystem();
        int day = timeSystem.getDateTime().getDay();
        int hour = timeSystem.getDateTime().getHour();
        DayOfWeek dayOfWeek = timeSystem.getDateTime().getDayOfWeek();

        dayLabel = new Label(String.format("%s,%02d", dayOfWeek.name(), day), skin);
        dayLabel.setPosition(180, 188);

        timeLabel = new Label(String.format(" %02d:00 ", hour), skin);
        timeLabel.setPosition(210, 94);

        addActor(clockFrame);
        addActor(arrowPointer);
        addActor(dayLabel);
        addActor(timeLabel);
        addActor(weatherIcon);
        addActor(seasonIcon);

        addActor(goldDigitsGroup);

        setPosition(
            Gdx.graphics.getWidth() - 370,
            Gdx.graphics.getHeight() - 260
        );

        updateTime();
        updateGold();
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

        String assetNameWeather = App.getCurrentUser().getCurrentGame().getWeatherState().getCurrentWeather().getAssetName();
        String assetPathWeather = GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetNameWeather);
        if (assetPathWeather != null) {
            if (currentWeatherTexture != null) {
                try { currentWeatherTexture.dispose(); } catch (Exception ignored) {}
            }
            currentWeatherTexture = new Texture(Gdx.files.internal(assetPathWeather));
            weatherIcon.setDrawable(new Image(currentWeatherTexture).getDrawable());
        }

        String assetNameSeason = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason().getAssetName();
        String assetPathSeason = GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetNameSeason);
        if (assetPathSeason != null) {
            if (currentSeasonTexture != null) {
                try { currentSeasonTexture.dispose(); } catch (Exception ignored) {}
            }
            currentSeasonTexture = new Texture(Gdx.files.internal(assetPathSeason));
            seasonIcon.setDrawable(new Image(currentSeasonTexture).getDrawable());
        }
    }

    public void updateGold() {
        int gold = App.getMe().getGold();
        String goldStr = String.valueOf(gold);

        goldDigitsGroup.clear();


        float startX = 164f;
        float y = 24f;
        float spacing = 22f;
        int len = goldStr.length();
        startX += (8 - len) * spacing;

        for (int i = 0; i < goldStr.length(); i++) {
            char digit = goldStr.charAt(i);
            Label digitLabel = new Label(String.valueOf(digit), skin);
            digitLabel.setPosition(startX + i * spacing, y);
            goldDigitsGroup.addActor(digitLabel);
        }
    }

    public void disposeTextures() {
        if (currentWeatherTexture != null) {
            try { currentWeatherTexture.dispose(); } catch (Exception ignored) {}
            currentWeatherTexture = null;
        }
        if (currentSeasonTexture != null) {
            try { currentSeasonTexture.dispose(); } catch (Exception ignored) {}
            currentSeasonTexture = null;
        }
    }

    public Image getSeasonIcon() {
        return seasonIcon;
    }
}
