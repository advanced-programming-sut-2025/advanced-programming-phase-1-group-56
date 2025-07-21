package io.src.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final HashMap<String, String> assetsDictionary = new HashMap<>();
    private final HashMap<String, String> atlasDictionary = new HashMap<>();
    private static final Path root_path = Paths.get("assets\\Stardew_Valley_Images");
    private static final Path assetsJsonPath = Paths.get("assets\\Stardew_Valley_Images\\assets_dictionary.json");
    private static final Path atlasJsonPath = Paths.get("assets\\Stardew_Valley_Images\\atlas_dictionary.json");

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
            String name = f.getName();
            if (f.isFile()) {
                String key = name.replaceFirst("\\.[^.]+$", "");
                if (name.toLowerCase().contains("atlas")) {
                    atlasDictionary.put(key, f.getPath());
                } else if (name.endsWith("png") || name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("gif")) {
                    assetsDictionary.put(key, f.getPath());
                }
            }
        }
    }

    public HashMap<String, String> getAssetsDictionary() {
        return assetsDictionary;
    }

    public HashMap<String, String> getAtlasDictionary() {
        return atlasDictionary;
    }

    public void saveDictionariesToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter assetWriter = new FileWriter(assetsJsonPath.toFile());
             FileWriter atlasWriter = new FileWriter(atlasJsonPath.toFile())) {
            gson.toJson(assetsDictionary, assetWriter);
            gson.toJson(atlasDictionary, atlasWriter);
        } catch (IOException e) {
            System.err.println("Error writing JSON: " + e.getMessage());
        }
    }

    public void loadDictionariesFromJson() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        try (FileReader assetReader = new FileReader(assetsJsonPath.toFile());
             FileReader atlasReader = new FileReader(atlasJsonPath.toFile())) {
            HashMap<String, String> loadedAssets = gson.fromJson(assetReader, type);
            HashMap<String, String> loadedAtlas = gson.fromJson(atlasReader, type);
            assetsDictionary.clear();
            assetsDictionary.putAll(loadedAssets);
            atlasDictionary.clear();
            atlasDictionary.putAll(loadedAtlas);
        } catch (IOException e) {
            System.err.println("Error reading JSON: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        GameAssetManager manager = getGameAssetManager();
        manager.saveDictionariesToJson();
        manager.loadDictionariesFromJson();
    }
}
