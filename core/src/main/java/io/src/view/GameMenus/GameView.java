package io.src.view.GameMenus;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.src.StardewValley;
import io.src.controller.GameMenuController.GameController;
import io.src.model.*;
import io.src.controller.GameMenuController.FishingController;
import io.src.model.App;
import io.src.model.Enums.Animals.FishBehavior;
import io.src.model.Enums.AnimationKey;
import io.src.model.Enums.Direction;
import io.src.model.Enums.GameObjects.EtcObjectType;
import io.src.model.Enums.Menu;
import io.src.model.Enums.TileType;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.Enums.TileType;
import io.src.model.Enums.WeatherAndTime.WeatherType;
import io.src.model.Game;
import io.src.model.GameObject.*;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.MapModule.Buildings.Home;
import io.src.model.MapModule.Buildings.Store;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;
import io.src.view.AppMenu;
import io.src.view.GameMenus.ShopMenus.ShopStateWindow;
import io.src.view.LoginMenu;
import io.src.model.Player;
import io.src.model.items.Fish;
import io.src.model.items.Tool;
import io.src.model.items.Etc;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.*;


public class GameView implements Screen, TimeObserver {
    public static final int TILE_SIZE = 16;

    private final Game game;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private BitmapFont smallFont;
    private GlyphLayout layout = new GlyphLayout();
    private final HashMap<String, TextureRegion> gameObjectTextureMap = new HashMap<>();
    private TextureAtlas playerAtlas;
    private final ArrayList<Animation<TextureRegion>> playerAnimations = new ArrayList<>();
    private AnimationManager animationManager = new AnimationManager();
    private ScreenTransition transitionManager;
    private ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera = new OrthographicCamera();
    private ShippingBarWindow shippingBarWindow;

    private final ObjectMap<String, Float> stateTimeMap = new ObjectMap<>();
    private float stateTime = 0f;

    private Texture pixel; // Add this
    private int moveDirection = 0;
    private Stage stage;
    private TimerWindow timeWindow;
    private InventoryWindow invWindow;
    private DialogWindow dialogWindow;
    private WarningWindow warningWindow;
    private EnergyBar energyWindow;
    private CheatWindow cheatWindow;
    private ShopStateWindow shopStateWindow;


    private InputMultiplexer multiplexer = new InputMultiplexer();
    private GameMenuInputAdapter gameMenuInputAdapter;
    private final ArrayList<ToolSwing> activeToolSwings = new ArrayList<>();
    private craftingWindow craftingWindow;
    private InventoryBar inventoryBar;
    private Label itemLabel;
    private FoodWindow foodWindow;
    private RefrigeratorWindow refrigeratorWindow;
    private Image foodBuff;
    private FishingMinigame activeFishingMinigame = null;
    private DayNightLighting lighting;
    private ShapeRenderer sr = new ShapeRenderer();
    private RainSystem rainSystem = new RainSystem();
    private ThorSystem thorSystem = new ThorSystem();

    private Texture whitePixel;
    private boolean thor;

//    private List<DayNightLighting.Light> lights = new ArrayList<>();


    public void updateMapWithFade(Runnable afterFadeOut) {
        transitionManager.start(() -> {
            gameMenuInputAdapter.setStopMoving(true);
            afterFadeOut.run(); // تغییرات position و location
            updateMap();
            gameMenuInputAdapter.setStopMoving(false);
        });
    }

