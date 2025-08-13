package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DayNightLighting {
    private final Texture gradientTexture; // یک تصویر گرادیان شفاف->آبی تیره
//    private final SpriteBatch batch;

    public DayNightLighting() {
        // گرادیان رو بساز یا از فایل لود کن
        Pixmap pm = new Pixmap(1, 256, Pixmap.Format.RGBA8888);
        for (int y = 0; y < 256; y++) {
            float t = y / 255f;
            // از آبی تیره به شفاف
            pm.setColor(0f, 0f, 0.5f, t);
            pm.drawPixel(0, y);
        }
        gradientTexture = new Texture(pm);
        pm.dispose();

//        batch = new SpriteBatch();
    }

    public void render(float gameHour, SpriteBatch batch) {
        // شدت تاریکی (0=روشن، 1=تاریک کامل)
        float darkness = calculateDarkness(gameHour);

        if (darkness > 0f) {
            batch.begin();
            batch.setColor(0.5f, 0.5f, 0.5f, 1 - darkness);
            batch.draw(gradientTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }
    }

    private float calculateDarkness(float hour) {
        // مثال: 6 صبح (روشن) -> 18 عصر (شروع تاریک شدن) -> 22 شب (تاریکی کامل)
        if (hour >= 6f && hour < 18f) return 0f; // روز کامل
        if (hour >= 18f && hour < 22f) {
            return (hour - 18f) / 4f; // از 0 تا 1
        }
        if (hour >= 22f || hour < 6f) return 1f; // شب کامل
        return 0f;
    }

    public void dispose() {
        gradientTexture.dispose();
//        batch.dispose();
    }
}

