package io.src.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.src.StardewValley;
import io.src.controller.MenuController.LoginMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.commands.LoginMenuCommands;
import io.src.model.SkinManager;
import io.src.model.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu, Screen {
    private static final String FILE_PATH_FOR_STAY_LOGGED = "assets\\StayLoggedIn.json";

    // fields :

    private final Stage stage;
    private final TextButton loginButton;
    private final TextButton registerButton;
    private final TextField usernameField;
    private final TextField passwordField;
    private final CheckBox stayLoggedInCheckBox;
    private final TextButton exitButton;
    private final Texture background;
    private final Window window;
    private final Window registerWindow;
    private Label warningLabel;
    private final Skin skin;

    private final int screenWidth;
    private final int screenHeight;

    // init :

    public LoginMenu() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = SkinManager.getInstance().getSkin("mainSkin/mainSkin.json");

        int textFieldWidth = 300;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        Label welcomeLabel = new Label("Welcome!", skin, "font-90_PINK");
        Label usernameLabel = new Label("Username:", skin);
        Label passwordLabel = new Label("Password:", skin);
        loginButton = new TextButton(" Login ", skin, "button1-2");
        registerButton = new TextButton(" Register ", skin);
        usernameField = new TextField("", skin);
        usernameField.setAlignment(1);
        passwordField = new TextField("", skin);
        passwordField.setAlignment(1);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        stayLoggedInCheckBox = new CheckBox(" Stay Logged in", skin);
        exitButton = new TextButton("Exit", skin);
        background = new Texture(Gdx.files.internal("background1.jpg"));
        window = new Window("", skin);
        window.setSize((float) screenWidth / 2, (float) screenHeight / 2);
        window.setPosition(((float) screenWidth / 2) - window.getWidth() / 2,
            ((float) screenHeight / 2) - window.getHeight() / 2);

        window.add(welcomeLabel);
        window.row();
        window.add(usernameLabel).padTop(15);
        window.row();
        window.add(usernameField).padTop(5).width(textFieldWidth);
        window.row();
        window.add(passwordLabel).padTop(10);
        window.row();
        window.add(passwordField).padTop(5).width(textFieldWidth);
        window.row();
        window.add(stayLoggedInCheckBox).padTop(10);
        window.row();
        window.add(loginButton).padTop(20).width(150);
        window.row();
        window.add(registerButton).padTop(10);
        window.row();
        window.add(exitButton).padTop(10);

        // Register Menu :
        registerWindow = new Window("", skin);
        registerWindow.setSize((float) screenWidth / 2, (float) screenHeight / 2);
        registerWindow.setPosition(((float) screenWidth / 2) - registerWindow.getWidth() / 2,
            ((float) screenHeight / 2) - registerWindow.getHeight() / 2);
        registerWindow.setVisible(false);

        stage.addActor(window);
        stage.addActor(registerWindow);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(1, 1, 1, 1);
        StardewValley.getBatch().begin();
        StardewValley.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        StardewValley.getBatch().end();
        stage.act(v);
        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
    }

    // getter

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public Window getRegisterWindow() {
        return registerWindow;

    }

    public Window getWindow() {
        return window;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public CheckBox getStayLoggedInCheckBox() {
        return stayLoggedInCheckBox;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    //

    public void showWarningLabel(String message) {
        if (warningLabel != null) {
            warningLabel.remove();
        }

        warningLabel = new Label(" " + message, skin, "default-GREEN_warning");
        warningLabel.setPosition(
            ((float) screenWidth / 2) - (warningLabel.getWidth() / 2),
            ((float) screenHeight / 4) - (warningLabel.getHeight() * 2)
        );
        stage.addActor(warningLabel);
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
        User tmpUser;

        if ((matcher = LoginMenuCommands.register.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageRegisterUser(matcher));
            if (LoginMenuController.manageRegisterUser(matcher).isSuccess()) {
                String input1 = scanner.nextLine();
                Matcher matcher1;
                if ((matcher1 = LoginMenuCommands.pickSecurityQuestion.getMatcher(input1)).find() && LoginMenuController.manageRegisterUser(matcher).isSuccess()) {
                    System.out.println(LoginMenuController.peakSecurityQuestion(matcher1, matcher));
                } else {
                    System.out.println("ok you don't want it!");
                }
            }
        } else if ((matcher = LoginMenuCommands.goMenu.getMatcher(input)).find()) {
            String menu = matcher.group(1).trim();
            System.out.println(LoginMenuController.goToMenu(menu));
        } else if ((matcher = LoginMenuCommands.loginWithStayLoggedin.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageLoginUser(matcher.group(1), matcher.group(2), true));
        } else if ((matcher = LoginMenuCommands.login.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageLoginUser(matcher.group(1), matcher.group(2), false));
        } else if ((matcher = LoginMenuCommands.forgetPassword.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageForgotPassword(matcher));
            if (LoginMenuController.manageForgotPassword(matcher).isSuccess()) {
                Matcher matcher1;
                String input1 = scanner.nextLine();
                if ((matcher1 = LoginMenuCommands.answerSecurityQuestion.getMatcher(input1)).find() && LoginMenuController.manageForgotPassword(matcher).isSuccess()) {
                    System.out.println(LoginMenuController.answer(matcher, matcher1));
                    String input2 = scanner.nextLine();
                    System.out.println(LoginMenuController.manageAnswerForgotPassword(input2, matcher));
                } else {
                    System.out.println("ok you don't want it!");
                }
            }

        } else if ((input.equals("menu exit"))) {
            App.setCurrentMenu(Menu.exitMenu);
            System.out.println("why do you want to exit? );  ");
        } else if ((LoginMenuCommands.ShowCurrentMenu.getMatcher(input)).find()) {
            System.out.println("you are in login menu now!");
        } else if ((LoginMenuCommands.back.getMatcher(input)).find()) {
            System.out.println("you can't go back!");
        } else {
            System.out.println("invalid command bro!..");
        }
    }
}
