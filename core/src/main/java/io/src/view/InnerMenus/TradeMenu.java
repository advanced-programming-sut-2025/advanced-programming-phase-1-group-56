package io.src.view.InnerMenus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.model.Enums.NpcType;

public class TradeMenu extends Window {

    public TradeMenu(Skin skin, PlayerMeetingMenu playerMeetingMenu) {
        super("", skin, "default3");

        TextButton itemToItem = new TextButton("Item to Item", skin, "button1-2_font30GREEN");
        TextButton itemToMoney = new TextButton("Item to Money", skin, "button1-2_font30GREEN");
        TextButton requestMoney = new TextButton("Request Money", skin, "button1-2_font30GREEN");
        TextButton requestItem = new TextButton("Request Item", skin, "button1-2_font30GREEN");
        TextButton back = new TextButton("BACK", skin, "button1-2_font30");

        add(itemToItem).width(300).height(70).pad(5).row();
        add(itemToMoney).width(300).height(70).pad(5).row();
        add(requestMoney).width(300).height(70).pad(5).row();
        add(requestItem).width(300).height(70).pad(5).row();
        add(back).width(300).height(70).pad(20);

        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                TradeMenu.this.setVisible(false);
                playerMeetingMenu.setVisible(true);
            }
        });

        itemToItem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, 0, NpcType.ABIGAIL.getRequests().get(1), 1);
                npcQuestMenu.getAcceptButton().addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        //TODO
                        npcQuestMenu.hide();
                    }
                });
                npcQuestMenu.show(TradeMenu.this.getStage());
            }
        });

        itemToMoney.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, 0, NpcType.ABIGAIL.getRequests().get(1), 2);
                npcQuestMenu.getAcceptButton().addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        //TODO
                        npcQuestMenu.hide();
                    }
                });
                npcQuestMenu.show(TradeMenu.this.getStage());
            }
        });

        requestMoney.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, 0, NpcType.ABIGAIL.getRequests().get(1), 3);
                npcQuestMenu.getAcceptButton().addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        //TODO
                        npcQuestMenu.hide();
                    }
                });
                npcQuestMenu.show(TradeMenu.this.getStage());
            }
        });

        requestItem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, 0, NpcType.ABIGAIL.getRequests().get(1), 4);
                npcQuestMenu.getAcceptButton().addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        //TODO
                        npcQuestMenu.hide();
                    }
                });
                npcQuestMenu.show(TradeMenu.this.getStage());
            }
        });

        pack();
        setMovable(false);
        setModal(true);
    }
}
