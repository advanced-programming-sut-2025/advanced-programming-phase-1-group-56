package controller.GameMenuController;

import controller.CommandController;
import model.*;
import model.Enums.FarmPosition;
import model.Enums.Menu;
import model.MapModule.GameLocations.Farm;
import model.MapModule.GameMap;
import model.MapModule.Position;
import model.States.WeatherState;
import model.TimeSystem.TimeSystem;
import model.items.Item;

import java.util.ArrayList;
import java.util.Scanner;

public class PreGameMenuController extends CommandController {
    public static Result manageNewGame(String usernamesStr,Scanner scanner) {
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
            if(user == App.getCurrentUser()){
                return new Result(false, "you can not add yourself to game");
            }
            usersToPlay.add(user);
        }


        for (User user : usersToPlay) {
            if(user.getCurrentGame() != null) {
                return new Result(false, "user" + user.getUsername() + " already in a game");
            }
        }

        ArrayList<Integer> positions = new ArrayList<Integer>();
        for (User user : usersToPlay) {
            System.out.print("user: " + user.getUsername() + ". pls choose your farm(1-4):");
            String input = scanner.nextLine().trim();
            try{
                int pos = Integer.parseInt(input);
                if(pos < 1 || pos > 4) {
                    return new Result(false,"Please Enter Valid Number");
                }
                else if(positions.contains(pos)) {
                    return new Result(false,"This Farm already occupied");
                }
                else {
                    positions.add(pos);
                }
            }
            catch(NumberFormatException e){
                System.out.println("invalid farm position");
            }
        }
        //Inputs Are OK

        ArrayList<Player> playersToPlay = new ArrayList<>();
        for (int i = 0; i < usersToPlay.size(); i++) {
            Player player = new Player(usersToPlay.get(i));
            playersToPlay.add(player);
            player.setFarmPosition(FarmPosition.values()[positions.get(i)-1]);
            //TODO check if it's ok
        }

        //TODO MakingInitialMap should call chooseMap;
//        TODO
//        Map map = MakeInitialMap(positions);
        //TODO set default home for each player
        //TODO SetEachPlayerFarm
        //TODO give player starter pack
        TimeSystem timeSystem = new TimeSystem(1,9);
        WeatherState weatherState = new WeatherState();
        Game newGame = new Game(playersToPlay,map,timeSystem,weatherState);
//        GivePlayersInitialItem(newGame);
        App.getCurrentUser().setCurrentGame(newGame);
        App.getCurrentUser().setGameId(newGame.getGameId());
        App.getCurrentUser().setNumOfGames(App.getCurrentUser().getNumOfGames()+1);
        App.getCurrentUser().getAllGamesId().add(newGame.getGameId());
        newGame.setCurrentPlayer(newGame.getPlayerByUser(App.getCurrentUser()));
        newGame.setStarterPlayer(newGame.getPlayerByUser(App.getCurrentUser()));
        return new Result(true, "successfully added game with id:" + newGame.getGameId());
    }

    public static Result loadGame() {
        String gameId = App.getCurrentUser().getGameId();
//        Game gameToLoad= somthing .... ;
        //TODO
        gameToLoad.setStarterPlayer(gameToLoad.getPlayerByUser(App.getCurrentUser()));
    }


}
