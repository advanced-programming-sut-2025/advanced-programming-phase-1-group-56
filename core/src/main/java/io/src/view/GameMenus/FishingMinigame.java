package io.src.view.GameMenus;

// io.src.view.GameMenus.fishing.FishingMinigame.java

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.math.Vector2;
import io.src.model.App;
import io.src.model.Enums.Animals.FishBehavior;
import io.src.model.Enums.Items.FishType;
import io.src.model.Enums.Items.ItemQuality;
import io.src.model.Enums.Skills;
import io.src.model.GameAssetManager;
import io.src.model.Player;
import io.src.model.items.Fish;
import io.src.model.skills.Skill;

import java.util.function.Consumer;

public class FishingMinigame extends Group {
    private final Stage stage;
    private final Player player;
    private final FishBehavior behavior;
    private final Runnable onSuccess;
    private final Runnable onFail;

    private final ShapeRenderer sr = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();

    private final Texture panelTex;
    private final Texture bobberTex;
    private final Texture fishTex;

    private float elapsed = 0f;
    private final float duration = 10f;

    // bobber normalized position (-1 .. 1) [player-controlled]
    private float bobPos = 0f;
    private float bobVel = 0f;

    // fish normalized pos (-1 .. 1)
    private float fishPos = 0f;
    private float fishTime = 0f;

    // catch progress 0..1
    private float catchProgress = 0f;
    private final float catchSpeed;
    private final float decaySpeed;

    // bobber "half size" (in normalized units) - determines overlap window
    private final float bobHalfSize = 0.3f;


    private final float panelW = 64f;
    private final float panelH = 256f;
    private final float panelX;
    private final float panelY;

    private boolean perfectCatch = true;
    private boolean firstHover = false;

    private Fish fish;

    // fish speed limit in pixels/sec (مثلاً 20)
    private final float fishMaxPixelsPerSec = 20f;

