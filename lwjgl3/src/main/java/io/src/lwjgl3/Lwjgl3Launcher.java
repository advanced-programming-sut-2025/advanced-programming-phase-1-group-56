package io.src.lwjgl3;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.src.StardewValley;
import io.src.model.App;
import io.src.model.Game;
import io.src.view.AppView;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    private static Lwjgl3Application createApplication() {

        return new Lwjgl3Application(new StardewValley(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("stardew valley_graphic");
        configuration.useVsync(true);
        configuration.setResizable(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
//        configuration.setFullscreenMode(displayMode);
        configuration.setWindowedMode(1920, 1080);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
