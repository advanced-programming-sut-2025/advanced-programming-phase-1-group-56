package io.src.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.src.Main;
import io.src.controller.MenuController.MainMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.commands.MainMenuCommands;


import java.security.PrivilegedAction;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu, Screen {
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

    public MainMenu() {
    }

    private Main game;
    private SpriteBatch batch;
    private Stage stage;
    private Texture image;

    public MainMenu(Main game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        image = new Texture(Gdx.files.internal("Farm1.png"));
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(image));
        Button button = new Button(drawable);
        stage.addActor(button);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
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
    public void dispose() {
        stage.dispose();
    }
}
