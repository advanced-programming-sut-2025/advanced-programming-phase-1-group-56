package model.GameObject.NPC;

import model.Enums.NpcType;
import model.MapModule.Position;
import model.GameObject.LivingEntity;
import model.MapModule.Buildings.Building;
import model.items.Item;

import java.util.ArrayList;

public class NPC extends LivingEntity {
    private final NpcType type;
    private ArrayList<NpcFriendship> friendships;

    public NPC(Position position,NpcType type) {
        super(position,false);
        this.type = type;
    }

    public NpcType getType() {
        return type;
    }


    public ArrayList<NpcFriendship> getFriendships() {
        return friendships;
    }

    public void addFriendship(NpcFriendship friendship) {
        friendships.add(friendship);
    }
}
