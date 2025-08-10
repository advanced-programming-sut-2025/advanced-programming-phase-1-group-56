package io.src.controller.Network;

import com.google.gson.Gson;
import io.src.model.Network.Client.TCPClient;
import io.src.model.Network.Lobby;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;
import io.src.model.Network.Server.LobbyServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
    public static Message leaveLobby(String username, List<Lobby> lobbies) {
        Iterator<Lobby> iterator = lobbies.iterator();
        while (iterator.hasNext()) {
            Lobby lobby = iterator.next();

            if (lobby.getOwner().equals(username)) {
                iterator.remove();
                removeLobby(lobby.getId(), lobby.getOwner(), lobbies);
                int i = 0;
                for(String member : lobby.getMembers()) {
                    if(member.equals(username)) {
                        break;
                    }
                    i++;
                }
                Boolean[] ready = lobby.getCountReady();
                ready[i] = false;
            } else if (lobby.getMembers().remove(username)) {
                int i = 0;
                for(String member : lobby.getMembers()) {
                    if(member.equals(username)) {
                        break;
                    }
                    i++;
                }
                Boolean[] ready = lobby.getCountReady();
                ready[i] = false;
                break;
            }
        }

        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.leave_lobby);
        body.put("isSuccessful", "left the lobby");
        return new Message(body, Message.Type.response);
    }

    public static void removeLobby(String lobbyId, String requester, List<Lobby> lobbies) {
        lobbies.removeIf(l -> l.getId().equals(lobbyId) && l.getOwner().equals(requester));

    }

    public static Message toggleReady(String lobbyId, String username, List<Lobby> lobbies) {
        int i = 0 ;
        Lobby lobby1 = null;
        for (Lobby lobby : lobbies) {
            if(lobby.getId().equals(lobbyId)){
                lobby1 = lobby;
                for(String member : lobby.getMembers()) {
                    if(member.equals(username)){
                        break;
                    }
                    i++;
                }
            }
        }
        Boolean[] ready = lobby1.getCountReady();
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.toggle_ready);
        if(ready[i]){
            ready[i] = false;
            body.put("Ready", "you aren't ready");
        } else {
            ready[i] = true;
            body.put("Ready", "you are ready now!");
        }
        Message.Type type = Message.Type.response;
        return new Message(body,type);
    }

}
