package io.src.model.MapModule;

import com.google.gson.*;
import io.src.model.Enums.GameLocationType;
import io.src.model.Enums.GameObjects.ForagingGameObjectType;
import io.src.model.Enums.GameObjects.TreeType;
import io.src.model.Enums.Items.GrassType;
import io.src.model.Enums.Items.MineralItemType;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.*;
import io.src.model.MapModule.Buildings.Building;
import io.src.model.MapModule.Buildings.GreenHouse;
import io.src.model.MapModule.Buildings.Home;
import io.src.model.MapModule.GameLocations.Farm;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class FarmLoader {
    private static final int tileSize = 16;

    public static Tile[][] load(String jsonPath, Farm farm) {
        JsonObject map = null;
        try {
            map = JsonParser
                .parseReader(new FileReader(jsonPath))
                .getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        int width = map.get("width").getAsInt();
        int height = map.get("height").getAsInt();

        JsonArray layers = map.getAsJsonArray("layers");
        JsonArray data = null;
        for (JsonElement lyrEl : layers) {
            JsonObject lyr = lyrEl.getAsJsonObject();
            if ("tilelayer".equals(lyr.get("type").getAsString())) {
//                data = lyr.getAsJsonArray("data");
                break;
            }
        }
        Tile[][] tiles = new Tile[height][width];

        JsonArray tilesets = map.getAsJsonArray("tilesets");
        JsonObject ts = tilesets.get(0).getAsJsonObject();
        int firstGid = ts.get("firstgid").getAsInt();
        JsonArray defs = ts.getAsJsonArray("tiles");
        for (int i = 0; i < defs.size(); i++) {
            JsonObject def = defs.get(i).getAsJsonObject();
            int gid = def.get("id").getAsInt() + firstGid;
            int x = i % width, y = i / width;
            JsonArray props = def.getAsJsonArray("properties");
            TileType type = null;
            boolean isWalkable = false;
            if (props != null) {
                for (JsonElement pEl : props) {
                    JsonObject p = pEl.getAsJsonObject();
                    if ("tileType".equals(p.get("name").getAsString())) {
                        String v = p.get("value").getAsString().toLowerCase();
                        try {
                            type = switch (v) {
                                case "vanity" -> TileType.Vanity;
                                case "water" -> TileType.Water;
                                case "grass" -> TileType.Grass;
                                case "stone" -> TileType.Stone;
                                case "soil" -> TileType.Soil;
//                                case "farmingsoil" -> TileType.FarmingSoil;
                                case "plowedsoil" -> TileType.PlowedSoil;
                                case "waterplowedsoil" -> TileType.WaterPlowedSoil;
                                case "wrapper" -> TileType.Wrapper;
                                case "mine" -> TileType.Mine;
                                case "default" -> TileType.Default;
                                default -> TileType.Default;
                            };
                        } catch (IllegalArgumentException ignored) {
                        }
                    } else if ("walkability".equals(p.get("name").getAsString())) {
                        String w = p.get("value").getAsString().toUpperCase();
                        isWalkable = w.equals("TRUE");
                    }
                }
            }
            tiles[y][x] = new Tile(
                new Position(x, y),
                isWalkable,
                type
            );

        }

        for (JsonElement lyrEl : layers) {
            JsonObject layer = lyrEl.getAsJsonObject();
            if ("objectgroup".equals(layer.get("type").getAsString())) {
                for (JsonElement objEl : layer.getAsJsonArray("objects")) {
                    JsonObject obj = objEl.getAsJsonObject();
                    int tx = obj.get("x").getAsInt() / tileSize;
                    int ty = obj.get("y").getAsInt() / tileSize;
                    int objWidth = obj.get("width").getAsInt() / tileSize;
                    int objHeight = obj.get("height").getAsInt() / tileSize;
                    String typeName = obj.get("name").getAsString().toLowerCase();

                    GameObject go;
                    switch (typeName) {
                        case "playerhome" -> {
                            JsonArray props = obj.getAsJsonArray("properties");
                            int doorX = 0;
                            int doorY = 0;
                            for (JsonElement pEl : props) {
                                JsonObject p = pEl.getAsJsonObject();
                                if ("doorX".equals(p.get("name").getAsString())) {
                                    doorX = Integer.parseInt(p.get("value").getAsString());
                                } else if ("doorY".equals(p.get("name").getAsString())) {
                                    doorY = Integer.parseInt(p.get("value").getAsString());
                                }
                            }
                            go = new Home(new Position(tx, ty), false, "PlayerHome", new Position(tx + 4, ty + 4), objHeight, objWidth);
                            farm.getBuildings().add((Building) go);
                        }
                        case "greenhouse" -> {
                            JsonArray props = obj.getAsJsonArray("properties");
                            int doorX = 0;
                            int doorY = 0;
                            for (JsonElement pEl : props) {
                                JsonObject p = pEl.getAsJsonObject();
                                if ("doorX".equals(p.get("name").getAsString())) {
                                    doorX = p.get("value").getAsInt();
                                } else if ("doorY".equals(p.get("name").getAsString())) {
                                    doorY = p.get("value").getAsInt();
                                }
                            }
                            go = new GreenHouse(new Position(tx, ty), false, "GreenHouse", new Position(tx + 3, ty + 5), objHeight, objWidth);
                            farm.getBuildings().add((Building) go);
                        }
                        //TODO
                        case "shippingbar" -> go = new ShippingBar(new Position(tx, ty), farm);
                        case "mailbox" -> go = new MailBox(new Position(tx, ty));
                        case "grass" -> go = new Grass(true, new Position(tx, ty), GrassType.NormalGrass);
                        case "fibergrass" -> go = new Grass(true, new Position(tx, ty), GrassType.FiberGrass);
                        case "wood" -> go = new Tree(TreeType.TREE_BARK, new Position(tx, ty));//TODO
                        case "stone" ->
                            go = new ForagingMineral(false, new Position(tx, ty), ForagingGameObjectType.Stone_Boulder);//TODO
                        case "tree" -> go = new Tree(TreeType.APPLE_TREE, new Position(tx, ty));
                        case "stick" -> go = new Tree(TreeType.TREE_BARK, new Position(tx, ty));
                        case "bigstone" ->
                            go = new ForagingMineral(false, new Position(tx, ty), ForagingGameObjectType.Stone_Boulder);
                        default -> go = null;
                    }

                    if (go != null
                        && ty >= 0 && ty + objHeight < height
                        && tx >= 0 && tx + objWidth < width) {
                        for (int i = ty; i < ty + objHeight; i++) {
                            for (int j = tx; j < tx + objWidth; j++) {
                                tiles[i][j].setFixedObject(go);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 3; i < 17; i++) {
            for (int j = 8; j < 16; j++) {
                tiles[j][i].setTileType(TileType.Mine);
            }
        }
        return tiles;
    }

    public static Farm loadTheFarm(String farmName) {
        Farm farm;
        if (farmName.contains("1"))
            farm = new Farm(GameLocationType.Farm1);
        else {
            farm = new Farm(GameLocationType.Farm2);
        }
        Tile[][] farmTileSet = load(farmName + ".tmj", farm);
        farm.setTiles(farmTileSet);
        return farm;
    }


//    public static void main(String[] args) throws Exception {
//        Tile[][] farm = load("hello3.tmj");
//        System.out.println("Loaded " + farm.length + "×" + farm[0].length + " tiles.");
//
//        // ANSI colors
//        final String RESET = "\u001B[0m";
//        final String RED = "\u001B[41m";     // background red
//        final String GREEN = "\u001B[42m";   // background green
//        final String BLUE = "\u001B[44m";    // background blue
//        final String YELLOW = "\u001B[43m";  // background yellow
//        final String GRAY = "\u001B[100m";   // background gray
//        char toPrint;
//        for (int i = 0; i < 65; i++) {
//            for (int j = 0; j < 80; j++) {
//                GameObject go = farm[i][j].getFixedObject();
//                String color = GRAY; // default background
//
//                if (go != null) {
//                    String name = go.getClass().getSimpleName();
//                    switch (name) {
//                        case "Tree":
//                            color = GREEN;
//                            break;
//                        case "Rock":
//                            color = RED;
//                            break;
//                        case "Water":
//                            color = BLUE;
//                            break;
//                        case "Home":
//                            color = YELLOW;
//                            break;
//                        default:
//                            color = GRAY;
//                    }
//                    if (name.isEmpty()) {
//                        name =" ";
//                    }
//                    System.out.print(color + name.charAt(0) + RESET); // two spaces colored
//                } else {
//                    System.out.print("  "); // empty space
//                }
//            }
//            System.out.println();
//        }
//    }

}
