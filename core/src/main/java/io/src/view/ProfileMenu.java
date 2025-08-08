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
import io.src.model.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu, Screen {
    private Stage stage;
    private Label label;

    public ProfileMenu() {
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

    @Override
    public Result check(Scanner scanner, String cmd) {
        Matcher matcher;
        if (ProfileMenuCommands.ShowCurrentMenu.getMatcher(cmd) != null) {
            return new Result(true,"you are in profile menu!");
        } else if ((matcher = ProfileMenuCommands.changeEmail.getMatcher(cmd)) != null) {
            return ProfileMenuController.manageChangeEmail(matcher.group(1).trim());
        } else if ((matcher = ProfileMenuCommands.changePassword.getMatcher(cmd)) != null) {
            return ProfileMenuController.manageChangePassword(matcher.group(1).trim(), matcher.group(2));
        } else if ((matcher = ProfileMenuCommands.changeUserName.getMatcher(cmd)) != null) {
            return ProfileMenuController.manageChangeUsername(matcher.group(1).trim());
        } else if ((matcher = ProfileMenuCommands.changeNickName.getMatcher(cmd)) != null) {
            return ProfileMenuController.manageChangeNickName(matcher.group(1).trim());
        } else if (ProfileMenuCommands.showUserInformation.getMatcher(cmd) != null) {
            return ProfileMenuController.UserInfo();
        } else if (ProfileMenuCommands.Back.getMatcher(cmd) != null) {
            App.setCurrentMenu(Menu.mainMenu);
            return new Result(true, "you are now in Main Menu");
        } else {
            return new Result(false, "invalid command");
        }
    }

    public ProfileMenu(ProfileMenuController controller){

    }
}
