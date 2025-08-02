package io.src.controller.Network;

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
}
