package io.src.model.MapModule;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import io.src.model.Enums.GameObjects.TreeType;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.GameObject;
import io.src.model.GameObject.MailBox;
import io.src.model.GameObject.Tree;


public class newFarmLoader {

    private static final int TILE_SIZE = 16;

    /**
     * @param tmxPath مسیر داخل assets، مثلاً "maps/Farm1.tmx"
     * @return آرایه‌ی Tile[height][width]
     */
    public static Tile[][] load(String tmxPath) {
        // 1. بارگذاری TiledMap
        TiledMap tiledMap = new TmxMapLoader().load(tmxPath);
        // فرض می‌کنیم لایه‌ی اول tilelayer است:
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        int width  = layer.getWidth();
        int height = layer.getHeight();

        Tile[][] tiles = new Tile[height][width];

        // 2. پیمایش هر سلّول
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // توجه: LibGDX مختصات (0,0) پایین-چپ است، پس اگر تابع Tile می‌خواهد مبدأ بالا-چپ،
                // باید y را invert کنید. ولی اگر شما Tile می‌پذیرد پایین-چپ، همین استفاده کنید:
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                boolean    isWalkable = true;       // مقدار پیش‌فرض
                TileType tileType   = TileType.Default;
                GameObject fixedObject = null;      // بعداً پاک کنید یا اختصاص دهید

                if (cell != null && cell.getTile() != null) {
                    MapProperties props = cell.getTile().getProperties();

                    // 2.1 خواندن walkability
                    if (props.containsKey("walkability")) {
                        String w = props.get("walkability", String.class);
                        isWalkable = Boolean.parseBoolean(w);
                    }

                    // 2.2 خواندن نوع کاشی
                    if (props.containsKey("tileType")) {
                        String t = props.get("tileType", String.class).toLowerCase();
                        // نگاشت به enum داخلی‌تان:
                        switch (t) {
                            case "soil":    tileType = TileType.Soil;    break;
                            case "water":   tileType = TileType.Water;   break;
                            case "grass":   tileType = TileType.Grass;   break;
                            // … بقیهٔ موارد …
                            default:        tileType = TileType.Default; break;
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

        // 4. (اختیاری) اگر یک آبجکت‌لِیِر دارید:
        TiledMapTileLayer objLayer = (TiledMapTileLayer) tiledMap.getLayers().get("ObjectLayerName");
        if (objLayer != null) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    // فرض کنید هر آبجکت یک property به نام "objectType" داشته باشد:
                    TiledMapTileLayer.Cell cell = objLayer.getCell(col, row);
                    if (cell != null && cell.getTile() != null) {
                        MapProperties props = cell.getTile().getProperties();
                        if (props.containsKey("objectType")) {
                            String objType = props.get("objectType", String.class);
                            GameObject go = createGameObjectFromType(objType, col, row);
                            tiles[row][col].setFixedObject(go);
                        }
                    }
                }
            }
        }

        return tiles;
    }

    private static GameObject createGameObjectFromType(String type, int x, int y) {
        // مثالِ نگاشت String به شیء واقعی:
        Position p = new Position(x, y);
        switch (type.toLowerCase()) {
            case "tree":
                return new Tree(TreeType.NORMAL_TREE, p);
            case "mailbox":
                return new MailBox(p);
            // … بقیهٔ انواع …
            default:
                return null;
        }
    }
}
