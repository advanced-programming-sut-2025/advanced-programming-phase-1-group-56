package io.src.model.Network.Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.src.controller.Network.ServerController;
import io.src.model.Network.Lobby;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;
import io.src.model.TimeSystem.LocalDateTimeAdapter;

import java.io.IOException;
import java.lang.reflect.Member;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import static io.src.controller.Network.ServerController.sendCurrentGameState;

public class LobbyServer {
    private static final int PORT = 5000;
    private static final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private static final List<Lobby> lobbies = Collections.synchronizedList(new ArrayList<>());
    private final HashMap<String, DisconnectedSession> disconnectedClients = new HashMap<>();
    private final Map<String, Timer> disconnectTimers = new ConcurrentHashMap<>();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

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
            ClientHandler handler = new ClientHandler(socket, this);
            clients.add(handler);

            new Thread(handler).start();

        }
    }

    public void sendToDCGame(Message message) {
        String userName = message.getFromBody("username");
        for (ClientHandler clientHandler : clients) {
            if (userName.equals(clientHandler.getUsername())) {
                clientHandler.sendMessage(gson.toJson(message));
            }
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
        for(Lobby lobby : lobbies){
            if(lobby.getName().equals(name)||lobby.getOwner().equals(owner)||lobby.getMembers().contains(owner)){
                return null;
            }
        }
        Lobby lobby = new Lobby(UUID.randomUUID().toString(), name, owner, isPrivate,isVisible, password);
        lobbies.add(lobby);
        return lobby;
    }

    public boolean joinLobby(String userName, String lobbyId, String password) {
        int i = 0;
        for (Lobby lobby : lobbies) {
            for(String member : lobby.getMembers()) {
                i++;
                if(member.equals(userName)){
                    return false;
                }
            }
        }
        if(i>3){
            return false;
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



    public Message leaveLobby(String username) {
        broadcastOnlineUsers();
        broadcastLobbyList();
        return ServerController.leaveLobby(username, lobbies);
    }

    public void kickPlayer(String lobbyId, String targetUser) {
        for (Lobby l : lobbies) {
            if (l.getId().equals(lobbyId)) {
                leaveLobby(targetUser);
            }
        }
    }

    public Message startGameLobby(String lobbyId) {
        Lobby lobby1  = null;
        for(Lobby lobby : lobbies){
            if(lobby.getId().equals(lobbyId)){
                lobby1 = lobby;
                break;
            }
        }
        int count = 1 ;
        for(Boolean ready : lobby1.getCountReady()){
            if(ready){
                count++;
            }
        }
        if(count >0){
            ArrayList<String> users = new ArrayList<>();
            users.add(lobby1.getOwner());
            for(String member : lobby1.getMembers()){
                users.add(member);
            }
            return ServerController.startTheGame(lobby1.getMembers().size()+1,users);
        }else{
            return ServerController.startFalseGame();
        }
    }



    public Message toggleReady(String lobbyId,String username){
        return ServerController.toggleReady(lobbyId,username, lobbies);
    }

    public static Collection<Lobby> getLobbies() {
        return lobbies;
    }

    public void removeInactiveLobbies() {
        long now = System.currentTimeMillis();
        lobbies.removeIf(lobby -> lobby.getMembers().isEmpty() && lobby.isInactiveFor(5 * 60 * 1000));
    }

    public void sendChangePlayer(Message msg){
        String username = msg.getFromBody("username");
        Lobby found = null;
        for (Lobby l : lobbies) {
            if (l.getOwner().equals(username) || l.getMembers().contains(username)) {
                found = l;
                break;
            }
        }
        if (found == null) return;

        List<String> recipients = new ArrayList<>();
        recipients.add(found.getOwner());
        recipients.addAll(found.getMembers());

        synchronized (clients) {
            for (String member : recipients) {
                if (member.equals(username)) continue;
                for (ClientHandler clientHandler : clients) {
                    if (member.equals(clientHandler.getUsername())) {
                        clientHandler.sendMessage(gson.toJson(msg));
                    }
                }
            }
        }
    }

    public void markClientAsDisconnected(ClientHandler client) {
        String username = client.getUsername();
        disconnectedClients.put(username, new DisconnectedSession(client, System.currentTimeMillis()));

        Timer timer = new Timer();
        disconnectTimers.put(username, timer);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DisconnectedSession session = disconnectedClients.get(username);
                if (session != null &&
                    System.currentTimeMillis() - session.disconnectTime >= 120_000) {
                    removeClient(session.client);
                    disconnectedClients.remove(username);
                    disconnectTimers.remove(username);
                    System.out.println("Removed client after timeout: " + username);
                }
            }
        }, 120_000);
    }

    public void restoreClientSession(String username, ClientHandler newHandler) {
        DisconnectedSession oldSession = disconnectedClients.remove(username);
        if (oldSession != null) {
            Timer timer = disconnectTimers.remove(username);
            if (timer != null) {
                timer.cancel();
            }
            clients.remove(oldSession.client);
            clients.add(newHandler);
            HashMap<String, Object> body = new HashMap<>();
            body.put("commandType", NetworkCommand.ready_for_state);
            Message.Type type = Message.Type.command;
            System.out.println("love");
            newHandler.sendMessage(new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create().toJson(new Message(body, type)));

            System.out.println("Client restored: " + username);
        }
    }



    public static Set<ClientHandler> getClients() {
        return clients;
    }

    public HashMap<String, DisconnectedSession> getDisconnectedClients() {
        return disconnectedClients;
    }

}
class DisconnectedSession {
    ClientHandler client;
    long disconnectTime;

    DisconnectedSession(ClientHandler client, long disconnectTime) {
        this.client = client;
        this.disconnectTime = disconnectTime;
    }
}

