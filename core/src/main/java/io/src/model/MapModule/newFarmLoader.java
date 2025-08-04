package io.src.model.MapModule;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import io.src.model.App;
import io.src.model.Enums.GameLocationType;
import io.src.model.Enums.GameObjects.EtcObjectType;
import io.src.model.Enums.GameObjects.TreeType;
import io.src.model.Enums.Items.GrassType;
import io.src.model.Enums.Items.MineralItemType;
import io.src.model.Enums.NpcType;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.*;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.MapModule.Buildings.*;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.GameLocations.Town;


public class newFarmLoader {

    private static final int TILE_SIZE = 16;

    /**
     * @param tmxPath مسیر داخل assets، مثلاً "maps/Farm1.tmx"
     * @return آرایه‌ی Tile[height][width]
     */
    public static Tile[][] load(String tmxPath, GameLocation location) {
        // 1. بارگذاری TiledMap
//        TiledMap tiledMap = new TmxMapLoader().load(tmxPath);
        TiledMap tiledMap = new TmxMapLoader().load(tmxPath);
//            new TmxMapLoader(new ExternalFileHandleResolver()).load("C:\\Users\\USER\\Desktop\\Ap\\Java program\\stradew valley project\\" + tmxPath);
        // فرض می‌کنیم لایه‌ی اول tilelayer است:
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        int width = layer.getWidth();
        int height = layer.getHeight();

        Tile[][] tiles = new Tile[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                boolean isWalkable = true;
                TileType tileType = TileType.Default;
                GameObject fixedObject = null;

                if (cell != null && cell.getTile() != null) {
                    MapProperties props = cell.getTile().getProperties();

                    if (props.containsKey("walkability")) {
                        String w = props.get("walkability", String.class);
                        isWalkable = Boolean.parseBoolean(w);
                    }

                    // 2.2 خواندن نوع کاشی
                    if (props.containsKey("tileType")) {
                        String t = props.get("tileType", String.class).toLowerCase();
                        // نگاشت به enum داخلی‌تان:
                        switch (t) {
//                            case "soil":    tileType = TileType.Soil;    break;
//                            case "water":   tileType = TileType.Water;   break;
//                            case "grass":   tileType = TileType.Grass;   break;
//                            // … بقیهٔ موارد …
//                            default:        tileType = TileType.Default; break;

                            case "vanity" -> tileType = TileType.Vanity;
                            case "water" -> tileType = TileType.Water;
                            case "grass" -> tileType = TileType.Grass;
                            case "stone" -> tileType = TileType.Stone;
                            case "soil" -> tileType = TileType.Soil;
//                                case "farmingsoil" -> TileType.FarmingSoil;
                            case "plowedsoil" -> tileType = TileType.PlowedSoil;
                            case "waterplowedsoil" -> tileType = TileType.WaterPlowedSoil;
                            case "wrapper" -> tileType = TileType.Wrapper;
                            case "mine" -> tileType = TileType.Mine;
//                            case "default" -> tileType = TileType.Default;
//                            default -> tileType = TileType.Default;
                        }
                    }
                }

                // 3. ساخت شیء Tile خودتان
                Position pos = new Position(col, row);
                Tile tile = new Tile(pos, isWalkable, tileType);

                // اختیاری: اگر در لایه‌های آبجکتِ دیگری آبجکت دارید،
                // می‌توانید پس از loop اول، یک loop دیگر برای objectLayer بزنید
                // و fixedObject را هم تنظیم کنید.

                tiles[row][col] = tile;
            }
        }

//        TiledMapTileLayer objLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Object Layer 1");
//        if (objLayer != null) {
//            for (int row = 0; row < height; row++) {
//                for (int col = 0; col < width; col++) {

//                    TiledMapTileLayer.Cell cell = objLayer.getCell(col, row);
//                    if (cell != null && cell.getTile() != null) {
//                        MapProperties props = cell.getTile().getProperties();
//                        if (props.containsKey("objectType")) {
//                            String objType = props.get("objectType", String.class);
//                            GameObject go = createGameObjectFromType(objType, col, row);
//                            tiles[row][col].setFixedObject(go);
//                        }
//                    }
//                }
//            }
//        }

