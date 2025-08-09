package io.src.view.GameMenus;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.src.model.App;
import io.src.model.Enums.AnimationKey;
import io.src.model.Enums.Direction;
import io.src.model.Enums.GameObjects.EtcObjectType;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.Enums.TileType;
import io.src.model.Game;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.*;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;
import io.src.model.Player;
import io.src.model.items.Tool;
import io.src.model.items.Etc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class GameView implements Screen {
    private final HashMap<String, TextureRegion> gameObjectTextureMap = new HashMap<>();
    public static final int TILE_SIZE = 16;
    private final Game game;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private BitmapFont smallFont;
    private GlyphLayout layout = new GlyphLayout();
    private TextureAtlas playerAtlas;
    private final ArrayList<Animation<TextureRegion>> playerAnimations = new ArrayList<>();
    private AnimationManager animationManager = new AnimationManager();
    private float stateTime = 0f;
    private final ObjectMap<String, Float> stateTimeMap = new ObjectMap<>();
    private int moveDirection = 0;
    private Texture pixel; // Add this
    public Image background = new Image(new Texture(Gdx.files.internal("gameLocations\\Farm2.png")));
    private final OrthographicCamera camera = new OrthographicCamera();
    private static Stage stage;
    private TimerWindow timeWindow;
    private static InventoryWindow invWindow;
    private DialogWindow dialogWindow;
    private InputMultiplexer multiplexer = new InputMultiplexer();
    private static GameMenuInputAdapter gameMenuInputAdapter;
    private EnergyBar energyWindow;
    private ScreenTransition transitionManager;
    private ShapeRenderer shapeRenderer;
    private final ArrayList<ToolSwing> activeToolSwings = new ArrayList<>();
    private static craftingWindow craftingWindow;
    private static InventoryBar inventoryBar;
    private static Label itemLabel;
    private static FoodWindow foodWindow;
    private static RefrigeratorWindow refrigeratorWindow;
    private Image foodBuff;


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

        this.gameMenuInputAdapter = new GameMenuInputAdapter(game);

        multiplexer.addProcessor(gameMenuInputAdapter);
//        multiplexer.addProcessor(keyListener);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        transitionManager = new ScreenTransition();
        shapeRenderer = new ShapeRenderer();

        setCustomCursor("assets/Cursor.png", 0, 0);

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

    private void renderPlayer() {
        Player player = App.getMe();
        float x = player.getPixelPosition().getX(), y = player.getPixelPosition().getY();
        AnimationKey key;
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

    private void updateCameraPosition() {
        Player player = App.getMe();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        float mapWidthPixels = layer.getWidth() * TILE_SIZE;
        float mapHeightPixels = layer.getHeight() * TILE_SIZE;

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float cameraX, cameraY;

        // X محور
        if (mapWidthPixels <= screenWidth*0.3) {
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
        if (mapHeightPixels <= screenHeight*0.3) {
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

    public void spawnToolSwing(Tool tool, Direction dir , Runnable onComplete) {
        if (tool == null) return;

        String toolName = tool.getName();
        String toolMaterial = tool.getToolType().getToolMaterial().toString();
        String toolId = toolName+toolMaterial;

        Animation<TextureRegion> baseAnim = animationManager.get(toolId, AnimationKey.valueOf(toolName.toUpperCase() + "_SWING_" + dir.toString()));
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
                baseAngles = new float[]{10, -50, -100};
                offsets = List.of(
                    new Vector2(8, 24),
                    new Vector2(12, 20),
                    new Vector2(12, 10)
                );
            }
            case UP    -> {
                baseAngles = new float[]{0};
                offsets = List.of(
                    new Vector2(8, 24),
                    new Vector2(0, 20),
                    new Vector2(0, 12)
                );
            }
            case LEFT  -> {
                baseAngles = new float[]{-10, 50, 100};
                offsets = List.of(
                    new Vector2(8, 24),
                    new Vector2(4, 20),
                    new Vector2(3, 10)
                );
            }
            case DOWN  -> {
                baseAngles = new float[]{0, 0};
                offsets = List.of(
                    new Vector2(0, 20),
                    new Vector2(8, 0),
                    new Vector2(0, 16)
                );
            }
            default    -> {
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


//    public Stage getStage() {
//        return stage;
//    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public Texture getPixel() {
        return pixel;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        camera.update();
//        timeWindow.update(LocalTime.now());
        renderer.setView(camera);
        gameMenuInputAdapter.update(v);
        renderer.render();

        renderer.getBatch().begin();
//        renderPlayer();

        //render tile type plowed soil
        for (Tile[] tileLine : App.getMe().getCurrentGameLocation().getTiles()) {
            for (Tile tile : tileLine) {
                if (tile.getTileType()==TileType.PlowedSoil){
                    Texture texture = new Texture(Gdx.files.internal(
                        GameAssetManager.getGameAssetManager().getAssetsDictionary().get(tile.getTileType().toString())
                    ));
                    TextureRegion region = new TextureRegion(texture);
                    renderer.getBatch().draw(region , tile.getPosition().getX() , tile.getPosition().getY());
                }
            }
        }

        ArrayList<GameObject> objects = App.getMe().getCurrentGameLocation().getCopyOfGameObjects();
        Position renderingPosition = new Position((App.getMe().getPixelPosition().getX() + 16) / 16, (App.getMe().getPixelPosition().getY()) / 16);
        PlayerObject me = new PlayerObject(App.getMe().getUser().getName(), true, renderingPosition);
        objects.add(me);
        objects.sort(
            Comparator
                .comparingDouble((GameObject o) -> -o.getPosition().getY())
                .thenComparingInt(o -> (int) o.getPosition().getX())
//                .thenComparingDouble()
        );


        for (GameObject go : objects) {
            String assetName = go.getAssetName();
            TextureRegion region;
            if (go instanceof PlayerObject) {


                renderPlayer();

                updateAndDrawToolSwings(v);

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
//                System.out.println(npc.getPosition().getX() + " " + npc.getPosition().getY());
                continue;
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

            if ((go instanceof Tree tree && tree.isComplete()) || go instanceof EtcObject && (((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE1 ||
                ((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE2 || ((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE3)) {
                worldX -= 16;
            }

            if (go instanceof EtcObject && ((EtcObject) go).getEtcObjectType() == EtcObjectType.PINKFU_TREE) {
                worldX -= 24;
            }

            if(go instanceof ArtesianMachine || go instanceof EtcObject){
                worldX-=25;
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


        camera.update();
    }


    public void onPlayerTalk(String npcName, String dialogText) {
        dialogWindow.showDialog(npcName, dialogText);
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

    }

    public static FoodWindow foodWindow() {
        return foodWindow;
    }

    public static craftingWindow getCraftingWindow() {
        return craftingWindow;
    }

    public static InventoryWindow getInvWindow() {
        return invWindow;
    }

    public void setInvWindow(InventoryWindow invWindow) {
        this.invWindow = invWindow;
    }

    public static Stage getStage() {
        return stage;
    }

    public static GameMenuInputAdapter getGameMenuInputAdapter() {
        return gameMenuInputAdapter;
    }

    public static InventoryBar getInventoryBar() {
        return inventoryBar;
    }

    public static RefrigeratorWindow getRefrigeratorWindow() {
        return refrigeratorWindow;
    }

}
