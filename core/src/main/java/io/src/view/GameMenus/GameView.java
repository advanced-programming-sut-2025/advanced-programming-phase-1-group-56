package io.src.view.GameMenus;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import io.src.model.App;
import io.src.model.Game;
import io.src.model.Player;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Map;

public class GameView {
    private final Game game;
    private final SpriteBatch batch;
    //    private TextureRegion[][] tileTextures;
    private Map<String, TextureRegion> textures;
    private BitmapFont smallFont;
    private final GlyphLayout layout = new GlyphLayout();
    private TextureAtlas playerAtlas;
    private final ArrayList<Animation<TextureRegion>> playerAnimations = new ArrayList<>();
    private float stateTime = 0f;
    private int moveDirection = 0;
    private Texture pixel; // Add this
    public Image background = new Image(new Texture(Gdx.files.internal("Farm2.png")));
    private final OrthographicCamera camera = new OrthographicCamera();


    private void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("StardewValley.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        smallFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public GameView(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        loadTextures();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        loadFont();
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
//        for (CarrotStages cs : CarrotStages.values()) {
//            String path = cs.getIconPath();
//            textures.put(cs.name(), new TextureRegion(new Texture(Gdx.files.internal(path))));
//        }

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


    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Texture texture = ((TextureRegionDrawable) background.getDrawable()).getRegion().getTexture();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        renderPlayer();

        batch.end();
        float y = game.getCurrentPlayer().getPosition().getY();
        float x = game.getCurrentPlayer().getPosition().getX();
        if (game.getCurrentPlayer().getPosition().getY() + 150 >= Gdx.graphics.getHeight()) {
            y = Gdx.graphics.getHeight() - 150;
        }
        if (game.getCurrentPlayer().getPosition().getX() + 300 >= Gdx.graphics.getWidth()) {
            x = Gdx.graphics.getWidth() - 300;
        }

        if (game.getCurrentPlayer().getPosition().getY() - 200 <= 0) {
            y = 200;
        }

        if (game.getCurrentPlayer().getPosition().getX() - 240 <= 0) {
            x = 240;
        }
        camera.position.set(x, y, 0);

        camera.zoom = 0.25f;

        camera.update();
    }


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
        System.out.println();
        moveDirection = game.getCurrentPlayer().getMovingDirection();

        stateTime += Gdx.graphics.getDeltaTime();

        Animation<TextureRegion> currentAnimation = playerAnimations.get(moveDirection);
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame, game.getCurrentPlayer().getPosition().getX(), game.getCurrentPlayer().getPosition().getY(), 20, 20 * 2);
//        renderInventory();
    }


//    private void renderInventory() {
//        Player player = game.getCurrentPlayer();
//        int selectedSlot = player.getSelectedSlot(); // Assuming you have this method
//
//        int screenWidth = Gdx.graphics.getWidth();
//        int slotSize = 16 /2;
//        int numSlots = player.getCurrentBackpack().getCapacity();
//        int startX = (screenWidth - numSlots * slotSize) / 2;
//        int y = 16/2;
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

    public Batch getBatch() {
        return batch;
    }


    public Texture getPixel() {
        return pixel;
    }

}
