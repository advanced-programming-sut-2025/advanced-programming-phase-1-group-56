package io.src.model.Network.Server;

import com.google.gson.Gson;
import io.src.controller.Network.ServerController;
import io.src.model.Network.Lobby;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.Timer;

public class LobbyServer {
    private static final int PORT = 5000;
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private final List<Lobby> lobbies = Collections.synchronizedList(new ArrayList<>());
    private final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        new LobbyServer().start();
    }

    public void start() throws IOException {
        new Timer().schedule(new TimerTask() {
            public void run() {
                removeInactiveLobbies();
            }
        }, 0, 60_000);

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket, this,"mohsen");
            clients.add(handler);

            new Thread(handler).start();

        }
    }

    public void broadcastOnlineUsers() {
        var usernames = clients.stream()
            .map(ClientHandler::getUsername)
            .filter(Objects::nonNull)
            .toList();

        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.online_Users);
        body.put("Users List", usernames);
        Message.Type type = Message.Type.response;
        System.out.println(123);
        sendToAll(new Message(body, type));
    }

    public void sendToAll(Message msg) {
        String json = gson.toJson(msg);
        synchronized (clients) {
            for (ClientHandler c : clients) {
                c.sendMessage(json);
            }
        }
    }

    public void sendLobbyListToClient(ClientHandler client) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.list_lobbies);
        ArrayList<Lobby> lobbies1 = new ArrayList<>();
        for(Lobby lobby : lobbies) {
            if(lobby.getOwner().equals(client.getUsername())||lobby.isVisible()){
                lobbies1.add(lobby);
            }
        }
        body.put("Lobbies List", lobbies1);
        Message.Type type = Message.Type.response;
        client.sendMessage(gson.toJson(new Message(body, type)));
    }

    public void broadcastLobbyList() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.list_lobbies);
        ArrayList<Lobby> lobbies1 = new ArrayList<>();
        for(Lobby lobby : lobbies) {
            if(lobby.isVisible()){
                lobbies1.add(lobby);
            }
        }
        body.put("Lobbies List", lobbies1);
        Message.Type type = Message.Type.response;
        sendToAll(new Message(body, type));
    }


    public void removeClient(ClientHandler client) {
        clients.remove(client);
        leaveLobby(client.getUsername());
        broadcastOnlineUsers();
    }

    public Lobby createLobby(String name, String owner, boolean isPrivate, String password,boolean isVisible) {
//        for(Lobby lobby : lobbies){
//            if(lobby.getName().equals(name)||lobby.getOwner().equals(owner)){
//                return null;
//            }
//        }

        Lobby lobby = new Lobby(UUID.randomUUID().toString(), name, owner, isPrivate,isVisible, password);
        lobbies.add(lobby);
        return lobby;
    }

    public boolean joinLobby(String userName, String lobbyId, String password) {
        for (Lobby lobby : lobbies) {
            for(String member : lobby.getMembers()) {
                if(member.equals(userName)){
                    return false;
                }
            }
        }
        for (Lobby lobby : lobbies) {
            if (lobby.getId().equals(lobbyId)) {
                if (lobby.isPrivate() && !lobby.checkPassword(password)) {
                    return false;
                }
                if (lobby.getMembers().contains(userName)) return true;
                lobby.addMember(userName);
                broadcastLobbyList();
                return true;
            }
        }
        return false;
    }

    public void leaveLobby(String username) {
        ServerController.leaveLobby(username,(ArrayList<Lobby>) lobbies);
        broadcastOnlineUsers();
    }

    public void removeLobby(String lobbyId, String requester) {
        ServerController.removeLobby(lobbyId,requester, (ArrayList<Lobby>) lobbies);
        broadcastLobbyList();
    }

    public void kickPlayer(String lobbyId, String requester, String targetUser) {
        for (Lobby l : lobbies) {
            if (l.getId().equals(lobbyId) && l.getOwner().equals(requester)) {
                leaveLobby(targetUser);
            }
        }
    }


//    public void sendChatToLobby(Message msg) {
//        for (Lobby lobby : lobbies) {
//            if (lobby.getId().equals(msg.getLobbyId())) {
//                String json = gson.toJson(msg);
//                synchronized (clients) {
//                    for (ClientHandler c : clients) {
//                        if (lobby.getMembers().contains(c.getUsername())) {
//                            c.sendMessage(json);
//                        }
//                    }
//                }
//                break;
//            }
//        }
//    }

    public Collection<Lobby> getLobbies() {
        return lobbies;
    }

    public void removeInactiveLobbies() {
        long now = System.currentTimeMillis();
        lobbies.removeIf(lobby -> lobby.getMembers().isEmpty() && lobby.isInactiveFor(5 * 60 * 1000));
    }
}
