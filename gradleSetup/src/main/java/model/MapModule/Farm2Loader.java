package model.MapModule;

import com.google.gson.*;
import model.Enums.GameObjects.TreeType;
import model.Enums.Items.GrassType;
import model.Enums.Items.MineralItemType;
import model.Enums.TileType;
import model.GameObject.*;
import model.MapModule.Buildings.Building;
import model.MapModule.Buildings.GreenHouse;
import model.MapModule.Buildings.Home;
import model.MapModule.GameLocations.Farm;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Farm2Loader {
    private static final int tileSize = 16;

    public static Tile[][] load(String jsonPath, Farm farm) throws FileNotFoundException {
        JsonObject map = JsonParser
                .parseReader(new FileReader(jsonPath))
                .getAsJsonObject();

        int width = map.get("width").getAsInt();
        int height = map.get("height").getAsInt();

        JsonArray layers = map.getAsJsonArray("layers");
        JsonArray data = null;
        for (JsonElement lyrEl : layers) {
            JsonObject lyr = lyrEl.getAsJsonObject();
            if ("tilelayer".equals(lyr.get("type").getAsString())) {
                data = lyr.getAsJsonArray("data");
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
                                case "farmingsoil" -> TileType.FarmingSoil;
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
                            go = new Home(new Position(tx, ty), false, "PlayerHome", new Position(doorX, doorY), objHeight, objWidth);

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
                            go = new GreenHouse(new Position(tx, ty), false, "GreenHouse", new Position(doorX, doorY), objHeight, objWidth);
                            farm.getBuildings().add((Building) go);
                        }
                        //TODO
                        case "shippingmail" -> go = new ShippingBar(new Position(tx, ty), farm);
                        case "mailbox" -> go = new MailBox(new Position(tx, ty));
//                        case "grass" -> go = new Grass(true, new Position(tx, ty), GrassType.NormalGrass);
//                        case "fibergrass" -> go = new Grass(true, new Position(tx, ty), GrassType.FiberGrass);
//                        case "wood" -> go = new Tree(TreeType.TREE_BARK, new Position(tx, ty));//TODO
//                        case "stone" ->
//                                go = new ForagingMineral(false, new Position(tx, ty), MineralItemType.STONE);//TODO
//                        case "tree" -> go = new Tree(TreeType.APPLE_TREE, new Position(tx, ty));
//                        case "stick" -> go = new Tree(TreeType.TREE_BARK, new Position(tx, ty));
//                        case "bigstone" ->
//                                go = new ForagingMineral(false, new Position(tx, ty), MineralItemType.BIG_STONE);
                        default -> go = null;
                    }


                    if (go != null
                            && ty >= 0 && ty + objHeight < height
                            && tx >= 0 && tx + objWidth < width) {
                        for (int i = ty; i < ty + objHeight; i++) {
                            for (int j = tx; j < tx + objWidth; j++) {
                                System.out.println(i);
                                System.out.println(j);
                                tiles[i][j].setFixedObject(go);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0;i < height; i ++){
            for (int j = 0;j < width;j ++){
                if (tiles[i][j] != null && tiles[i][j].isWalkable() && tiles[i][j].getFixedObject() == null && tiles[i][j].getTileType() == TileType.Soil) {
                    int rand = (int) (Math.random() * 100);
                    GameObject go = null;
                    if (rand <= 30){
                        if (rand < 3){
                            go = new ForagingMineral(false, new Position(j, i), MineralItemType.STONE);
                        } else if (rand < 12){
                            go = new Tree(TreeType.APPLE_TREE, new Position(j, i));
                        } else if (rand < 15){
                            go = new Grass(true, new Position(j, i), GrassType.NormalGrass);
                        } else if (rand < 20){
                            go = new Grass(true, new Position(j, i), GrassType.FiberGrass);
                        } else if (rand < 25){
                            go = new Tree(TreeType.TREE_BARK, new Position(j, i));
                        } else {
                            go = new Tree(TreeType.NORMAL_TREE, new Position(j, i));
                        }
                    }
                    tiles[i][j].setFixedObject(go);
                }
            }
        }

        return tiles;
    }

    public static Farm loadTheFarm2(String farmName) throws Exception {
        Farm farm = new Farm();
        Tile[][] farmTileSet = load(farmName + ".tmj", farm);
        return farm;
    }
}
