package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.FarmPosition;
import model.Enums.TileType;
import model.Game;
import model.MapModule.AStarPathFinding;
import model.MapModule.Buildings.*;
import model.MapModule.GameLocations.Farm;
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
        Game game = App.getCurrentUser().getCurrentGame();
        ArrayList<Node> path = findPath(x, y);
        int neededEnergy = (int) path.size() /20;
        int availableDistance = player.getEnergy().getEnergy()*20;
        player.setPosition(((Tile)path.get(path.size() - availableDistance)).getPosition());
        player.subtractEnergy(neededEnergy);
        if (player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() , player.getPosition().getY()).getTileType() == TileType.Wrapper){
//            Tile t = player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() , player.getPosition().getY());
            if (player.getCurrentGameLocation() instanceof Farm) {
                player.setCurrentGameLocation(game.getGameMap().getPelikanTown());
            } else{
                if (player.getPosition().getX() == 0 && (player.getPosition().getY() == 52 || player.getPosition().getY() == 53 || player.getPosition().getY() == 54)){
                    if (player.getFarmPosition() == FarmPosition.LEFT){
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.LEFT){
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                } else if (player.getPosition().getX() == 111 && player.getPosition().getY() == 76) {
                    if (player.getFarmPosition() == FarmPosition.RIGHT){
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.RIGHT){
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                } else if (player.getPosition().getY() == 0 && (player.getPosition().getX() == 80 || player.getPosition().getX() == 81 || player.getPosition().getX() == 82)) {
                    if (player.getFarmPosition() == FarmPosition.UP){
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.UP){
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                } else if (player.getPosition().getY() == 109 && (player.getPosition().getX() == 53 || player.getPosition().getX() == 54 || player.getPosition().getX() == 55)){
                    if (player.getFarmPosition() == FarmPosition.DOWN){
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.DOWN){
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                }
            }

        } else if (player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() , player.getPosition().getY()).getFixedObject() instanceof Building building){
            if (player.getPosition() == building.getDoorPosition()){
                if (building instanceof JojaMart){

                } else if (building instanceof Blacksmith){

                } else if (building instanceof CarpentersShop){

                } else if (building instanceof MarniesRanch){

                } else if (building instanceof PierresGeneralStore){

                } else if (building instanceof TheSaloonStardrop){

                } else if
            }

        }
        if (player.getEnergy().getEnergy() < neededEnergy) {
            return new Result(false , String.format("you faint at Tile <%d , %d>" , player.getPosition().getX() , player.getPosition().getY()));
        } else {
            return new Result(true , String.format("you are at Tile <%d , %d>" , player.getPosition().getX() , player.getPosition().getY()));
        }
    }


}
