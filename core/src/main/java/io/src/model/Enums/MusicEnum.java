package io.src.model.Enums;

public enum MusicEnum {
    AXOLOTL("StardewMusic\\axolotl.ogg"),
    CALM1("StardewMusic\\calm1.ogg"),
    CALM2("StardewMusic\\calm2.ogg"),
    CALM3("StardewMusic\\calm3.ogg"),
    DRAGON_FISH("StardewMusic\\dragon_fish.ogg"),
    FallTheme("StardewMusic\\Fall Theme 1.mp3"),
    Menu1("StardewMusic\\menu1.ogg"),
    Menu2("StardewMusic\\menu2.ogg"),
    Menu3("StardewMusic\\menu3.ogg"),
    Menu4("StardewMusic\\menu4.ogg"),
    SpringTheme("StardewMusic\\Spring Theme 1.mp3"),
    StardewValleyTheme("StardewMusic\\Stardew Valley Theme.mp3"),
    SummerTheme("StardewMusic\\Summer Theme 1.mp3"),
    WinterTheme("StardewMusic\\Winter Theme 1.mp3"),
    PIANO1("StardewMusic\\piano1.ogg"),
    PIANO2("StardewMusic\\piano2.ogg"),
    PIANO3("StardewMusic\\piano3.ogg"),
    SHUNIJI("StardewMusic\\shuniji.ogg"),;



    private final String path;

    MusicEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String toString() {
        return path;
    }
}
