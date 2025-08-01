package io.src.view.GameMenus;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.src.model.App;
import io.src.model.Enums.TileType;
import io.src.model.Game;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.*;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class GameView implements Screen {
    private final HashMap<String, TextureRegion> gameObjectTextureMap = new HashMap<>();
    private static final int TILE_SIZE = 16;
    private final Game game;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //    private SpriteBatch batch;
//    private TextureRegion[][] tileTextures;
//    private Map<String, TextureRegion> textures;
    private BitmapFont smallFont;
    private GlyphLayout layout = new GlyphLayout();
    private TextureAtlas playerAtlas;
    private final ArrayList<Animation<TextureRegion>> playerAnimations = new ArrayList<>();
    private float stateTime = 0f;
    private int moveDirection = 0;
    private Texture pixel; // Add this
    public Image background = new Image(new Texture(Gdx.files.internal("Farm2.png")));
    private final OrthographicCamera camera = new OrthographicCamera();
    private Stage stage;
    private TimerWindow timeWindow;
    private InventoryWindow invWindow;
    private DialogWindow dialogWindow;
    //    private final GameController gameController;
    private InputMultiplexer multiplexer = new InputMultiplexer();
    private GameMenuInputAdapter gameMenuInputAdapter;
    private EnergyBar energyWindow;


    public void updateMap() {
        this.map = new TmxMapLoader().load(App.getMe().getCurrentGameLocation().getType().getAssetName());
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        loadTextures();
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
//        this.gameController = gameController;
        this.gameMenuInputAdapter = new GameMenuInputAdapter(game);
        this.map = new TmxMapLoader().load(App.getMe().getCurrentGameLocation().getType().getAssetName());
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        loadTextures();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        invWindow = new InventoryWindow();
        energyWindow = new EnergyBar();
        timeWindow = new TimerWindow();
        energyWindow.setPosition(Gdx.graphics.getWidth() - 50, 50);
        invWindow.setVisible(false);
        stage.addActor(invWindow);
        stage.addActor(energyWindow);
        stage.addActor(timeWindow);

        InputAdapter keyListener = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.E) {
                    App.getMe().addGold(1000);
                    invWindow.setVisible(!invWindow.isVisible());
                }
                if (keycode == Input.Keys.ENTER) {
                    dialogWindow.hideDialog();
                }
                return true;
            }
        };
        multiplexer.addProcessor(gameMenuInputAdapter);
        multiplexer.addProcessor(keyListener);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);


    }

    private void loadTextures() {
//        textures = new HashMap<>();

//        for (TileDescriptionId id : TileDescriptionId.values()) {
//            String path = id.getIconPath();
//            textures.put(id.name(), new TextureRegion(new Texture(Gdx.files.internal(path))));
//        }
//        for (ItemDescriptionId id : ItemDescriptionId.values()) {
//            String path = id.getIconPath();
//            textures.put(id.name(), new TextureRegion(new Texture(Gdx.files.internal(path))));
//        }
//

        playerAtlas = new TextureAtlas(Gdx.files.internal("sprites_player.atlas"));

        for (int i = 14; i > 9; i--) {
            Array<TextureRegion> walkFrames = new Array<>();
            if (i == 14) {
                for (int j = 0; j < 4; j++) {
                    String region = "player_" + 13 + "_" + 0;
                    walkFrames.add(playerAtlas.findRegion(region));
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    String region = "player_" + i + "_" + j;
                    walkFrames.add(playerAtlas.findRegion(region));
                }
            }
            playerAnimations.add(new Animation<>(0.15f, walkFrames, Animation.PlayMode.LOOP));
        }

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        pixel = new Texture(pixmap);
        pixmap.dispose();
    }


//    public void render() {

    /// /        batch.setProjectionMatrix(camera.combined);
    /// /        batch.begin();
    /// /
    /// /        Texture texture = ((TextureRegionDrawable) background.getDrawable()).getRegion().getTexture();
    /// /        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    /// /
    /// /        renderPlayer();
    /// /
    /// /        batch.end();
//        camera.position.set(game.getCurrentPlayer().getPosition().getX(), game.getCurrentPlayer().getPosition().getY(), 0);
//        camera.zoom = 0.3f;
//
//        camera.update();
//    }


