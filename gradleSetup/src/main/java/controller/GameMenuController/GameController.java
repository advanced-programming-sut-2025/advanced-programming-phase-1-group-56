package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.Menu;
import model.Game;
import model.MapModule.AStarPathFinding;
import model.MapModule.Buildings.MarniesRanch;
import model.MapModule.Node;
import model.MapModule.Tile;
import model.Player;
import model.Result;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Scanner;

public class GameController extends CommandController {
    public static Result saveGame() {
        //TODO save game
        return null;
    }

    public static Result exitGame() {
        boolean isPLayerOwner= App.getCurrentUser().getCurrentGame().getStarterPlayer().equals(
                App.getCurrentUser().getCurrentGame().getCurrentPlayer());
        System.out.println("Going to exit game...");
        App.setCurrentMenu(Menu.mainMenu);
        return saveGame();
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