    public FishingMinigame(Stage stage, Player player, FishBehavior behavior, Fish fish,
                           Runnable onSuccess, Runnable onFail) {
        this.stage = stage;
        this.player = player;
        this.behavior = behavior;
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        this.fish = fish;

        // load textures via GameAssetManager (do not dispose them here)
        String panelPath = GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Thumbnail");
        String bobPath   = GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Bobber");
        String fishPath;
        if (fish.isLegendary()) {
            fishPath = GameAssetManager.getGameAssetManager().getAssetsDictionary().get("ThumbnailLegendFish");
        } else {
            fishPath = GameAssetManager.getGameAssetManager().getAssetsDictionary().get("ThumbnailFish");
        }
        panelTex  = new Texture(Gdx.files.internal(panelPath));
        bobberTex = new Texture(Gdx.files.internal(bobPath));
        fishTex   = new Texture(Gdx.files.internal(fishPath));

        // tuning speeds
        this.catchSpeed = 0.5f;
        this.decaySpeed = 0.25f;

        // panel pos (center screen)
        panelX = (App.getMe().getPixelPosition().getX() - panelW);
        panelY = (App.getMe().getPixelPosition().getY() - panelH);
        setBounds(panelX, panelY, panelW, panelH);
        stage.addActor(this);

        // init positions
        fishPos = MathUtils.random(-0.6f, 0.6f);
        bobPos = 0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsed += delta;
        fishTime += delta;

        // ===== fish movement (targeted by behavior, but limited by max pixel speed) =====
        float desired = behavior.nextTarget(fishTime);
        desired = MathUtils.clamp(desired, -1f, 1f);

        // track pixel height:
        float trackH = panelH - 40f;
        float maxNormPerSec = fishMaxPixelsPerSec / trackH; // normalized/sec

        float diff = desired - fishPos;
        float maxStep = maxNormPerSec * delta;
        if (Math.abs(diff) > maxStep) fishPos += Math.signum(diff) * maxStep;
        else fishPos = desired;
        fishPos = MathUtils.clamp(fishPos, -1f, 1f);

        // ===== player control: bobber (hold to lift) =====
        boolean hold = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        if (hold) bobVel += 8f * delta;
        else      bobVel += -6f * delta;
        // damping
        bobVel *= Math.pow(0.85, delta * 60f);
        bobPos += bobVel * delta;
        bobPos = MathUtils.clamp(bobPos, -1f, 1f);

        // ===== catch progress: based on overlap between bobber box and fish =====
        float dist = Math.abs(fishPos - bobPos);
        if (dist <= bobHalfSize) {
            // overlap ratio 0..1 (1 = perfectly centered)
            firstHover = true;
            float overlap = 1f - (dist / bobHalfSize);
            catchProgress += catchSpeed * overlap * delta;
        } else {
            if (firstHover) perfectCatch = false;
            catchProgress -= decaySpeed * delta;
        }
        catchProgress = MathUtils.clamp(catchProgress, 0f, 1f);

        // success early if reaches 0.6 (60%)
        if (catchProgress == 1f) {
            if (perfectCatch && (fish.getItemQuality()== ItemQuality.Silver || fish.getItemQuality()== ItemQuality.Gold)){
                if (fish.getItemQuality()== ItemQuality.Silver){
                    fish.setItemQuality(ItemQuality.Gold);
                } else {
                    fish.setItemQuality(ItemQuality.Iridium);
                }
                System.out.println("perfect catch");
                Skill playerSkill = player.getSkillByName(Skills.Fishing.toString());
                playerSkill.setXp(playerSkill.getXp() + 7);
            }
            finish(true);
            return;
        }

        // end of time: success only if catchProgress >= 0.6
        if (elapsed >= duration) {
            finish(false);
            return;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        float trackX = panelX + 6f;
        float trackY = panelY + 10f;
        float trackW = panelW - 12f;
        float trackH = panelH - 20f;

        batch.draw(panelTex, panelX, panelY, panelW, panelH);



        // bobber (visual)
        float bobW = trackW * 0.4f;
        float bobH = trackH * (bobHalfSize);
        float bobY = trackY + (bobPos + 1f)/2f * trackH - bobH/2f;
        float bobX = trackX + (trackW - bobW) / 2f;
        batch.draw(bobberTex, bobX, bobY, bobW, bobH);

        // fish
        float fishW = 24f, fishH = 19f;
        float fishY = trackY + (fishPos + 1f)/2f * trackH - fishH/2f;
        float fishX = trackX + (trackW - fishW) / 2f;
        batch.draw(fishTex, fishX, fishY, fishW, fishH);


        batch.end();

        sr.setProjectionMatrix(batch.getProjectionMatrix()); // هماهنگ‌سازی
        sr.begin(ShapeRenderer.ShapeType.Filled);


        float progX = trackX + trackW - 4f;
        float progW = 10f;
        float progFullH = trackH;
        float progFilledH = progFullH * catchProgress;

        sr.setColor(0f, 0.6f, 0f, 1f);

        sr.rect(progX, trackY, progW, progFilledH);

        sr.end();


        batch.begin();


        font.setColor(1f,1f,1f,1f);
        font.getData().setScale(0.7f);
        font.draw(batch, "Fishing", panelX + 4f, panelY + panelH - 4f);
        font.draw(batch, String.format("%.1f / 10.0  prog: %d%%", elapsed, (int)(catchProgress*100)), panelX + 4f, panelY + 12f);


    }

    private void finish(boolean success) {
        remove();
        // don't dispose textures if they are shared / loaded from asset manager
        if (success) {
            if (onSuccess != null) onSuccess.run();
        } else {
            if (onFail != null) onFail.run();
        }
        sr.dispose();
    }

    public boolean isPerfectCatch() {
        return perfectCatch;
    }

    public void setPerfectCatch(boolean perfectCatch) {
        this.perfectCatch = perfectCatch;
    }

    public Fish getFish() {
        return fish;
    }
}








//public class FishingMinigame extends Group {
//    private final Stage stage; // stage اصلی که این گروه را به آن اضافه می‌کنی
//    private final Player player;
//    private final FishBehavior fishBehavior;
//    private final BobberActor bobber;
//    private final float duration; // مدت مینی‌گیم ثانیه
//    private float elapsed = 0f;
//    private final float perfectZoneCenter; // در دامنه pos نرمال‌شده
//    private final float perfectZoneHalfSize; // اندازه نیمه ناحیهٔ perfect
//    private final Runnable onSuccess;
//    private final Runnable onFail;
//    private final BitmapFont font;
//    private final TextureRegion panelBg;
//
//    // flags
//    private boolean active = true;
//
//    public FishingMinigame(Player player, FishBehavior behavior,
//                           Runnable onSuccess, Runnable onFail) {
//        this.stage = GameView.getStage();
//        this.player = player;
//        this.fishBehavior = behavior;
//        this.onSuccess = onSuccess;
//        this.onFail = onFail;
//
//        this.duration = 6f + (float)Math.random()*4f; // 6-10s
//        this.perfectZoneCenter = MathUtils.random(-0.4f, 0.4f);
//        this.perfectZoneHalfSize = 0.15f + MathUtils.random()*0.12f;
//
//        // UI
//        font = new BitmapFont(); // or use game font
//        panelBg = new TextureRegion(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Thumbnail")))); // optional
//
//        // bobber
//        TextureRegion bobTex = new TextureRegion(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Bobber")))); // optional
//        bobber = new BobberActor(bobTex);
//        bobber.setSize(25, 120); // bobber actor height = area height (set Y=panelY)
//        bobber.setPosition(26, 100); // temporary; adjust to panel position
//
//        // add to this group so draw order managed
//        addActor(bobber);
//
//        // place this group centered on screen
//        setSize(64, 256);
//        setPosition((Gdx.graphics.getWidth()-getWidth())/2f, (Gdx.graphics.getHeight()-getHeight())/2f);
//
//        // show on stage
//        stage.addActor(this);
//
//        Gdx.input.setInputProcessor(new InputMultiplexer(stage)); // ensure stage receives input
//    }
//
//    @Override
//    public void act(float delta) {
//        if (!active) return;
//        elapsed += delta;
//
//        // fish target movement
//        float fishTarget = fishBehavior.nextTarget(elapsed);
//        // move bobber gently towards fish target (scale target to pos range)
//        // e.g. map fishTarget [-1,1] to world pos
//        // we'll use bobber.update for physics; here we nudge bobber velocity toward fishTarget
//        float desiredPos = fishTarget; // fish decides target pos in -1..1
//        // PD controller
//        float error = desiredPos - bobber.getNormalizedPos();
//        bobber.applyLift(error * 6f * delta); // tuning: make fish stronger/weaker
//
//        // update bobber physics
//        bobber.update(delta);
//
//        // player input: "lift" when key pressed
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
//            // apply upward force (player holding button)
//            bobber.applyLift(12f * delta);
//        }
//
//        // check win/loss conditions
//        // if bobber inside perfect zone for some time -> success (we can require say 0.5s)
//        float centerDiff = Math.abs(bobber.getNormalizedPos() - perfectZoneCenter);
//        boolean inPerfect = centerDiff <= perfectZoneHalfSize;
//
//        if (inPerfect && elapsed >= 0.5f && Math.random() < 0.02) {
//            // small probabilistic chance to catch while in perfect
//            finish(true);
//        } else if (elapsed >= duration) {
//            finish(false);
//        } else if (Math.abs(bobber.getNormalizedPos()) >= 1f) {
//            // left allowed area
//            finish(false);
//        }
//
//        super.act(delta);
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        // draw background panel
//        float px = getX(), py = getY();
//        // draw panelBg if available
//        if (panelBg != null) batch.draw(panelBg, px, py, getWidth(), getHeight());
//
//        // draw zone: we implement a simple rect: green band (perfect) + grey background
//        ShapeRenderer sr = new ShapeRenderer();
//        batch.end();
//        sr.setProjectionMatrix(batch.getProjectionMatrix());
//        sr.begin(ShapeRenderer.ShapeType.Filled);
//        // background
//        sr.setColor(Color.DARK_GRAY);
//        sr.rect(px + 20, py + 10, 30, getHeight() - 20); // track rect
//        // perfect green
//        float trackBottom = py + 10;
//        float trackHeight = getHeight() - 20;
//        float greenCenterY = trackBottom + (perfectZoneCenter + 1f)/2f * trackHeight;
//        sr.setColor(Color.GREEN);
//        sr.rect(px + 20, greenCenterY - perfectZoneHalfSize*trackHeight, 30, perfectZoneHalfSize*2f*trackHeight);
//        sr.end();
//        batch.begin();
//
//        // draw bobber: convert bobber pos to group coords
//        // set bobber x in group and draw via its draw
//        // compute local y of bobber based on normalized pos:
//        float bobLocalY = (bobber.getHeight() - 16) * (bobber.getNormalizedPos() + 1f)/2f;
//        bobber.setPosition(px + 20 + 15, py + bobLocalY + 10);
//        bobber.draw(batch, parentAlpha);
//
//        // draw UI texts
//        font.draw(batch, "Fishing...", px+10, py+getHeight()-8);
//        font.draw(batch, "Hold SPACE or Left to lift", px+10, py+8);
//    }
//
//    private void finish(boolean success) {
//        active = false;
//        remove(); // remove group from stage
//        if (success) {
//            if (onSuccess != null) onSuccess.run();
//        } else {
//            if (onFail != null) onFail.run();
//        }
//    }
//}

