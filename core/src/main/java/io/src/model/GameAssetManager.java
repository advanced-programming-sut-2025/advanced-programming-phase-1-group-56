package io.src.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
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
    private static final Path root_path = Paths.get("assets");
    private static final Path assetsJsonPath = Paths.get("assets\\Stardew_Valley_Images\\assets_dictionary.json");
    private static final Path atlasJsonPath = Paths.get("assets\\Stardew_Valley_Images\\atlas_dictionary.json");

    private Texture coopBackground =  new Texture("assets/coop_background.jpg");
    private Texture shippingBar = new Texture("assets/Inventory/shippingBar.png");
    private Texture refrigeratorBackground = new Texture("assets/Inventory/refrigeratorBackground.png");
    private Texture toolTipBackground = new Texture("assets/Inventory/tooltipCraftableItem.png");
    private Texture Charcoal_Klin = new Texture("assets/Stardew_Valley_Images/Craftable_item/Charcoal_Kiln.png");
    private Texture tripleShotEspresso = new Texture("assets/Stardew_Valley_Images/Recipe/Triple_Shot_Espresso.png");
    private Texture craftingBackground = new Texture("assets/Inventory/craftingBackground.png");
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
    private Texture InventoryBarBackground = new Texture("assets/Inventory/InventoryBarBackground.png");
    private Texture redSelection = new Texture("assets/Inventory/redSelection.png");

    private static final String EMOTE_DIR = "assets/Emotes/";
    private static final String EMOTE_PREFIX = "Emote";
    private static final String EMOTE_EXT = ".png";
    private static final int EMOTE_COUNT = 9;
    private static final float FRAME_DURATION = 0.2f;

    private static Animation<TextureRegion>[] emotes;
    private static final Array<Texture> emoteTextures = new Array<>();
    private static boolean emotesLoaded = false;

    private static void ensureEmotesLoaded() {
        if (emotesLoaded) return;

        emotes = (Animation<TextureRegion>[]) new Animation<?>[EMOTE_COUNT];

        for (int i = 0; i < EMOTE_COUNT; i++) {
            int index = i + 1;

            Texture frame1 = new Texture(Gdx.files.internal(
                EMOTE_DIR + EMOTE_PREFIX + index + EMOTE_EXT
            ));
            Texture frame2 = new Texture(Gdx.files.internal(
                EMOTE_DIR + EMOTE_PREFIX + index + ".1" + EMOTE_EXT
            ));

            frame1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frame2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            emoteTextures.add(frame1);
            emoteTextures.add(frame2);

            TextureRegion[] frames = new TextureRegion[] {
                new TextureRegion(frame1),
                new TextureRegion(frame2)
            };

            emotes[i] = new Animation<>(FRAME_DURATION, frames);
            emotes[i].setPlayMode(Animation.PlayMode.LOOP);
        }

        emotesLoaded = true;
    }

    public static Animation<TextureRegion> getEmote(int index1to9) {
        ensureEmotesLoaded();
        if (index1to9 < 1 || index1to9 > EMOTE_COUNT) {
            throw new IllegalArgumentException("Emote index must be 1..9");
        }
        return emotes[index1to9 - 1];
    }

    public static Animation<TextureRegion>[] getAllEmotes() {
        ensureEmotesLoaded();
        return emotes;
    }

    public static void disposeEmotes() {
        for (Texture t : emoteTextures) {
            if (t != null) t.dispose();
        }
        emoteTextures.clear();
        emotes = null;
        emotesLoaded = false;
    }














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

    public Texture getCraftingBackground() {
        return craftingBackground;
    }

    public Texture getCharcoal_Klin() {
        return Charcoal_Klin;
    }

    public Texture getToolTipBackground() {
        return toolTipBackground;
    }

    public Texture getInventoryBarBackground() {
        return InventoryBarBackground;
    }

    public Texture getRedSelection() {
        return redSelection;
    }

    public Texture getTripleShotEspresso() {
        return tripleShotEspresso;
    }

    public Texture getRefrigeratorBackground() {
        return refrigeratorBackground;
    }

    public Texture getShippingBar() {
        return shippingBar;
    }

    public Texture getCoopBackground() {
        return coopBackground;
    }
}
