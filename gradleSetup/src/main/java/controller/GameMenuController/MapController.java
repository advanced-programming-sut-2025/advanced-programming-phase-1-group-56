package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.FarmPosition;
import model.Enums.Items.ArtisanGoodType;
import model.Enums.Items.ArtisanMachineItemType;
import model.Enums.Menu;
import model.Enums.TileType;
import model.Game;
import model.GameObject.*;
import model.GameObject.NPC.NPC;
import model.MapModule.AStarPathFinding;
import model.MapModule.Buildings.*;
import model.MapModule.GameLocations.Farm;
import model.MapModule.GameLocations.GameLocation;
import model.MapModule.Node;
import model.MapModule.Position;
import model.MapModule.Tile;
import model.Player;
import model.Result;

import java.util.ArrayList;

public class MapController extends CommandController {
    public static Result printMap() {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String BLUE = "\u001B[34m";
        String YELLOW = "\u001B[33m";
        String ORANGE = "\u001B[38;5;208m";
        String MAGENTA = "\u001B[35m";
        String CYAN = "\u001B[36m";
        String WHITE = "\u001B[37m";
        String GRAY = "\u001B[38;5;240m";
        String BLACK_BROWN = "\u001B[38;5;94m";
        String BROWN = "\u001B[38;5;130m";

        Tile[][] tiles = App.getMe().getCurrentGameLocation().getTiles();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tiles[0].length; i++) {
            result.append(String.format("%02d", i));
        }
        System.out.println();
        for (int i = 0; i < tiles.length; i++) {
            result.append(String.format("%02d", i));
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getPosition().getX() == App.getMe().getPosition().getX() &&
                        tiles[i][j].getPosition().getY() == App.getMe().getPosition().getY()) {
                    result.append(CYAN + "PP");
                } else if (tiles[i][j].getFixedObject() == null) {
                    switch (tiles[i][j].getTileType()) {
                        case TileType.Soil -> result.append(ORANGE + ((tiles[i][j].isWalkable()) ? ".." : ",,"));
                        case TileType.Grass -> result.append(GREEN + ((tiles[i][j].isWalkable()) ? ".." : ",,"));
                        case TileType.Water -> result.append(CYAN + ((tiles[i][j].isWalkable()) ? ".." : ",,"));
                        case TileType.Vanity -> result.append(WHITE + "XX");
                        case TileType.Stone -> result.append(GRAY + ((tiles[i][j].isWalkable()) ? ".." : ",,"));
                        case TileType.PlowedSoil -> result.append(BROWN + ((tiles[i][j].isWalkable()) ? ".." : ",,"));
                        case TileType.WaterPlowedSoil ->
                                result.append(BLACK_BROWN + ((tiles[i][j].isWalkable()) ? ".." : ",,"));
                        case TileType.Mine -> result.append(WHITE + ",,");
                        default -> result.append(GRAY + ((tiles[i][j].isWalkable()) ? ".." : ",,"));
                    }
                } else {
                    if (tiles[i][j].getFixedObject() instanceof Home home) {
                        if (i == home.getDoorPosition().getY() && j == home.getDoorPosition().getX()) {
                            result.append(BROWN + "DD");
                        } else {
                            result.append(BROWN + "PH");
                        }
                    } else if (tiles[i][j].getFixedObject() instanceof GreenHouse greenHouse) {
                        if (i == greenHouse.getDoorPosition().getY() && j == greenHouse.getDoorPosition().getX()) {
                            result.append(BROWN + "DD");
                        } else {
                            result.append(GREEN + "GH");
                        }
                    } else if (tiles[i][j].getFixedObject() instanceof Crop) {
                        result.append("CC");
                    } else if (tiles[i][j].getFixedObject() instanceof ForagingCrop) {
                        result.append("FC");
                    } else if (tiles[i][j].getFixedObject() instanceof EtcObject) {
                        result.append("ET");
                    } else if (tiles[i][j].getFixedObject() instanceof Animal) {
                        result.append("AN");
                    } else if (tiles[i][j].getFixedObject() instanceof NPC) {
                        result.append("NP");
                    } else if (tiles[i][j].getFixedObject() instanceof MailBox) {
                        result.append("MB");
                    } else if (tiles[i][j].getFixedObject() instanceof ShippingBar) {
                        result.append("SB");
                    } else if (tiles[i][j].getFixedObject() instanceof Well) {
                        result.append("WL");
                    } else if (tiles[i][j].getFixedObject() instanceof ForagingMineral) {
                        result.append("FM");
                    } else if (tiles[i][j].getFixedObject() instanceof Tree) {
                        result.append("TT");
                    } else if (tiles[i][j].getFixedObject() instanceof Grass) {
                        result.append(GREEN + "GG");
                    } else if (tiles[i][j].isWalkable()) {
                        result.append(GRAY + "..");
                    } else if (!tiles[i][j].isWalkable()) {
                        result.append(GRAY + ",,");
                    }
                }
            }
            result.append('\n');
        }

        return new Result(true, result.toString());
    }


    public static Result calculateMoveEnergy(int x, int y) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();

        ArrayList<Node> path = findPath(x, y);

        if (path == null) {
            return new Result(false, "path is null");
        } else if (path.isEmpty()) {
            return new Result(false, "path is empty");
        } else {
            double neededEnergy = (double) path.size() / 20;
            return new Result(true, String.format("you need %f energy to go to tile<%d , %d>. Are you sure that you want to go?", neededEnergy, x, y));
        }
    }

    private static ArrayList<Node> findPath(int x, int y) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        ArrayList<Node> path = new AStarPathFinding(player.getCurrentGameLocation(),
                player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX(),
                        player.getPosition().getY()), player.getCurrentGameLocation().getTiles()[y][x]).solve();
        return path;
    }

    public static Result printMapHint() {
        return new Result(true, """
                GG == Grass
                TT == Tree 
                BB == Building
                CC == Crop
                FC == Foraging Crop
                ET == EtcObject
                AN == Animal
                NP == NPC
                MB == MailBox
                Sb == ShippingBar
                WL == WELL
                FM == ForagingMineral
                PP == Player"""
        );
    }


    public static Result movePlayer(int x, int y) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Game game = App.getCurrentUser().getCurrentGame();
        ArrayList<Node> path = findPath(x, y);
        int turn = AStarPathFinding.countDirectionChanges(path);
        double neededEnergy =  path.size() + (double)((2 * turn) / 20);
        double availableDistance = Math.min(player.getEnergy().getEnergy() * 20, path.size());
        player.setPosition(((Tile) path.get((path.size() - (int)availableDistance))).getPosition());
        player.subtractEnergy(availableDistance / 20);
        if (player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX(), player.getPosition().getY()).getTileType() == TileType.Wrapper) {
//            Tile t = player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() , player.getPosition().getY());
            if (player.getCurrentGameLocation() instanceof Farm) {
                player.setCurrentGameLocation(game.getGameMap().getPelikanTown());
                if ((player.getFarmPosition() == FarmPosition.LEFT)){
//                    player.getPosition().getX() == 0 player.getPosition().getY() == 52;
                    player.setPosition(new Position( 1  , 53));
                } else if ((player.getFarmPosition() == FarmPosition.RIGHT)){
                    player.setPosition(new Position( 110 , 76));
                } else if ((player.getFarmPosition() == FarmPosition.UP)){
                    player.setPosition(new Position( 81 , 1));
                } else{
                    player.setPosition(new Position(54 , 108));
                }
            } else {
                if (player.getPosition().getX() == 0 && (player.getPosition().getY() == 52 || player.getPosition().getY() == 53 || player.getPosition().getY() == 54)) {
                    if (player.getFarmPosition() == FarmPosition.LEFT) {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.LEFT) {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(78, 17));
                } else if (player.getPosition().getX() == 111 && player.getPosition().getY() == 76) {
                    if (player.getFarmPosition() == FarmPosition.RIGHT) {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.RIGHT) {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(78, 17));
                } else if (player.getPosition().getY() == 0 && (player.getPosition().getX() == 80 || player.getPosition().getX() == 81 || player.getPosition().getX() == 82)) {
                    if (player.getFarmPosition() == FarmPosition.UP) {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.UP) {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(41, 63));
                } else if (player.getPosition().getY() == 109 && (player.getPosition().getX() == 53 || player.getPosition().getX() == 54 || player.getPosition().getX() == 55)) {
                    if (player.getFarmPosition() == FarmPosition.DOWN) {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.DOWN) {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(41, 1));
                }
            }

        } else if (player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX(), player.getPosition().getY()).getFixedObject() instanceof Building building) {
            if (player.getPosition().equals(building.getDoorPosition())) {
                if (building instanceof JojaMart) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((JojaMart) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((JojaMart) building).getClosingHour()) {
                        return new Result(false, "The shop is closed, please come from 9 am to 11 pm.");
                    }
                    App.setCurrentMenu(Menu.JojaMartMenu);
                    return new Result(true, "welcome to Joja Mart");
                } else if (building instanceof Blacksmith) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((Blacksmith) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((Blacksmith) building).getClosingHour()) {
                        return new Result(false, "The shop is closed, please come from 9 am to 4 pm.");
                    }
                    App.setCurrentMenu(Menu.BlackSmithMenu);
                    return new Result(true, "welcome to Black Smith");
                } else if (building instanceof CarpentersShop) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((CarpentersShop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((CarpentersShop) building).getClosingHour()) {
                        return new Result(false, "The shop is closed, please come from 9 am to 8 pm.");
                    }
                    App.setCurrentMenu(Menu.CarpenterShopMenu);
                    return new Result(true, "welcome to Carpenters Shop");
                } else if (building instanceof MarniesRanch) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((MarniesRanch) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((MarniesRanch) building).getClosingHour()) {
                        return new Result(false, "The shop is closed, please come from 9 am to 4 pm.");
                    }
                    App.setCurrentMenu(Menu.MarniesRanchMenu);
                    return new Result(true, "welcome to Marnies Ranch");
                } else if (building instanceof PierresGeneralStore) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((PierresGeneralStore) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((PierresGeneralStore) building).getClosingHour()) {
                        return new Result(false, "The shop is closed, please come from 9 am to 5 pm.");
                    }
                    App.setCurrentMenu(Menu.PierresGeneralStoreMenu);
                    return new Result(true, "welcome to Pirrer Store");
                } else if (building instanceof TheSaloonStardrop) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((TheSaloonStardrop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((TheSaloonStardrop) building).getClosingHour()) {
                        return new Result(false, "The shop is closed, please come from 12 am to 12 pm.");
                    }
                    App.setCurrentMenu(Menu.TheSaloonStarDropMenu);
                    return new Result(true, "welcome to Saloon Star Drop");
                } else if (building instanceof FishShop) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((FishShop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((FishShop) building).getClosingHour()) {
                        return new Result(false, "The shop is closed, please come from 9 am to 5 pm.");
                    }
                    App.setCurrentMenu(Menu.FishShopMenu);
                    return new Result(true, "welcome to Fish Shop");
                } else if (building instanceof Home) {
                    App.setCurrentMenu(Menu.HouseMenu);
                    return new Result(true, "welcome to your home");
                } else if (building instanceof GreenHouse) {
                    App.setCurrentMenu(Menu.gameMenu);
                    if (((GreenHouse) building).isBroken()) {
                        return new Result(false, "the green house is broken and you cant come into it.");
                    }
                    player.setCurrentGameLocation(((GreenHouse) building).getIndoor());
                    player.setPosition(new Position(5, 9));
                    return new Result(true, "welcome to green house");
                }
            }
        }
        return new Result(true, String.format("you are at Tile <%d , %d>", player.getPosition().getX(), player.getPosition().getY()));
    }

    public void walkToHome() {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (player.getCurrentGameLocation() instanceof Farm) {
            movePlayer(player.getDefaultHome().getDoorPosition().getX(), player.getDefaultHome().getDoorPosition().getY());
        } else {
            switch (player.getFarmPosition()) {
                case FarmPosition.RIGHT -> {
                    movePlayer(111, 76);
                    movePlayer(player.getPlayerFarm().getDefaultHome().getPosition().getX(), player.getPlayerFarm().getDefaultHome().getPosition().getY());
                }
                case FarmPosition.LEFT -> {
                    movePlayer(0, 53);
                    movePlayer(player.getPlayerFarm().getDefaultHome().getPosition().getX(), player.getPlayerFarm().getDefaultHome().getPosition().getY());
                }
                case FarmPosition.UP -> {
                    movePlayer(81, 0);
                    movePlayer(player.getPlayerFarm().getDefaultHome().getPosition().getX(), player.getPlayerFarm().getDefaultHome().getPosition().getY());
                }
                case FarmPosition.DOWN -> {
                    movePlayer(54, 109);
                    movePlayer(player.getPlayerFarm().getDefaultHome().getPosition().getX(), player.getPlayerFarm().getDefaultHome().getPosition().getY());
                }
            }
        }
    }


}
