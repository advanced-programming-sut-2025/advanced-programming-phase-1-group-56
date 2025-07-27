package io.src.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.src.controller.MenuController.ProfileMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.commands.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu, Screen {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if (ProfileMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println("you are in profile menu!");
        } else if ((matcher = ProfileMenuCommands.changeEmail.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.manageChangeEmail(matcher.group(1).trim()).getMessage());
        } else if ((matcher = ProfileMenuCommands.changePassword.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.manageChangePassword(matcher.group(1).trim(), matcher.group(2)).getMessage());
        } else if ((matcher = ProfileMenuCommands.changeUserName.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.manageChangeUsername(matcher.group(1).trim()).getMessage());
        } else if ((matcher = ProfileMenuCommands.changeNickName.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.manageChangeNickName(matcher.group(1).trim()).getMessage());
        } else if (ProfileMenuCommands.showUserInformation.getMatcher(input) != null) {
            System.out.println(ProfileMenuController.UserInfo().getMessage());
        } else if (ProfileMenuCommands.Back.getMatcher(input) != null) {
            App.setCurrentMenu(Menu.mainMenu);
            System.out.println("you are in main menu now!");
        } else {
            System.out.println("invalid command bro!..");
        }
    }

    private ProfileMenuController profileMenuController;
    private Stage stage;
    private Label label;

    public ProfileMenu(ProfileMenuController controller) {
        profileMenuController = controller;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        label = new Label("", labelStyle);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
