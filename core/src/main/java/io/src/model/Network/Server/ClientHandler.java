package io.src.model.Network.Server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

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
                try {
                    Message msg = gson.fromJson(line, Message.class);
                    if (msg == null ) continue;
                    switch (msg.getFromBody("commandType")) {
                        case NetworkCommand.join_lobby -> {
                            this.username = msg.getFromBody("username");
                            String lobbyId = msg.getFromBody("lobbyId");
                            String password = msg.getFromBody("password");
                            boolean success = server.joinLobby(username, lobbyId, password);
                            if (success) {
                                sendMessage(gson.toJson(new Message(NetworkCommand.join_lobby, "server", msg.getLobbyId(), "joined", null)));
                            } else {
                                sendMessage(gson.toJson(new Message(NetworkCommand.error, "server", null, "Join failed (maybe wrong password or full lobby)", null)));
                            }
                        }
                        case NetworkCommand.create_lobby -> {
                            String owner = msg.getFromBody("ownerName");
                            String lobbyName = msg.getFromBody("lobbyName");
                            String password = msg.getFromBody("password");
                            String isPrivate1 = msg.getFromBody("isPrivate");
                            String isVisible1 = msg.getFromBody("isVisible");
                            boolean isPrivate = isPrivate1.equals("yes");
                            boolean isVisible = isVisible1.equals("yes");

                            boolean success = server.createLobby(lobbyName,owner,isPrivate,password,isVisible) != null;
                            HashMap<String, Object> body = new HashMap<>();
                            body.put("commandType", NetworkCommand.create_lobby);
                            body.put("isSuccessful", success);
                            Message.Type type = Message.Type.response;
                            sendMessage(gson.toJson(new Message(body, type)));
                        }
                        case NetworkCommand.list_lobbies -> {
                            server.sendLobbyListToClient(this);
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
                    }
                } catch (JsonSyntaxException e) {
                    sendMessage(gson.toJson(new Message(NetworkCommand.error, "server", null, "Invalid message format", null)));
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + username);
        } finally {
            server.removeClient(this);
        }
    }
}
