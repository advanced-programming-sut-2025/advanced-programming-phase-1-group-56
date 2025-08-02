package io.src.controller.Network;

import com.google.gson.Gson;
import io.src.model.Network.Client.TCPClient;
import io.src.model.Network.Lobby;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;
import io.src.model.Network.Server.LobbyServer;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerController {
    public static Message sendJoinMessage(Message msg, LobbyServer server) {
        String username = msg.getFromBody("username");
        String lobbyId = msg.getFromBody("lobbyId");
        String password = msg.getFromBody("password");
        boolean success = server.joinLobby(username, lobbyId, password);
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.join_lobby);
        body.put("isSuccessful", success);
        Message.Type type = Message.Type.response;
        Message response = new Message(body, type);
        return response;
    }

    public static Message sendCreateMessage(Message msg, LobbyServer server) {
        String owner = msg.getFromBody("ownerName");
        String lobbyName = msg.getFromBody("lobbyName");
        String password = msg.getFromBody("password");
        boolean isPrivate = msg.getFromBody("isPrivate");
        boolean isVisible = msg.getFromBody("isVisible");

        boolean success = server.createLobby(lobbyName,owner,isPrivate,password,isVisible) != null;

        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.create_lobby);
        body.put("isSuccessful", success);
        Message.Type type = Message.Type.response;
        return new  Message(body, type);
    }
    public static void leaveLobby(String username, ArrayList<Lobby> lobbies) {
        for (Lobby lobby : lobbies) {
            if (lobby.getOwner().equals(username)) {
                removeLobby(lobby.getId(), lobby.getOwner(), lobbies);
            } else if (lobby.getMembers().remove(username)) {
                break;
            }
        }
    }
    public static void removeLobby(String lobbyId, String requester, ArrayList<Lobby> lobbies) {
        lobbies.removeIf(l -> l.getId().equals(lobbyId) && l.getOwner().equals(requester));
    }
}
