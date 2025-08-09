package io.src.view.GameMenus;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.src.StardewValley;
import io.src.controller.GameMenuController.GameController;
import io.src.model.*;
import io.src.model.Enums.AnimationKey;
import io.src.model.Enums.Direction;
import io.src.model.Enums.GameObjects.EtcObjectType;
import io.src.model.Enums.Menu;
import io.src.model.Enums.TileType;
import io.src.model.Enums.TileType;
import io.src.model.Game;
import io.src.model.GameObject.*;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.MapModule.Buildings.Store;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;
import io.src.view.AppMenu;
import io.src.view.GameMenus.ShopMenus.ShopStateWindow;
import io.src.view.LoginMenu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class GameView implements Screen {
    private static final int TILE_SIZE = 16;

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

    public GameView(Game game) {
        this.game = game;
        this.gameMenuInputAdapter = new GameMenuInputAdapter(game);
        this.map = new TmxMapLoader().load(App.getMe().getCurrentGameLocation().getType().getAssetName());
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        //Time Window
        timeWindow = new TimerWindow();
        stage.addActor(timeWindow);
        //Energy Window
        energyWindow = new EnergyBar();
        energyWindow.setPosition(Gdx.graphics.getWidth() - 50, 50);
        stage.addActor(energyWindow);
        //Inventory Window
        invWindow = new InventoryWindow();
        invWindow.setVisible(false);
        stage.addActor(invWindow);
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


        //Input Adapters
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(gameMenuInputAdapter);
        Gdx.input.setInputProcessor(multiplexer);

        transitionManager = new ScreenTransition();
        shapeRenderer = new ShapeRenderer();
    }

    public void updateMapWithFade(Runnable afterFadeOut) {
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(true);
        transitionManager.start(() -> {
            afterFadeOut.run(); // تغییرات position و location
            updateMap();
            gameMenuInputAdapter.setStopMoving(false);
        });
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);
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

    private void loadTextures() {
    }

    //commitTest


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
        String name = npc.getType().getName(); // مثل "grandma"
        float x = npc.getPixelPosition().getX(), y = npc.getPixelPosition().getY();

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
//        AnimationKey key = moving
//            ? AnimationKey.valueOf("WALK_" + dir.toUpperCase())
//            : AnimationKey.valueOf("IDLE_" + dir.toUpperCase());

        renderCharacter(name, key, x, y);

    }

//    private void renderNPCs(SpriteBatch batch, float deltaTime) {
//        for (NPC npc : game.getNPCs()) {
//            String id = npc.getId(); // مثل "grandma"
//            String dir = npc.getDirection();
//            boolean moving = npc.isMoving();
//            float x = npc.getX(), y = npc.getY();
//
//            AnimationKey key = moving
//                ? AnimationKey.valueOf("WALK_" + dir.toUpperCase())
//                : AnimationKey.valueOf("IDLE_" + dir.toUpperCase());
//
//            renderCharacter(batch, deltaTime, id, key, x, y);
//        }
//    }

//    private void renderPlayer() {
//
//        stateTime += Gdx.graphics.getDeltaTime();
//        Player p = game.getCurrentPlayer();
//        AnimationKey key;
//        if (p.isMoving()) {
//            switch (p.getLastDirection()) {
//                case UP:
//                    key = AnimationKey.WALK_UP;
//                    break;
//                case DOWN:
//                    key = AnimationKey.WALK_DOWN;
//                    break;
//                case LEFT:
//                    key = AnimationKey.WALK_LEFT;
//                    break;
//                default:
//                    key = AnimationKey.WALK_RIGHT;
//                    break;
//            }
//        } else {
//            switch (p.getLastDirection()) {
//                case UP:
//                    key = AnimationKey.IDLE_UP;
//                    break;
//                case DOWN:
//                    key = AnimationKey.IDLE_DOWN;
//                    break;
//                case LEFT:
//                    key = AnimationKey.IDLE_LEFT;
//                    break;
//                default:
//                    key = AnimationKey.IDLE_RIGHT;
//                    break;
//            }
//        }
//        Animation<TextureRegion> anim = animMgr.get(key);
//        TextureRegion frame = anim.getKeyFrame(stateTime, true);
//        renderer.getBatch().draw(
//            frame,
//            p.getPixelPosition().getX(), p.getPixelPosition().getY(),
//            20, 40);
//

    /// /        moveDirection = game.getCurrentPlayer().getMovingDirection();
    /// /
    /// /        stateTime += Gdx.graphics.getDeltaTime();
    /// /
    /// /        Animation<TextureRegion> currentAnimation = playerAnimations.get(moveDirection);
    /// /        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
    /// /
    /// /        renderer.getBatch().draw(currentFrame, game.getCurrentPlayer().getPixelPosition().getX(), game.getCurrentPlayer().getPixelPosition().getY(), 20, 20 * 2);
    /// /        renderInventory();
//    }


