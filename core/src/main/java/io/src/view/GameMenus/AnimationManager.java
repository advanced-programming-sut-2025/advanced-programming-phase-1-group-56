package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import io.src.model.Enums.AnimationKey;
import io.src.model.GameAssetManager;

import java.util.Map;

public class AnimationManager {
//    private final ObjectMap<AnimationKey, Animation<TextureRegion>> animations = new ObjectMap<>();
//
//    public AnimationManager(TextureAtlas atlas) {
//        loadDirection(atlas, "up");
//        loadDirection(atlas, "down");
//        loadDirection(atlas, "left");
//        loadDirection(atlas, "right");
//    }
//
////    private void loadDirection(TextureAtlas atlas, String dirName) {
////        // WALK_<DIR>
////        Array<TextureAtlas.AtlasRegion> walkFrames = atlas.findRegions("player_" + dirName);
////        animations.put(AnimationKey.valueOf("WALK_" + dirName.toUpperCase()),
////            new Animation<>(0.15f, walkFrames, Animation.PlayMode.LOOP));
////        // IDLE_<DIR> (یک فریم اول یا جداگانه)
////        TextureRegion idleFrame = atlas.findRegion("player_" + dirName + "_0");
////        animations.put(AnimationKey.valueOf("IDLE_" + dirName.toUpperCase()),
////            new Animation<>(1f, new Array<>(new TextureRegion[]{ idleFrame }), Animation.PlayMode.NORMAL));
////    }
//
//    private void loadDirection(TextureAtlas atlas, String dirName) {
//        Array<TextureRegion> walkFrames = new Array<>();
//        for (int i = 0; i < 4; i++) { // فرض بر اینکه 4 فریم داری
//            TextureRegion frame = atlas.findRegion("player_" + dirName + "_" + i);
//            if (frame == null) {
//                System.out.println("Missing frame: player_" + dirName + "_" + i);
//            } else {
//                walkFrames.add(frame);
//            }
//        }
//
//        animations.put(AnimationKey.valueOf("WALK_" + dirName.toUpperCase()),
//            new Animation<>(0.15f, walkFrames, Animation.PlayMode.LOOP));
//
//        TextureRegion idleFrame = atlas.findRegion("player_" + dirName + "_0");
//        animations.put(AnimationKey.valueOf("IDLE_" + dirName.toUpperCase()),
//            new Animation<>(1f, new Array<>(new TextureRegion[]{ idleFrame }), Animation.PlayMode.NORMAL));
//    }
//
//    public Animation<TextureRegion> get(AnimationKey key) {
//        return animations.get(key);
//    }

    private final ObjectMap<String, ObjectMap<AnimationKey, Animation<TextureRegion>>> characterAnimations = new ObjectMap<>();

    public AnimationManager() {
        // Load player
//        TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("atlases/sprites_player.atlas"));
        TextureAtlas playerAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("sprites_player"));
        TextureAtlas AlexAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("Alex"));
        TextureAtlas ElliotAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("Elliot"));
        TextureAtlas HaleyAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("Haley"));
        TextureAtlas SebastianAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("Sebastian"));
        TextureAtlas RobinAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("Robin"));
        System.out.println(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("axe2"));
        TextureAtlas Axe1Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("axe1"));
        TextureAtlas Axe2Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("axe2"));
        TextureAtlas Axe3Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("axe3"));
        TextureAtlas Axe4Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("axe4"));
        TextureAtlas Axe5Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("axe5"));
        TextureAtlas Hoe1Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("hoe1"));
        TextureAtlas Hoe2Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("hoe2"));
        TextureAtlas Hoe3Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("hoe3"));
        TextureAtlas Hoe4Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("hoe4"));
        TextureAtlas Hoe5Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("hoe5"));
        TextureAtlas Pickaxe1Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("pickaxe1"));
        TextureAtlas Pickaxe2Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("pickaxe2"));
        TextureAtlas Pickaxe3Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("pickaxe3"));
        TextureAtlas Pickaxe4Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("pickaxe4"));
        TextureAtlas Pickaxe5Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("pickaxe5"));
        TextureAtlas WateringCan1Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("wateringCan1"));
        TextureAtlas WateringCan2Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("wateringCan2"));
        TextureAtlas WateringCan3Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("wateringCan3"));
        TextureAtlas WateringCan4Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("wateringCan4"));
        TextureAtlas WateringCan5Atlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("wateringCan5"));



