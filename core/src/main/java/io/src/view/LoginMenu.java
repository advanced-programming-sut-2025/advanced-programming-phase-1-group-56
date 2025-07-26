package io.src.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.src.controller.MenuController.LoginMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.commands.LoginMenuCommands;
import io.src.model.SkinManager;
import io.src.model.User;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu, Screen {
    private static final String FILE_PATH_FOR_STAY_LOGGED = "assets\\StayLoggedIn.json";

    public LoginMenu() {
    }

    private LoginMenuController controller;
    private Stage stage;
    private Label label;
    //    private Table table;
    private Skin skin;
    private TextButton loginButton;

    public LoginMenu(LoginMenuController controller) {
        this.controller = controller;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
//        table = new Table();
//        table.setFillParent(true);
//        table.center();
//        table.setDebug(true);
        skin = SkinManager.getInstance().getSkin1();

        label = new Label("Hello world!", skin);
//        table.add(label).pad(10);
        label.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 200);
        loginButton = new TextButton(" Login ", skin);
        loginButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 200);
//        table.add(loginButton).pad(10);

        stage.addActor(label);
        stage.addActor(loginButton);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(1, 01, 01, 1);
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
            System.out.println(LoginMenuController.manageLoginUser(matcher, true));
        } else if ((matcher = LoginMenuCommands.login.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageLoginUser(matcher, false));
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
