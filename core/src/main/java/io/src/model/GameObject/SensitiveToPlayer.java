package io.src.model.GameObject;

public interface SensitiveToPlayer {
    boolean onPlayerGoesNearby(float distance);
    boolean onPlayerGetsFar(float distance);
    boolean onPlayerFocus();
    boolean onPlayerDefocus();
    float getSensitivityDistance();

}
