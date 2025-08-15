package io.src.view.GameMenus;

// io/src/view/GameMenus/ToolSwing.java

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class ToolSwing {
    private final Animation<TextureRegion> anim;
    private final float[] baseAngles;
    private final List<Vector2> frameOffsets;
    private final Vector2 anchor;
//    private final float dirRotation;
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


        float worldX = anchor.x + frameOffsets.get(idx).x;
        float worldY = anchor.y + frameOffsets.get(idx).y;


        float originX = w / 2f;
        float originY = 0;

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

