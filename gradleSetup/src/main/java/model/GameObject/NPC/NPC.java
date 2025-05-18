package model.GameObject.NPC;

import model.Enums.NpcType;
import model.MapModule.Position;
import model.GameObject.LivingEntity;
import model.MapModule.Buildings.Building;
import model.Player;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;
import model.items.Item;

import java.util.ArrayList;

public class NPC extends LivingEntity implements TimeObserver {
    private final NpcType type;
    private final ArrayList<NpcFriendship> friendships = new ArrayList<>();

    public NPC(Position position, NpcType type) {
        super(position, false);
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

    public NpcFriendship findFriendshipByPlayer(Player player) {
        for (NpcFriendship friendship : friendships) {
            if (friendship.getPlayer().equals(player)) {
                return friendship;
            }
        }
        NpcFriendship friendship = new NpcFriendship(player, this);
        friendships.add(friendship);
        player.getNpcFriendShips().add(friendship);
        return friendship;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            for (NpcFriendship friendship : friendships) {
                if (friendship.getLevel() < 3)
                    continue;
                Player playerToGift = friendship.getPlayer();
                int randomPercent = (int) (Math.random() * 100);
                if (randomPercent < 50) {
                    ArrayList<Item> favItems = this.getType().getFavoriteItems();
                    Item item = favItems.get(randomPercent % favItems.size());
                    if (playerToGift.getInventory().canAddItem(item, 1)) {
                        playerToGift.getInventory().add(item, 1);
                        System.out.println(this.type.getName() + " gifted 1*" + item.getName() + " to " +
                                playerToGift.getUser().getName());
                    } else {
                        System.out.println(this.type.getName() + " failed to send you gift beacuse" +
                                " your inventory was full ");
                    }
                }

            }
        }
    }
}
