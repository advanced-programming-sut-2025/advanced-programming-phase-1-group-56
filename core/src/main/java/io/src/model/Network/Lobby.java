package io.src.model.Network;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private final String id;
    private final String name;
    private final String owner;
    private final boolean isPrivate;
    private final boolean isVisible;
    private final String password;
    private final List<String> members = new ArrayList<>();
    private long lastJoinTime;

    public Lobby(String id, String name, String owner, boolean isPrivate, boolean isVisible, String password) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.password = password;
        this.lastJoinTime = System.currentTimeMillis();
        members.add(owner);
    }

    public boolean checkPassword(String input) {
        return password == null || password.equals(input);
    }

    public void updateJoinTime() {
        lastJoinTime = System.currentTimeMillis();
    }

    public boolean isInactiveFor(long ms) {
        return System.currentTimeMillis() - lastJoinTime > ms;
    }

    public List<String> getMembers() {
        return members;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getOwner() { return owner; }
    public boolean isPrivate() { return isPrivate; }


    public void addMember(String username) {
        members.add(username);
    }

    public boolean isVisible() {
        return isVisible;
    }
}