//        TextureAtlas playerAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("sprites_player"));
//        TextureAtlas playerAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("sprites_player"));
//        TextureAtlas playerAtlas = new TextureAtlas(GameAssetManager.getGameAssetManager().getAtlasDictionary().get("sprites_player"));


        characterAnimations.put("player", loadCharacterAnimations(playerAtlas, "player"));
        characterAnimations.put("AxeWooden" , loadToolSwingAnimations(Axe1Atlas , "Axe"));
        characterAnimations.put("AxeCupric" , loadToolSwingAnimations(Axe2Atlas , "Axe"));
        characterAnimations.put("AxeIronic" , loadToolSwingAnimations(Axe3Atlas , "Axe"));
        characterAnimations.put("AxeGolden" , loadToolSwingAnimations(Axe4Atlas , "Axe"));
        characterAnimations.put("AxeIridium" , loadToolSwingAnimations(Axe5Atlas , "Axe"));
        characterAnimations.put("HoeWooden" , loadToolSwingAnimations(Hoe1Atlas , "Hoe"));
        characterAnimations.put("HoeCupric" , loadToolSwingAnimations(Hoe2Atlas , "Hoe"));
        characterAnimations.put("HoeIronic" , loadToolSwingAnimations(Hoe3Atlas , "Hoe"));
        characterAnimations.put("HoeGolden" , loadToolSwingAnimations(Hoe4Atlas , "Hoe"));
        characterAnimations.put("HoeIridium" , loadToolSwingAnimations(Hoe5Atlas , "Hoe"));
        characterAnimations.put("PickaxeWooden" , loadToolSwingAnimations(Pickaxe1Atlas , "Pickaxe"));
        characterAnimations.put("PickaxeCupric" , loadToolSwingAnimations(Pickaxe2Atlas , "Pickaxe"));
        characterAnimations.put("PickaxeIronic" , loadToolSwingAnimations(Pickaxe3Atlas , "Pickaxe"));
        characterAnimations.put("PickaxeGolden" , loadToolSwingAnimations(Pickaxe4Atlas , "Pickaxe"));
        characterAnimations.put("PickaxeIridium" , loadToolSwingAnimations(Pickaxe5Atlas , "Pickaxe"));
        characterAnimations.put("WateringCanWooden" , loadToolSwingAnimations(WateringCan1Atlas , "WateringCan"));
        characterAnimations.put("WateringCanCupric" , loadToolSwingAnimations(WateringCan2Atlas , "WateringCan"));
        characterAnimations.put("WateringCanIronic" , loadToolSwingAnimations(WateringCan3Atlas , "WateringCan"));
        characterAnimations.put("WateringCanGolden" , loadToolSwingAnimations(WateringCan4Atlas , "WateringCan"));
        characterAnimations.put("WateringCanIridium" , loadToolSwingAnimations(WateringCan5Atlas , "WateringCan"));


        characterAnimations.put("Alex", loadCharacterAnimationsNumeric(AlexAtlas));
        characterAnimations.put("Elliot", loadCharacterAnimationsNumeric(ElliotAtlas));
        characterAnimations.put("Haley", loadCharacterAnimationsNumeric(HaleyAtlas));
        characterAnimations.put("Sebastian", loadCharacterAnimationsNumeric(SebastianAtlas));
        characterAnimations.put("Robin", loadCharacterAnimationsNumeric(RobinAtlas));




        // Load grandma NPC