    public void updateMap() {
        this.map = new TmxMapLoader().load(App.getMe().getCurrentGameLocation().getType().getAssetName());
        renderer = new OrthogonalTiledMapRenderer(map, 1f);

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    private void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("StardewValley.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        smallFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public GameView(Game game) {
        this.game = game;
        this.map = new TmxMapLoader().load(App.getMe().getCurrentGameLocation().getType().getAssetName());
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        stage = new Stage(new ScreenViewport());
        itemLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        foodBuff = null;
        invWindow = new InventoryWindow();
        energyWindow = new EnergyBar();
        timeWindow = new TimerWindow();
        craftingWindow = new craftingWindow(App.getMe());
        inventoryBar = new InventoryBar();
        foodWindow = new FoodWindow(App.getMe());
        refrigeratorWindow = new RefrigeratorWindow();
        energyWindow.setPosition(Gdx.graphics.getWidth() - 50, 50);
        invWindow.setVisible(false);
        craftingWindow.setVisible(false);
        foodWindow.setVisible(false);

        stage.addActor(craftingWindow);
        stage.addActor(invWindow);
        stage.addActor(energyWindow);
        stage.addActor(timeWindow);
        stage.addActor(itemLabel);
        stage.addActor(foodWindow);
        stage.addActor(inventoryBar);
        stage.addActor(refrigeratorWindow);
        refrigeratorWindow.setVisible(false);
        inventoryBar.toFront();
        itemLabel.setPosition(930, 200);
        //Warning Window
        warningWindow = new WarningWindow(((LoginMenu) Menu.loginMenu.getMenu()).getSkin());
        warningWindow.setVisible(false);
        stage.addActor(warningWindow);
        //Cheat Window
        cheatWindow = new CheatWindow(((LoginMenu) Menu.loginMenu.getMenu()).getSkin());
        cheatWindow.setVisible(false);
        stage.addActor(cheatWindow);
        //ShopState Window
        shopStateWindow = new ShopStateWindow(SkinManager.getInstance().getSkin(SkinManager.MAIN_SKIN));
        shopStateWindow.setVisible(false);
        stage.addActor(shopStateWindow);
        //Shipping
        shippingBarWindow = new ShippingBarWindow();
        shippingBarWindow.setVisible(false);
        stage.addActor(shippingBarWindow);

        this.gameMenuInputAdapter = new GameMenuInputAdapter(game);

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(gameMenuInputAdapter);
        Gdx.input.setInputProcessor(multiplexer);

        transitionManager = new ScreenTransition();
        shapeRenderer = new ShapeRenderer();


        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.WHITE);
        pm.fill();
        whitePixel = new Texture(pm);
        pm.dispose();
        lighting = new DayNightLighting();
//        rainSystem.setSpawnRate(150f);      // ذرات در ثانیه
//        rainSystem.setWind(40f, 40f);       // باد به سمت راست 40 px/s با تغییر ±40
//        rainSystem.setGroundOffset(6f);

        setCustomCursor("assets/Cursor.png", 0, 0);

        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);

    }

    private Pixmap resizeToPowerOfTwo(Pixmap src) {
        int newWidth = MathUtils.nextPowerOfTwo(src.getWidth());
        int newHeight = MathUtils.nextPowerOfTwo(src.getHeight());

        Pixmap resized = new Pixmap(newWidth, newHeight, src.getFormat());
        resized.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(), 0, 0, newWidth, newHeight);
        return resized;
    }

    private void setCustomCursor(String path, int hotX, int hotY) {
        try {
            if (Gdx.files.internal(path).exists()) {
                Pixmap original = new Pixmap(Gdx.files.internal(path));
                Pixmap powerOfTwo = resizeToPowerOfTwo(original);
                Cursor customCursor = Gdx.graphics.newCursor(powerOfTwo, hotX, hotY);
                Gdx.graphics.setCursor(customCursor);
                original.dispose();
                powerOfTwo.dispose();
            } else {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        } catch (Exception e) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            e.printStackTrace();
        }
    }

    private void renderCharacter(String characterName, AnimationKey key, float x, float y) {
        Animation<TextureRegion> animation = animationManager.get(characterName, key);
        if (animation == null) return;

        if (!stateTimeMap.containsKey(characterName)) {
            stateTimeMap.put(characterName, 0f);
        }
        float newStateTime = stateTimeMap.get(characterName) + Gdx.graphics.getDeltaTime();
        stateTimeMap.put(characterName, newStateTime);

        TextureRegion frame = animation.getKeyFrame(newStateTime);
        renderer.getBatch().draw(frame, x, y);
    }

    private void renderCharacter(String characterName, AnimationKey key,
                                 float x, float y,
                                 float rotationDegrees,
                                 float scaleX, float scaleY,
                                 float extraOffsetX, float extraOffsetY) {

        Animation<TextureRegion> animation = animationManager.get(characterName, key);
        if (animation == null) return;

        // state time tracking (ObjectMap<String, Float> stateTimeMap داریم)
        if (!stateTimeMap.containsKey(characterName)) {
            stateTimeMap.put(characterName, 0f);
        }
        float newStateTime = stateTimeMap.get(characterName) + Gdx.graphics.getDeltaTime();
        stateTimeMap.put(characterName, newStateTime);

        TextureRegion frame = animation.getKeyFrame(newStateTime);
        if (frame == null) return;

        float w = frame.getRegionWidth();
        float h = frame.getRegionHeight();

        // origin = مرکز فریم (چرخش حول مرکز)
        float originX = w / 2f;
        float originY = h / 2f;

        // drawX/drawY: اگر x,y همان موقعیت پیکسلی پایین-چپ کاراکتر باشد،
        // برای اینکه مرکز تصویر روی موقعیت کاراکتر قرار بگیرد، باید نصف عرض/ارتفاع را کم کنیم.
        // این رفتار ممکن است بسته به آرایش اسپرایت‌تت تغییر کند — در صورت لزوم extraOffsetX/Y را تنظیم کن.
        float drawX = x - originX + extraOffsetX;
        float drawY = y - originY + extraOffsetY;
        Actions.delay(2);
        renderer.getBatch().draw(frame,
            drawX, drawY,
            originX, originY,
            w, h,
            scaleX, scaleY,
            rotationDegrees);
    }

    private void renderPlayer(Player player) {
        float x = player.getPixelPosition().getX(), y = player.getPixelPosition().getY();
        AnimationKey key;

        if (player.isFainted()) {
            // از یک فریم IDLE جهت آخرین جهت استفاده کن
            switch (player.getLastDirection()) {
                case UP -> key = AnimationKey.IDLE_UP;
                case DOWN -> key = AnimationKey.IDLE_DOWN;
                case LEFT -> key = AnimationKey.IDLE_LEFT;
                default -> key = AnimationKey.IDLE_RIGHT;
            }

            // تعیین زاویه برای "افتادن" — قابل تنظیم:
            // پیشنهاد اولیه:
            //   - اگر قبلاً رو به بالا بود (UP) یا پایین (DOWN) یه جهت بگیر (مثلاً 90/-90)
            //   - اگر قبلاً سمت راست/چپ بود هم به همون صورت
            float rotation = 0f;
            switch (player.getLastDirection()) {
                case UP -> rotation = 90f;    // تنظیم دلخواه: امتحان کن
                case DOWN -> rotation = -90f;
                case LEFT -> rotation = 90f;
                case RIGHT -> rotation = -90f;
            }

            float scaleX = 1f;
            float scaleY = 1f;


            float extraOffsetX = 0f;
            float extraOffsetY = 0f;


            renderCharacter("player", key, x, y, rotation, scaleX, scaleY, extraOffsetX, extraOffsetY);
            return;
        }

        if (player.isMoving()) {
            switch (player.getLastDirection()) {
                case UP:
                    key = AnimationKey.WALK_UP;
                    break;
                case DOWN:
                    key = AnimationKey.WALK_DOWN;
                    break;
                case LEFT:
                    key = AnimationKey.WALK_LEFT;
                    break;
                default:
                    key = AnimationKey.WALK_RIGHT;
                    break;
            }
        } else {
            switch (player.getLastDirection()) {
                case UP:
                    key = AnimationKey.IDLE_UP;
                    break;
                case DOWN:
                    key = AnimationKey.IDLE_DOWN;
                    break;
                case LEFT:
                    key = AnimationKey.IDLE_LEFT;
                    break;
                default:
                    key = AnimationKey.IDLE_RIGHT;
                    break;
            }
        }

        renderCharacter("player", key, x, y);
    }


    private void renderNPC(NPC npc) {
        String name = npc.getType().getAssetName(); // مثل "grandma"
        float x = npc.getPixelPosition().x, y = npc.getPixelPosition().y;

        AnimationKey key;
        if (npc.isMoving()) {
            switch (npc.getLastDirection()) {
                case UP:
                    key = AnimationKey.WALK_UP;
                    break;
                case DOWN:
                    key = AnimationKey.WALK_DOWN;
                    break;
                case LEFT:
                    key = AnimationKey.WALK_LEFT;
                    break;
                default:
                    key = AnimationKey.WALK_RIGHT;
                    break;
            }
        } else {
            switch (npc.getLastDirection()) {
                case UP:
                    key = AnimationKey.IDLE_UP;
                    break;
                case DOWN:
                    key = AnimationKey.IDLE_DOWN;
                    break;
                case LEFT:
                    key = AnimationKey.IDLE_LEFT;
                    break;
                default:
                    key = AnimationKey.IDLE_RIGHT;
                    break;
            }
        }

        renderCharacter(name, key, x, y);

    }

    private void renderAnimal(Animal animal) {
        String name = animal.getType().getAssetName(); // مثل "grandma"
        float x = animal.getPixelPosition().x, y = animal.getPixelPosition().y;

        AnimationKey key;
        if (animal.isMoving()) {
            switch (animal.getLastDirection()) {
                case UP:
                    key = AnimationKey.WALK_UP;
                    break;
                case DOWN:
                    key = AnimationKey.WALK_DOWN;
                    break;
                case LEFT:
                    key = AnimationKey.WALK_LEFT;
                    break;
                default:
                    key = AnimationKey.WALK_RIGHT;
                    break;
            }
        } else {
            switch (animal.getLastDirection()) {
                case UP:
                    key = AnimationKey.IDLE_UP;
                    break;
                case DOWN:
                    key = AnimationKey.IDLE_DOWN;
                    break;
                case LEFT:
                    key = AnimationKey.IDLE_LEFT;
                    break;
                default:
                    key = AnimationKey.IDLE_RIGHT;
                    break;
            }
        }

        renderCharacter(name, key, x, y);

    }



    public void spawnToolSwing(Tool tool, Direction dir, Runnable onComplete) {
        if (tool == null) return;

        String toolName = tool.getName();
        String toolMaterial = tool.getToolType().getToolMaterial().toString();
        String toolId = toolName + toolMaterial;
        Animation<TextureRegion> baseAnim;
        if (toolName.equals("Scythe")){
            baseAnim = animationManager.get(toolId, AnimationKey.valueOf("PICKAXE" + "_SWING_" + dir.toString()));

        } else {
            baseAnim = animationManager.get(toolId, AnimationKey.valueOf(toolName.toUpperCase() + "_SWING_" + dir.toString()));
        }
        if (baseAnim == null) {
            Gdx.app.error("GameView", "No swing animation for tool: " + toolId);
            return;
        }

        // pick angles & offsets for the tool (example only for Axe)
        float[] baseAngles;
        List<Vector2> offsets;

        // rotation offset per direction
        float dirRotation;
        switch (dir) {
            case RIGHT -> {
                if (toolName.equals("FishingPole")) {
                    baseAngles = new float[]{0, 0, 0};
                } else {
                    baseAngles = new float[]{10, -50, -100};
                }
                offsets = List.of(
                    new Vector2(8, 24),
                    new Vector2(12, 20),
                    new Vector2(12, 10)
                );
            }
            case UP -> {
                baseAngles = new float[]{0};
                offsets = List.of(
                    new Vector2(8, 24),
                    new Vector2(0, 20),
                    new Vector2(0, 12)
                );
            }
            case LEFT -> {
                if (toolName.equals("FishingPole")) {
                    baseAngles = new float[]{0, 0, 0};
                } else {
                    baseAngles = new float[]{-10, 50, 100};
                }
                offsets = List.of(
                    new Vector2(8, 24),
                    new Vector2(4, 20),
                    new Vector2(3, 10)
                );
            }
            case DOWN -> {
                if (toolName.equals("FishingPole")) {
                    baseAngles = new float[]{0, 0, 0};
                } else {
                    baseAngles = new float[]{0, 0};
                }
                offsets = List.of(
                    new Vector2(0, 20),
                    new Vector2(8, 0),
                    new Vector2(0, 16)
                );
            }
            default -> {
                baseAngles = new float[3];
                offsets = List.of();
            }
        }

        // anchor -> player's pixel position (world coords)
        Vector2 anchor = new Vector2(App.getMe().getPixelPosition().getX(), App.getMe().getPixelPosition().getY());

        ToolSwing s = new ToolSwing(baseAnim, baseAngles, offsets, anchor, 1f, onComplete);
        activeToolSwings.add(s);
    }

    private void updateAndDrawToolSwings(float delta) {
        for (int i = activeToolSwings.size() - 1; i >= 0; i--) {
            ToolSwing s = activeToolSwings.get(i);
            boolean finished = s.update(delta);
            s.draw(renderer.getBatch()); // draws in world coordinates because it uses anchor world coords
            if (finished) activeToolSwings.remove(i);
        }
    }

    public void startFishing(FishBehavior behavior) {
        if (activeFishingMinigame != null) return;
        Player player = App.getMe();
        Fish fish = FishingController.catchFish(player.getLastDirection());
        if (fish == null) {
            gameMenuInputAdapter.setStopMoving(false);
            System.out.println("use fishingPole in the water");
        } else {
            activeFishingMinigame = new FishingMinigame(stage, player, behavior, fish,
                () -> {
                    // onSuccess
                    FishingController.Fishing(fish, true); // یا تابع خودت
                    Gdx.input.setInputProcessor(new InputMultiplexer(StardewValley.getGameView().getStage(), gameMenuInputAdapter));
                    gameMenuInputAdapter.setStopMoving(false);
                    activeFishingMinigame = null;
                },
                () -> {
                    // onFail
                    FishingController.Fishing(fish, false);
                    Gdx.input.setInputProcessor(new InputMultiplexer(StardewValley.getGameView().getStage(), gameMenuInputAdapter));
                    gameMenuInputAdapter.setStopMoving(false);
                    activeFishingMinigame = null;
                }
            );
        }
    }


