package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.src.model.Activities.Gift;
import io.src.model.Enums.NpcType;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.NPC.NpcRequest;
import io.src.model.User;
import io.src.model.items.Item;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerMeetingMenu extends Window {
    private Item ringItem;
    private Gift giftItem;
    private ArrayList<Button> buttons;
    private NpcRequest request;

    public PlayerMeetingMenu(Skin skin, User user) {
        super("", skin, "noWindow");
        setFillParent(true);
        int buttonHeight = 70;

        Button closeButton = new Button(skin, "closeButton");
        add(closeButton).right().row();

        Window mainWin = new Window("", skin);

        // 1
        Table column1 = new Table();

        TextButton hugButton = new TextButton("Hug", skin, "button1-2_font30GREEN");
        TextButton bloomButton = new TextButton("Bloom", skin, "button1-2_font30GREEN");

        TextButton marryButton = new TextButton("MARRY", skin, "button1-2_font30GREEN");
        TextButton recentProposalButton = new TextButton("Recent Proposal", skin, "button1-2_font30GREEN");
        TextButton giftButton = new TextButton("Gift", skin, "button1-2_font30GREEN");
        TextButton recentGiftButton = new TextButton("Recent Gift", skin, "button1-2_font30GREEN");
        TextButton tradeButton = new TextButton("Trade", skin, "button1-2_font30GREEN");
        TextButton recentTradeButton = new TextButton("Recent Trade", skin, "button1-2_font30GREEN");

        column1.add(hugButton).width(500).height(buttonHeight).row();
        column1.add(bloomButton).width(500).height(buttonHeight).row();

        Table row1 = new Table();
        row1.add(marryButton).height(buttonHeight).width(150);
        row1.add(recentProposalButton).height(buttonHeight).width(350).row();
        row1.add(giftButton).height(buttonHeight).width(150);
        row1.add(recentGiftButton).height(buttonHeight).width(350).row();
        row1.add(tradeButton).height(buttonHeight).width(150);
        row1.add(recentTradeButton).height(buttonHeight).width(350).row();

        column1.add(row1);

        // 2
        Table column2 = new Table();

        // load avatars :
        File[] avatarsPath = new File("assets\\AVATAR\\final\\").listFiles(File::isDirectory);
        Texture userTex = new Texture(Gdx.files.internal("AVATAR\\final\\" +
                Objects.requireNonNull(avatarsPath)[user.getAvatarIndex()].getName() + "\\" +
                user.getAvatarStyleIndex() + "\\avatarProfile.png"));
        Image userImg = new Image(userTex);

        // crate column 2 :
        column2.add(userImg).padTop(50).padRight(50).width(userTex.getWidth() * 3).height(userTex.getHeight() * 3).row();
        column2.add(new Label(user.getName(), skin, "back45")).padBottom(50).padTop(-30).padRight(50);

        // add

        mainWin.add(column1).pad(50);
        mainWin.add(column2);

        mainWin.setMovable(false);
        add(mainWin);

        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - getHeight() / 2);
        pack();
        setMovable(false);
        setModal(true);

        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                PlayerMeetingMenu.this.setVisible(false);
            }
        });

        recentProposalButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("", skin, "askWindow");
                dialog.setMovable(false);
                Texture tex = new Texture
                        (GameAssetManager.getGameAssetManager().getAssetsDictionary().get(ringItem.getAssetName()));
                dialog.getContentTable().add(new Image(tex))
                        .width(tex.getWidth() * 3).height(tex.getHeight() * 3).padTop(25).row();
                dialog.getContentTable().add(new Label("Do you accept the marriage proposal?", skin)).pad(50);
                TextButton acceptButton = new TextButton("  ACCEPT  ", skin, "button1-2_font30GREEN");
                acceptButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        //TODO
                        dialog.hide();
                    }
                });
                TextButton cancelButton = new TextButton("  CANCEL  ", skin, "button1-2_font30");
                cancelButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        //TODO
                        dialog.hide();
                    }
                });
                dialog.getButtonTable().add(acceptButton).height(60).pad(25);
                dialog.getButtonTable().add(cancelButton).height(60).pad(25);
                dialog.show(PlayerMeetingMenu.this.getStage());
            }
        });

        recentTradeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, "", request, 0, "Do you accept the Trade?");
                npcQuestMenu.show(PlayerMeetingMenu.this.getStage());
            }
        });

        recentGiftButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("", skin, "askWindow");
                dialog.setMovable(false);
                Table row1 = new Table();
                Texture texture = new Texture(
                        GameAssetManager.getGameAssetManager().getAssetsDictionary().get(giftItem.getGift().getAssetName()));
                row1.add(new Image(texture)).width(texture.getWidth() * 3).height(texture.getHeight() * 3);
                Table row2 = new Table();
                row2.align(Align.left);
                row2.add(new Label("x" + giftItem.getAmount(), skin, "gray24")).bottom().row();
                row2.add(new Label(giftItem.getGift().getName(), skin, "default30"));
                row1.add(row2).padLeft(20).bottom().padTop(25);
                dialog.getContentTable().add(row1).row();
                dialog.getContentTable().add(new Label("Rate the gift", skin)).pad(50).row();

                Table buttonsTable = new Table();
                buttons = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    Button button = new Button(skin, "star");
                    buttons.add(button);
                    buttonsTable.add(button).pad(20);
                    button.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                            for (int j = 0; j < 5; j++)
                                buttons.get(j).setChecked(j <= buttons.indexOf(button));
                            button.setChecked(true);
                        }
                    });
                }
                buttons.getFirst().setChecked(true);
                dialog.getContentTable().add(buttonsTable);

                TextButton cancelButton = new TextButton("Cancel", skin);
                cancelButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        dialog.hide();
                    }
                });
                TextButton acceptButton = new TextButton("Done", skin);
                acceptButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        int rate = checkRate();
                        System.out.println(rate);
                        //TODO
                        dialog.hide();
                    }
                });
                dialog.getButtonTable().add(cancelButton).pad(50);
                dialog.getButtonTable().add(acceptButton).pad(50);


                dialog.show(PlayerMeetingMenu.this.getStage());
            }
        });

        tradeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                TradeMenu tradeMenu = new TradeMenu(skin, PlayerMeetingMenu.this);
                tradeMenu.setPosition(Gdx.graphics.getWidth() / 2f - tradeMenu.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2f - tradeMenu.getHeight() / 2);
                tradeMenu.setVisible(false);
                PlayerMeetingMenu.this.getStage().addActor(tradeMenu);
                PlayerMeetingMenu.this.setVisible(false);
                tradeMenu.setVisible(true);
            }
        });
    }

    private int checkRate() {
        int rate = 0;
        for (Button button : buttons) {
            if (button.isChecked())
                rate++;
        }
        return rate;
    }

    public void setRing(Item item) {
        ringItem = item;
    }

    public void setGiftItem(Gift giftItem) {
        this.giftItem = giftItem;
    }

    public void setRequest(NpcRequest npcRequest) {
        this.request = npcRequest;
    }
}
