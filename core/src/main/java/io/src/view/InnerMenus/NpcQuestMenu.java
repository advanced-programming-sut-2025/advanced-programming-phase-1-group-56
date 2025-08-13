package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.StardewValley;
import io.src.controller.GameMenuController.NpcController;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.GameObject.NPC.NpcRequest;
import io.src.model.Result;

public class NpcQuestMenu extends Dialog {


    private TextButton acceptButton;

    public NpcQuestMenu(Skin skin, int index, NpcRequest request, NPC npc) {
        super("", skin);

        // fields :
        Window item1Win = new Window("", skin, "default3");
        Window item2Win = new Window("", skin, "default3");
        Table buttons = new Table();
        acceptButton = new TextButton(" ACCEPT ", skin, "button1-2_font30GREEN");
        TextButton cancelButten = new TextButton(" CANCEL ", skin, "button1-2_font30");
        Image flashImage = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get("flashDirection"))));
        buttons.add(flashImage).width(120).height(80).padBottom(50).row();
        buttons.add(acceptButton).row();
        buttons.add(cancelButten);
        System.out.println(request.getRequestedItem().getAssetName());
        System.out.println(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRequestedItem().getAssetName()));
        Texture item1Tex = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRequestedItem().getAssetName())));
        System.out.println(request.getRewardItem().getAssetName());
        System.out.println(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRewardItem().getAssetName()));
        Texture item2Tex = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRewardItem().getAssetName())));
        Image item1Image = new Image(item1Tex);
        Image item2Image = new Image(item2Tex);
        Label item1NameLabel = new Label(request.getRequestedItem().getName(), skin);
        Label item2NameLabel = new Label(request.getRewardItem().getName(), skin);
        Label item1AmountLabel = new Label("x" + request.getRequestedQuantity(), skin, "default-GREEN");
        Label item2AmountLabel = new Label("x" + request.getRewardAmount(), skin, "default-GREEN");
        item1NameLabel.pack();
        item2NameLabel.pack();

        System.out.println(item1NameLabel.getWidth());
        System.out.println(item2NameLabel.getWidth());

        float maxLabel = item1NameLabel.getWidth();
        if (item2NameLabel.getWidth() > maxLabel)
            maxLabel = item2NameLabel.getWidth();

        float maxImageWidth = item1Image.getWidth();
        if (item2Image.getWidth() > maxImageWidth)
            maxImageWidth = item2Image.getWidth();

        float maxImageHeight = item1Image.getHeight();
        if (item2Image.getHeight() > maxImageHeight)
            maxImageHeight = item2Image.getHeight();

        System.out.println(maxLabel);

        // create
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

        Table items = new Table();
        items.add(item1Win).width(maxLabel + 100).height(maxImageHeight + item1NameLabel.getHeight() + 200).pad(50);
        items.add(buttons).bottom().padBottom(50);
        items.add(item2Win).width(maxLabel + 100).height(maxImageHeight + item1NameLabel.getHeight() + 200).pad(50);

        getContentTable().debug();

        // add

        getContentTable().add(new Label("Request No#" + index, skin, "font-90_PINK")).center().row();
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
}
