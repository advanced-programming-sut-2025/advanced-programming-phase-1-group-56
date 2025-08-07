package io.src.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.src.model.items.Inventory;

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
    private static final Path root_path = Paths.get("assets");
    private static final Path assetsJsonPath = Paths.get("assets\\Stardew_Valley_Images\\assets_dictionary.json");
    private static final Path atlasJsonPath = Paths.get("assets\\Stardew_Valley_Images\\atlas_dictionary.json");


    //Inventory
    private Texture InventoryBackGround = new Texture("assets/Inventory/InventoryBackground.png");
    private Texture closeTab = new Texture("assets/Inventory/exit.png");
    private Texture skillBackground = new Texture("assets/Inventory/skillBackGround.png");
    private Texture mapBackground = new Texture("assets/Inventory/miniMap.png");
    private String inventoryIcon = "assets/Inventory/Inventory.png";
    private String skillIcon = "assets/Inventory/skills.png";
    private String mapIcon ="assets/Inventory/map.png";
    private String socialIcon =  "assets/Inventory/social.png";
    private String settingIcon = "assets/Inventory/setting.png";
    private String outIcon = "assets/Inventory/out.png";
    private Texture emptySlot = new Texture("assets/Inventory/slot.png");
    private Texture lockSlot = new Texture("assets/Inventory/lockSlot.png");
    private Texture emptySmallSkillBar = new Texture("assets/Inventory/SkillBar1.png");
    private Texture filledSmallSkillBar = new Texture("assets/Inventory/SkillBar1.1.png");
    private Texture emptyBigSkillBar = new Texture("assets/Inventory/SkillBar2.png");
    private Texture filledBigSkillBar = new Texture("assets/Inventory/SkillBar2.1.png");
    private Texture exitBackground = new Texture("assets/Inventory/exitMenu.png");
    private Texture exitMenuButtonUp = new Texture("assets/Inventory/exit to menu.png");
    private Texture exitMenuButtonDown = new Texture("assets/Inventory/exit to menu down.png");
    private Texture exitToDesktopUp  = new Texture("assets/Inventory/exit to desktop.png");
    private Texture exitToDesktopDown = new Texture("assets/Inventory/exit to desktop down.png");
    private Texture trashcan1 = new Texture("assets/Inventory/1.png");
    private Texture trashcan2 = new Texture("assets/Inventory/2.png");
    private Texture trashcan3 = new Texture("assets/Inventory/3.png");
    private Texture trashcan4 = new Texture("assets/Inventory/4.png");
    private Texture trashcan5 = new Texture("assets/Inventory/5.png");
    private Skin skin = new Skin(Gdx.files.internal("assets/Export/menu_Skin_v0.0.1.json"));
    private Texture farmingHover = new Texture("assets/Inventory/farmingHover.png");
    private Texture miningHover = new Texture("assets/Inventory/miningHover.png");
    private Texture fishingHover = new Texture("assets/Inventory/fishingHover.png");
    private Texture foragingHover = new Texture("assets/Inventory/ForagingHover.png");
    private Texture tmpBackground = new Texture("assets/Inventory/tmpBackground.png");
    private Texture energyBar = new Texture("assets/Inventory/energyBar.png");
    private Texture clock = new Texture("assets/Inventory/clock.png");
    private Texture clockCursor = new Texture("assets/Inventory/clockCursor.png");
    private Texture greenBar = new Texture("assets/Inventory/greenBar.png");
    private GameAssetManager() {
        fillTheDictionary(root_path);
    }

    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
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

//    public static void main(String[] args) throws IOException {
//        GameAssetManager manager = getGameAssetManager();
//        manager.saveDictionariesToJson();
//        manager.loadDictionariesFromJson();
//    }

    public String getOutIcon() {
        return outIcon;
    }

    public String getSocialIcon() {
        return socialIcon;
    }

    public Texture getInventoryBackGround() {
        return InventoryBackGround;
    }

    public String getInventoryIcon() {
        return inventoryIcon;
    }

    public String getSkillIcon() {
        return skillIcon;
    }

    public String getMapIcon() {
        return mapIcon;
    }

    public String getSettingIcon() {
        return settingIcon;
    }

    public Texture getSkillBackground() {
        return skillBackground;
    }

    public Texture getMapBackground() {
        return mapBackground;
    }

    public Texture getCloseTab() {
        return closeTab;
    }

    public Texture getLockSlot() {
        return lockSlot;
    }

    public Texture getEmptySlot() {
        return emptySlot;
    }

    public Texture getExitBackground() {
        return exitBackground;
    }


    public Texture getFilledBigSkillBar() {
        return filledBigSkillBar;
    }

    public Texture getEmptyBigSkillBar() {
        return emptyBigSkillBar;
    }

    public Texture getFilledSmallSkillBar() {
        return filledSmallSkillBar;
    }

    public Texture getEmptySmallSkillBar() {
        return emptySmallSkillBar;
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getExitToDesktopDown() {
        return exitToDesktopDown;
    }

    public Texture getExitToDesktopUp() {
        return exitToDesktopUp;
    }

    public Texture getExitMenuButtonDown() {
        return exitMenuButtonDown;
    }

    public Texture getExitMenuButtonUp() {
        return exitMenuButtonUp;
    }

    public Texture getTrashcan5() {
        return trashcan5;
    }

    public Texture getTrashcan4() {
        return trashcan4;
    }

    public Texture getTrashcan2() {
        return trashcan2;
    }

    public Texture getTrashcan3() {
        return trashcan3;
    }

    public Texture getTrashcan1() {
        return trashcan1;
    }

    public Texture getFarmingHover() {
        return farmingHover;
    }

    public Texture getForagingHover() {
        return foragingHover;
    }

    public Texture getFishingHover() {
        return fishingHover;
    }

    public Texture getMiningHover() {
        return miningHover;
    }

    public Texture getTmpBackground() {
        return tmpBackground;
    }

    public Texture getEnergyBar() {
        return energyBar;
    }

    public Texture getClockCursor() {
        return clockCursor;
    }

    public Texture getClock() {
        return clock;
    }

    public Texture getGreenBar() {
        return greenBar;
    }
}
