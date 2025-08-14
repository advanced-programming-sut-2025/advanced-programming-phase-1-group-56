package io.src.model.Network.Server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.src.controller.Network.ServerController;
import io.src.model.Network.Lobby;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static io.src.controller.Network.ServerController.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final LobbyServer server;
    private String username;
    private PrintWriter out;
    private final Gson gson = new Gson();

    public ClientHandler(Socket socket, LobbyServer server) {
        this.socket = socket;
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String msg) {
        if (out != null) {
            out.println(msg);
            out.flush();
        }
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                processMessage(line);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected (waiting for reconnect): " + username);
            server.markClientAsDisconnected(this);
        }
    }
    private void processMessage(String line){
            Message msg = gson.fromJson(line, Message.class);
            NetworkCommand command;
            try {
                command = NetworkCommand.valueOf(msg.getFromBody("commandType"));
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Unexpected value: " + msg.getFromBody("commandType"));
            }
            switch (command) {
                case updateEmote, emote -> {
                    server.sendChangePlayer(msg);
                }
                case request_game_state -> {
                    sendCurrentGameState(msg.getFromBody("username"), this);
                }

                case OWNERtoDC->{
                    server.sendToDCGame(msg);
                }
//                case load -> {
//                    server.sendLoadGame(msg.getFromBody("lobbyId"));
//                }
                case updatePlayer -> {
                    server.sendChangePlayer(msg);
                }
                case username -> {
                    String name = msg.getFromBody("username");
                    this.username = name;

                    if (server.getDisconnectedClients().containsKey(name)) {
                        System.out.println("Restoring session for: " + name);
                        server.restoreClientSession(name, this);
                    } else {
                        System.out.println("New player connected: " + name);
                    }
                }

                case NetworkCommand.join_lobby -> {
                    System.out.println("Joining lobby");
                    sendMessage(gson.toJson(sendJoinMessage(msg,server)));
                }
                case NetworkCommand.create_lobby -> {
                    System.out.println(10);
                    sendMessage(gson.toJson(sendCreateMessage(msg,server)));
                }
                case NetworkCommand.list_lobbies -> {
                    System.out.println(2);
                    server.sendLobbyListToClient(this);
                }
                case NetworkCommand.online_Users -> {
                    server.broadcastOnlineUsers();
                }
                case NetworkCommand.toggle_ready -> {
                    sendMessage(gson.toJson(server.toggleReady(msg.getFromBody("lobbyId"),msg.getFromBody("username"))));
                }
                case NetworkCommand.leave_lobby -> {
                    System.out.println("Leaving lobby");
                    sendMessage(gson.toJson(server.leaveLobby(msg.getFromBody("username"))));
                }
                case NetworkCommand.kick_user -> {
                    server.kickPlayer(msg.getFromBody("lobbyId"), msg.getFromBody("username"));
                }
                case NetworkCommand.start -> {

                    sendGame(msg.getFromBody("lobbyId"),server.startGameLobby(msg.getFromBody("lobbyId")));
                }
                default ->
                    throw new IllegalStateException("Unexpected value: " + msg.getFromBody("commandType"));
            }
        }


    private void sendGame(String lobbyId,Message messages) {
        Lobby lobby1  = null;
        for(Lobby lobby : server.getLobbies()){
            if(lobby.getId().equals(lobbyId)){
                lobby1 = lobby;
                break;
            }
        }
        ArrayList<String> users = new ArrayList<>();
        users.add(lobby1.getOwner());
        for(String member : lobby1.getMembers()){
            users.add(member);
        }
        for(ClientHandler client : server.getClients()){
            for(String member : users){
                if(client.getUsername().equals(member)){
                    client.sendMessage(gson.toJson(messages));
                }
            }
        }
    }
}
