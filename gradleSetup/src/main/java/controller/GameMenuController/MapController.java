package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.FarmPosition;
import model.Enums.Menu;
import model.Enums.TileType;
import model.Game;
import model.MapModule.AStarPathFinding;
import model.MapModule.Buildings.*;
import model.MapModule.GameLocations.Farm;
import model.MapModule.Node;
import model.MapModule.Position;
import model.MapModule.Tile;
import model.Player;
import model.Result;
import view.GameMenus.GreenHouseMenu;

import java.util.ArrayList;

public class MapController extends CommandController {
    public static Result printMap() {
        Game game = App.getCurrentUser().getCurrentGame();

        return null;
    }

    public static Result manageHelpReadingMap() {

        return null;
    }



    public static Result calculateMoveEnergy(int x , int y){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();

        ArrayList<Node> path = findPath(x, y);

        if (path == null || path.isEmpty()) {
            return new Result(false,"No path found");
        } else {
            int neededEnergy = (int) path.size() /20;
            return new Result(true , String.format("you need %d energy to go to tile<%d , %d>. Are you sure that you want to go?" , neededEnergy , x , y));
        }
    }

    private static ArrayList<Node> findPath(int x, int y) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        ArrayList<Node> path = new AStarPathFinding(player.getCurrentGameLocation() ,
                player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() ,
                        player.getPosition().getY()) , player.getCurrentGameLocation().getTiles()[x][y]).solve();
        return path;
    }


    public static Result movePlayer(int x , int y){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Game game = App.getCurrentUser().getCurrentGame();
        ArrayList<Node> path = findPath(x, y);
        int neededEnergy = (int) path.size() /20;
        int availableDistance = Math.min(player.getEnergy().getEnergy()*20  , path.size());
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
                    player.setPosition(new Position(78 , 17));
                } else if (player.getPosition().getX() == 111 && player.getPosition().getY() == 76) {
                    if (player.getFarmPosition() == FarmPosition.RIGHT){
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.RIGHT){
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(78 , 17));
                } else if (player.getPosition().getY() == 0 && (player.getPosition().getX() == 80 || player.getPosition().getX() == 81 || player.getPosition().getX() == 82)) {
                    if (player.getFarmPosition() == FarmPosition.UP){
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.UP){
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(41 , 63));
                } else if (player.getPosition().getY() == 109 && (player.getPosition().getX() == 53 || player.getPosition().getX() == 54 || player.getPosition().getX() == 55)){
                    if (player.getFarmPosition() == FarmPosition.DOWN){
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.DOWN){
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(41 , 1));
                }
            }

        } else if (player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() , player.getPosition().getY()).getFixedObject() instanceof Building building){
            if (player.getPosition() == building.getDoorPosition()){
                if (building instanceof JojaMart){
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((JojaMart) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((JojaMart) building).getClosingHour()){
                        return new Result(false , "The shop is closed, please come from 9 am to 11 pm.");
                    }
                    App.setCurrentMenu(Menu.JojaMartMenu);
                    return new Result(true , "welcome to Joja Mart");
                } else if (building instanceof Blacksmith){
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((Blacksmith) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((Blacksmith) building).getClosingHour()){
                        return new Result(false , "The shop is closed, please come from 9 am to 4 pm.");
                    }
                    App.setCurrentMenu(Menu.BlackSmithMenu);
                    return new Result(true , "welcome to Black Smith");
                } else if (building instanceof CarpentersShop){
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((CarpentersShop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((CarpentersShop) building).getClosingHour()){
                        return new Result(false , "The shop is closed, please come from 9 am to 8 pm.");
                    }
                    App.setCurrentMenu(Menu.CarpenterShopMenu);
                    return new Result(true , "welcome to Carpenters Shop");
                } else if (building instanceof MarniesRanch){
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((MarniesRanch) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((MarniesRanch) building).getClosingHour()){
                        return new Result(false , "The shop is closed, please come from 9 am to 4 pm.");
                    }
                    App.setCurrentMenu(Menu.MarniesRanchMenu);
                    return new Result(true , "welcome to Marnies Ranch");
                } else if (building instanceof PierresGeneralStore){
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((PierresGeneralStore) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((PierresGeneralStore) building).getClosingHour()){
                        return new Result(false , "The shop is closed, please come from 9 am to 5 pm.");
                    }
                    App.setCurrentMenu(Menu.PierresGeneralStoreMenu);
                    return new Result(true , "welcome to Pirrer Store");
                } else if (building instanceof TheSaloonStardrop){
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((TheSaloonStardrop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((TheSaloonStardrop) building).getClosingHour()){
                        return new Result(false , "The shop is closed, please come from 12 am to 12 pm.");
                    }
                    App.setCurrentMenu(Menu.TheSaloonStarDropMenu);
                    return new Result(true , "welcome to Saloon Star Drop");
                } else if(building instanceof FishShop){
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((FishShop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((FishShop) building).getClosingHour()){
                        return new Result(false , "The shop is closed, please come from 9 am to 5 pm.");
                    }
                    App.setCurrentMenu(Menu.FishShopMenu);
                    return new Result(true , "welcome to Fish Shop");
                } else if(building instanceof Home){
                    App.setCurrentMenu(Menu.HouseMenu);
                    return new Result(true , "welcome to your home");
                } else if(building instanceof GreenHouse){
                    App.setCurrentMenu(Menu.GreenHouseMenu);
                    if (((GreenHouse) building).isBroken()){
                        return new Result(false , "the green house is broken and you cant come into it.");
                    }
                    player.setCurrentGameLocation(((GreenHouse) building).getIndoor());
                    player.setPosition(new Position(5 , 9));
                    return new Result(true , "welcome to green house");
                }
            }
        }
        if (player.isFainted()) {/////toye view seda bezan
            return new Result(false , String.format("you faint at Tile <%d , %d>" , player.getPosition().getX() , player.getPosition().getY()));
        } else {
            return new Result(true , String.format("you are at Tile <%d , %d>" , player.getPosition().getX() , player.getPosition().getY()));
        }


    }

    public void walkToHome(){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (player.getCurrentGameLocation() instanceof Farm){
            movePlayer(player.getDefaultHome().getDoorPosition().getX() , player.getDefaultHome().getDoorPosition().getY());
        } else{
            switch(player.getFarmPosition()){
                case FarmPosition.RIGHT -> {
                    movePlayer(111 , 76);
                    movePlayer(player.getPlayerFarm().getDefaultHome().getPosition().getX() , player.getPlayerFarm().getDefaultHome().getPosition().getY());
                }
                case FarmPosition.LEFT -> {
                    movePlayer(0 , 53);
                    movePlayer(player.getPlayerFarm().getDefaultHome().getPosition().getX() , player.getPlayerFarm().getDefaultHome().getPosition().getY());
                }
                case FarmPosition.UP -> {
                    movePlayer(81 , 0);
                    movePlayer(player.getPlayerFarm().getDefaultHome().getPosition().getX() , player.getPlayerFarm().getDefaultHome().getPosition().getY());
                }
                case FarmPosition.DOWN -> {
                    movePlayer(54 , 109);
                    movePlayer(player.getPlayerFarm().getDefaultHome().getPosition().getX() , player.getPlayerFarm().getDefaultHome().getPosition().getY());
                }
            }
        }
    }


}