//        TextureAtlas grandmaAtlas = new TextureAtlas(Gdx.files.internal("atlases/grandma.atlas"));
//        characterAnimations.put("grandma", loadCharacterAnimations(grandmaAtlas, "grandma"));
    }

    private ObjectMap<AnimationKey, Animation<TextureRegion>> loadCharacterAnimations(TextureAtlas atlas, String prefix) {
        ObjectMap<AnimationKey, Animation<TextureRegion>> map = new ObjectMap<>();
        for (String dirName : new String[]{"up", "down", "left", "right"}) {
            Array<TextureRegion> walkFrames = new Array<>();
            for (int i = 0; i < 4; i++) {
                TextureRegion frame = atlas.findRegion(prefix + "_" + dirName + "_" + i);
                if (frame != null) {
                    walkFrames.add(frame);
                }
            }
            map.put(AnimationKey.valueOf("WALK_" + dirName.toUpperCase()), new Animation<>(0.15f, walkFrames, Animation.PlayMode.LOOP));

            TextureRegion idleFrame = atlas.findRegion(prefix + "_" + dirName + "_0");
            map.put(AnimationKey.valueOf("IDLE_" + dirName.toUpperCase()), new Animation<>(1f, new Array<>(new TextureRegion[]{ idleFrame }), Animation.PlayMode.NORMAL));
        }
        return map;
    }

    private ObjectMap<AnimationKey, Animation<TextureRegion>> loadCharacterAnimationsNumeric(TextureAtlas atlas) {
        ObjectMap<AnimationKey, Animation<TextureRegion>> map = new ObjectMap<>();

        // ۱۶ فریم را از داخل atlas بگیریم
        Array<TextureAtlas.AtlasRegion> allRegions = atlas.getRegions();
        // مرتب‌سازی بر اساس فیلد index (باید از قبل در .atlas تنظیم شده باشد)
        allRegions.sort((a, b) -> Integer.compare(a.index, b.index));

        // بررسی تضمینی: اگر کمتر از 16 فریم داریم، ارور بده
        if (allRegions.size < 16) {
            Gdx.app.error("AnimationManager", "Expected ≥16 regions but found " + allRegions.size);
            return map;
        }

        // تقسیم‌بندی ۴ در ۴:
        // [0..3]  → Down
        // [4..7]  → Right
        // [8..11] → Up
        // [12..15]→ Left
        Array<TextureRegion> down = new Array<>();
        Array<TextureRegion> right = new Array<>();
        Array<TextureRegion> up = new Array<>();
        Array<TextureRegion> left = new Array<>();

        for (int i = 0; i < 16; i++) {
            TextureRegion r = allRegions.get(i);
            if (i < 4)          down.add(r);
            else if (i < 8)     right.add(r);
            else if (i < 12)    up.add(r);
            else                left.add(r);
        }

        // بسازیم AnimationKey و Animation ها را
        map.put(AnimationKey.WALK_DOWN,  new Animation<>(0.15f, down,  Animation.PlayMode.LOOP));
        map.put(AnimationKey.WALK_RIGHT, new Animation<>(0.15f, right, Animation.PlayMode.LOOP));
        map.put(AnimationKey.WALK_UP,    new Animation<>(0.15f, up,    Animation.PlayMode.LOOP));
        map.put(AnimationKey.WALK_LEFT,  new Animation<>(0.15f, left,  Animation.PlayMode.LOOP));

        // برای حالت IDLE هم معمولاً از اولین فریم هر جهت استفاده می‌کنیم:
        map.put(AnimationKey.IDLE_DOWN,  new Animation<>(1f, new Array<>(new TextureRegion[]{ down.first()  }), Animation.PlayMode.NORMAL));
        map.put(AnimationKey.IDLE_RIGHT, new Animation<>(1f, new Array<>(new TextureRegion[]{ right.first() }), Animation.PlayMode.NORMAL));
        map.put(AnimationKey.IDLE_UP,    new Animation<>(1f, new Array<>(new TextureRegion[]{ up.first()    }), Animation.PlayMode.NORMAL));
        map.put(AnimationKey.IDLE_LEFT,  new Animation<>(1f, new Array<>(new TextureRegion[]{ left.first()  }), Animation.PlayMode.NORMAL));

        return map;
    }

    public ObjectMap<AnimationKey, Animation<TextureRegion>> loadToolSwingAnimations(TextureAtlas atlas, String tool) {
        ObjectMap<AnimationKey, Animation<TextureRegion>> map = new ObjectMap<>();
        String[] dirs1 = {"Left", "Right", "Up", "Down"};

        for (String dir : dirs1) {
            Array<TextureRegion> frames = new Array<>();
            if (dir.equals("Up")) {
                for (int i = 0; i < 1; i++) {
                    String regionName = tool + "_" + dir + "_Frame_" + i;
                    TextureRegion fr = atlas.findRegion(regionName);
                    if (fr != null) frames.add(fr);
                    else Gdx.app.error("AnimationManager", "Missing frame: " + regionName);
                }
            } else if (dir.equals("Down")) {
                for (int i = 0; i < 2; i++) {
                    String regionName = tool + "_" + dir + "_Frame_" + i;
                    TextureRegion fr = atlas.findRegion(regionName);
                    if (fr != null) frames.add(fr);
                    else Gdx.app.error("AnimationManager", "Missing frame: " + regionName);
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    String regionName = tool + "_" + dir + "_Frame_" + i;
                    TextureRegion fr = atlas.findRegion(regionName);
                    if (fr != null) frames.add(fr);
                    else Gdx.app.error("AnimationManager", "Missing frame: " + regionName);
                }
            }
            Animation<TextureRegion> anim = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);
            AnimationKey key = AnimationKey.valueOf((tool + "_SWING_" + dir).toUpperCase());
            map.put(key, anim);
        }
        return map;
    }


    public Animation<TextureRegion> get(String characterId, AnimationKey key) {
        return characterAnimations.get(characterId).get(key);
    }

}
