package io.src.model;

import com.badlogic.gdx.Input;

public interface Clickable {
    public boolean touchDown(int screenX, int screenY, int pointer, int button);
}
