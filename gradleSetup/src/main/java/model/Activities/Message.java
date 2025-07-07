package model.Activities;

import model.App;
import model.Player;
import model.TimeSystem.DateTime;

public class Message {
    private String message;
    private final Player sender;
    private final Player reciever;
    private final DateTime timeStamp;


    public Message(String message,Player sender, Player reciever) {
        this.message = message;
        this.sender = sender;
        this.reciever = reciever;
        this.timeStamp = new DateTime(App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime());
    }


    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public Player getReciever() {
        return reciever;
    }

    public Player getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return  "(" +  timeStamp.getDay() +":" + timeStamp.getHour() +") "+
                sender.getUser().getName() + " says: " + message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
