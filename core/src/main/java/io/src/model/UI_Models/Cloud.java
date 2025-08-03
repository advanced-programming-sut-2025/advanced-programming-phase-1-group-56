package io.src.model.UI_Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cloud {
    private final float speed;
    private final Sprite sprite;

    public Cloud(Texture texture, float speed, float x, float y) {
        this.sprite = new Sprite(texture);
        sprite.setScale(2f);
        this.speed = speed;
        sprite.setPosition(x, y);
    }

    public void update(float delta) {
        float x = sprite.getX() - speed * delta;
        if (x + (sprite.getWidth() * 2) < 0)
            x = Gdx.graphics.getWidth() + sprite.getWidth() * 2;
        sprite.setX(x);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
