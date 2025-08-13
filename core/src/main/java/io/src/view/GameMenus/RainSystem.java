package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import io.src.model.GameAssetManager;

import java.util.Random;

public class RainSystem {
    private final Array<RainParticle> particles = new Array<>();
    private final Array<TextureRegion> frames;
    private Texture rainSheet;


    private float spawnRate = 40f;
    private float spawnAcc = 0f;
    private float baseWind = -20f;
    private float windVar = 10f;
    private float minVy = 100f, maxVy = 180f;
    private float minLife = 1.2f, maxLife = 2.5f;

    public RainSystem() {
        String path = GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Rain");
        rainSheet = new Texture(Gdx.files.internal(path));
        TextureRegion[][] regs = TextureRegion.split(rainSheet, 16, 16);
        frames = new Array<>();
        for (TextureRegion[] row : regs)
            for (TextureRegion r : row)
                frames.add(r);
    }

    public void update(float delta, OrthographicCamera camera) {
        spawnAcc += spawnRate * delta;
        while (spawnAcc >= 1f) {
            spawnOne(camera);
            spawnAcc -= 1f;
        }

        for (int i = particles.size - 1; i >= 0; i--) {
            RainParticle p = particles.get(i);
            p.update(delta);
            if (p.isDead()) particles.removeIndex(i);
        }
    }

    private void spawnOne(OrthographicCamera camera) {
        float halfW = (camera.viewportWidth * camera.zoom) / 2f;
        float halfH = (camera.viewportHeight * camera.zoom) / 2f;

        float left = camera.position.x - halfW;
        float right = camera.position.x + halfW;
        float top = camera.position.y + halfH;

        float x = MathUtils.random(left, right);
        float y = top + MathUtils.random(5f, 50f);

        float vy = MathUtils.random(minVy, maxVy);
        float vx = baseWind + MathUtils.random(-windVar, windVar);
        float life = MathUtils.random(minLife, maxLife);

        particles.add(new RainParticle(x, y, vx, vy, life));
    }

    public void render(Batch batch) {
        for (RainParticle p : particles) {
            TextureRegion t;
            if (!p.hit) {
                t = frames.get(0);
            } else {
                t = frames.get(3);
            }
            batch.setColor(1f, 1f, 1f, p.alpha);
            batch.draw(t, p.x - t.getRegionWidth() / 2f, p.y - t.getRegionHeight() / 2f);
        }
        batch.setColor(Color.WHITE);
    }

    public void dispose() {
        if (rainSheet != null) rainSheet.dispose();
    }
}

