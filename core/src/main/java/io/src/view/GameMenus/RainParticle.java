package io.src.view.GameMenus;

public class RainParticle {
    public float x, y;
    public float vx, vy;
    public float maxLife;
    public float remainingLife;
    public boolean hit = false;
    public float alpha = 1f;

    public RainParticle(float x, float y, float vx, float vy, float life) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.maxLife = life;
        this.remainingLife = life;
    }

    public void update(float delta) {
        remainingLife -= delta;

        if (!hit) {
            x += vx * delta;
            y -= vy * delta;


            if (remainingLife <= maxLife * 0.8f) {
                hit = true;
                remainingLife = 0.25f;
                alpha = 1f;
            }
        } else {
            alpha = Math.max(0f, remainingLife / 0.25f);
        }
    }

    public boolean isDead() {
        return remainingLife <= 0f;
    }
}

