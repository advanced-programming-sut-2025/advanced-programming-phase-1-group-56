package io.src.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import io.src.StardewValley;
import io.src.controller.MenuController.MainMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.commands.MainMenuCommands;
import io.src.model.SkinManager;


import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu, Screen {
    // fields :
    private final Stage stage;
    private final Texture image;
    private final Texture stardewValleyImage;
    private final Skin skin;

    private final Button exitButton;
    private final TextButton newButton;
    private final TextButton loadButton;
//    private final

    public MainMenu() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        image = new Texture(Gdx.files.internal("background3.png"));
        skin = SkinManager.getInstance().getSkin("mainSkin/mainSkin.json");
        stardewValleyImage = new Texture(Gdx.files.internal("StardewValley.png"));

        exitButton = new Button(skin, "ExitButton");
        float exitButtonPadding = exitButton.getWidth();
        exitButton.setPosition(Gdx.graphics.getWidth() - exitButton.getWidth() - exitButtonPadding / 2,
            Gdx.graphics.getHeight() - exitButton.getHeight() - exitButtonPadding / 2);
        stage.addActor(exitButton);

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        newButton = new TextButton("NEW", skin);
        buttonTable.add(newButton);
        loadButton = new TextButton("LOAD", skin);
        buttonTable.add(loadButton);
        stage.addActor(buttonTable);
    }

    @Override
    public void render(float v) {
        int stardewWidth = 398 * 3;
        int stardewHeight = 187 * 3;

        ScreenUtils.clear(0, 0, 0, 1);
        StardewValley.getBatch().begin();
        StardewValley.getBatch().draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        StardewValley.getBatch().draw(stardewValleyImage,
            ((float) Gdx.graphics.getWidth() / 2) - ((float) stardewWidth / 2),
            ((float) Gdx.graphics.getHeight() / 2) - ((float) stardewHeight / 2),
            stardewWidth, stardewHeight);
        StardewValley.getBatch().end();
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    //

    public Button getExitButton() {
        return exitButton;
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((MainMenuCommands.ShowCurrentMenu.getMatcher(input)).find()) {
            System.out.println("you are in Main Menu BROOOOO!");
        } else if ((matcher = MainMenuCommands.goMenu.getMatcher(input)).find()) {
            String menu = matcher.group(1).trim();
            System.out.println(MainMenuController.goToMenu(menu).message());
        } else if ((MainMenuCommands.logout.getMatcher(input)).find()) {
            System.out.println(MainMenuController.manageUserLogout().message());
        } else if ((MainMenuCommands.back.getMatcher(input)).find()) {
            App.setCurrentMenu(Menu.loginMenu);
            System.out.println("you are in login menu now!");
        } else {
            System.out.println("invalid command bro!..");
        }
    }
}
