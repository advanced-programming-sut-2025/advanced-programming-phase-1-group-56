package io.src.model.GameObject;

import io.src.model.App;
import io.src.model.MapModule.Position;

import java.util.ArrayList;

public class MailBox extends GameObject {
    private final ArrayList<String> unseenMessages = new ArrayList<>();
    private final ArrayList<String> historyMessages = new ArrayList<>();
    public MailBox(Position position) {
        super(false, position);
    }

    public ArrayList<String> getHistoryMessages() {
        return historyMessages;
    }

    public ArrayList<String> getUnseenMessages() {
        return unseenMessages;
    }

    @Override
    public String getAssetName() {
        return "Vanity_MailBox_" + App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason().toString();
    }
}
