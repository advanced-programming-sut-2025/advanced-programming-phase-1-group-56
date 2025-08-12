package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class NpcStateMenu extends Window {
    private Stage stage;

    public NpcStateMenu(Skin skin, String npcAssetImage) {
        super("", skin, "noWindow");
        debug();

        Window mainWin = new Window("", skin, "npcStateWin");
        mainWin.align(Align.right | Align.top);

        mainWin.setMovable(false);
        Texture npcTex = new Texture(Gdx.files.internal("assets\\AVATAR\\final\\Abigail\\1\\avatarProfile.png"));
        Image profile = new Image(npcTex);


        Button closeButton = new Button(skin, "closeButton");
        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcStateMenu.this.setVisible(false);
            }
        });

        // Buttons

        Table buttonTable = new Table();

        TextButton meetButton = new TextButton("Meet", skin, "npc-45");
        TextButton QuestButton = new TextButton("Quest", skin, "npc-45");
        TextButton GiftButton = new TextButton("Gift", skin, "npc-45");
        int buttonsWidth = 472;
        buttonTable.add(meetButton).width(buttonsWidth).row();
        buttonTable.add(QuestButton).width(buttonsWidth).row();
        buttonTable.add(GiftButton).width(buttonsWidth).row();

        // add

        mainWin.add(buttonTable).expandX().left();
        mainWin.add(profile).width(npcTex.getWidth() * 3).height(npcTex.getHeight() * 3).padRight(35);

        add(closeButton).right().row();
        add(mainWin).width(npcTex.getWidth() * 12 - 85).height(npcTex.getHeight() * 4.5f - 10).row();

        pack();
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - getHeight() / 2f);
        setModal(true);
        setMovable(false);
        validate();
    }

    public void addStage(Stage stage) {
        this.stage = stage;
    }
}
