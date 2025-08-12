package io.src.controller.Network;

import com.google.gson.Gson;
import io.src.model.Network.Client.TCPClient;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;

import java.io.IOException;
import java.util.HashMap;

public class ClientController {
    public static Message sendJoinMessageToServer(String lobbyId, String password, String username) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.join_lobby);
        body.put("username", username);
        body.put("lobbyId", lobbyId);
        body.put("password", password);
        Message.Type type = Message.Type.command;
        return new Message(body, type);
    }

    public static void sendUserNameToServer(String username, TCPClient client, Gson gson){
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.username);
        body.put("username", username);
        client.send(gson.toJson(new Message(body, Message.Type.command)));
    }


    public static void startTheGame(int numberOfPlayers){

    }


}
