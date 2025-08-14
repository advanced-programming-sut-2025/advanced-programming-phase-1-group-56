package io.src.model.Network.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.src.model.Network.DTO.GameDTO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameSaveManager {

    private static final String SAVE_DIR = "saves";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    public static void saveGame(GameDTO gameDTO, String gameId) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new IllegalArgumentException("GameId is empty!");
        }
        File file = new File(SAVE_DIR, gameId + ".json");

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(gameDTO, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static GameDTO loadGame(String gameId) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new IllegalArgumentException("GameId is empty!");
        }
        File file = new File(SAVE_DIR, gameId + ".json");

        if (!file.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, GameDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
