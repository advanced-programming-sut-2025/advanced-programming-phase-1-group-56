package io.src.view.GameMenus;

// io.src.view.GameMenus.fishing.BobberActor.java
//package io.src.view.GameMenus.fishing;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class BobberActor extends Actor {
    private final TextureRegion region; // تصویر bobber اگر داری
    private float pos; // مقدار نرمال‌شده در بازه [-1..1] نشان‌دهنده‌ی موقعیت داخل نوار (مثلاً -1 پایین, +1 بالا)
    private float velocity = 0f;

    public BobberActor(TextureRegion region) {
        this.region = region;
        this.pos = 0f;
        setSize(region != null ? region.getRegionWidth() : 8, region != null ? region.getRegionHeight() : 8);
    }

    /** کنترل بالا بردن: مقدار مثبت به velocity اضافه کن */
    public void applyLift(float power) {
        velocity += power;
    }

    /** physics ساده برای bobber */
    public void update(float delta) {
        // گرانش آرام (bobber ثبات ندارد)
        velocity += -6f * delta; // gravity-like pull down (تنظیم کن)
        // اصطکاک
        velocity *= Math.pow(0.85, delta*60f); // damping per second

        // تغییر pos با velocity
        pos += velocity * delta; // velocity باید واحد مناسب داشته باشه؛ تنظیم با تست

        // clamp
        if (pos > 1f) { pos = 1f; velocity = 0f; }
        if (pos < -1f) { pos = -1f; velocity = 0f; }
    }

    /** نرمال‌شده بر حسب پیکسل: تبدیل pos (-1..1) به y داخل نوار (x ثابت) */
    public float getNormalizedPos() { return pos; }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // اگر TextureRegion داری draw کن، یا fallback به مربع
        if (region != null) {
            batch.draw(region, getX() - getWidth()/2f, getY() + (pos+1f)/2f * getHeight() - getHeight()/2f, getWidth(), getHeight());
        } else {
            // draw a simple rectangle using NinePatch or ShapeRenderer (ShapeRenderer must be separate)
        }
    }
}
