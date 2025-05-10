package model.GameObject.NPC;

import model.MapModule.Position;
import model.GameObject.LivingEntity;
import model.MapModule.Buildings.Building;
import model.items.Item;

import java.util.ArrayList;

public class NPC extends LivingEntity {
    private final String name;
    private final NpcRequest[] requests = new NpcRequest[3];
    private final ArrayList<NpcRequest> npcRequests = new ArrayList<>();
    private final ArrayList<Item> favoriteItems = new ArrayList<>();
    private final Building workingBuilding;
    private final Job job;
    private int lastActiveRequest;

    public NPC(Position position, boolean walkable, String name, Building workingBuilding, Job job, int lastActiveRequest) {
        super(position,walkable);
        this.name = name;
        this.workingBuilding = workingBuilding;
        this.job = job;
        this.lastActiveRequest = 1;
    }

    public int getLastActiveRequest() {
        return lastActiveRequest;
    }

    public void setLastActiveRequest(int lastActiveRequest) {
        this.lastActiveRequest = lastActiveRequest;
    }

    public Job getJob() {
        return job;
    }

    public Building getWorkingBuilding() {
        return workingBuilding;
    }

    public String getName() {
        return name;
    }

    public NpcRequest[] getRequests() {
        return requests;
    }

    public ArrayList<NpcRequest> getNpcRequests() {
        return npcRequests;
    }

    public ArrayList<Item> getFavoriteItems() {
        return favoriteItems;
    }
}
