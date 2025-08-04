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

        characterAnimations.put("player", loadCharacterAnimations(playerAtlas, "player"));

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

    public Animation<TextureRegion> get(String characterId, AnimationKey key) {
        return characterAnimations.get(characterId).get(key);
    }

}
