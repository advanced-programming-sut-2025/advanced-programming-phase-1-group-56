package io.src.controller.Network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.src.model.*;
import io.src.model.Activities.Friendship;
import io.src.model.Enums.FarmPosition;
import io.src.model.Enums.GameLocationType;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.GameMap;
import io.src.model.Network.DTO.GameDTO;
import io.src.model.Network.DTO.GameMapMapper;
import io.src.model.Network.DTO.GameMapper;
import io.src.model.Network.Lobby;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;
import io.src.model.Network.Server.ClientHandler;
import io.src.model.Network.Server.LobbyServer;
import io.src.model.States.WeatherState;
import io.src.model.TimeSystem.TimeSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

        boolean success = server.createLobby(lobbyName, owner, isPrivate, password, isVisible) != null;

        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.create_lobby);
        body.put("isSuccessful", success);
        Message.Type type = Message.Type.response;
        return new Message(body, type);
    }

    public static Message leaveLobby(String username, List<Lobby> lobbies) {
        Iterator<Lobby> iterator = lobbies.iterator();
        while (iterator.hasNext()) {
            Lobby lobby = iterator.next();

            if (lobby.getOwner().equals(username)) {
                iterator.remove();
                removeLobby(lobby.getId(), lobby.getOwner(), lobbies);
                int i = 0;
                for (String member : lobby.getMembers()) {
                    if (member.equals(username)) {
                        break;
                    }
                    i++;
                }
                Boolean[] ready = lobby.getCountReady();
                ready[i] = false;
            } else if (lobby.getMembers().remove(username)) {
                int i = 0;
                for (String member : lobby.getMembers()) {
                    if (member.equals(username)) {
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
        int i = 0;
        Lobby lobby1 = null;
        for (Lobby lobby : lobbies) {
            if (lobby.getId().equals(lobbyId)) {
                lobby1 = lobby;
                for (String member : lobby.getMembers()) {
                    if (member.equals(username)) {
                        break;
                    }
                    i++;
                }
            }
        }
        Boolean[] ready = lobby1.getCountReady();
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.toggle_ready);
        if (ready[i]) {
            ready[i] = false;
            body.put("Ready", "you aren't ready");
        } else {
            ready[i] = true;
            body.put("Ready", "you are ready now!");
        }
        Message.Type type = Message.Type.response;
        return new Message(body, type);
    }


    public static Message startFalseGame() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.start);
        body.put("isSuccessful", "ready count is not >3 !");
        return new Message(body, Message.Type.response);
    }

    public static Message startTrueGame() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.start);
        body.put("isSuccessful", "Starting ...");
        return new Message(body, Message.Type.response);
    }

    public static Message startTheGame(int numberOfPlayers, List<String> usernames) {
        ArrayList<User> usersToPlay = new ArrayList<>();
        for (String username : usernames) {
            User user = getUserByUsername(username);
            System.out.println("add user");
            usersToPlay.add(user);
        }
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < numberOfPlayers; i++) {
            positions.add(i+1);
        }
        Game game = makeGame(usersToPlay, positions);
        GameDTO gameDTO = GameMapper.toDTO(game);
        return sendGame(gameDTO,usersToPlay);

    }

    private static Game makeGame(ArrayList<User> usersToPlay, ArrayList<Integer> positions) {
        Game newGame = new Game(null, null, null, null);
        TimeSystem timeSystem = new TimeSystem(1, 9);
        newGame.setTimeSystem(timeSystem);// 1/4 set


        ArrayList<Player> playersToPlay = new ArrayList<>();
        for (int i = 0; i < usersToPlay.size(); i++) {
            Player player = new Player(usersToPlay.get(i));
            playersToPlay.add(player);
            player.setFarmPosition(FarmPosition.values()[positions.get(i) - 1]);
            //TODO check if it's ok
        }

        WeatherState weatherState = new WeatherState();
        newGame.setWeatherState(weatherState);// 2/4 set


        newGame.setPlayers(playersToPlay);// 3/4


        GameMap map = new GameMap();
        Town town = new Town(GameLocationType.Town);
        town.setTownmapPath("assets\\gameLocations\\Town4");
        switch (playersToPlay.size()) {
            case 1: {
                Farm farm1 = new Farm(GameLocationType.Farm1);
                farm1.setFarnmapPath("assets\\gameLocations\\Farm1");
                farm1.setPosition(FarmPosition.LEFT);
                playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                farm1.setPlayer(playersToPlay.getFirst());
                playersToPlay.getFirst().setPlayerFarm(farm1);
                playersToPlay.get(0).setCurrentGameLocation(farm1);


                map.setFarm1(farm1).setFarm2(null).setFarm3(null).setFarm4(null).setPelikanTown(town);
            }
            break;
            case 2: {
                Farm farm1 = new Farm(GameLocationType.Farm1);
                farm1.setFarnmapPath("assets\\gameLocations\\Farm1");
                farm1.setPosition(FarmPosition.LEFT);
                playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                farm1.setPlayer(playersToPlay.getFirst());
                playersToPlay.getFirst().setPlayerFarm(farm1);
                playersToPlay.get(0).setCurrentGameLocation(farm1);

                Farm farm2 = new Farm(GameLocationType.Farm1);
                farm2.setFarnmapPath("assets\\gameLocations\\Farm1");
                farm2.setPosition(FarmPosition.UP);
                playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                farm2.setPlayer(playersToPlay.get(1));
                playersToPlay.get(1).setPlayerFarm(farm2);
                playersToPlay.get(1).setCurrentGameLocation(farm2);


                map.setFarm1(farm1).setFarm2(farm2).setFarm3(null).setFarm4(null).setPelikanTown(town);
            }
            break;
            case 3: {
                Farm farm1 = new Farm(GameLocationType.Farm1);
                farm1.setFarnmapPath("assets\\gameLocations\\Farm1");
                farm1.setPosition(FarmPosition.LEFT);
                playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                farm1.setPlayer(playersToPlay.getFirst());
                playersToPlay.getFirst().setPlayerFarm(farm1);
                playersToPlay.getFirst().setDefaultHome(farm1.getDefaultHome());
                playersToPlay.get(0).setCurrentGameLocation(farm1);


                Farm farm2 = new Farm(GameLocationType.Farm2);
                farm2.setFarnmapPath("assets\\gameLocations\\Farm2");
                farm2.setPosition(FarmPosition.UP);
                playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                farm2.setPlayer(playersToPlay.get(1));
                playersToPlay.get(1).setPlayerFarm(farm2);
                playersToPlay.get(1).setDefaultHome(farm2.getDefaultHome());
                playersToPlay.get(1).setCurrentGameLocation(farm2);


                Farm farm3 = new Farm(GameLocationType.Farm1);
                farm3.setFarnmapPath("assets\\gameLocations\\Farm1");
                farm3.setPosition(FarmPosition.DOWN);
                playersToPlay.get(2).setFarmPosition(FarmPosition.DOWN);
                farm3.setPlayer(playersToPlay.get(2));
                playersToPlay.get(2).setPlayerFarm(farm3);
                playersToPlay.get(2).setDefaultHome(farm3.getDefaultHome());
                playersToPlay.get(2).setCurrentGameLocation(farm3);


                map.setFarm1(farm1).setFarm2(farm2).setFarm3(farm3).setFarm4(null).setPelikanTown(town);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

            }
            break;
            case 4: {
//                try {
                Farm farm1 = new Farm(GameLocationType.Farm1);


//                Farm farm1 = loadTheFarm("assets\\gameLocations\\Farm1");
                farm1.setPosition(FarmPosition.LEFT);
                playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                farm1.setPlayer(playersToPlay.getFirst());
                playersToPlay.getFirst().setPlayerFarm(farm1);
                playersToPlay.getFirst().setDefaultHome(farm1.getDefaultHome());
                playersToPlay.get(0).setCurrentGameLocation(farm1);


                Farm farm2 = new Farm(GameLocationType.Farm2);
                farm2.setFarnmapPath("assets\\gameLocations\\Farm2");

//                Farm farm2 = loadTheFarm2("assets\\gameLocations\\Farm2");
                farm2.setPosition(FarmPosition.UP);
                playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                farm2.setPlayer(playersToPlay.get(1));
                playersToPlay.get(1).setPlayerFarm(farm2);
                playersToPlay.get(1).setDefaultHome(farm2.getDefaultHome());
                playersToPlay.get(1).setCurrentGameLocation(farm2);


                Farm farm3 = new Farm(GameLocationType.Farm1);
                farm3.setFarnmapPath("assets\\gameLocations\\Farm1");

//                Farm farm3 = loadTheFarm("assets\\gameLocations\\Farm1");
                farm3.setPosition(FarmPosition.DOWN);
                playersToPlay.get(2).setFarmPosition(FarmPosition.DOWN);
                farm3.setPlayer(playersToPlay.get(2));
                playersToPlay.get(2).setPlayerFarm(farm3);
                playersToPlay.get(2).setDefaultHome(farm3.getDefaultHome());
                playersToPlay.get(2).setCurrentGameLocation(farm3);

                Farm farm4 = new Farm(GameLocationType.Farm1);
                farm4.setFarnmapPath("assets\\gameLocations\\Farm2");
//                Farm farm4 = loadTheFarm2("assets\\gameLocations\\Farm2");
                farm4.setPosition(FarmPosition.RIGHT);
                playersToPlay.get(3).setFarmPosition(FarmPosition.RIGHT);
                farm4.setPlayer(playersToPlay.get(3));
                playersToPlay.get(3).setPlayerFarm(farm4);
                playersToPlay.get(3).setDefaultHome(farm4.getDefaultHome());
                playersToPlay.get(3).setCurrentGameLocation(farm4);


                map.setFarm1(farm1).setFarm2(farm2).setFarm3(farm3).setFarm4(farm4).setPelikanTown(town);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

            }
            break;
            default: {
                System.out.println("that's not possible..you should start with (2-4) player");
            }
            break;
        }

        newGame.setGameMap(map);// 4/4

        return newGame;
    }

    public static Message sendGame(GameDTO game,ArrayList<User> users) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.Game);
        body.put("Game", game);
        body.put("users", users);
        Message.Type type = Message.Type.response;
        return new Message(body, type);
    }


    private static final String FILE_PATH = "assets//users.json";
    public static ArrayList<User> getUsers() {
        Gson gson = new Gson();
        ArrayList<User> tmpUsers = new ArrayList<>();
        try (Reader reader = new FileReader(FILE_PATH)) {
            tmpUsers = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            tmpUsers = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpUsers;
    }

    public static User getUserByUsername(String username) {
        for (User user : getUsers()) {
            if (user.getUsername().equals(username))
                return user;
        }
        for (User user : getUsers())
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public static void sendCurrentGameState(String username, ClientHandler newHandler) {
        Gson gson = new Gson();
        String lobbyOwner = null;

        for (Lobby lobby : LobbyServer.getLobbies()) {
            if (lobby.getOwner().equals(username) || lobby.getMembers().contains(username)) {
                lobbyOwner = lobby.getOwner();
                break;
            }
        }

        if (lobbyOwner != null) {
            for (ClientHandler clientHandler : LobbyServer.getClients()) {
                if (clientHandler.getUsername().equals(lobbyOwner)) {
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("commandType", NetworkCommand.DCtoOWNER);
                    body.put("username", username);
                    Message.Type type = Message.Type.command;
                    clientHandler.sendMessage(gson.toJson(new Message(body, type)));
                    break;
                }
            }
        }
    }

}
