//package io.src.view;
//
//import com.badlogic.gdx.*;
//import com.badlogic.gdx.graphics.*;
//import com.badlogic.gdx.graphics.g2d.*;
//import com.badlogic.gdx.scenes.scene2d.*;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.badlogic.gdx.video.VideoPlayer;
//import com.badlogic.gdx.video.VideoPlayerCreator;
//
//
//import java.io.FileNotFoundException;
//
//public class MainMenu1 extends ScreenAdapter {
//    enum Phase { INTRO, MENU }
//    private Phase currentPhase = Phase.INTRO;
//
//    private VideoPlayer introPlayer;
//    private VideoPlayer loopPlayer;
//    private Stage menuStage;
//    private Skin skin;
//    private SpriteBatch batch;
//
//    private boolean introFinished = false;
//
//    @Override
//    public void show() {
//        batch = new SpriteBatch();
//        createMenu();
//
//        introPlayer = VideoPlayerCreator.createVideoPlayer();
//        loopPlayer = VideoPlayerCreator.createVideoPlayer();
//
//        // وقتی intro تموم شد، بریم به MENU و loop رو اجرا کن
//        introPlayer.setOnCompletionListener((vp) -> {
//            showMenuWithLoop();
//        });
//
//        try {
//            introPlayer.play(Gdx.files.internal("video/intro.mp4"));
//        } catch (FileNotFoundException e) {
//            // اگه نبود، مستقیماً بریم منو
//            showMenuWithLoop();
//        }
//
//        // با کلیک هم بپره به منو و stop کن intro
//        Gdx.input.setInputProcessor(new InputAdapter() {
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//                if (currentPhase == Phase.INTRO && !introFinished) {
//                    introFinished = true;
//                    introPlayer.stop();
//                    showMenuWithLoop();
//                }
//                return true;
//            }
//        });
//    }
//
//    private void showMenuWithLoop() {
//        currentPhase = Phase.MENU;
//        try {
//            loopPlayer.play(Gdx.files.internal("video/loop.mp4"));
//        } catch (FileNotFoundException e) {
//            Gdx.app.log("Video", "loop.mp4 not found");
//        }
//        Gdx.input.setInputProcessor(menuStage);
//    }
//
//    private void createMenu() {
//        menuStage = new Stage(new ScreenViewport());
//        skin = new Skin(Gdx.files.internal("assets/LibGdx-Skin-main/NzSkin.json"));
//
//        Table table = new Table();
//        table.setFillParent(true);
//        table.bottom().left().pad(20);
//
//        TextButton newGame = new TextButton("New Game", skin);
//        TextButton loadGame = new TextButton("Load Game", skin);
//        TextButton options = new TextButton("Options", skin);
//        TextButton exit = new TextButton("Exit", skin);
//
//        exit.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });
//
//        table.add(newGame).pad(10).row();
//        table.add(loadGame).pad(10).row();
//        table.add(options).pad(10).row();
//        table.add(exit).pad(10);
//
//        menuStage.addActor(table);
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        if (currentPhase == Phase.INTRO) {
//            if (introPlayer != null && introPlayer.isPlaying()) {
//                introPlayer.update();
//            }
//        } else if (currentPhase == Phase.MENU) {
//            if (loopPlayer != null && !loopPlayer.isPlaying()) {
//                try {
//                    loopPlayer.play(Gdx.files.internal("video/loop.mp4")); // برای لوپ مجدد
//                } catch (FileNotFoundException ignored) {}
//            }
//            if (loopPlayer != null) loopPlayer.update();
//
//            menuStage.act(delta);
//            menuStage.draw();
//        }
//    }
//
//    @Override
//    public void dispose() {
//        if (introPlayer != null) introPlayer.dispose();
//        if (loopPlayer != null) loopPlayer.dispose();
//        if (menuStage != null) menuStage.dispose();
//        if (skin != null) skin.dispose();
//        if (batch != null) batch.dispose();
//    }
//}
