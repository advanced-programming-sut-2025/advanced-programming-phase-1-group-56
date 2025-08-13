package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import io.src.model.GameAssetManager;

import java.util.List;


//public class DayNightLighting {
//    private final Texture lightMask; // دایره سفید->شفاف
//    private final Texture whitePixel; // برای پوشش تاریک
//    // رنگ پایهٔ تاریکی (آبی تیره)
//    private final Color nightTint = new Color(0f, 0.08f, 0.16f, 1f);
//
//    public static class Light {
//        public float worldX, worldY; // در واحد پیکسل (همان world pixel coords)
//        public float radius; // px
//        public float intensity; // 0..1 multiplier (unused directly, می‌توان برای alpha استفاده کرد)
//
//        public Light(float worldX, float worldY, float radius, float intensity) {
//            this.worldX = worldX; this.worldY = worldY; this.radius = radius; this.intensity = intensity;
//        }
//    }
//
//    public DayNightLighting(String lightMaskPath, Texture whitePixel) {
//        this.lightMask = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(lightMaskPath))); // e.g. "light_mask.png"
//        this.whitePixel = whitePixel; // از GameView گرفته می‌شود (1x1 white)
//    }
//
//    /**
//     * hour : ساعت بازی (مثال 0..23 یا اعشاری)
//     * batch : SpriteBatch فعلی (DayNightLighting خودش begin/end می‌کند)
//     * cam : camera فعلی برای تبدیل world -> screen
//     * lights : لیست نورها (world coordinates)
//     */
//    public void render(float hour, SpriteBatch batch, OrthographicCamera cam, List<Light> lights) {
//        float darkness = calculateDarkness(hour);
//        if (darkness <= 0f) return; // روز کامل، هیچ پرده‌ای نیاز نیست
//
//        // 1) پوشش تاریک آبی
//        batch.begin();
//        // رنگ تاریک آبی با آلفا برابر darkness
//        batch.setColor(nightTint.r, nightTint.g, nightTint.b, darkness);
//        batch.draw(whitePixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch.setColor(Color.WHITE);
//
//        // 2) اضافه کردنٔ نورها به صورت additive (روشن کردن نواحی اطراف لامپ)
//        // blend: src alpha, ONE  -> سفیدِ ماسک با آلفا بالا، ناحیه را روشن‌تر می‌کند
//        batch.setBlendFunction(com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA,
//            com.badlogic.gdx.graphics.GL20.GL_ONE);
//
//        // برای تبدیل موقعیت world -> screen
//        Vector3 tmp = new Vector3();
//        for (Light L : lights) {
//            // تبدیل world (pixel coords) به screen coords برای draw بر روی batch
//            tmp.set(L.worldX, L.worldY, 0);
//            cam.project(tmp); // tmp.x,tmp.y حالا در screen pixels (0..width, 0..height)
//            float sx = tmp.x;
//            float sy = tmp.y;
//            float r = L.radius;
//            float alpha = Math.max(0.2f, Math.min(1f, L.intensity)); // ایندکس شدت برای آلفا
//            batch.setColor(1f, 1f, 1f, alpha);
//            batch.draw(lightMask, sx - r, sy - r, r*2f, r*2f);
//        }
//
//        // بازنشانی blend
//        batch.setColor(Color.WHITE);
//        batch.setBlendFunction(com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA,
//            com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        batch.end();
//    }
//
//    private float calculateDarkness(float hour) {
//        // hour میتواند اعشاری باشد؛ خروجی 0..1
//        // روشن: 6:00  | شروع تاریکی: 18:00 | شب کامل: 22:00
//        if (hour >= 6f && hour < 18f) return 0f;
//        if (hour >= 18f && hour < 22f) {
//            return (hour - 18f) / 4f; // از 0 تا 1
//        }
//        // ساعت های 22..24 و 0..6 => شب کامل
//        return 1f;
//    }
//
//    public void dispose() {
//        lightMask.dispose();
//        // whitePixel را نباید disposal کند اگر بیرون مشترک شده (GameView نگهدارد)
//    }
//}





public class DayNightLighting {
    private final Texture whitePixel; // یک پیکسل سفید برای کشیدن فیلتر

    public DayNightLighting() {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.BLUE);
        pm.fill();
        whitePixel = new Texture(pm);
        pm.dispose();
    }

    public void render(float gameHour, SpriteBatch batch) {
        float alpha = calculateDarkness(gameHour);

        if (alpha > 0f) {
            batch.begin();
            batch.setColor(0.0f, 0f, 0.1f, alpha); // آبی تیره با شفافیت متغیر
            batch.draw(whitePixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(Color.WHITE);
            batch.end();
        }
    }

    private float calculateDarkness(float hour) {
        // روز کامل (6 تا 18)
        if (hour >= 6f && hour < 18f) return 0f;

        // غروب (18 تا 22) — تدریجی تاریک شدن
        if (hour >= 18f && hour < 22f) {
            return (hour - 18f) / 4f; // 0 تا 1
        }

        // شب کامل (22 تا 6) — تاریکی کامل
        return 1f;
    }

    public void dispose() {
        whitePixel.dispose();
    }
}

