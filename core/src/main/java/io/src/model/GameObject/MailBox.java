package io.src.model.GameObject;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.src.StardewValley;
import io.src.model.Activities.Message;
import io.src.model.App;
import io.src.model.SkinManager;
import io.src.view.GameMenus.MailBoxDialog;
import io.src.model.MapModule.Position;
import io.src.model.Clickable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MailBox extends GameObject implements Clickable, SensitiveToPlayer {
    private ArrayList<Message> unseenMessages = new ArrayList<>();
    private final ArrayList<Message> historyMessages = new ArrayList<>();
    private Boolean hasNewMessages = false;

    public MailBox(Position position) {
        super(false, position);

    }

    public ArrayList<Message> getHistoryMessages() {
        return new ArrayList<>(historyMessages);
    }

    public ArrayList<Message> getUnseenMessages() {
        return new ArrayList<>(unseenMessages);
    }

    public void addUnseenMessage(Message msg) {
        if (msg == null) return;
        unseenMessages.add(msg);
    }

    public void addHistoryMessage(Message msg) {
        if (msg == null) return;
        historyMessages.add(msg);
    }

    /**
     * Mark all unseen as seen by moving them to history.
     * This is called when the dialog is closed (so next open they will appear as seen).
     */
    public void markAllUnseenAsSeen() {
        if (!unseenMessages.isEmpty()) {
            historyMessages.addAll(unseenMessages);
            unseenMessages.clear();
        }
    }

    @Override
    public String getAssetName() {
        return "Farm_Mailbox";
    }

    /**
     * When mailbox is touched (e.g. touchDown from the in-game menu), show the messages dialog.
     * Returns true to indicate the event was handled.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Prepare skin & stage (adjust skin path if your project uses different path)
        if (button == Input.Buttons.RIGHT) {
            Skin skin = SkinManager.getInstance().getSkin("mainSkin/mainSkin.json");
            Stage stage = StardewValley.getGameView().getStage();
            // Create dialog, pass references (we pass the mailbox itself so dialog can read unseen/history)
            MailBoxDialog dialog = new MailBoxDialog(skin, this);

            // Show centered on stage
            dialog.showCentered(stage);
            hasNewMessages = false;
        }
        // consume event
        return true;
    }

    @Override
    public boolean onPlayerGoesNearby(float distance) {
        ArrayList<Message> allMessages = new ArrayList<>();
        allMessages.addAll(unseenMessages);
        allMessages.addAll(historyMessages);
        for (Message message : App.getMe().getMessages()) {
            if (!allMessages.contains(message)) {
                unseenMessages.add(message);
                hasNewMessages = true;
            }
        }
        return false;
    }

    @Override
    public boolean onPlayerGetsFar(float distance) {
        return false;
    }

    @Override
    public boolean onPlayerFocus() {
        return false;
    }

    @Override
    public boolean onPlayerDefocus() {
        return false;
    }

    @Override
    public float getSensitivityDistance() {
        return 3;
    }

    public Boolean getHasNewMessages() {
        return hasNewMessages;
    }

    public void setHasNewMessages(Boolean hasNewMessages) {
        this.hasNewMessages = hasNewMessages;
    }
}
