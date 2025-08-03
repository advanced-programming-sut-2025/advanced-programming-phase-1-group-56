package io.src.model.MapModule;

import com.google.gson.*;
import io.src.model.Enums.GameLocationType;
import io.src.model.Enums.GameObjects.TreeType;
import io.src.model.Enums.NpcType;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.*;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.MapModule.Buildings.*;
import io.src.model.MapModule.GameLocations.Town;
import netscape.javascript.JSObject;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class TownLoader {
    private static final int tileSize = 16;

    public static Tile[][] load(Town town, String jsonPath) {
        JsonObject map = null;
        try {
            map = JsonParser.parseReader(new FileReader(jsonPath)).getAsJsonObject();
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
        Tile[][] tiles = new Tile[width][height];

        JsonArray tilesets = map.getAsJsonArray("tilesets");
        JsonObject ts = tilesets.get(0).getAsJsonObject();
        //int firstGid = ts.get("firstgid").getAsInt();
        JsonArray defs = ts.getAsJsonArray("tiles");
        for (int i = 0; i < defs.size(); i++) {
            JsonObject def = defs.get(i).getAsJsonObject();
            //int gid = def.get("id").getAsInt() + firstGid;
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
                                case "soil", "farmingsoil" -> TileType.Soil;
                                case "plowedsoil" -> TileType.PlowedSoil;
                                case "waterplowedsoil" -> TileType.WaterPlowedSoil;
                                case "wrapper" -> TileType.Wrapper;
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
            //System.out.println("y: "+ y + " x: " + x);
            tiles[x][y] = new Tile(
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
                        case "pierresgeneralstore" -> {
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
                            go = new PierresGeneralStore(new Position(tx, ty), false, "PierresGeneralStore", new Position(doorX / 16, doorY / 16), objHeight, objWidth);
                            town.getStores().add((Store) go);
                            int dX = (int) ((PierresGeneralStore) go).getDoorPosition().getX();
                            int dY = (int) ((PierresGeneralStore) go).getDoorPosition().getY() + 2;
                            NPC newNPC = NpcType.SEBASTIAN.getNPC(new Position(dX, dY));
                            town.getNPCs().add(newNPC);
                            tiles[dX][dY].setFixedObject(newNPC);
                        }

                        case "thestardropsaloon" -> {
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
                            go = new TheSaloonStardrop(new Position(tx, ty), false, "TheSaloonStardrop", new Position(doorX / 16, doorY / 16 + 1), objHeight, objWidth);
                            town.getStores().add((Store) go);
                            int dX = (int) ((TheSaloonStardrop) go).getDoorPosition().getX();
                            int dY = (int) ((TheSaloonStardrop) go).getDoorPosition().getY() + 2;
                            NPC newNPC = NpcType.LEAH.getNPC(new Position(dX, dY));
                            town.getNPCs().add(newNPC);
                            tiles[dX][dY].setFixedObject(newNPC);
                        }

                        case "blacksmith" -> {
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
                            go = new Blacksmith(new Position(tx, ty), false, "Blacksmith", new Position(doorX / 16, doorY / 16), objHeight, objWidth);
                            int dX = (int) ((Blacksmith) go).getDoorPosition().getX();
                            int dY = (int) ((Blacksmith) go).getDoorPosition().getY() + 2;
                            NPC newNPC = NpcType.ROBIN.getNPC(new Position(dX, dY));
                            town.getNPCs().add(newNPC);
                            tiles[dX][dY].setFixedObject(newNPC);
                            town.getStores().add((Store) go);
                        }

                        case "jojamart" -> {
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
                            go = new JojaMart(new Position(tx, ty), false, "JojaMart", new Position(doorX / 16, doorY / 16), objHeight, objWidth);
                            town.getStores().add((Store) go);
                            int dX = (int) ((JojaMart) go).getDoorPosition().getX();
                            int dY = (int) ((JojaMart) go).getDoorPosition().getY() + 2;
                        }

                        case "carpentersshop" -> {
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
                            go = new CarpentersShop(new Position(tx, ty), false, "CarpenterShop", new Position(doorX / 16, doorY / 16), objHeight, objWidth);
                            town.getStores().add((Store) go);
                            int dX = (int) ((CarpentersShop) go).getDoorPosition().getX();
                            int dY = (int) ((CarpentersShop) go).getDoorPosition().getY() + 2;
                            NPC newNPC = NpcType.HARVEY.getNPC(new Position(dX, dY));
                            town.getNPCs().add(newNPC);
                            tiles[dX][dY].setFixedObject(newNPC);
                        }

                        case "fishshop" -> {
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
                            go = new FishShop(new Position(tx, ty), false, "FishShop", new Position(tx + 1, ty + 5), objHeight, objWidth);
                            town.getStores().add((Store) go);
                        }

                        case "marniesranch" -> {
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
                            go = new MarniesRanch(new Position(tx, ty), false, "MarniesRanch", new Position(tx + 1, ty + 6), objHeight, objWidth);
                            town.getStores().add((Store) go);
                        }
                        case "wood" -> go = new Tree(TreeType.TREE_BARK, new Position(tx, ty));//TODO
                        default -> go = null;
                    }

                    if (go != null
                        && ty >= 0 && ty + objHeight < height
                        && tx >= 0 && tx + objWidth < width) {
                        for (int i = tx; i < tx + objWidth; i++) {
                            for (int j = ty; j < ty + objHeight; j++) {
                                tiles[i][j].setFixedObject(go);
                            }
                        }
                    }
                }
            }
        }
        Tile[][] rotated = new Tile[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                rotated[x][y] = tiles[y][x];
            }
        }
        return rotated;
    }

//    public static void main(String[] args) throws Exception {
//        Tile[][] farm = load("Town4.tmj");
//        System.out.println("Loaded "
//                + farm.length + "x" + farm[0].length + " tiles.");
//        for (int i = 0; i < farm.length; i++) {
//            for (int j = 0; j < farm[i].length; j++) {
//                char toPrint= ' ';
//                GameObject go = farm[i][j].getFixedObject();
//                if (go != null) {
//                    toPrint = farm[i][j].getFixedObject().getClass().getSimpleName().charAt(0);
//                }
//                else{
//                    toPrint = farm[i][j].getTileType().toString().charAt(0);
//                    if(toPrint != 'W'&&toPrint != 'V'){
//                        toPrint = ' ';
//                    }
//                }
//
//                System.out.print(toPrint);
//            }
//            System.out.println();
//        }
//    }


    public static Town loadTheTown() {
        Town town = new Town(GameLocationType.Town);
        Tile[][] townTileSet = load(town, "assets\\gameLocations\\Town4.tmj");
        town.setTiles(townTileSet);
        //TODO npc registry
        //done
        return town;
    }


    //TODO chap rangi naghshe
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
