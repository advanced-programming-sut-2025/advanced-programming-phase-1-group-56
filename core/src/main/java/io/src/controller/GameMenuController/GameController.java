package io.src.controller.GameMenuController;

import io.src.Main;
import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Game;
import io.src.model.Player;
import io.src.model.Result;
import io.src.view.AppView;
import io.src.view.GameMenus.GameMenu;

import java.util.Scanner;

public class GameController extends CommandController {
    private final Main game;
    private GameMenu gameMenu;

    public GameController(Main main) {
        game = main;
    }

    public void init() {
        gameMenu = new GameMenu(this);
    }

    public void run() {
        game.setScreen(gameMenu);
    }

    public static Result saveGame() {
        for (Player player: App.getCurrentUser().getCurrentGame().getPlayers())
        {
            player.getUser().setGold(player.getGold());
        }
        return new Result(true,"saved  game");
    }

    public static Result exitGame() {
        boolean isPLayerOwner= App.getCurrentUser().getCurrentGame().getStarterPlayer().equals(
                App.getCurrentUser().getCurrentGame().getCurrentPlayer());
        if(isPLayerOwner) {
            System.out.println("Going to exit game...");
            Result res = saveGame();

            for (Player player: App.getCurrentUser().getCurrentGame().getPlayers()){
                player.getUser().setGold(Math.max(player.getGold(),player.getGold()));
            }
            App.setCurrentMenu(Menu.mainMenu);
            App.getCurrentUser().setCurrentGame(null);
            return res;
        }
        else {
            return new Result(false,"you are not owner of this game to exit game");
        }
    }

    public static Result terminateGame(Scanner scanner) {
        Game game = App.getCurrentUser().getCurrentGame();
        int startOfCycleIndex = game.getPlayers().indexOf(game.getCurrentPlayer());
        int playerCount = game.getPlayers().size();
        for (int i = 1; i < playerCount; i++) {
            Player playerToVote = game.getPlayers().get(startOfCycleIndex+i % playerCount);
            System.out.println("Game Is Going to be TERMINATED.\nif you vote Type(DELETE,I KNOW WHAT I'M DOING)"+
                    "\nType AnyThing else to Cancel The Process..");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("DELETE,I KNOW WHAT I'M DOING")) {
                return new Result(false,"Process cancelled by player");
            }
        }

        //TODO delete game
        String ignoringMessage = exitGame().getMessage();
        for (Player player : game.getPlayers()) {
            player.getUser().setGameId(null);
            player.getUser().setCurrentGame(null);

        }
        return new Result(true,"Mission Failed Successfully... ):");
    }

    public static Result manageNextTurn() {
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().setEnergyUsage(0);
        Game game =App.getCurrentUser().getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        int indexOfCurrent= game.getPlayers().indexOf(currentPlayer);

        boolean isEndOfCycle = indexOfCurrent+1>=game.getPlayers().size();
        int indexOfNext = (isEndOfCycle)?0:indexOfCurrent+1;
        game.setCurrentPlayer(game.getPlayers().get(indexOfNext));
        if(isEndOfCycle){
            App.getCurrentUser().getCurrentGame().getTimeSystem().nextHour();
        }

        //TODO add every thing that should be done
        //Here is the start of the next player turn do every thing needed
        if(App.getMe().isFainted())
        {
            skipTurn();
        }

        return new Result(true,currentPlayer.getUser().getName() +" turn ended.. its now turn of :" +
                game.getCurrentPlayer().getUser().getName());
    }

    public static Result skipTurn(){
        System.out.println("Skipping turn...");
        return manageNextTurn();
    }



}
