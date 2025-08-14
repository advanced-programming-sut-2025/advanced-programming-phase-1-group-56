package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.google.gson.Gson;
import io.src.model.App;
import io.src.model.Enums.Direction;
import io.src.model.GameAssetManager;
import io.src.model.Network.Client.LobbyClient;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;
import io.src.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class EmoteWindow extends Group implements InputProcessor {

    private final Group group;
    private final ArrayList<Integer> all = App.getMe().getEmotes();

    public EmoteWindow() {
        group = new Group();
        addActor(group);
        buildEmoteTable();
    }

    private void buildEmoteTable() {
        float[][] offsets = new float[][]{
            {-70, 10},
            {50, 0},
            {0, 70},
            {-50, -70},
            {20, -70}
        };

        float baseX = Gdx.graphics.getWidth() - 120;
        float baseY = Gdx.graphics.getHeight() - 420;


        int count = Math.min(all.size(), 5);

        for (int i = 0; i < count; i++) {
            int emoteId = all.get(i);
            Animation<TextureRegion> animation = GameAssetManager.getEmote(emoteId);

            AnimatedImage icon = new AnimatedImage(animation);
            Stack stack = new Stack();
            stack.setSize(60, 84);
            stack.add(icon);

            final int selectedEmoteId = emoteId;

            stack.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    stack.setScale(2f);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    stack.setScale(1f);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gson gson = new Gson();
                    App.getMe().setShowEmote(selectedEmoteId);
                    EmoteWindow.this.setVisible(false);
                    System.out.println(App.getMe().getShowEmote());
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("commandType", NetworkCommand.emote);
                    body.put("username", App.getMe().getUserName());
                    body.put("emoteId", selectedEmoteId);
                    Message msg = new Message(body, Message.Type.command);
                    LobbyClient.getClient().send(gson.toJson(msg));
                    Gdx.input.setInputProcessor(GameView.getGameMenuInputAdapter());
                    return true;
                }
            });


            if (i < offsets.length) {
                float px = baseX + offsets[i][0];
                float py = baseY + offsets[i][1];
                stack.setPosition(px, py);
            }

            group.addActor(stack);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.Y) {
            if(GameView.emoteWindow().isVisible()) {
                Gdx.input.setInputProcessor(GameView.getGameMenuInputAdapter());
            }
            GameView.emoteWindow().setVisible(!GameView.emoteWindow().isVisible());
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            showAllEmotes();
            return true;
        }
        return false;
    }

    private void showAllEmotes() {
        Group allEmotesGroup = new Group();
        float startX = 50;
        float startY = Gdx.graphics.getHeight() - 100;
        int col = 0;
        for (int emoteId = 1; emoteId <= 9; emoteId++) {
            Animation<TextureRegion> anim = GameAssetManager.getEmote(emoteId);
            Image icon = new Image(anim.getKeyFrame(0)) {
                float stateTime = 0f;
                @Override
                public void act(float delta) {
                    super.act(delta);
                    stateTime += delta;
                    setDrawable(new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(
                        anim.getKeyFrame(stateTime, true)
                    ));
                }
            };
            icon.setSize(48, 48);

            final int selectedEmoteId = emoteId;
            icon.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float tx, float ty, int pointer, int btn) {
                    if (btn == Input.Buttons.LEFT) {
                        showSlotInputDialog(selectedEmoteId, allEmotesGroup);
                    }
                    return true;
                }
            });
            icon.setPosition(startX + col * 60, startY);
            allEmotesGroup.addActor(icon);

            col++;
            if (col >= 8) {
                col = 0;
                startY -= 60;
            }
        }
        addActor(allEmotesGroup);
    }


    private void showSlotInputDialog(int selectedEmoteId, Group allEmotesGroup) {
        com.badlogic.gdx.scenes.scene2d.ui.Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        com.badlogic.gdx.scenes.scene2d.ui.Dialog dialog = new com.badlogic.gdx.scenes.scene2d.ui.Dialog("Set Emote Slot", skin);

        com.badlogic.gdx.scenes.scene2d.ui.TextField textField = new com.badlogic.gdx.scenes.scene2d.ui.TextField("", skin);
        dialog.getContentTable().add("");
        dialog.getContentTable().row();
        dialog.getContentTable().add(textField).width(100);

        TextButton okButton = new TextButton("OK", skin);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int slot;
                try {
                    slot = Integer.parseInt(textField.getText().trim());
                } catch (NumberFormatException e) {
                    slot = -1;
                }
                if (slot >= 1 && slot <= 5) {
                    Gson gson = new Gson();
                    all.set(slot - 1, selectedEmoteId);
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("commandType", NetworkCommand.updateEmote);
                    body.put("username", App.getMe().getUserName());
                    body.put("allEmote", all);
                    Message msg = new Message(body, Message.Type.command);
                    LobbyClient.getClient().send(gson.toJson(msg));
                    buildEmoteTable();
                    dialog.hide();
                    allEmotesGroup.remove();
                } else {
                    textField.setText("");
                }
            }
        });

        dialog.getButtonTable().add(okButton).pad(6);
        dialog.button("Cancel", null);

        dialog.show(getStage());
    }



    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private static class AnimatedImage extends Image {
        private final Animation<TextureRegion> animation;
        private float stateTime = 0f;

        public AnimatedImage(Animation<TextureRegion> animation) {
            super(animation.getKeyFrame(0));
            this.animation = animation;
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            stateTime += delta;
            setDrawable(new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(animation.getKeyFrame(stateTime, true)));
        }
    }
}