        MapLayer objectLayer = tiledMap.getLayers().get("Object Layer 1");
        if (objectLayer != null) {
            for (MapObject obj : objectLayer.getObjects()) {
                if (!(obj instanceof RectangleMapObject)) continue;

                RectangleMapObject rectObj = (RectangleMapObject) obj;
                String name = obj.getName();

                float x = rectObj.getRectangle().x;
                float y = rectObj.getRectangle().y;

                int objWidth = (int) (rectObj.getRectangle().width / 16);
                int objHeight = (int) (rectObj.getRectangle().height / 16);


                int tileX = (int) (x / 16);
                int tileY = (int) (y / 16);


                // چون در Tiled محور Y از پایین شروع میشه و در آرایه ما از بالا:
//                tileY = height - 1 - tileY;

                GameObject gameObject = createGameObjectFromObject(name, obj.getProperties(), x, y, objWidth, objHeight, location);
                if (gameObject != null) {
                    tiles[tileY][tileX].setFixedObject(gameObject);
                    location.getGameObjects().add(gameObject);
                }
                if (gameObject != null
                    && tileY >= 0 && tileY + objHeight < height
                    && tileX >= 0 && tileX + objWidth < width) {
                    for (int i = tileY; i < tileY + objHeight; i++) {
                        for (int j = tileX; j < tileX + objWidth; j++) {
//                                System.out.println(i);
//                                System.out.println(j);
//                            tiles[i][j].setFixedObject(gameObject);
                            tiles[i][j].setWalkable(gameObject.isWalkable());

                        }
                    }
                }

                if (gameObject != null && tileY >= 0 && tileY < height && tileX >= 0 && tileX < width) {
//                    tiles[tileY][tileX].setFixedObject(gameObject);
                    if (location instanceof Town && gameObject instanceof Store) {
                        ((Town) location).getStores().add((Store) gameObject);
                        NPC newNPC;
                        int dX = (int) ((Building) gameObject).getDoorPosition().getX();
                        int dY = (int) ((Building) gameObject).getDoorPosition().getY() + 2;
                        if (gameObject instanceof PierresGeneralStore) {
                            newNPC = NpcType.SEBASTIAN.getNPC(new Position(dX, dY));
                            ((Town) location).getNPCs().add(newNPC);
                            tiles[dY][dX].setFixedObject(newNPC);
                        } else if (gameObject instanceof TheSaloonStardrop) {
                            newNPC = NpcType.LEAH.getNPC(new Position(dX, dY));
                            ((Town) location).getNPCs().add(newNPC);
                            tiles[dY][dX].setFixedObject(newNPC);
                        } else if (gameObject instanceof Blacksmith) {
                            newNPC = NpcType.ROBIN.getNPC(new Position(dX, dY));
                            ((Town) location).getNPCs().add(newNPC);
                            tiles[dY][dX].setFixedObject(newNPC);
                        } else if (gameObject instanceof CarpentersShop) {
                            newNPC = NpcType.HARVEY.getNPC(new Position(dX, dY));
                            ((Town) location).getNPCs().add(newNPC);
                            tiles[dY][dX].setFixedObject(newNPC);
                        }
//                        else if (gameObject instanceof JojaMart) {
//                            newNPC = NpcType.ROBIN.getNPC(new Position(dX,dY));
//                        }
//                        else if (gameObject instanceof FishShop) {
//                            newNPC = NpcType.ROBIN.getNPC(new Position(dX,dY));
//                        } else if (gameObject instanceof MarniesRanch) {
//                            newNPC = NpcType.ROBIN.getNPC(new Position(dX,dY));
//                        }
                    } else if (location instanceof Farm && gameObject instanceof Building) {
                        ((Farm) location).getBuildings().add((Building) gameObject);
                    }

                }
            }
        }

        if (location instanceof Farm) {
            for (int i = 8; i < height - 8; i++) {
                for (int j = 8; j < width - 8; j++) {
                    if (tiles[i][j] != null && tiles[i][j].isWalkable() && tiles[i][j].getFixedObject() == null && tiles[i][j].getTileType() == TileType.Soil) {
                        int rand = (int) (Math.random() * 100);
                        GameObject go = null;
                        boolean cont = false;
                        for (int k = i - 1; k < i + 1; k++) {
                            for (int l = j - 1; l < j + 1; l++) {
                                if (tiles[k][l].getFixedObject() != null & tiles[k][l].getFixedObject() instanceof Tree) {
                                    cont = true;
                                    break;
                                }
                            }
                        }
                        if (cont) continue;
                        if (rand <= 10) {
                            if (rand < 1) {
                                go = new ForagingMineral(false, new Position(j, i), MineralItemType.STONE);
                            } else if (rand < 4) {
                                int randomTreeType = (int) (Math.random() * (TreeType.values().length - 8)) + 5;
                                TreeType treeType = TreeType.values()[randomTreeType];
                                Tree tree = new Tree(treeType, new Position(j, i));
                                tree.setCurrentStage(4);
                                GameObject obj = tree;
                                for (int k = i - 2; k < i + 2; k++) {
                                    for (int l = j - 2; l < j + 2; l++) {
                                        if (tiles[k][l].getFixedObject() != null & tiles[k][l].getFixedObject() instanceof Tree) {
                                            obj = null;
                                            break;
                                        }
                                    }
                                }
                                go = obj;
                            } else if (rand < 5 && !tmxPath.contains("Farm1")) {
                                go = new Grass(true, new Position(j, i), GrassType.NormalGrass);
                            } else if (rand < 7 && !tmxPath.contains("Farm1")) {
                                go = new Grass(true, new Position(j, i), GrassType.FiberGrass);
                            } else if (rand < 9) {
                                go = new Tree(TreeType.TREE_BARK, new Position(j, i));
                            } else {
                                go = new Tree(TreeType.NORMAL_TREE, new Position(j, i));
                            }
                        }
                        tiles[i][j].setFixedObject(go);
                        if (go != null) {
                            location.getGameObjects().add(go);
                        }

                    }
                }
            }

            if (tmxPath.contains("Farm1")) {
                for (int i = 3; i < 17; i++) {
                    for (int j = 8; j < 16; j++) {
                        tiles[j][i].setTileType(TileType.Mine);
                    }
                }
            } else {
                for (int i = 6; i < 17; i++) {
                    for (int j = 10; j < 17; j++) {
                        tiles[j][i].setTileType(TileType.Mine);
                    }
                }
            }
        }

