package model.MapModule.GameLocations;

import model.App;
import model.GameObject.NPC.NPC;
import model.MapModule.Buildings.Store;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

import java.util.ArrayList;

public class Town extends GameLocation implements TimeObserver {
    private final ArrayList<NPC> NPCs = new ArrayList<>();
    private final ArrayList<Store> stores = new ArrayList<>();

    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    public Town(){
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        //TODO towns change when time changes

    }

    public ArrayList<Store> getStores() {
        return stores;
    }
}
