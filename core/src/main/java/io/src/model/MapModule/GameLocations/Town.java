package io.src.model.MapModule.GameLocations;

import io.src.model.App;
import io.src.model.Enums.GameLocationType;
import io.src.model.GameObject.GameObject;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.MapModule.Buildings.Store;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;

import java.util.ArrayList;

public class Town extends GameLocation implements TimeObserver {
    private final ArrayList<NPC> NPCs = new ArrayList<>();
    private final ArrayList<Store> stores = new ArrayList<>();

    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    public Town(GameLocationType type) {
        super(type);
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        //TODO towns change when time changes

    }

    public void updateGameObjects() {
        for (NPC npc : NPCs) {
            if (!super.getGameObjects().contains(npc)) {
                super.getGameObjects().add(npc);
            }
        }
        for (Store store : this.stores) {
            if (!super.getGameObjects().contains(store)) {
                super.getGameObjects().add(store);
            }
        }
    }

    @Override
    public ArrayList<GameObject> getGameObjects() {

        return super.getGameObjects();
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public NPC getNpcByName(String name) {
        for (NPC npc : NPCs) {
            if (name.equalsIgnoreCase(npc.getType().getName())) {
                return npc;
            }
        }
        return null;
    }
}
