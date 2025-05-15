package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Game;
import model.MapModule.AStarPathFinding;
import model.MapModule.Node;
import model.MapModule.Tile;
import model.Player;
import model.Result;

import java.util.ArrayList;

public class MapController extends CommandController {
    public static Result printMap() {
        Game game = App.getCurrentUser().getCurrentGame();

        return null;
    }

    public static Result manageHelpReadingMap() {

        return null;
    }



    public Result calculateMoveEnergy(int x , int y){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();

        ArrayList<Node> path = findPath(x, y);

        if (path == null || path.isEmpty()) {
            return new Result(false,"No path found");
        } else {
            int neededEnergy = (int) path.size() /20;
            return new Result(true , String.format("you need %f energy to go to tile<%d , %d>. Are you sure that you want to go?" , neededEnergy , x , y));
        }
//        App.getCurrentUser().getCurrentGame().getCurrentPlayer()
    }

    private static ArrayList<Node> findPath(int x, int y) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        ArrayList<Node> path = new AStarPathFinding(player.getCurrentGameLocation() ,
                player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() ,
                        player.getPosition().getY()) , player.getCurrentGameLocation().getTiles()[x][y]).solve();
        return path;
    }


    public Result movePlayer(int x , int y){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        ArrayList<Node> path = findPath(x, y);
        int neededEnergy = (int) path.size() /20;
        int availableDistance = player.getEnergy().getEnergy()*20;
        player.setPosition(((Tile)path.get(path.size() - availableDistance)).getPosition());
        player.subtractEnergy(neededEnergy);
        if (player.getEnergy().getEnergy() < neededEnergy) {
            return new Result(false , String.format("you faint at Tile <%d , %d>" , player.getPosition().getX() , player.getPosition().getY()));
        } else {
            return new Result(true , String.format("you are at Tile <%d , %d>" , player.getPosition().getX() , player.getPosition().getY()));
        }
    }


}