//    public Stage getStage() {
//        return stage;
//    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public Texture getPixel() {
        return pixel;
    }

    public void renderWarningDialog() {
        warningWindow.setRemainingTime(warningWindow.getRemainingTime() - 1);
    }

    private void updateCameraPosition() {
        Player player = App.getMe();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        float mapWidthPixels = layer.getWidth() * TILE_SIZE;
        float mapHeightPixels = layer.getHeight() * TILE_SIZE;

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float cameraX, cameraY;

        // X محور
        if (mapWidthPixels <= screenWidth * 0.3) {
            // اگر نقشه از صفحه کوچکتر بود، دوربین را وسط نقشه قرار بده
            cameraX = mapWidthPixels / 2f;
        } else {

//            float y = game.getCurrentPlayer().getPixelPosition().getY();
            float x = game.getCurrentPlayer().getPixelPosition().getX();
//            if (y + 182 >= mapHeight) {
//                y = mapHeight - 182;
//            }
            if (x + 300 >= mapWidthPixels) {
                x = mapWidthPixels - 300;
            }

//            if (y - 150 <= 0) {
//                y = 150;
//            }

            if (x - 290 <= 0) {
                x = 290;
            }
            cameraX = x;
            // اگر نقشه از صفحه بزرگ‌تر بود، دوربین روی پلیر با محدودیت قرار بگیرد
//            cameraX = MathUtils.clamp(player.getPixelPosition().getX(), screenWidth / 2f, mapWidthPixels - screenWidth / 2f);
        }

        // Y محور
        if (mapHeightPixels <= screenHeight * 0.3) {
            cameraY = mapHeightPixels / 2f;
        } else {
            float y = game.getCurrentPlayer().getPixelPosition().getY();
            if (y + 182 >= mapHeightPixels) {
                y = mapHeightPixels - 182;
            }
            if (y - 150 <= 0) {
                y = 150;
            }
            cameraY = y;
//            cameraY = MathUtils.clamp(player.getPixelPosition().getY(), screenHeight / 2f, mapHeightPixels - screenHeight / 2f);
        }

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    @Override
    public void show() {
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        background = new Texture(pixmap);
    }

    private Texture background;

    @Override
    public void render(float v) {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//BlackBackGround


        renderer.setView(camera);
        gameMenuInputAdapter.update(v);
        renderer.render();

        if (!(App.getMe().getCurrentGameLocation() instanceof Town)) {
            for (NPC npc : App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown().getNPCs()) {
                npc.update(v);
            }
        }

        renderer.getBatch().begin();


        renderWarningDialog();
        //render tile type plowed soil
        for (Tile[] tileLine : App.getMe().getCurrentGameLocation().getTiles()) {
            for (Tile tile : tileLine) {
                if (tile.getTileType() == TileType.PlowedSoil || tile.getTileType() == TileType.WaterPlowedSoil) {
                    String key = tile.getTileType().toString();
                    TextureRegion region;
                    if (!gameObjectTextureMap.containsKey(key)) {
                        // یک بار لود و ذخیره کن
                        String path = GameAssetManager.getGameAssetManager().getAssetsDictionary().get(key);
                        if (path != null) {
                            Texture texture = new Texture(Gdx.files.internal(path));
                            region = new TextureRegion(texture);
                            gameObjectTextureMap.put(key, region);
                        } else {
                            continue; // asset not found
                        }
                    } else {
                        region = gameObjectTextureMap.get(key);
                    }
                    // draw region
                    renderer.getBatch().draw(region, tile.getPosition().getX() * TILE_SIZE, tile.getPosition().getY() * TILE_SIZE);
                }
            }
        }

        //render tile type plowed soil
//        for (Tile[] tileLine : App.getMe().getCurrentGameLocation().getTiles()) {
//            for (Tile tile : tileLine) {
//                if (tile.getTileType() == TileType.PlowedSoil) {
//                    Texture texture = new Texture(Gdx.files.internal(
//                        GameAssetManager.getGameAssetManager().getAssetsDictionary().get(tile.getTileType().toString())
//                    ));
//                    TextureRegion region = new TextureRegion(texture);
//                    renderer.getBatch().draw(region, tile.getPosition().getX() * TILE_SIZE, tile.getPosition().getY() * TILE_SIZE);
//                }
//            }
//        }

        ArrayList<GameObject> objects = App.getMe().getCurrentGameLocation().getCopyOfGameObjects();
        Position myRenderingPosition = new Position((App.getMe().getPixelPosition().getX() + 16) / 16, (App.getMe().getPixelPosition().getY()) / 16);
        objects.add(App.getMe().getPlayerObjectPlusPosition(myRenderingPosition));
        for (Player player : App.getCurrentUser().getCurrentGame().getPlayers()) {
            if (player.equals(App.getMe())) continue;
            if (App.getMe().getCurrentGameLocation().equals(player.getCurrentGameLocation())) {
                Position renderingPosition = new Position((player.getPixelPosition().getX() + 16) / 16, (player.getPixelPosition().getY()) / 16);
                objects.add(player.getPlayerObjectPlusPosition(renderingPosition));
            }
        }
        objects.sort(
            Comparator
                .comparingDouble((GameObject o) -> -o.getPosition().getY())
                .thenComparingInt(o -> (int) o.getPosition().getX())
        );

        for (GameObject go : objects) {
            String assetName = go.getAssetName();
            TextureRegion region;
            if (go instanceof PlayerObject) {
                renderPlayer(((PlayerObject) go).getPlayer());
                renderWarningDialog();
                updateAndDrawToolSwings(v);

//                App.getMe().setFinishActing(true);

                //Debug

                //GREEN HIT BOX
//                Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
//                pixmap.setColor(0, 1, 0, 1);
//                pixmap.fill();
//                Texture texture = new Texture(pixmap);
//                TextureRegion greenRegion = new TextureRegion(texture);
//                float worldX = App.getMe().getPixelPosition().getX();
//                float worldY = App.getMe().getPixelPosition().getY();
//                renderer.getBatch().draw(greenRegion,
//                    worldX, worldY,
//                    16,  // Origin X (مرکز تصویر)
//                    16, // Origin Y
//                    16, 16, // اندازه اصلی
//                    0.9f, 0.9f, // scaleX, scaleY
//                    0); // rotation

                continue;
            }
            if (go instanceof NPC npc) {
                renderNPC(npc);
                npc.update(v);
                handleNpcHint(npc);
                continue;
            }

            if (go instanceof MailBox mailBox) {
                handleMailBoxHint(mailBox);
                continue;
            }
            if (go instanceof Animal animal) {
                renderAnimal(animal);
                animal.update(v);
                handleAnimalHint(animal);
                continue;
            }

            if (GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName) == null) {
                System.out.println(assetName);
            }


            if (!gameObjectTextureMap.containsKey(assetName)) {
                Texture texture = new Texture(Gdx.files.internal(
                    GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName)
                ));
                region = new TextureRegion(texture);
                gameObjectTextureMap.put(assetName, region);
            } else {
                region = gameObjectTextureMap.get(assetName);
            }


            float worldX = go.getPosition().getX() * TILE_SIZE;
            float worldY = go.getPosition().getY() * TILE_SIZE;

            if ((go instanceof Tree tree && tree.getCurrentStage() >= 4) || go instanceof EtcObject && (((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE1 ||
                ((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE2 || ((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE3)) {
                worldX -= 16;
            }

            if (go instanceof EtcObject && ((EtcObject) go).getEtcObjectType() == EtcObjectType.PINKFU_TREE) {
                worldX -= 24;
            }
//
//            if (go instanceof EtcObject && ((EtcObject) go).getEtcObjectType() == EtcObjectType.LANTERN){
//                float wx = go.getPixelPosition().x + TILE_SIZE/2f;
//                float wy = go.getPixelPosition().y + TILE_SIZE/2f + 8f; // ارتفاعِ کمی بالاتر
//                lights.add(new DayNightLighting.Light(wx, wy, 120f, 1f));
//            }

            if (go instanceof ArtesianMachine) {
                worldX -= 25;
                renderer.getBatch().draw(region,
                    worldX, worldY,
                    region.getRegionWidth(), 0,
                    region.getRegionWidth(), region.getRegionHeight(),
                    0.5f, 0.5f, 0);
            } else {
                renderer.getBatch().draw(region,
                    worldX, worldY,
                    region.getRegionWidth(), 0,
                    region.getRegionWidth(), region.getRegionHeight(),
                    1f, 1f, 0);
            }


        }

        //check for shop hint
        handleShopHint(renderer.getBatch());


//        //RED HIT BOXES
//        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
//        pixmap.setColor(1, 0, 0, 1);
//        pixmap.fill();
//        Texture texture = new Texture(pixmap);
//        TextureRegion redRegion = new TextureRegion(texture);
//
//        for (Tile[] row : App.getMe().getCurrentGameLocation().getTiles()) {
//            for (Tile tile : row) {
//                if (tile.isWalkable()) continue;
//                float worldX = tile.getPosition().getX() * TILE_SIZE;
//                float worldY = tile.getPosition().getY() * TILE_SIZE;
//
//                TextureRegion region = new TextureRegion(redRegion);
//                renderer.getBatch().draw(region,
//                    worldX, worldY,
//                    16,  // Origin X (مرکز تصویر)
//                    16, // Origin Y
//                    16, 16, // اندازه اصلی
//                    0.9f, 0.9f, // scaleX, scaleY
//                    0); // rotation
//
//            }
//        }
        if ((App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather() == WeatherType.Rainy || App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather() == WeatherType.Snow) && (App.getMe().getCurrentGameLocation() instanceof Farm || App.getMe().getCurrentGameLocation() instanceof Town) ){

            renderer.getBatch().end();

            renderer.getBatch().setProjectionMatrix(stage.getViewport().getCamera().combined);
            renderer.getBatch().begin();
            if (App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather() == WeatherType.Snow){
                renderer.getBatch().setColor(0f, 0.12f, 0.18f, 0.7f);
            } else {
                renderer.getBatch().setColor(0f, 0.12f, 0.18f, 0.7f);
            }
            renderer.getBatch().draw(whitePixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            renderer.getBatch().setColor(Color.WHITE);
            renderer.getBatch().end();

            renderer.getBatch().setProjectionMatrix(camera.combined);
            renderer.getBatch().begin();

            // update و render rain با دادن camera
            rainSystem.update(v, camera);
            if (App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather() == WeatherType.Snow){
                rainSystem.render(renderer.getBatch() ,true);
            } else {
                rainSystem.render(renderer.getBatch() , false);

            }
        }
        if (isThor()){
            thorSystem.update(v, camera);
            thorSystem.render(renderer.getBatch());
        }


        renderer.getBatch().end();

        transitionManager.update(v);
        transitionManager.render(shapeRenderer);


        //DEBUG
//        float y = game.getCurrentPlayer().getPixelPosition().getY();
//        float x = game.getCurrentPlayer().getPixelPosition().getX();
//        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
//        int mapWidth = layer.getWidth() * TILE_SIZE;
//        int mapHeight = layer.getHeight() * TILE_SIZE;
//
//        if (y + 182 >= mapHeight) {
//            y = mapHeight - 182;
//        }
//        if (x + 300 >= mapWidth) {
//            x = mapWidth - 300;
//        }
//
//        if (y - 150 <= 0) {
//            y = 150;
//        }
//
//        if (x - 290 <= 0) {
//            x = 290;
//        }
//
//        camera.position.set(x, y, 0);
        updateCameraPosition();
//        camera.position.set(game.getCurrentPlayer().getPosition().getX(), game.getCurrentPlayer().getPosition().getY(), 0);
        camera.zoom = 0.3f;

        stage.act(v);
        stage.draw();

        energyWindow.updateEnergyBar();

        timeWindow.updateGold();
        timeWindow.updateTime();
        if (App.getMe().getCurrentItem() != null) {
            itemLabel.setText(App.getMe().getCurrentItem().getName());
        } else {
            itemLabel.setText("");
        }

        if (App.getMe().getCurrentBuff() != null) {
            String assetName = App.getMe().getCurrentBuff().getBuffType().getAssetName();
            foodBuff = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName))));
            stage.addActor(foodBuff);
            foodBuff.setPosition(Gdx.graphics.getWidth() - 70, 735);
        }


        lighting.render(((float) App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour()), (SpriteBatch) renderer.getBatch());


        camera.update();


        //END OF GRAPHICAL RENDER
        if ((App.getMe().isFainted() || App.getMe().getEnergyUsage() > 50) && (!App.getMe().isActing())) {
            //TODO remove this for phase three
            renderer.getBatch().begin();
            renderPlayer(App.getMe());
            renderer.getBatch().end();
            GameController.manageNextTurn();
            updateMap();
        }
//        App.getMe().setFinishActing(false);
    }

    private void handleAnimalHint(Animal animal) {
        if (animal.isPetHint()) {
            if (animal.getLastPettingTime().until(LocalDateTime.now(), ChronoUnit.SECONDS) > 3) {
                animal.setPetHint(false);
            } else {
                Texture texture = new Texture(Gdx.files.internal(
                    GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Secret_Heart")
                ));
                TextureRegion region = new TextureRegion(texture);
                float x = animal.getPixelPosition().x, y = animal.getPixelPosition().y;
                float worldX = (float) ((x + 6));
                float worldY = (float) ((y + 20));
                renderer.getBatch().draw(region,
                    worldX, worldY,
                    16,  // Origin X (مرکز تصویر)
                        16, // Origin Y
                    24, 24, // اندازه اصلی
                    1f, 1f, // scaleX, scaleY
                    0); // rotation
            }
        } else if(animal.isFeedHint()) {
            if (animal.getLastFeedingTime().until(LocalDateTime.now(), ChronoUnit.SECONDS) > 3) {
                animal.setFeedHint(false);
            } else {
                Texture texture = new Texture(Gdx.files.internal(
                    GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Wheat1")
                ));
                TextureRegion region = new TextureRegion(texture);
                float x = animal.getPixelPosition().x, y = animal.getPixelPosition().y;
                float worldX = (float) ((x + 6));
                float worldY = (float) ((y + 22));
                renderer.getBatch().draw(region,
                    worldX, worldY,
                    16,  // Origin X (مرکز تصویر)
                    16, // Origin Y
                    12, 12, // اندازه اصلی
                    1f, 1f, // scaleX, scaleY
                    0); // rotation
            }
        }
    }


public void handleShopHint(Batch batch) {
    if (gameMenuInputAdapter.isShopCounterHintActive()) {
        Store store = App.getCurrentUser().getCurrentGame().findStoreByClass(
            (Class<? extends Store>) App.getMe().getCurrentGameLocation().getType().getRelatedClazz()
        );
        Texture texture = new Texture(Gdx.files.internal(
            GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Shop_Hint_Dollar")
        ));
        TextureRegion region = new TextureRegion(texture);
        float worldX = (float) ((store.getNPCposition().getX() + 0.5) * TILE_SIZE);
        float worldY = (float) ((store.getNPCposition().getY() + 0.5) * TILE_SIZE);
        renderer.getBatch().draw(region,
            worldX, worldY
        );

    }
}

public void handleNpcHint(NPC npc) {
    if (npc.isDialogReady() && npc.isMeetHint()) {
        Texture texture = new Texture(Gdx.files.internal(
            GameAssetManager.getGameAssetManager().getAssetsDictionary().get("exclamation_mark")
        ));
        TextureRegion region = new TextureRegion(texture);
        float x = npc.getPixelPosition().x, y = npc.getPixelPosition().y;
        float worldX = (float) ((x + 6));
        float worldY = (float) ((y + 31));
        renderer.getBatch().draw(region,
            worldX, worldY
        );

    }
}

private void handleMailBoxHint(MailBox mailBox) {
    if (mailBox.getHasNewMessages()) {
        Texture texture = new Texture(Gdx.files.internal(
            GameAssetManager.getGameAssetManager().getAssetsDictionary().get("exclamation_mark")
        ));
        TextureRegion region = new TextureRegion(texture);
        float x = mailBox.getPixelPosition().x, y = mailBox.getPixelPosition().y;
        float worldX = (float) ((x + 6));
        float worldY = (float) ((y + 29));
        renderer.getBatch().draw(region,
            worldX, worldY
        );

    }
}


@Override
public void resize(int i, int i1) {

}

@Override
public void pause() {

}

@Override
public void resume() {

}

@Override
public void hide() {

}

@Override
public void dispose() {
    lighting.dispose();
    if (whitePixel != null) whitePixel.dispose();
}

public FoodWindow foodWindow() {
    return foodWindow;
}

public craftingWindow getCraftingWindow() {
    return craftingWindow;
}

public InventoryWindow getInvWindow() {
    return invWindow;
}

public void setInvWindow(InventoryWindow invWindow) {
    this.invWindow = invWindow;
}

public DialogWindow getDialogWindow() {
    return dialogWindow;
}

public WarningWindow getWarningWindow() {
    return warningWindow;
}


public CheatWindow getCheatWindow() {
    return cheatWindow;
}

public Stage getStage() {
    return stage;
}


public ShopStateWindow getShopStateWindow() {
    return shopStateWindow;
}

public GameMenuInputAdapter getGameMenuInputAdapter() {
    return gameMenuInputAdapter;
}

public TimerWindow getTimerWindow() {
    return this.timeWindow;
}


public FoodWindow getFoodWindow() {
    return foodWindow;
}

public RefrigeratorWindow getRefrigeratorWindow() {
    return refrigeratorWindow;
}

public InventoryBar getInventoryBar() {
    return inventoryBar;
}


public boolean isThor() {return thor;}
public void setThor(boolean thor) {
    this.thor = thor;
}


@Override
public void onHourChanged(DateTime time, boolean newDay) {
    if (newDay) {
        updateMapWithFade(() -> {
            for (Player player : App.getCurrentUser().getCurrentGame().getPlayers()) {
                player.setCurrentGameLocation(player.getPlayerFarm().getDefaultHome().getIndoor());
            }
            App.getMe().setPosition(new Position(8, 3));
        });
    }
}

public InputMultiplexer getMultiplexer() {
    return multiplexer;
}


public ShippingBarWindow getShippingBarWindow() {
    return shippingBarWindow;
}
}
