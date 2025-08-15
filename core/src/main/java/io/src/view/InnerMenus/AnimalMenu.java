package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.StardewValley;
import io.src.controller.GameMenuController.HusbandryController;
import io.src.model.Enums.SfxEnum;
import io.src.model.Enums.commands.GameCommands.HusbandryCommands;
import io.src.model.GameAssetManager;
import io.src.model.GameAudioManager;
import io.src.model.GameObject.Animal;
import io.src.model.Result;
import io.src.view.GameMenus.GameMenu;
import io.src.view.GameMenus.InterruptingWindow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class AnimalMenu extends Window implements InterruptingWindow {
    private final TextButton freeButton;
    private final TextButton homeButton;
    private final ArrayList<Button> hearts;

    int friendShip = 0;

    public AnimalMenu(Skin skin, Animal animal) {
        super("", skin, "noWindow");

        Button closeButton = new Button(skin, "closeButton");

        // fields
        Window mainWin = new Window("", skin);
        Label TitleLabel = new Label("Animal Menu:", skin, "font-45_PINK");
        mainWin.add(TitleLabel).padTop(40).row();
        Texture animalTexture = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(animal.getType().getAssetName())));
        Image image = new Image(animalTexture);

        Table friendShipButtonTable = new Table();
        hearts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Button heart = new Button(skin, "heart");
            heart.setChecked(true);
            heart.setDisabled(true);
            hearts.add(heart);
            friendShipButtonTable.add(heart).pad(10);
        }

        Table colmnTable = new Table();
        colmnTable.add(friendShipButtonTable).expandY().top().row();
        colmnTable.add(new Label(animal.getName(), skin, "labelBack-24_ZERESHK")).expandY().bottom().left();

        Table animalProfileTable = new Table(skin);
        animalProfileTable.add(image).width(animalTexture.getWidth() * 1.5f).height(animalTexture.getHeight() * 1.5f);
        animalProfileTable.add(colmnTable).bottom();
        mainWin.add(animalProfileTable).pad(70);

        TextButton feedButton = new TextButton("FEED", skin, "button1-2_font30GREEN");
        TextButton petButton = new TextButton("PET", skin, "button1-2_font30GREEN");
        homeButton = new TextButton("HOME", skin, "button1-2_font30GREEN");
        freeButton = new TextButton("FREE", skin, "button1-2_font30");
        TextButton sellButton = new TextButton("SELL", skin, "button1-2_font30");
        TextButton productsButton = new TextButton("GET PRODUCTS", skin, "button1-2_font30");

        Stack homeFreeStack = new Stack();
        homeButton.setSize(150, 70);
        freeButton.setSize(150, 70);
        homeFreeStack.add(homeButton);
        homeFreeStack.add(freeButton);
        freeButton.setVisible(false);

        Table buttonTable = new Table();
        buttonTable.add(feedButton).width(150).height(70);
        buttonTable.add(petButton).width(150).height(70).row();
        buttonTable.add(homeFreeStack).width(150).height(70);
        buttonTable.add(sellButton).width(150).height(70).row();

        Table buttonTable2 = new Table();
        buttonTable2.add(buttonTable).row();
        buttonTable2.add(productsButton).width(300).height(70);

        mainWin.add(buttonTable2).pad(70);
        mainWin.pack();
        mainWin.setMovable(false);


        add(closeButton).right().row();
        add(mainWin);

        pack();
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f, Gdx.graphics.getHeight() / 2f - getHeight() / 2f);
        setMovable(false);
        setModal(true);

        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.NOTE_SNARE.getPath(), false, GameAudioManager.sfxVolume);
                hideDialog();
            }
        });

        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.NOTE_HARP.getPath(), false, GameAudioManager.sfxVolume);
                changeHomeFreeState(false);
            }
        });

        freeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.NOTE_HARP.getPath(), false, GameAudioManager.sfxVolume);
                changeHomeFreeState(true);
            }
        });

        feedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.NOTE_HARP.getPath(), false, GameAudioManager.sfxVolume);
                Result result = HusbandryController.feedHay(animal.getName());
                StardewValley.getGameView().getWarningWindow().showDialog(animal.getNickName(), result.getMessage(), 300);
                if (result.isSuccess()) {
                    animal.setFeedHint(true);
                }
                animal.setLastFeedingTime(LocalDateTime.now());
                AnimalMenu.this.hideDialog();
            }
        });

        petButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.NOTE_HARP.getPath(), false, GameAudioManager.sfxVolume);
                Result result = HusbandryController.petting(animal.getName());
                StardewValley.getGameView().getWarningWindow().showDialog(animal.getNickName(), result.getMessage(), 300);
                animal.setPetHint(true);
                animal.setLastPettingTime(LocalDateTime.now());
                AnimalMenu.this.hideDialog();
            }
        });

        productsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.NOTE_HARP.getPath(), false, GameAudioManager.sfxVolume);
                Result result = HusbandryController.collectProduce(animal.getName());
                StardewValley.getGameView().getWarningWindow().showDialog(animal.getNickName(), result.getMessage(), 300);
                AnimalMenu.this.hideDialog();
            }
        });

        sellButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.NOTE_HARP.getPath(), false, GameAudioManager.sfxVolume);
                Result result = HusbandryController.sellAnimal(animal.getName());
                StardewValley.getGameView().getWarningWindow().showDialog(animal.getNickName(), result.getMessage(), 300);
                AnimalMenu.this.hideDialog();
            }
        });
    }

    private void changeHomeFreeState(boolean state) {
        freeButton.setVisible(!state);
        homeButton.setVisible(state);
    }

    public void setFriendShip(int amount) {
        for (int i = 0; i < 4; i++)
            hearts.get(i).setChecked(true);

        if (400 > amount && amount >= 200) {
            hearts.getFirst().setChecked(false);
        } else if (amount >= 400 && amount < 600) {
            hearts.getFirst().setChecked(false);
            hearts.get(1).setChecked(false);
        } else if (amount >= 600 && amount < 800) {
            hearts.getFirst().setChecked(false);
            hearts.get(2).setChecked(false);
            hearts.get(1).setChecked(false);
        } else if (amount >= 800 && amount < 1000) {
            hearts.getFirst().setChecked(false);
            hearts.get(1).setChecked(false);
            hearts.get(2).setChecked(false);
            hearts.get(3).setChecked(false);
        }
    }

    @Override
    public void showDialog() {
        GameAudioManager.getInstance().playSound(GameAudioManager.pickRandom(Arrays.asList(
            SfxEnum.UI_SELECT_PATTERN1.getPath()
            , SfxEnum.UI_SELECT_PATTERN2.getPath()
            , SfxEnum.UI_SELECT_PATTERN3.getPath()
            , SfxEnum.UI_SELECT_PATTERN4.getPath()
            , SfxEnum.UI_SELECT_PATTERN5.getPath()
        )), false, GameAudioManager.sfxVolume);
        this.setVisible(true);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(true);
    }

    @Override
    public void hideDialog() {
        this.setVisible(false);
        StardewValley.getGameView().getStage().unfocus(this);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);
        Gdx.input.setInputProcessor(StardewValley.getGameView().getMultiplexer());
    }
}
