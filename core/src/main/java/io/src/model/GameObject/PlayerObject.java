package io.src.model.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.StardewValley;
import io.src.controller.GameMenuController.FriendshipController;
import io.src.model.*;
import io.src.model.MapModule.Position;
import io.src.view.GameMenus.DragFromInventoryWindow;
import io.src.view.GameMenus.NpcGiftWindow;
import io.src.view.GameMenus.WarningWindow;
import io.src.view.InnerMenus.PlayerMeetingMenu;

import java.time.LocalDateTime;

public class PlayerObject extends GameObject implements Clickable, SensitiveToPlayer {
    private Player player;
    private boolean recentlyFlirt = false;
    private boolean recentlyGifted = false;
    private boolean recentlyBloomed = false;

    public void setRecentlyProposed(boolean recentlyProposed) {
        this.recentlyProposed = recentlyProposed;
    }

    private boolean recentlyProposed = false;
    private boolean recentlyRejected = false;
    private LocalDateTime lastFlirt = LocalDateTime.now();


    public PlayerObject(Player player, Position position) {
        super(false, position);
        this.player = player;
    }


    @Override
    public String getAssetName() {
        return "";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            PlayerObject me = App.getMe().getPlayerObjectPlusPosition(App.getMe().getPosition());
            PlayerObject meetingPlayer = this;
            System.out.println("Meeting" + this.getPlayer().getUser().getName());
            PlayerMeetingMenu playerMeetingMenu = new PlayerMeetingMenu(SkinManager.getInstance().getSkin(SkinManager.MAIN_SKIN), this.player.getUser());
            playerMeetingMenu.showDialog();
            StardewValley.getGameView().getStage().addActor(playerMeetingMenu);

            //hugListener
            playerMeetingMenu.getHugButton().addListener(
                new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Result result = FriendshipController.hugPlayer(meetingPlayer.getPlayer().getUser().getUsername());
                        WarningWindow warningWindow = StardewValley.getGameView().getWarningWindow();
                        warningWindow.showDialog(meetingPlayer.getPlayer().getUser().getName(), result.getMessage(), 250);
                        me.setLastFlirt(LocalDateTime.now());
                        me.setRecentlyFlirt(true);
                        //Freeze Screen
                        meetingPlayer.setLastFlirt(LocalDateTime.now());
                        meetingPlayer.setRecentlyFlirt(true);
                        playerMeetingMenu.hideDialog();
                        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(true);
                        Gdx.input.setInputProcessor(null);
                    }
                }
            );
            //Bloom Listener
            playerMeetingMenu.getBloomButton().addListener(
                new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Image box = new Image(new Texture(Gdx.files.internal(
                            GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Bloom_Hand1")
                        )));

                        DragFromInventoryWindow bloomingWindow = new DragFromInventoryWindow(box);
                        bloomingWindow.setBoxPosition(new Position(210, 270));
                        bloomingWindow.createTrash();
                        playerMeetingMenu.hideDialog();

                        bloomingWindow.setBoxDropRunnable(
                            (index) -> {
                                if (App.getMe().getCurrentItem() == null) return;
                                if (App.getMe().getInventory().getSlots().get(index) == null) return;
                                if (App.getMe().getInventory().getSlots().get(index).getItem() == null) return;
                                Result result = FriendshipController.buyFlower(meetingPlayer.getPlayer().getUser().getUsername(), App.getMe().getInventory().getSlots().get(index).getItem().getName());
                                WarningWindow warningWindow = StardewValley.getGameView().getWarningWindow();
                                warningWindow.showDialog(meetingPlayer.getPlayer().getUser().getName(), result.getMessage(), 250);
                                if (result.isSuccess()) {
                                    me.setLastFlirt(LocalDateTime.now());
                                    me.setRecentlyFlirt(true);
                                    me.setRecentlyBloomed(true);
                                    //Freeze Screen
                                    meetingPlayer.setLastFlirt(LocalDateTime.now());
                                    meetingPlayer.setRecentlyFlirt(true);
                                    playerMeetingMenu.hideDialog();
                                    bloomingWindow.hideDialog();
                                }
                            }
                        );
                        bloomingWindow.showDialog();
                    }
                }
            );

            //GIFT Listener
            playerMeetingMenu.getGiftButton().addListener(
                new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Image box = new Image(new Texture(Gdx.files.internal(
                            GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Gift_Box")
                        )));

                        DragFromInventoryWindow giftWindow = new DragFromInventoryWindow(box);
                        giftWindow.setBoxPosition(new Position(210, 270));
                        giftWindow.setBoxDropDimentions(new Position(80, 80));
                        giftWindow.createTrash();
                        playerMeetingMenu.hideDialog();

                        giftWindow.setBoxDropRunnable(
                            (index) -> {
                                if (App.getMe().getCurrentItem() == null) return;
                                if (App.getMe().getInventory().getSlots().get(index) == null) return;
                                if (App.getMe().getInventory().getSlots().get(index).getItem() == null) return;
                                Result result = FriendshipController.sendGift(meetingPlayer.getPlayer().getUser().getUsername(), App.getMe().getInventory().getSlots().get(index).getItem().getName(), String.valueOf(1));
                                WarningWindow warningWindow = StardewValley.getGameView().getWarningWindow();
                                warningWindow.showDialog(meetingPlayer.getPlayer().getUser().getName(), result.getMessage(), 250);
                                if (result.isSuccess()) {
                                    me.setLastFlirt(LocalDateTime.now());
                                    me.setRecentlyFlirt(true);
                                    me.setRecentlyGifted(true);
                                    //Freeze Screen
                                    meetingPlayer.setLastFlirt(LocalDateTime.now());
                                    meetingPlayer.setRecentlyFlirt(true);
                                    playerMeetingMenu.hideDialog();
                                    giftWindow.hideDialog();
                                }
                            }
                        );
                        giftWindow.showDialog();
                    }
                }
            );


            //Marry Listener
            playerMeetingMenu.getMarryButton().addListener(
                new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Image box = new Image(new Texture(Gdx.files.internal(
                            GameAssetManager.getGameAssetManager().getAssetsDictionary().get("courtship")
                        )));

                        DragFromInventoryWindow marryWindow = new DragFromInventoryWindow(box);
                        marryWindow.setBoxPosition(new Position(210, 270));
                        marryWindow.setBoxDropDimentions(new Position(80, 70));
                        marryWindow.createTrash();
                        playerMeetingMenu.hideDialog();

                        marryWindow.setBoxDropRunnable(
                            (index) -> {
                                if (App.getMe().getCurrentItem() == null) return;
                                if (App.getMe().getInventory().getSlots().get(index) == null) return;
                                if (App.getMe().getInventory().getSlots().get(index).getItem() == null) return;
                                Result result = FriendshipController.askMarriage(meetingPlayer.getPlayer().getUser().getUsername(), App.getMe().getInventory().getSlots().get(index).getItem().getName());
                                WarningWindow warningWindow = StardewValley.getGameView().getWarningWindow();
                                warningWindow.showDialog(meetingPlayer.getPlayer().getUser().getName(), result.getMessage(), 250);
                                if (result.isSuccess()) {
                                    me.setLastFlirt(LocalDateTime.now());
                                    me.setRecentlyFlirt(true);
                                    meetingPlayer.setLastFlirt(LocalDateTime.now());
                                    meetingPlayer.setRecentlyFlirt(true);
                                    meetingPlayer.setRecntlyProposed(true);
                                    playerMeetingMenu.hideDialog();
                                    marryWindow.hideDialog();
                                }
                            }
                        );
                        marryWindow.showDialog();
                    }
                }
            );
        }
        return false;
    }

    private void setRecntlyProposed(boolean b) {
        this.recentlyProposed = true;
    }

    @Override
    public boolean onPlayerGoesNearby(float distance) {
        System.out.println("Nearing" + this.getPlayer().getUser().getName());
        return false;
    }

    @Override
    public boolean onPlayerGetsFar(float distance) {
        System.out.println("Faring" + this.getPlayer().getUser().getName());
        return false;
    }

    @Override
    public boolean onPlayerFocus() {
        System.out.println("Focusing" + this.getPlayer().getUser().getName());
        return false;
    }

    @Override
    public boolean onPlayerDefocus() {
        System.out.println("Defocusing" + this.getPlayer().getUser().getName());
        return false;
    }

    @Override
    public float getSensitivityDistance() {
        return 3;
    }

    public boolean isRecentlyFlirt() {
        return recentlyFlirt;
    }

    public void setRecentlyFlirt(boolean recentlyFlirt) {
        this.recentlyFlirt = recentlyFlirt;
    }

    public LocalDateTime getLastFlirt() {
        return lastFlirt;
    }

    public void setLastFlirt(LocalDateTime lastFlirt) {
        this.lastFlirt = lastFlirt;
    }

    public boolean isRecentlyGifted() {
        return recentlyGifted;
    }

    public void setRecentlyGifted(boolean recentlyGifted) {
        this.recentlyGifted = recentlyGifted;
    }

    public boolean isRecentlyBloomed() {
        return recentlyBloomed;
    }

    public void setRecentlyBloomed(boolean recentlyBloomed) {
        this.recentlyBloomed = recentlyBloomed;
    }


    public boolean isRecentlyProposed() {
        return recentlyProposed;
    }

    public boolean isRecentlyRejected() {
        return recentlyRejected;
    }

    public void setRecentlyRejected(boolean recentlyRejected) {
        this.recentlyRejected = recentlyRejected;
    }
}
