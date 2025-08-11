package io.src.model.Enums;

public enum MusicEnum {
    AXOLOTL("StardewMusic\\axolotl.ogg"),
    CALM1("StardewMusic\\calm1.ogg"),
    CALM2("StardewMusic\\calm2.ogg"),
    CALM3("StardewMusic\\calm3.ogg"),
    DRAGON_FISH("StardewMusic\\dragon_fish.ogg"),
    PIANO1("StardewMusic\\piano1.ogg"),
    PIANO2("StardewMusic\\piano2.ogg"),
    PIANO3("StardewMusic\\piano3.ogg"),
    SHUNIJI("StardewMusic\\shuniji.ogg")
;

    private final String path;
    MusicEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
