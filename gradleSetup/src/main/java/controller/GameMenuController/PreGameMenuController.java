package controller.GameMenuController;

import controller.CommandController;
import model.*;
import model.Activities.Friendship;
import model.Enums.FarmPosition;
import model.MapModule.GameLocations.Farm;
import model.MapModule.GameLocations.Town;
import model.MapModule.GameMap;
import model.States.WeatherState;
import model.TimeSystem.TimeSystem;

import java.util.ArrayList;
import java.util.Scanner;

import static model.MapModule.Farm2Loader.loadTheFarm2;
import static model.MapModule.FarmLoader.loadTheFarm;
import static model.MapModule.TownLoader.loadTheTown;

public class PreGameMenuController extends CommandController {
    public static Result manageNewGame(String usernamesStr, Scanner scanner) throws Exception {
        usernamesStr = usernamesStr.trim();
        if (usernamesStr.isEmpty()) {
            return new Result(false, "empty flag");
        }
        String[] usernames = usernamesStr.split("\\s+");
        if (usernames.length > 3) {
            return new Result(false, "too many players");
        }

        ArrayList<User> usersToPlay = new ArrayList<>();
        usersToPlay.add(App.getCurrentUser());
        for (String username : usernames) {
            User user = App.getUserByUsername(username);
            if (user == null) {
                return new Result(false, "user" + username + " not found");
            }
            if (user == App.getCurrentUser()) {
                return new Result(false, "you can not add yourself to game");
            }
            usersToPlay.add(user);
        }


        for (User user : usersToPlay) {
            if (user.getCurrentGame() != null) {
                return new Result(false, "user" + user.getUsername() + " already in a game");
            }
        }

        ArrayList<Integer> positions = new ArrayList<Integer>();
        for (User user : usersToPlay) {
            System.out.print("user: " + user.getUsername() + ". pls choose your farm(1-4):");
            String input = scanner.nextLine().trim();
            try {
                int pos = Integer.parseInt(input);
                if (pos < 1 || pos > 4) {
                    return new Result(false, "Please Enter Valid Number");
                } else if (positions.contains(pos)) {
                    return new Result(false, "This Farm already occupied");
                } else {
                    positions.add(pos);
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid farm position");
            }
        }

        ArrayList<Player> playersToPlay = new ArrayList<>();
        for (int i = 0; i < usersToPlay.size(); i++) {
            Player player = new Player(usersToPlay.get(i));
            playersToPlay.add(player);
            player.setFarmPosition(FarmPosition.values()[positions.get(i) - 1]);
            //TODO check if it's ok
        }

        GameMap map = new GameMap();
        Town town = loadTheTown();

        switch (playersToPlay.size()) {
            case 2: {
                Farm farm1 = loadTheFarm("Farm1");
                farm1.setPosition(FarmPosition.LEFT);
                playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                farm1.setPlayer(playersToPlay.getFirst());
                playersToPlay.getFirst().setPlayerFarm(farm1);


                Farm farm2 = loadTheFarm2("Farm2");
                farm2.setPosition(FarmPosition.UP);
                playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                farm2.setPlayer(playersToPlay.get(1));
                playersToPlay.get(1).setPlayerFarm(farm2);


                map.setFarm1(farm1).setFarm2(farm2).setFarm3(null).setFarm4(null).setPelikanTown(town);
            }
            break;
            case 3: {
                Farm farm1 = loadTheFarm("Farm1");
                farm1.setPosition(FarmPosition.LEFT);
                playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                farm1.setPlayer(playersToPlay.getFirst());
                playersToPlay.getFirst().setPlayerFarm(farm1);
                playersToPlay.getFirst().setDefaultHome(farm1.getDefaultHome());


                Farm farm2 = loadTheFarm2("Farm2");
                farm2.setPosition(FarmPosition.UP);
                playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                farm2.setPlayer(playersToPlay.get(1));
                playersToPlay.get(1).setPlayerFarm(farm2);
                playersToPlay.get(1).setDefaultHome(farm2.getDefaultHome());


                Farm farm3 = loadTheFarm("Farm1");
                farm3.setPosition(FarmPosition.DOWN);
                playersToPlay.get(2).setFarmPosition(FarmPosition.DOWN);
                farm3.setPlayer(playersToPlay.get(2));
                playersToPlay.get(2).setPlayerFarm(farm3);
                playersToPlay.get(2).setDefaultHome(farm3.getDefaultHome());


                map.setFarm1(farm1).setFarm2(farm2).setFarm3(farm3).setFarm4(null).setPelikanTown(town);
            }
            break;
            case 4: {
                Farm farm1 = loadTheFarm("Farm1");
                farm1.setPosition(FarmPosition.LEFT);
                playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                farm1.setPlayer(playersToPlay.getFirst());
                playersToPlay.getFirst().setPlayerFarm(farm1);
                playersToPlay.getFirst().setDefaultHome(farm1.getDefaultHome());


                Farm farm2 = loadTheFarm2("Farm2");
                farm2.setPosition(FarmPosition.UP);
                playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                farm2.setPlayer(playersToPlay.get(1));
                playersToPlay.get(1).setPlayerFarm(farm2);
                playersToPlay.get(1).setDefaultHome(farm2.getDefaultHome());


                Farm farm3 = loadTheFarm("Farm1");
                farm3.setPosition(FarmPosition.DOWN);
                playersToPlay.get(2).setFarmPosition(FarmPosition.DOWN);
                farm3.setPlayer(playersToPlay.get(2));
                playersToPlay.get(2).setPlayerFarm(farm3);
                playersToPlay.get(2).setDefaultHome(farm3.getDefaultHome());


                Farm farm4 = loadTheFarm2("Farm2");
                farm4.setPosition(FarmPosition.RIGHT);
                playersToPlay.get(3).setFarmPosition(FarmPosition.RIGHT);
                farm4.setPlayer(playersToPlay.get(3));
                playersToPlay.get(3).setPlayerFarm(farm4);
                playersToPlay.get(3).setDefaultHome(farm4.getDefaultHome());


                map.setFarm1(farm1).setFarm2(farm2).setFarm3(farm3).setFarm4(farm4).setPelikanTown(town);
            }
            break;
            default: {
                System.out.println("that's not pussyble..you should start with (2-4) player");
            }
            break;
        }
        TimeSystem timeSystem = new TimeSystem(1, 9);
        WeatherState weatherState = new WeatherState();
        Game newGame = new Game(playersToPlay, map, timeSystem, weatherState);

        //FriendShips
        for (Player player1 : playersToPlay) {
            for (Player player2 : playersToPlay) {
                player1.getFriendShips().add(new Friendship(player2));
            }
        }
        App.getCurrentUser().setCurrentGame(newGame);
        App.getCurrentUser().setGameId(newGame.getGameId());
        App.getCurrentUser().setNumOfGames(App.getCurrentUser().getNumOfGames() + 1);
        App.getCurrentUser().getAllGamesId().add(newGame.getGameId());
        GivePlayersInitialItem(newGame);
        newGame.setCurrentPlayer(newGame.getPlayerByUser(App.getCurrentUser()));
        newGame.setStarterPlayer(newGame.getPlayerByUser(App.getCurrentUser()));

        return new Result(true, "successfully added game with id:" + newGame.getGameId());
    }

    private static void GivePlayersInitialItem(Game newGame) {
        for (Player player : newGame.getPlayers()) {
            //player.getInventory().add(new Pickaxe());
            //TODO

        }
    }

    public static Result loadGame() {
        String gameId = App.getCurrentUser().getGameId();
        //Game gameToLoad= somthing .... ;
        //TODO
        //gameToLoad.setStarterPlayer(gameToLoad.getPlayerByUser(App.getCurrentUser()));
        return new Result(true, "successfully loaded game with id:" + gameId);
    }
}
