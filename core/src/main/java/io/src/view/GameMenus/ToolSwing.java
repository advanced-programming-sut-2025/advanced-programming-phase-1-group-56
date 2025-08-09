package io.src.view.GameMenus;

// io/src/view/GameMenus/ToolSwing.java

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class ToolSwing {
    private final Animation<TextureRegion> anim;   // base animation (right-facing frames)
    private final float[] baseAngles;              // زاویه پایه برای هر فریم (مثلاً {-60,0,60})
    private final List<Vector2> frameOffsets;     // آفست‌های هر فریم (world pixels) نسبت به anchor
    private final Vector2 anchor;                  // موقعیت world (پیکسل) که انیمیشن حول آن قرار می‌گیرد (مثلاً موقعیت پلیر)
//    private final float dirRotation;               // rotation offset بر حسب جهت (Right=0, Up=-90, Left=180, Down=90)
    private float stateTime = 0f;
    private final float scale;
    private final Runnable onComplete;
    private boolean completed = false;

    public ToolSwing(Animation<TextureRegion> anim,
                     float[] baseAngles,
                     List<Vector2> frameOffsets,
                     Vector2 anchor,
                     float scale,
                     Runnable onComplete) {
        this.anim = anim;
        this.baseAngles = baseAngles;
        this.frameOffsets = frameOffsets;
        this.anchor = anchor;
//        this.dirRotation = dirRotation;
        this.scale = scale;
        this.onComplete = onComplete;
    }

    /** update; return true if finished (should be removed) */
//    public boolean update(float delta) {
//        stateTime += delta;
//        return anim.isAnimationFinished(stateTime);
//    }

    public boolean update(float delta) {
        stateTime += delta;
        if (anim.isAnimationFinished(stateTime)) {
            if (!completed) {
                completed = true;
                // اجرا کردن callback (روی همان ترد رندر)
                if (onComplete != null) {
                    try {
                        onComplete.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void draw(Batch batch) {
        TextureRegion frame = anim.getKeyFrame(stateTime, false);
        int idx = anim.getKeyFrameIndex(stateTime);
        if (idx < 0) idx = 0;
        if (idx >= frameOffsets.size()) idx = 0;

        float w = frame.getRegionWidth();
        float h = frame.getRegionHeight();

        float angle = baseAngles[idx];

        // محاسبه موقعیت رسم: anchor (world) + offset(frame) -> پایین-چپ را بدست می‌آوریم
        float worldX = anchor.x + frameOffsets.get(idx).x;
        float worldY = anchor.y + frameOffsets.get(idx).y;

        // origin (pivot) — انتخاب مرکز فریم یا هر نقطه‌ای که می‌خواهی (مثال: مرکز)
        float originX = w / 2f;
        float originY = 0;

        // batch.draw expects bottom-left; بنابراین فاصله تا مرکز را کم می‌کنیم
        float drawX = worldX - originX * scale;
        float drawY = worldY - originY * scale;

        batch.draw(frame,
            drawX, drawY,
            originX * scale, originY * scale,
            w, h,
            scale, scale,
            angle);
    }
}

