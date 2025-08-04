package io.src.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import io.src.StardewValley;
import io.src.controller.MenuController.MainMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.commands.MainMenuCommands;
import io.src.model.SkinManager;
import io.src.model.UI_Models.Cloud;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu, Screen {
    // fields :
    private final Stage stage;
    private final Texture image;
    private final Texture stardewValleyImage;
    private final ScrollPane aboutScrollPane;
    private ArrayList<Cloud> clouds;
    private final Skin skin;

    private final Button logoutButton;
    private final Button exitButton;
    private final Button newButton;
    private final Button loadButton;
    private final Button coopButton;
    private final Button aboutButton;

    private final Button back_about_Button;
    private final Window aboutWindow;

    public MainMenu() {
        // essential
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        image = new Texture(Gdx.files.internal("background3.png"));
        skin = SkinManager.getInstance().getSkin("mainSkin/mainSkin.json");
        stardewValleyImage = new Texture(Gdx.files.internal("StardewValley.png"));

        // exitButton

        Table buttonTable = new Table();
        newButton = new Button(skin, "newButton");
        loadButton = new Button(skin, "loadButton");
        coopButton = new Button(skin, "co-opButton");
        logoutButton = new Button(skin, "exitButton");

        buttonTable.add(newButton).width(newButton.getWidth()).height(newButton.getHeight()).pad(5);
        buttonTable.add(loadButton).width(loadButton.getWidth()).height(loadButton.getHeight()).pad(5);
        buttonTable.add(coopButton).width(coopButton.getWidth()).height(coopButton.getHeight()).pad(5);
        buttonTable.add(logoutButton).width(logoutButton.getWidth()).height(logoutButton.getHeight()).pad(5);
        buttonTable.setPosition((float) Gdx.graphics.getWidth() / 2,
            newButton.getHeight() - 75);
        stage.addActor(buttonTable);

        // about button
        aboutButton = new Button(skin, "aboutButton");
        aboutButton.setPosition((float) (Gdx.graphics.getWidth() - (aboutButton.getWidth() * 1.5)),
            buttonTable.getY() - newButton.getHeight() / 2);
        stage.addActor(aboutButton);

        // exit button
        exitButton = new Button(skin, "ExitButton");
        exitButton.setPosition(Gdx.graphics.getWidth() - exitButton.getWidth() - 20,
            Gdx.graphics.getHeight() - exitButton.getHeight() - 20);
        stage.addActor(exitButton);

        // about
        aboutWindow = new Window("", skin, "default2");
        aboutWindow.setVisible(false);
        aboutWindow.setSize((float) ((float) Gdx.graphics.getWidth() / 1.2), (float) ((float) Gdx.graphics.getHeight() / 1.5));
        aboutWindow.setPosition(((float) Gdx.graphics.getWidth() / 2) - (aboutWindow.getWidth() / 2), ((float) Gdx.graphics.getHeight() / 2) - (aboutWindow.getHeight() / 2));
        back_about_Button = new Button(skin, "backButton");
        back_about_Button.setPosition((float) ((float) Gdx.graphics.getWidth() - back_about_Button.getWidth() * 1.2), back_about_Button.getHeight() / 4);
        back_about_Button.setVisible(false);
        Table aboutTable = new Table();
        aboutTable.setSize(aboutWindow.getWidth(), aboutWindow.getHeight());
        Label aboutLabel = new Label("\n\n\n\nStardew Valley created by :\n\n", skin, "default-PURPLE");
        Label aboutLabel2 = new Label("Group-56\n\n", skin, "font2-45_WHITE");
        Label aboutText = new Label("\nGroup Members:\n\n\n    -Nima Nazary\n\n    -Mohsen Zare\n\n    -Mahdi Ashiyani\n\n    -Mohammad Amin Zeinalian\n\n", skin, "default-WHITE");
        aboutText.setWrap(true);
        Texture logoNoBackground = new Texture(Gdx.files.internal("Logo No Background.png"));
        Image logo = new Image(logoNoBackground);
        aboutTable.add(logo).width(logo.getWidth()*3).height(logo.getHeight()*3).pad(5);
        aboutTable.row();
        aboutTable.add(aboutLabel).pad(5);
        aboutTable.row();
        aboutTable.add(aboutLabel2).pad(5);
        aboutTable.row();
        aboutTable.add(aboutText).width(logo.getWidth()*2).pad(5);
        aboutScrollPane = new ScrollPane(aboutTable, skin, "default2");
        aboutScrollPane.setFadeScrollBars(false);
        aboutScrollPane.setScrollingDisabled(true, false);
        aboutScrollPane.setSize(aboutWindow.getWidth(), aboutWindow.getHeight());
        aboutWindow.add(aboutScrollPane).pad(5);
        aboutWindow.setModal(false);
        aboutWindow.setMovable(false);
        stage.addActor(aboutWindow);
        back_about_Button.toFront();
        stage.addActor(back_about_Button);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        StardewValley.getBatch().begin();
        StardewValley.getBatch().draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (Cloud c : clouds) {
            c.update(v);
            c.draw(StardewValley.getBatch());
        }
        StardewValley.getBatch().draw(stardewValleyImage,
            ((float) Gdx.graphics.getWidth() / 2) - (float) (398 * 3) / 2,
            ((float) Gdx.graphics.getHeight() / 2) - (float) 187 / 2,
            (float) (398 * 3),
            (float) (187 * 3));
        StardewValley.getBatch().end();
        stage.act(v);
        back_about_Button.toFront();
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

    public ScrollPane getScrollPane() {
        return aboutScrollPane;
    }

    public Stage getStage() {
        return stage;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Button getNewButton() {
        return newButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }

    public Button getCoopButton() {
        return coopButton;
    }

    public Button getAboutButton() {
        return aboutButton;
    }

    public Table getAboutWindow() {
        return aboutWindow;
    }

    public Button getBack_about_Button() {
        return back_about_Button;
    }

    @Override
    public void show() {
        clouds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            float x = (float) (Math.random() * Gdx.graphics.getWidth());
            float y = 300 + (float) (MathUtils.random(0, Gdx.graphics.getHeight() - 400));
            float speed = 10 + (float) (Math.random() * 50);
            clouds.add(new Cloud(new Texture(Gdx.files.internal("cloud" + ((i % 3) + 1) + ".png")), speed, x, y));
        }
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
