package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.StardewValley;
import io.src.controller.GameMenuController.TradeController;
import io.src.model.Activities.Trade;
import io.src.model.Enums.Activity.TradeType;
import io.src.model.Enums.NpcType;
import io.src.model.Result;

public class TradeMenu extends Window {

    private TextButton itemToItem;
    private TextButton itemToMoney;
    private TextButton requestMoney;
    private TextButton requestItem;
    private TextButton back;
    private Trade tradeTODO = null;

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
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, "0", NpcType.ABIGAIL.getRequests().get(1), 1, "Item To Item Trade");
                npcQuestMenu.getAcceptButton().addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        npcQuestMenu.hide();
                        TradeMenu.this.hideDialog();
                        String item1 = npcQuestMenu.getTf1().getText();
                        String itemCount1 = npcQuestMenu.getCountTf1().getText();
                        String item2 = npcQuestMenu.getTf2().getText();
                        String itemCount2 = npcQuestMenu.getCountTf2().getText();
                        Result result = TradeController.makeNewTrade(item1, itemCount1, item2, itemCount2, TradeType.PRODUCT_TO_PRODUCT_OFFER, playerMeetingMenu.getPlayer());
                        StardewValley.getGameView().getWarningWindow().showDialog(playerMeetingMenu.getPlayer().getUser().getName(), result.getMessage(), 300);
                    }
                });
                npcQuestMenu.show(TradeMenu.this.getStage());
            }
        });

        itemToMoney.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, "0", NpcType.ABIGAIL.getRequests().get(1), 2, "Item To Money Trade");
                npcQuestMenu.getAcceptButton().addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        String item1 = npcQuestMenu.getTf1().getText();
                        String itemCount1 = npcQuestMenu.getCountTf1().getText();
                        String item2 = "";
                        String itemCount2 = npcQuestMenu.getTf2().getText();
                        Result result = TradeController.makeNewTrade(item1, itemCount1, item2, itemCount2, TradeType.PRODUCT_TO_MONEY_OFFER, playerMeetingMenu.getPlayer());
                        StardewValley.getGameView().getWarningWindow().showDialog(playerMeetingMenu.getPlayer().getUser().getName(), result.getMessage(), 300);
                        npcQuestMenu.hide();
                        TradeMenu.this.hideDialog();
                    }
                });
                npcQuestMenu.show(TradeMenu.this.getStage());
            }
        });

        requestMoney.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, "0", NpcType.ABIGAIL.getRequests().get(1), 3, "Request Money Trade");
                npcQuestMenu.getAcceptButton().addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        String item1 = "";
                        String itemCount1 = "";
                        String item2 = "";
                        String itemCount2 = npcQuestMenu.getTf2().getText();
                        Result result = TradeController.makeNewTrade(item1, itemCount1, item2, itemCount2, TradeType.MONEY_REQUEST, playerMeetingMenu.getPlayer());
                        StardewValley.getGameView().getWarningWindow().showDialog(playerMeetingMenu.getPlayer().getUser().getName(), result.getMessage(), 300);
                        npcQuestMenu.hide();
                        TradeMenu.this.hideDialog();
                    }
                });
                npcQuestMenu.show(TradeMenu.this.getStage());
            }
        });

        requestItem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                NpcQuestMenu npcQuestMenu = new NpcQuestMenu(skin, "0", NpcType.ABIGAIL.getRequests().get(1), 4, "Trade Request");
                npcQuestMenu.getAcceptButton().addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        String item1 = "";
                        String itemCount1 = "";
                        String item2 = npcQuestMenu.getTf2().getText();
                        String itemCount2 = npcQuestMenu.getCountTf2().getText();
                        Result result = TradeController.makeNewTrade(item1, itemCount1, item2, itemCount2, TradeType.PRODUCT_REQUEST, playerMeetingMenu.getPlayer());
                        StardewValley.getGameView().getWarningWindow().showDialog(playerMeetingMenu.getPlayer().getUser().getName(), result.getMessage(), 300);
                        npcQuestMenu.hide();
                        TradeMenu.this.hideDialog();
                    }
                });
                npcQuestMenu.show(TradeMenu.this.getStage());
            }
        });

        pack();
        setMovable(false);
        setModal(true);
    }

    public Trade getTradeTODO() {
        return tradeTODO;
    }

    public void setTradeTODO(Trade tradeTODO) {
        this.tradeTODO = tradeTODO;
    }

    public void hideDialog() {
        this.setVisible(false);
        StardewValley.getGameView().getStage().unfocus(this);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);
        Gdx.input.setInputProcessor(StardewValley.getGameView().getMultiplexer());
    }
}
