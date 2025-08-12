package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.NPC.NpcRequest;

public class NpcQuestMenu extends Dialog {
    public NpcQuestMenu(Skin skin, int index, NpcRequest request) {
        super("", skin, "default3");

        // fields :
        Window item1Win = new Window("", skin, "default3");
        Window item2Win = new Window("", skin, "default3");
        Table buttons = new Table();
        TextButton acceptButten = new TextButton(" ACCEPT ", skin, "button1-2_font30GREEN");
        TextButton cancelButten = new TextButton(" CANCEL ", skin, "button1-2_font30");
        buttons.add(acceptButten).row();
        buttons.add(cancelButten);
        Texture item1Tex = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRequestedItem().getAssetName())));
        System.out.println(request.getRewardItem().getAssetName());
        System.out.println(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRewardItem().getAssetName()));
        Texture item2Tex = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(request.getRewardItem().getAssetName())));
        Image item1Image = new Image(item1Tex);
        Image item2Image = new Image(item2Tex);
        Label item1NameLabel = new Label("item1", skin);
        Label item2NameLabel = new Label("item2", skin);
        Label item1AmountLabel = new Label("x21", skin);
        Label item2AmountLabel = new Label("x50", skin);

        // create
        item1Win.add(item1Image).row();
        item1Win.add(item1AmountLabel).row();
        item1Win.add(item1NameLabel).row();

        item2Win.add(item2Image).row();
        item2Win.add(item2AmountLabel).row();
        item2Win.add(item2NameLabel).row();

        // add

        getContentTable().add(new Label("Request No#" + index, skin, "font-90_PINK"));
        getContentTable().add(item1Win).width(item1Tex.getWidth() * 5).height(item1Tex.getHeight() * 5);
        getContentTable().add(buttons);
        getContentTable().add(item2Win).width(item2Tex.getWidth() * 5).height(item2Tex.getHeight() * 5);

        pack();
        setSize(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f, Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

        acceptButten.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        cancelButten.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
    }
}
