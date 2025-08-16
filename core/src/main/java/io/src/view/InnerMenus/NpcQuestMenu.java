package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.src.StardewValley;
import io.src.model.Enums.SfxEnum;
import io.src.model.GameAssetManager;
import io.src.model.GameAudioManager;
import io.src.model.GameObject.NPC.NpcRequest;

import java.util.Arrays;

public class NpcQuestMenu extends Dialog {
    private final TextButton acceptButton;
    private final TextButton rejectButton;
    private TextField tf1;
    private TextField countTf1;
    private TextField tf2;
    private TextField countTf2;

    public NpcQuestMenu(Skin skin, String index, NpcRequest request, int mode, String text) {
        super("", skin);
        // mode state : 0 -> NpcQuest | 1 -> ItemToItem Trade | 2 -> ItemToMoney | 3 -> Request Item | 4 -> Request Money

        GameAudioManager.getInstance().playSound(GameAudioManager.pickRandom(Arrays.asList(
            SfxEnum.UI_SELECT_PATTERN1.getPath()
            , SfxEnum.UI_SELECT_PATTERN2.getPath()
            , SfxEnum.UI_SELECT_PATTERN3.getPath()
            , SfxEnum.UI_SELECT_PATTERN4.getPath()
            , SfxEnum.UI_SELECT_PATTERN5.getPath()
        )), false, GameAudioManager.sfxVolume);

        Window item1Win = new Window("", skin, "default3");
        Window item2Win = new Window("", skin, "default3");
        Table buttons = new Table();
        if (mode == 0)
            acceptButton = new TextButton(" ACCEPT ", skin, "button1-2_font30GREEN");
        else
            acceptButton = new TextButton(" Request ", skin, "button1-2_font30GREEN");
        TextButton cancelButten = new TextButton(" CANCEL ", skin, "button1-2_font30");
        rejectButton = cancelButten;
        Image flashImage = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get("flashDirection"))));
        if (mode == 1 || mode == 0 || mode == 2)
            buttons.add(flashImage).width(120).height(80).padBottom(50).row();
        buttons.add(acceptButton).row();
        buttons.add(cancelButten);
        Texture item1Tex = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRequestedItem().getAssetName())));
        Texture item2Tex = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRewardItem().getAssetName())));
        Image item1Image = new Image(item1Tex);
        Image item2Image = new Image(item2Tex);
        Label item1NameLabel = new Label(request.getRequestedItem().getName(), skin);
        Label item2NameLabel = new Label(request.getRewardItem().getName(), skin);
        Label item1AmountLabel = new Label("x" + request.getRequestedQuantity(), skin, "default-GREEN");
        Label item2AmountLabel = new Label("x" + request.getRewardAmount(), skin, "default-GREEN");
        item1NameLabel.pack();
        item2NameLabel.pack();

        float maxLabel = item1NameLabel.getWidth();
        float maxImageWidth = item1Image.getWidth();
        float maxImageHeight = item1Image.getHeight();
        float xPadding = 100;
        float yPadding = 270;

        // create
        if (mode == 0) {
            if (item2NameLabel.getWidth() > maxLabel)
                maxLabel = item2NameLabel.getWidth();

            if (item2Image.getWidth() > maxImageWidth)
                maxImageWidth = item2Image.getWidth();

            if (item2Image.getHeight() > maxImageHeight)
                maxImageHeight = item2Image.getHeight();

            Table row1 = new Table();
            row1.add(item1Image).width(maxImageWidth * 1.5f).height(maxImageHeight * 1.5f);
            row1.add(item1AmountLabel).bottom();
            item1Win.add(row1).padBottom(25).row();
            item1Win.add(item1NameLabel).row();

            Table row11 = new Table();
            row11.add(item2Image).width(maxImageWidth * 1.5f).height(maxImageHeight * 1.5f);
            row11.add(item2AmountLabel).bottom();
            item2Win.add(row11).padBottom(25).row();
            item2Win.add(item2NameLabel).row();
        } else if (mode == 1) {
            Table table1 = new Table();
            tf1 = new TextField("", skin);
            countTf1 = new TextField("", skin);
            countTf1.setAlignment(Align.center);
            countTf1.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            tf1.setMessageText("for example : Pumpkin");
            table1.add(new Label("First Item name : ", skin)).pad(20).row();
            table1.add(tf1).width(250).pad(20).row();
            table1.add(new Label("Count : ", skin)).pad(20).row();
            table1.add(countTf1).pad(20).row();
            item1Win.add(table1);

            Table table2 = new Table();
            tf2 = new TextField("", skin);
            countTf2 = new TextField("", skin);
            countTf2.setAlignment(Align.center);
            countTf2.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            tf2.setMessageText("for example : Pumpkin");
            table2.add(new Label("Second Item name : ", skin)).pad(20).row();
            table2.add(tf2).width(250).row();
            table2.add(new Label("Count : ", skin)).pad(20).row();
            table2.add(countTf2).row();
            item2Win.add(table2);

            maxLabel = table2.getPrefWidth();
            maxImageHeight = table2.getPrefHeight();
        } else if (mode == 2) {
            Table table1 = new Table();
            tf1 = new TextField("", skin);
            countTf1 = new TextField("", skin);
            countTf1.setAlignment(Align.center);
            countTf1.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            tf1.setMessageText("for example : Pumpkin");
            table1.add(new Label("First Item name : ", skin)).pad(20).row();
            table1.add(tf1).width(250).pad(20).row();
            table1.add(new Label("Count : ", skin)).pad(20).row();
            table1.add(countTf1).pad(20).row();
            item1Win.add(table1);

            Table table2 = new Table();
            Texture tex = new Texture(GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Shop_Hint_Dollar"));
            tf2 = new TextField("", skin, "tf");
            tf2.setMessageText("1000");
            tf2.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            table2.add(new Image(tex)).width(tex.getWidth() * 10).height(tex.getHeight() * 10);
            table2.add(new Label("x", skin)).padLeft(10).bottom();
            table2.add(tf2).width(100).padLeft(5).bottom();
            item2Win.add(table2);

            maxLabel = table1.getPrefWidth();
            maxImageHeight = table1.getPrefHeight();
            yPadding /= 2;
        } else if (mode == 3) {
            Table table2 = new Table();
            Texture tex = new Texture(GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Shop_Hint_Dollar"));
            tf2 = new TextField("", skin, "tf");
            tf2.setMessageText("1000");
            tf2.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            table2.add(new Image(tex)).width(tex.getWidth() * 10).height(tex.getHeight() * 10);
            table2.add(new Label("x", skin)).padLeft(10).bottom();
            table2.add(tf2).width(100).padLeft(5).bottom();
            item2Win.add(table2);

            maxLabel = table2.getPrefWidth();
            maxImageHeight = table2.getPrefHeight();
            yPadding /= 2;
        } else {
            Table table2 = new Table();
            tf2 = new TextField("", skin);
            countTf2 = new TextField("", skin);
            countTf2.setAlignment(Align.center);
            countTf2.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            tf2.setMessageText("for example : Pumpkin");
            table2.add(new Label("Second Item name : ", skin)).pad(20).row();
            table2.add(tf2).width(250).row();
            table2.add(new Label("Count : ", skin)).pad(20).row();
            table2.add(countTf2).row();
            item2Win.add(table2);

            maxLabel = table2.getPrefWidth();
            maxImageHeight = table2.getPrefHeight();
        }

        // add

        Table items = new Table();
        if (mode == 0 || mode == 1 || mode == 2) {
            items.add(item1Win).width(maxLabel + xPadding).height(maxImageHeight + yPadding).pad(50);
            items.add(buttons).bottom().padBottom(50);
            items.add(item2Win).width(maxLabel + xPadding).height(maxImageHeight + yPadding).pad(50);
        } else {
            items.add(item2Win).width(maxLabel + xPadding).height(maxImageHeight + yPadding).pad(50).row();
            buttons.removeActor(flashImage);
            items.add(buttons).bottom().padBottom(50);
        }

        if (mode == 0)
            text += index;
        getContentTable().add(new Label(text, skin, "font-90_PINK")).pad(30).center().row();
        getContentTable().add(items);

        pack();
        setSize(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f, Gdx.graphics.getHeight() / 2f - getHeight() / 2f);


        cancelButten.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
    }

    public TextButton getAcceptButton() {
        return acceptButton;
    }

    public void hideDialog() {
        this.setVisible(false);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);
        Gdx.input.setInputProcessor(StardewValley.getGameView().getGameMenuInputAdapter());
    }


    public TextField getCountTf2() {
        return countTf2;
    }

    public void setCountTf2(TextField countTf2) {
        this.countTf2 = countTf2;
    }

    public TextField getTf2() {
        return tf2;
    }

    public void setTf2(TextField tf2) {
        this.tf2 = tf2;
    }

    public TextField getCountTf1() {
        return countTf1;
    }

    public void setCountTf1(TextField countTf1) {
        this.countTf1 = countTf1;
    }

    public TextField getTf1() {
        return tf1;
    }

    public void setTf1(TextField tf1) {
        this.tf1 = tf1;
    }

    public TextButton getRejectButton() {
        return rejectButton;
    }
}