//    private void renderTiles() {
//        TileDescriptionId[][] tiles = game.getTiles();
//
//        float camX = game.getCamera().position.x;
//        float camY = game.getCamera().position.y;
//        float viewportWidth = game.getCamera().viewportWidth;
//        float viewportHeight = game.getCamera().viewportHeight;
//
//        int tileSize = StardewMini.TILE_SIZE;
//
//        float cameraLeft = camX - viewportWidth / 2;
//        float cameraBottom = camY - viewportHeight / 2;
//
//        int startX = Math.max(0, (int) (cameraLeft / tileSize) - 2);
//        int startY = Math.max(0, (int) (cameraBottom / tileSize) - 2);
//        int endX = Math.min(tiles.length, (int) ((camX + viewportWidth / 2) / tileSize) + 2);
//        int endY = Math.min(tiles[0].length, (int) ((camY + viewportHeight / 2) / tileSize) + 2);
//
//        // Render base tiles
//        for (int x = startX; x < endX; x++) {
//            for (int y = startY; y < endY; y++) {
//                TileDescriptionId id = tiles[x][y];
//                if (id != null) {
//                    float drawX = x * tileSize - cameraLeft;
//                    float drawY = y * tileSize - cameraBottom;
//
//                    GrowingCrop crop = game.getGrowingCrops().get(new Point(x, y));
//                    if (crop != null && crop.watered()) {
//                        batch.setColor(0.7f, 0.7f, 0.7f, 1f);
//                    } else {
//                        batch.setColor(1f, 1f, 1f, 1f);
//                    }
//
//                    TextureRegion texture = textures.get(id.name());
//                    if (texture != null) {
//                        batch.draw(texture, drawX, drawY, tileSize, tileSize);
//                    }
//                }
//            }
//        }
//
//        // Render crops on top
//        for (Map.Entry<Point, GrowingCrop> entry : game.getGrowingCrops().entrySet()) {
//            Point point = entry.getKey();
//            GrowingCrop crop = entry.getValue();
//
//            int x = point.x;
//            int y = point.y;
//
//            if (x >= startX && x < endX && y >= startY && y < endY) {
//                float drawX = x * tileSize - cameraLeft;
//                float drawY = y * tileSize - cameraBottom;
//
//                int growth = crop.getGrowth();
//                CarrotStages cs;
//
//                if (growth < 2) cs = CarrotStages.CARROT_STAGE_1;
//                else if (growth < 4) cs = CarrotStages.CARROT_STAGE_2;
//                else if (growth < 6) cs = CarrotStages.CARROT_STAGE_3;
//                else cs = CarrotStages.CARROT_STAGE_4;
//
//                TextureRegion cropTexture = textures.get(cs.name());
//                if (cropTexture != null) {
//                    batch.setColor(1f, 1f, 1f, 1f);
//                    batch.draw(cropTexture, drawX, drawY, tileSize, tileSize);
//                }
//            }
//        }
//
//        batch.setColor(1f, 1f, 1f, 1f);
//    }
    private void renderPlayer() {

        moveDirection = game.getCurrentPlayer().getMovingDirection();

        stateTime += Gdx.graphics.getDeltaTime();

        Animation<TextureRegion> currentAnimation = playerAnimations.get(moveDirection);
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        renderer.getBatch().draw(currentFrame, game.getCurrentPlayer().getPixelPosition().getX(), game.getCurrentPlayer().getPixelPosition().getY(), 20, 20 * 2);
//        renderInventory();
    }


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


        ArrayList<GameObject> objects = App.getMe().getCurrentGameLocation().getCopyOfGameObjects();
        Position renderingPosition = new Position((App.getMe().getPixelPosition().getX() + 16) / 16, (App.getMe().getPixelPosition().getY()) / 16);
        PlayerObject me = new PlayerObject(App.getMe().getUser().getName(), true, renderingPosition);
        objects.add(me);
        objects.sort(
            Comparator
                .comparingInt((GameObject o) -> (int) -o.getPosition().getY())
                .thenComparingInt(o -> (int) o.getPosition().getX())
        );


        for (GameObject go : objects) {
            String assetName = go.getAssetName();
            TextureRegion region;
            if (go instanceof PlayerObject) {


                renderPlayer();


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
            if(App.getMe().getCurrentGameLocation() instanceof Town){
                System.out.println(go.getClass());
                System.out.println(assetName);
                System.out.println(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName));
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


            if (go instanceof Tree || go instanceof ForagingMineral || go instanceof ForagingCrop) {

                float worldX = go.getPosition().getX() * TILE_SIZE - (float) (region.getRegionWidth() / 2);
                float worldY = go.getPosition().getY() * TILE_SIZE;


                if (go instanceof Tree tree && tree.isComplete()) {
                    worldX -= 16;
                }

                renderer.getBatch().draw(region,
                    worldX, worldY,
                    region.getRegionWidth(), 0,
                    region.getRegionWidth(), region.getRegionHeight(),
                    0.5f, 0.5f, 0);

            } else {


                float worldX = go.getPosition().getX() * TILE_SIZE;
                float worldY = go.getPosition().getY() * TILE_SIZE;
                renderer.getBatch().draw(region,
                    worldX, worldY,
                    region.getRegionWidth(), 0,
                    region.getRegionWidth(), region.getRegionHeight(),
                    1f, 1f, 0);
            }


        }


        //RED HIT BOXES
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


        //DEBUG
        float y = game.getCurrentPlayer().getPixelPosition().getY();
        float x = game.getCurrentPlayer().getPixelPosition().getX();

        camera.position.set(x, y, 0);
//        camera.position.set(game.getCurrentPlayer().getPosition().getX(), game.getCurrentPlayer().getPosition().getY(), 0);
        camera.zoom = 0.3f;


        stage.act(v);
        stage.draw();

        energyWindow.updateEnergyBar();

        timeWindow.updateGold();
        timeWindow.updateTime();


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

    public InventoryWindow getInvWindow() {
        return invWindow;
    }

    public void setInvWindow(InventoryWindow invWindow) {
        this.invWindow = invWindow;
    }

}
