package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.model.GameAudioManager;

public class SettingMenu extends Window {

    public SettingMenu(Skin skin) {
        super("", skin, "noWindow");
        setFillParent(true);

        Button closeButton = new Button(skin, "closeButton");
        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        add(closeButton).right().row();

        Table layout = new Table();

        // column 1 :
        Window column1 = new Window("", skin);

        // GameAudioManager :
        Table settings = new Table();
        settings.add(new Label("Setting", skin, "font-90_GREEN")).top().pad(50).row();
        settings.add(new Label("Game Audio Setting", skin, "PINK-30")).pad(20).row();
        Table gameAudioSettings = new Table();
        gameAudioSettings.add(new Label("SFX Volume", skin)).pad(10).left().padTop(20);
        Slider sfxSlider = new Slider(0f, 1f, 0.1f, false, skin, "default-horizontal");
        sfxSlider.setValue(GameAudioManager.sfxVolume);
        gameAudioSettings.add(sfxSlider).padTop(20).row();
        gameAudioSettings.add(new Label("FootStep Volume", skin)).pad(10).left().padTop(20);
        Slider footStepSlider = new Slider(0f, 1f, 0.1f, false, skin, "default-horizontal");
        footStepSlider.setValue(GameAudioManager.footStepVolume);
        gameAudioSettings.add(footStepSlider).padTop(20).row();
        gameAudioSettings.add(new Label("Music Volume", skin)).pad(10).left().padTop(20);
        Slider musicSlider = new Slider(0f, 1f, 0.1f, false, skin, "default-horizontal");
        musicSlider.setValue(GameAudioManager.musicVolume);
        gameAudioSettings.add(musicSlider).padTop(20).row();
        gameAudioSettings.add(new Label("Ambient Volume", skin)).pad(10).left().padTop(20);
        Slider ambientSlider = new Slider(0f, 1f, 0.1f, false, skin, "default-horizontal");
        ambientSlider.setValue(GameAudioManager.sfxVolume);
        gameAudioSettings.add(ambientSlider).padTop(20).row();
        settings.add(gameAudioSettings);
        ScrollPane scrollPane = new ScrollPane(settings, skin, "default2");
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, false);
        scrollPane.setVariableSizeKnobs(false);
        column1.add(scrollPane).top().width(700);
        layout.add(column1).width(700).height(400).left().padRight(30);

        // column 2 :

        Table column2 = new Table();

        Button upButton = new Button(skin, "upButton");
        Button downButton = new Button(skin, "downButton");
        Slider scrollSlider = new Slider(0f, 10, 1, true, skin);
        scrollSlider.setValue(scrollSlider.getMaxValue());

        scrollSlider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                scrollPane.setScrollPercentY((scrollSlider.getMaxValue() - scrollSlider.getValue()) / scrollSlider.getMaxValue());
            }
        });

        scrollPane.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                float newValue = MathUtils.clamp(scrollSlider.getValue() - amountY, 0, scrollSlider.getMaxValue());
                scrollSlider.setValue(newValue);
                return super.scrolled(event, x, y, amountX, amountY);
            }
        });

        scrollPane.addListener(new ChangeListener() {
            private float lastScrollY = -1;

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float currentScroll = scrollPane.getScrollPercentY();
                if (currentScroll != lastScrollY) {
                    lastScrollY = currentScroll;
                    System.out.println("ScrollPane moved by drag! PercentY: " + currentScroll);
                    scrollSlider.setValue(currentScroll * scrollSlider.getMaxValue());
                }
            }
        });

        upButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                scrollSlider.setValue(scrollSlider.getValue() + 1);
                upButton.setChecked(false);
            }
        });

        downButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                scrollSlider.setValue(scrollSlider.getValue() - 1);
                downButton.setChecked(false);
            }
        });
        scrollPane.setScrollPercentY(scrollSlider.getValue() / 10);

        column2.add(upButton).top().row();
        column2.add(scrollSlider).padTop(20).padBottom(20).row();
        column2.add(downButton).bottom();

        layout.add(column2).right();

        add(layout);

        setMovable(false);
        setModal(true);
        setVisible(false);
    }

    public void show(Stage stage) {
        stage.setKeyboardFocus(this);
        setVisible(true);
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);
        stage.addActor(this);
    }

    public void hide() {
        remove();
        setVisible(false);
    }
}