//    private void renderInventory() {
//        Player player = game.getPlayer();
//        Map<ItemDescriptionId, Pair<Integer, Integer>> inventory = player.getInventory();
//        int selectedSlot = player.getSelectedSlot(); // Assuming you have this method
//
//        int screenWidth = Gdx.graphics.getWidth();
//        int slotSize = StardewMini.TILE_SIZE /2;
//        int numSlots = player.getMaxInventorySize();
//        int startX = (screenWidth - numSlots * slotSize) / 2;
//        int y = StardewMini.TILE_SIZE /2;
//
//        for (int i = 0; i < numSlots; i++) {
//            int x = startX + i * slotSize;
//
//            batch.draw(textures.get(TileDescriptionId.SLOT.name()), x, y, slotSize, slotSize);
//
//            String slotNum = String.valueOf(i + 1);
//            smallFont.draw(batch, slotNum, x + 2, y + slotSize - 2);
//        }
//
//        // Highlight selected slot
//        if (selectedSlot >= 0 && selectedSlot < numSlots) {
//            int highlightX = startX + selectedSlot * slotSize;
//            batch.draw(textures.get(TileDescriptionId.HIGHLIGHT.name()), highlightX, y, slotSize, slotSize);
//        }
//
//        for (Map.Entry<ItemDescriptionId, Pair<Integer, Integer>> entry : inventory.entrySet()) {
//            ItemDescriptionId id = entry.getKey();
//            int quantity = entry.getValue().first;
//            int index = entry.getValue().second;
//
//            if (index < 0 || index >= numSlots) continue;
//
//            TextureRegion itemTex = textures.get(id.name());
//            if (itemTex != null) {
//                int x = startX + index * slotSize;
//                batch.draw(itemTex, x, y, slotSize, slotSize);
//
//                // Draw item quantity at bottom-right corner
//                String count = String.valueOf(quantity);
//                layout.setText(smallFont, count);
//                smallFont.draw(batch, count, x + slotSize - layout.width - 2, y + layout.height + 2);
//            }
//        }
//    }

//    public Batch getBatch() {
//        return batch;
//    }
    public Texture getPixel() {
        return pixel;
    }

    public void renderWarningDialog() {
        warningWindow.setRemainingTime(warningWindow.getRemainingTime() - 1);
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

        renderer.getBatch().begin();
        renderer.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


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
                renderWarningDialog();


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

//            if (App.getMe().getCurrentGameLocation() instanceof Town){
//                System.out.println(assetName + "'  asset :'" + GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName)
//                    + "'location: " + worldX + "   " + worldY);
//            }

            if ((go instanceof Tree tree && tree.isComplete()) || go instanceof EtcObject && (((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE1 ||
                ((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE2 || ((EtcObject) go).getEtcObjectType() == EtcObjectType.VANITY_TREE3)) {
                worldX -= 16;
            }

            if (go instanceof EtcObject && ((EtcObject) go).getEtcObjectType() == EtcObjectType.PINKFU_TREE) {
                worldX -= 24;
            }

            renderer.getBatch().draw(region,
                worldX, worldY,
                region.getRegionWidth(), 0,
                region.getRegionWidth(), region.getRegionHeight(),
                1f, 1f, 0);
        }


        //RED HIT BOXES


        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion redRegion = new TextureRegion(texture);

        for (Tile[] row : App.getMe().getCurrentGameLocation().getTiles()) {
            for (Tile tile : row) {
                if (tile.getTileType() != TileType.Wrapper) continue;
                float worldX = tile.getPosition().getX() * TILE_SIZE;
                float worldY = tile.getPosition().getY() * TILE_SIZE;

                TextureRegion region = new TextureRegion(redRegion);
                renderer.getBatch().draw(region,
                    worldX, worldY,
                    16,  // Origin X (مرکز تصویر)
                    16, // Origin Y
                    16, 16, // اندازه اصلی
                    0.3f, 0.3f, // scaleX, scaleY
                    0); // rotation

            }
        }

        //check for shop hint
        handleShopHint(renderer.getBatch());

        renderer.getBatch().end();

        transitionManager.update(v);
        transitionManager.render(shapeRenderer);


        //DEBUG
        float y = game.getCurrentPlayer().getPixelPosition().getY();
        float x = game.getCurrentPlayer().getPixelPosition().getX();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int mapWidth = layer.getWidth() * TILE_SIZE;
        int mapHeight = layer.getHeight() * TILE_SIZE;

        if (y + 182 >= mapHeight) {
            y = mapHeight - 182;
        }
        if (x + 300 >= mapWidth) {
            x = mapWidth - 300;
        }

        if (y - 150 <= 0) {
            y = 150;
        }

        if (x - 290 <= 0) {
            x = 290;
        }

        camera.position.set(x, y, 0);
//        camera.position.set(game.getCurrentPlayer().getPosition().getX(), game.getCurrentPlayer().getPosition().getY(), 0);
        camera.zoom = 0.3f;

        stage.act(v);
        stage.draw();

        energyWindow.updateEnergyBar();

        timeWindow.updateGold();
        timeWindow.updateTime();


        camera.update();


        //END OF GRAPHICAL RENDER
        if (App.getMe().isFainted() || App.getMe().getEnergyUsage() > 50) {
            //TODO remove this for phase three
            GameController.manageNextTurn();
            updateMap();
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
}
