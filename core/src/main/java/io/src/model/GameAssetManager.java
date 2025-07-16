package io.src.model;

import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final HashMap<String,String> assetsDictionary= new HashMap<>();
    private static final Path root_path =  Paths.get(System.getProperty("assets\\Stardew_Valley_Images"));

    private GameAssetManager() {
        fillTheDictionary(root_path);
    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public void fillTheDictionary(Path root) {
        for (File f : Objects.requireNonNull(root.toFile().listFiles())) {
            if (f == null) continue;
            if (f.isDirectory()) {
                fillTheDictionary(Paths.get(f.getPath()));
            }
            if (f.isFile()) {
                assetsDictionary.put(f.getName(), f.getPath());
            }
        }
    }

    public HashMap<String, String> getAssetsDictionary() {
        return assetsDictionary;
    }

    public static void main(String[] args) {
        GameAssetManager manager =getGameAssetManager();
        HashMap<String,String> dic = manager.getAssetsDictionary();
        for (HashMap.Entry<String,String> entry : dic.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
