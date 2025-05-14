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
        //TODO hour ++
        if(isEndOfCycle){
            game.getTimeSystem().getDateTime().addHour(1);
            boolean newDay = game.getTimeSystem().getDateTime().getHour() >= 23;
            if(newDay){
                game.getTimeSystem().getDateTime().setHour(9);
                game.getTimeSystem().getDateTime().addDay(1);
            }
            game.getTimeSystem().notifyObservers(newDay);
        }
        //TODO add every thing that should be done
        return new Result(true,currentPlayer.getUser().getName() +" turn ended.. its now turn of :" +
                game.getCurrentPlayer().getUser().getName());
    }

    public Result skipTurn(){
        System.out.println("Skipping turn...");
        return manageNextTurn();
    }

    public Result calculateMoveEnergy(int x , int y){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();

        ArrayList<Node> path = new AStarPathFinding(player.getCurrentGameLocation() ,
                player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() ,
                        player.getPosition().getY()) , player.getCurrentGameLocation().getTiles()[x][y]).solve();

        if (path == null || path.size() == 0) {
            return new Result(false,"No path found");
        } else {
            double neededEnergy = (double) path.size() /20;
            return new Result(true , String.format("you need %f energy to go to tile<%d , %d>. Are you sure that you want to go?" , neededEnergy , x , y));
        }
//        App.getCurrentUser().getCurrentGame().getCurrentPlayer()
    }

    public Result movePlayer(int x , int y , int neededEnergy , ArrayList<Node> path){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        double availableDistance = player.getEnergy().getEnergy()*20;
        player.setPosition(((Tile)path.get((int) Math.floor(path.size() - availableDistance))).getPosition());
        if (player.getEnergy().getEnergy() < neededEnergy) {
            return new Result(false , String.format("you faint at Tile <%d , %d>" , player.getPosition().getX() , player.getPosition().getY()));
        } else {
            return new Result(true , String.format("you are at Tile <%d , %d>" , player.getPosition().getX() , player.getPosition().getY()));
        }
    }

}
