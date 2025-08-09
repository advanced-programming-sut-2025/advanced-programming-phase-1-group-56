package io.src.model.Enums;

public enum SFXs {
    WindowsError(""),
    TacobellBell(""),
    Slip(""),
    Slap(""),
    Punch(""),
    MissionFailed(""),
    MinecraftOof(""),
    MinecraftHit(""),
    MinecraftEating(""),
    MinecraftAchievement(""),
    MissionPassed(""),
    FlashBack(""),
    DiscordPing(""),
    Chirping(""),
    Censor(""),
    CashRegister(""),
    AnvilLand(""),
    Click(""),
    FallSmall(""),
    Glass(""),
    Ignite(""),
    LevelUp(""),
    MouseClick(""),
    Orb(""),
    Out(""),
    Pop(""),
    Pop2(""),
    Pop1(""),
    ShutterClick(""),
    SmallBell(""),
    SmithingTable(""),
    TridentReturn(""),
    WoodClick(""),
    ;

    private final String path;

    SFXs(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
