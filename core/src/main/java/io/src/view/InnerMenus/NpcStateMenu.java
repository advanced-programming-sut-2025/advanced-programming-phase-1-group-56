package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.src.StardewValley;
import io.src.controller.GameMenuController.NpcController;
import io.src.model.Activities.Friendship;
import io.src.model.App;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.GameObject.NPC.NpcFriendship;
import io.src.view.GameMenus.NpcGiftWindow;

public class NpcStateMenu extends Window {
    private Stage stage;
    private NPC npc;

    public NpcStateMenu(Skin skin, NPC npc) {
        super("", skin, "noWindow");
        debug();

        Window mainWin = new Window("", skin, "npcStateWin");
        mainWin.align(Align.right | Align.top);

        mainWin.setMovable(false);
        Texture npcTex = new Texture(Gdx.files.internal("assets\\AVATAR\\final\\" + npc.getType().getName() + "\\1\\avatarProfile.png"));
        Image profile = new Image(npcTex);


        Button closeButton = new Button(skin, "closeButton");
        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideDialog();
            }
        });

        // Buttons

        Table buttonTable = new Table();

        TextButton meetButton = new TextButton("Meet", skin, "npc-45");
        TextButton questButton = new TextButton("Quest", skin, "npc-45");
        TextButton giftButton = new TextButton("Gift", skin, "npc-45");
        int buttonsWidth = 472;
        buttonTable.add(meetButton).width(buttonsWidth).row();
        buttonTable.add(questButton).width(buttonsWidth).row();
        buttonTable.add(giftButton).width(buttonsWidth).row();

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


        // listeners

        meetButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcFriendship f = npc.findFriendshipByPlayer(App.getMe());
                f.setXp(400);
                hideDialog();
                Gdx.input.setInputProcessor(StardewValley.getGameView().getMultiplexer());
                StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);
                try {
                    StardewValley.getGameView().getWarningWindow().showDialog(npc.getType().getName(), NpcController.meetNPC(npc.getType().getName()).getMessage(), 400);
                } catch (Exception e) {
                    StardewValley.getGameView().getWarningWindow().showDialog(npc.getType().getName(), "I dont even know you..\n Why do you want to talk to me", 400);
                }
                npc.setDialogReady(false);
            }
        });

        giftButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideDialog();
                NpcGiftWindow npcGiftWindow = new NpcGiftWindow(npc);
                npcGiftWindow.setVisible(true);
                StardewValley.getGameView().getStage().addActor(npcGiftWindow);
                StardewValley.getGameView().getInvWindow().refreshInventory();
                StardewValley.getGameView().getInventoryBar().refreshInventory();
            }
        });

    }

    public void addStage(Stage stage) {
        this.stage = stage;
    }

    public void showDialog() {
        this.setVisible(true);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(true);
    }

    public void hideDialog() {
        this.setVisible(false);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);
        Gdx.input.setInputProcessor(StardewValley.getGameView().getMultiplexer());
    }
}
