package io.src.view.GameMenus;// TransitionManager.java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenTransition {
    private float alpha = 0f;
    private boolean fadingOut = false;
    private boolean fadingIn = false;
    private Runnable afterFadeOut;
    private final float fadeSpeed = 2f;


    public void start(Runnable afterFadeOut) {
        this.afterFadeOut = afterFadeOut;
        fadingOut = true;
        fadingIn = false;
    }


    public void update(float delta) {
        if (fadingOut) {
            alpha += delta * fadeSpeed;
            if (alpha >= 1f) {
                alpha = 1f;
                fadingOut = false;
                if (afterFadeOut != null) {
                    afterFadeOut.run();
                    afterFadeOut = null;
                }
                fadingIn = true;
            }
        } else if (fadingIn) {
            alpha -= delta * fadeSpeed;
            if (alpha <= 0f) {
                alpha = 0f;
                fadingIn = false;
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (alpha > 0f) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, alpha); // فقط به اندازه alpha تاریک کن
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }


    public boolean isTransitioning() {
        return fadingOut || fadingIn;
    }
}
