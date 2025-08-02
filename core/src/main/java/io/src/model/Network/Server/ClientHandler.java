package io.src.model.Network.Server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.src.controller.Network.ServerController;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static io.src.controller.Network.ServerController.sendCreateMessage;
import static io.src.controller.Network.ServerController.sendJoinMessage;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final LobbyServer server;
    private String username;
    private PrintWriter out;
    private final Gson gson = new Gson();

    public ClientHandler(Socket socket, LobbyServer server, String username) {
        this.username = username;
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
                try {
                    Message msg = gson.fromJson(line, Message.class);
                    NetworkCommand command;
                    if (msg == null ) continue;
                    try {
                        command = NetworkCommand.valueOf(msg.getFromBody("commandType"));
                    } catch (IllegalArgumentException e) {
                        throw new IllegalStateException("Unexpected value: " + msg.getFromBody("commandType"));
                    }
                    switch (command) {
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
//                        case NetworkCommand.leave_lobby -> {
//                            server.leaveLobby(msg.getSender());
//                        }
//                        case NetworkCommand.remove_lobby -> {
//                            server.removeLobby(msg.getLobbyId(), msg.getSender());
//                        }
//                        case NetworkCommand.kick_user -> {
//                            server.kickPlayer(msg.getLobbyId(), msg.getSender(), msg.getTarget());
//                        }
//                        case NetworkCommand.lobby_chat_message -> {
//                            server.sendChatToLobby(msg);
//                        }
//                        default -> sendMessage(gson.toJson(new Message(NetworkCommand.error, "server", null, "Unknown command", null)));
                        default ->
                            throw new IllegalStateException("Unexpected value: " + msg.getFromBody("commandType"));
                    }
                } catch (JsonSyntaxException e) {
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("commandType", NetworkCommand.error);
                    body.put("ERROR","THERE IS NO COMMAND TYPE:");
                    Message.Type type = Message.Type.response;
                    sendMessage(gson.toJson(new Message(body, type)));
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + username);
        } finally {
            server.removeClient(this);
        }
    }
}
