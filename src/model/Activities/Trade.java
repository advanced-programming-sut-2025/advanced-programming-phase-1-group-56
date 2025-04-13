package model.Activities;

import model.items.Item;
import model.User;
import java.util.HashMap;

public class Trade {
    private final int tradeID = 0;
    private final User counterParty = null;
    private final HashMap<Item, Integer> itemsGiven = new HashMap<>();
    private final HashMap<Item, Integer> itemsGets = new HashMap<>();
    private int moneyGiven;
    private int moneyGets;
    private boolean successful;

}
