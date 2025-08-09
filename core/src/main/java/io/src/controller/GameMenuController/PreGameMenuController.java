package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.*;
import io.src.model.Activities.Friendship;
import io.src.model.Enums.FarmPosition;
import io.src.model.Enums.Items.ToolType;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.GameMap;
import io.src.model.States.WeatherState;
import io.src.model.TimeSystem.TimeSystem;
import io.src.model.items.Tool;

import java.util.ArrayList;
import java.util.Scanner;

import static io.src.model.MapModule.Farm2Loader.loadTheFarm2;
import static io.src.model.MapModule.FarmLoader.loadTheFarm;
import static io.src.model.MapModule.TownLoader.loadTheTown;
import static io.src.model.MapModule.newFarmLoader.loadTheLocation;

public class PreGameMenuController extends CommandController {

    public static Result manageNewGame(String usernamesStr, Scanner scanner) {
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

        Game newGame = new Game(null, null, null, null);

        App.getCurrentUser().setCurrentGame(newGame);
        TimeSystem timeSystem = new TimeSystem(1, 9);
        newGame.setTimeSystem(timeSystem);// 1/4 set


        ArrayList<Player> playersToPlay = new ArrayList<>();
        for (int i = 0; i < usersToPlay.size(); i++) {
            Player player = new Player(usersToPlay.get(i));
            playersToPlay.add(player);
            player.setFarmPosition(FarmPosition.values()[positions.get(i) - 1]);
            //TODO check if it's ok
        }
        System.out.println(1);

        WeatherState weatherState = new WeatherState();
        newGame.setWeatherState(weatherState);// 2/4 set


        newGame.setPlayers(playersToPlay);// 3/4


        GameMap map = new GameMap();
        Town town = (Town) loadTheLocation("assets\\gameLocations\\Town4");
//        Town town = loadTheTown();

        switch (playersToPlay.size()) {
            case 2: {
                Farm farm1 = (Farm) loadTheLocation("assets\\gameLocations\\Farm1");
//                Farm farm1 = loadTheFarm("assets\\gameLocations\\Farm1");
                farm1.setPosition(FarmPosition.LEFT);
                playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                farm1.setPlayer(playersToPlay.getFirst());
                playersToPlay.getFirst().setPlayerFarm(farm1);
                playersToPlay.get(0).setCurrentGameLocation(farm1);

                Farm farm2 = (Farm) loadTheLocation("assets\\gameLocations\\Farm2");
//                Farm farm2 = loadTheFarm2("assets\\gameLocations\\Farm2");
                farm2.setPosition(FarmPosition.UP);
                playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                farm2.setPlayer(playersToPlay.get(1));
                playersToPlay.get(1).setPlayerFarm(farm2);
                playersToPlay.get(1).setCurrentGameLocation(farm2);


                map.setFarm1(farm1).setFarm2(farm2).setFarm3(null).setFarm4(null).setPelikanTown(town);
            }
            break;
            case 3: {
                    Farm farm1 = (Farm) loadTheLocation("assets\\gameLocations\\Farm1");
//                Farm farm1 = loadTheFarm("assets\\gameLocations\\Farm1");
                    farm1.setPosition(FarmPosition.LEFT);
                    playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                    farm1.setPlayer(playersToPlay.getFirst());
                    playersToPlay.getFirst().setPlayerFarm(farm1);
                    playersToPlay.getFirst().setDefaultHome(farm1.getDefaultHome());
                    playersToPlay.get(0).setCurrentGameLocation(farm1);


                    Farm farm2 = (Farm) loadTheLocation("assets\\gameLocations\\Farm2");
//                Farm farm2 = loadTheFarm2("assets\\gameLocations\\Farm2");
                    farm2.setPosition(FarmPosition.UP);
                    playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                    farm2.setPlayer(playersToPlay.get(1));
                    playersToPlay.get(1).setPlayerFarm(farm2);
                    playersToPlay.get(1).setDefaultHome(farm2.getDefaultHome());
                    playersToPlay.get(1).setCurrentGameLocation(farm2);


                    Farm farm3 = (Farm) loadTheLocation("assets\\gameLocations\\Farm1");
//                Farm farm3 = loadTheFarm("assets\\gameLocations\\Farm1");
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
                    Farm farm1 = (Farm) loadTheLocation("assets\\gameLocations\\Farm1");
//                Farm farm1 = loadTheFarm("assets\\gameLocations\\Farm1");
                    farm1.setPosition(FarmPosition.LEFT);
                    playersToPlay.getFirst().setFarmPosition(FarmPosition.LEFT);
                    farm1.setPlayer(playersToPlay.getFirst());
                    playersToPlay.getFirst().setPlayerFarm(farm1);
                    playersToPlay.getFirst().setDefaultHome(farm1.getDefaultHome());
                    playersToPlay.get(0).setCurrentGameLocation(farm1);



                    Farm farm2 = (Farm) loadTheLocation("assets\\gameLocations\\Farm2");
//                Farm farm2 = loadTheFarm2("assets\\gameLocations\\Farm2");
                    farm2.setPosition(FarmPosition.UP);
                    playersToPlay.get(1).setFarmPosition(FarmPosition.UP);
                    farm2.setPlayer(playersToPlay.get(1));
                    playersToPlay.get(1).setPlayerFarm(farm2);
                    playersToPlay.get(1).setDefaultHome(farm2.getDefaultHome());
                    playersToPlay.get(1).setCurrentGameLocation(farm2);



                    Farm farm3 = (Farm) loadTheLocation("assets\\gameLocations\\Farm1");
//                Farm farm3 = loadTheFarm("assets\\gameLocations\\Farm1");
                    farm3.setPosition(FarmPosition.DOWN);
                    playersToPlay.get(2).setFarmPosition(FarmPosition.DOWN);
                    farm3.setPlayer(playersToPlay.get(2));
                    playersToPlay.get(2).setPlayerFarm(farm3);
                    playersToPlay.get(2).setDefaultHome(farm3.getDefaultHome());
                    playersToPlay.get(2).setCurrentGameLocation(farm3);


                    Farm farm4 = (Farm) loadTheLocation("assets\\gameLocations\\Farm2");
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



        //FriendShips
        for (Player player1 : playersToPlay) {
            for (Player player2 : playersToPlay) {
                if(player2.equals(player1))
                    continue;
                player1.getFriendShips().add(new Friendship(player2));
            }
        }

        App.getCurrentUser().setGameId(newGame.getGameId());
        App.getCurrentUser().setNumOfGames(App.getCurrentUser().getNumOfGames() + 1);
//        App.getCurrentUser().getAllGamesId().add(newGame.getGameId());
        GivePlayersInitialItem(newGame);
        newGame.setCurrentPlayer(newGame.getPlayerByUser(App.getCurrentUser()));
        newGame.setStarterPlayer(newGame.getPlayerByUser(App.getCurrentUser()));

        for (User user: usersToPlay) {
            user.setGameId(newGame.getGameId());
            user.setCurrentGame(newGame);
            user.setNumOfGames(user.getNumOfGames() + 1);
        }

        for (NPC npc : town.getNPCs()) {
            npc.initializePaths(town);
        }

        return new Result(true, "successfully added game with id:" + newGame.getGameId());
    }

    private static void GivePlayersInitialItem(Game newGame) {
        for (Player player : newGame.getPlayers()) {
            App.getCurrentUser().getCurrentGame().setCurrentPlayer(player);
            player.getInventory().add(new Tool(ToolType.AXE_WOODEN),1);
            player.getInventory().add(new Tool(ToolType.PICK_WOODEN),1);
            player.getInventory().add(new Tool(ToolType.SCYTHE_BASIC),1);
            player.getInventory().add(new Tool(ToolType.HOE_WOODEN),1);
            player.getInventory().add(new Tool(ToolType.CAN_WOODEN),1);
            player.addGold(100);
            player.setDefaultHome(player.getPlayerFarm().getDefaultHome());
            //player.teleportToHome();
            player.addFoodRecipes(FoodRecipesList.FRIED_EGG);
            player.addFoodRecipes(FoodRecipesList.BAKED_FISH);
            player.addFoodRecipes(FoodRecipesList.SALAD);
            player.setCurrentItem(player.getInventory().findItemByName(ToolType.AXE_WOODEN.getName()));
        }
    }

    public static Result loadGame() {
        String gameId = App.getCurrentUser().getGameId();
        //Game gameToLoad= something .... ;
        //TODO
        //gameToLoad.setStarterPlayer(gameToLoad.getPlayerByUser(App.getCurrentUser()));
        return new Result(true, "successfully loaded game with id:" + gameId);
    }
}