        return tiles;
    }

    private static GameObject createGameObjectFromObject(String name, MapProperties properties, float x, float y, int objWidth, int objHeight, GameLocation location) {
        Position pos = new Position(x/16, y/16);

        switch (name.toLowerCase()) {
            case "playerhome" -> {
                int doorX = Integer.parseInt(properties.get("doorX", String.class));
                int doorY = Integer.parseInt(properties.get("doorY", String.class));
//                int objWidth = properties.get("width", Integer.class);
//                int objHeight = properties.get("height", Integer.class);

                return new Home(pos, false, "PlayerHome", new Position((float) doorX / 16, (float) doorY / 16), objWidth, objHeight);
//            new Home(new Position(tx, ty),false, "PlayerHome", new Position(tx+4, ty+4), objHeight, objWidth);
            }
            case "mailbox" -> {
                return new MailBox(pos);
            }
            case "shippingmail" -> {
                return new ShippingBar(pos, (Farm) location);
            }
            case "greenhouse" -> {
                int gx = Integer.parseInt(properties.get("doorX", String.class));
                int gy = Integer.parseInt(properties.get("doorY", String.class));
                return new GreenHouse(pos, false, "GreenHouse", new Position(gx / 16, gy / 16), 11, 11);
//            new GreenHouse( new Position(tx, ty),false, "GreenHouse", new Position(tx+3, ty+5), objHeight, objWidth);
            }

            case "fibegrass" -> {
                return new Grass(true, pos, GrassType.FiberGrass);
            }
            case "grass" -> {
                return new Grass(true, pos, GrassType.NormalGrass);
            }
            case "pierresgeneralstore" -> {
                int doorX = Integer.parseInt(properties.get("doorX", String.class));
                int doorY = Integer.parseInt(properties.get("doorY", String.class));
                return new PierresGeneralStore(pos, false, "PierresGeneralStore", new Position(doorX / 16, doorY / 16), objHeight, objWidth);
            }
            case "thestardropsaloon" -> {
                int doorX = Integer.parseInt(properties.get("doorX", String.class));
                int doorY = Integer.parseInt(properties.get("doorY", String.class));
                return new TheSaloonStardrop(pos, false, "TheSaloonStardrop", new Position(doorX / 16, doorY / 16 + 1), objHeight, objWidth);
            }
            case "blacksmith" -> {
                int doorX = Integer.parseInt(properties.get("doorX", String.class));
                int doorY = Integer.parseInt(properties.get("doorY", String.class));
                return new Blacksmith(pos, false, "Blacksmith", new Position(doorX / 16, doorY / 16), objHeight, objWidth);
            }
            case "jojamart" -> {
                int doorX = Integer.parseInt(properties.get("doorX", String.class));
                int doorY = Integer.parseInt(properties.get("doorY", String.class));
                return new JojaMart(pos, false, "JojaMart", new Position(doorX / 16, doorY / 16), objHeight, objWidth);
            }
            case "carpentersshop" -> {
                int doorX = Integer.parseInt(properties.get("doorX", String.class));
                int doorY = Integer.parseInt(properties.get("doorY", String.class));
                return new CarpentersShop(pos, false, "CarpenterShop", new Position(doorX / 16, doorY / 16), objHeight, objWidth);
            }
            case "fishshop" -> {
                int doorX = Integer.parseInt(properties.get("doorX", String.class));
                int doorY = Integer.parseInt(properties.get("doorY", String.class));
                return new FishShop(pos, false, "FishShop", new Position(x + 1, y + 5), objHeight, objWidth);
            }
            case "marniesranch" -> {
                int doorX = Integer.parseInt(properties.get("doorX", String.class));
                int doorY = Integer.parseInt(properties.get("doorY", String.class));
                return new MarniesRanch(pos, false, "MarniesRanch", new Position(x + 1, y + 6), objHeight, objWidth);
            }
            case "vanitytree2" -> {
                return new EtcObject(false , pos , EtcObjectType.VANITY_TREE2);
            }

            default -> {
                return null;
            }
        }
    }

    public static GameLocation loadTheLocation(String locationName) {
        GameLocation location;
        if (locationName.contains("Farm1")) {
            location = new Farm(GameLocationType.Farm1);

        } else if (locationName.contains("Farm2")) {
            location = new Farm(GameLocationType.Farm2);

        } else {
            location = new Town(GameLocationType.Town);

        }
        Tile[][] farmTileSet = load(locationName + ".tmx", location);
        location.setTiles(farmTileSet);
        return location;

    }
}
