package io.src.model;

import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
    private static final Path root_path = Paths.get("assets\\Stardew_Valley_Images");
    private static final Path jsonPath = Paths.get("assets\\Stardew_Valley_Images\\images_json.json");

    private GameAssetManager() {

        fillTheDictionary(root_path);
        //loadDictionaryFromJson();
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
                assetsDictionary.put(f.getName().replaceFirst("\\.[^.]+$", ""), f.getPath());
            }
        }
    }

    public HashMap<String, String> getAssetsDictionary() {
        return assetsDictionary;
    }

    public void loadDictionaryFromJson() {
        assetsDictionary.clear();
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(jsonPath.toFile())) {
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> loaded = gson.fromJson(reader, type);
            assetsDictionary.clear();
            assetsDictionary.putAll(loaded);
        } catch (IOException e) {
            System.err.println("Error reading JSON: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws IOException {
        GameAssetManager manager = getGameAssetManager();
        HashMap<String, String> dic = manager.getAssetsDictionary();
        for (HashMap.Entry<String, String> entry : dic.entrySet()) {
            System.out.println(entry.getKey() + "\t\t: " + entry.getValue());
        }

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        gson.toJson(dic, new FileWriter(jsonPath.toFile()));
    }
}
