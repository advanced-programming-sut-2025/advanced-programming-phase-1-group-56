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
import io.src.model.Result;
import io.src.model.SkinManager;
import io.src.model.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu, Screen {
    // fields :
    private final Stage stage;
    private final Skin skin;
    private final Table loginTable;
    private final Table registerTable;
    private final Table securityTable;
    private Label warningLabel;
    private final int screenWidth;
    private final int screenHeight;

    // login menu
    private final TextButton loginButton;
    private final TextButton registerButton;
    private final TextField usernameField;
    private final TextField passwordField;
    private final CheckBox stayLoggedInCheckBox;
    private final TextButton exitButton;
    private final Texture background;
    private final TextButton forgetPassButton;

    private final TextButton exitButton2;
    private final TextButton registerButton2;
    private final TextButton loginButton2;
    private final TextField usernameField2;
    private final TextField passwordField2;
    private final TextField rePassField;
    private final TextField nicknameField;
    private final TextField emailField;
    private final CheckBox maleCheckBox;
    private final CheckBox femaleCheckBox;

    // forget password :

    private final ArrayList<CheckBox> securityQuestions = new ArrayList<>();
    private final TextField securityField;
    private final TextButton signUpButton;
    private final TextButton cancelButton;

    // init :
    public LoginMenu() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = SkinManager.getInstance().getSkin("mainSkin/mainSkin.json");
        background = new Texture(Gdx.files.internal("background1.jpg"));
        int textFieldWidth = 300;
        int buttonWidth = 150;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // login menu
        loginTable = new Table();
        loginTable.setFillParent(true);
        Window loginWindow = new Window("", skin);
        loginWindow.setSize((float) screenWidth / 2, (float) screenHeight / 2);
        loginWindow.setPosition(((float) screenWidth / 2) - loginWindow.getWidth() / 2,
            ((float) screenHeight / 2) - loginWindow.getHeight() / 2);
        loginWindow.setMovable(false);
        Label welcomeLabel = new Label("Welcome!", skin, "font-90_PINK");
        Label usernameLabel = new Label("Username:", skin);
        Label passwordLabel = new Label("Password:", skin);
        loginButton = new TextButton(" Log In ", skin, "inventoryButtonTop-PINK24");
        registerButton = new TextButton(" Sign Up ", skin, "inventoryButtonTop-BLACK24");
        exitButton = new TextButton("Exit", skin, "inventoryButtonTop-BLACK24");
        forgetPassButton = new TextButton("Forget Password?", skin, "default2_PINK");
        usernameField = new TextField("", skin);
        usernameField.setAlignment(1);
        passwordField = new TextField("", skin);
        passwordField.setAlignment(1);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        stayLoggedInCheckBox = new CheckBox(" Stay Logged in", skin);
        loginWindow.align(2);
        loginWindow.add(welcomeLabel).left().padTop(10);
        loginWindow.row();
        loginWindow.add(usernameLabel).padTop(40);
        loginWindow.row();
        loginWindow.add(usernameField).padTop(5).width(textFieldWidth);
        loginWindow.row();
        loginWindow.add(passwordLabel).padTop(35);
        loginWindow.row();
        loginWindow.add(passwordField).padTop(5).width(textFieldWidth);
        loginWindow.row();
        loginWindow.add(forgetPassButton).padTop(5);
        loginWindow.row();
        loginWindow.add(stayLoggedInCheckBox).padTop(35);

        Table loginButtonsTable = new Table();
        loginButtonsTable.setSize(loginTable.getWidth(), loginButtonsTable.getHeight());
        loginButtonsTable.add(registerButton).bottom().width(buttonWidth);
        loginButtonsTable.add(loginButton).width(buttonWidth).height(65).bottom().padLeft(30).padRight(30);
        loginButtonsTable.add(exitButton).bottom().width(buttonWidth);
        loginTable.add(loginButtonsTable);
        loginTable.row();
        loginTable.add(loginWindow).width(loginWindow.getWidth()).height(loginWindow.getHeight());
        stage.addActor(loginTable);

        System.out.println(registerButton.getWidth());

        // Register Menu :
        registerTable = new Table();
        registerTable.setFillParent(true);
        registerTable.setVisible(false);
        // register menu
        Window registerWindow = new Window("", skin);
        registerWindow.setSize((float) screenWidth / 2, (float) screenHeight / 2);
        registerWindow.setPosition(((float) screenWidth / 2) - registerWindow.getWidth() / 2,
            ((float) screenHeight / 2) - registerWindow.getHeight() / 2);
        exitButton2 = new TextButton("Exit", skin, "inventoryButtonTop-BLACK24");
        registerButton2 = new TextButton(" Sign Up ", skin, "inventoryButtonTop-PINK24");
        loginButton2 = new TextButton(" Log In ", skin, "inventoryButtonTop-BLACK24");
        maleCheckBox = new CheckBox(" Male", skin, "default2");
        femaleCheckBox = new CheckBox(" Female", skin, "default2");
        usernameField2 = new TextField("", skin);
        usernameField2.setAlignment(1);
        passwordField2 = new TextField("", skin);
        passwordField2.setAlignment(1);
        rePassField = new TextField("", skin);
        rePassField.setAlignment(1);
        passwordField2.setPasswordCharacter('*');
        passwordField2.setPasswordMode(true);
        nicknameField = new TextField("", skin);
        nicknameField.setAlignment(1);
        rePassField.setPasswordCharacter('*');
        rePassField.setPasswordMode(true);
        emailField = new TextField("", skin);
        emailField.setAlignment(1);
        Label welcomeLabel2 = new Label("Welcome!", skin, "font-90_PINK");
        Label usernameLabel2 = new Label("Username:", skin);
        Label passwordLabel2 = new Label("Password:", skin);
        Label rePassLabel2 = new Label("Re-Password:", skin);
        Label nicknameLabel2 = new Label("Nickname:", skin);
        Label emailLabel2 = new Label("Email:", skin);

        maleCheckBox.setChecked(true);
        femaleCheckBox.setChecked(false);

        registerWindow.add(welcomeLabel2);
        registerWindow.row();
        Table fields = new Table();
        fields.add(usernameLabel2);
        fields.add(usernameField2).width(textFieldWidth);
        fields.row();
        fields.add(passwordLabel2).padTop(10);
        fields.add(passwordField2).width(textFieldWidth).padTop(10);
        fields.row();
        fields.add(rePassLabel2).padTop(10);
        fields.add(rePassField).width(textFieldWidth).padTop(10);
        fields.row();
        fields.add(nicknameLabel2).padTop(10);
        fields.add(nicknameField).width(textFieldWidth).padTop(10);
        fields.row();
        fields.add(emailLabel2).padTop(10);
        fields.add(emailField).width(textFieldWidth).padTop(10);
        fields.row();
        fields.add(maleCheckBox).padTop(10);
        fields.add(femaleCheckBox).padTop(10);
        registerWindow.add(fields).padTop(20);
        registerWindow.row();
        Table row8 = new Table();
        row8.add(loginButton2).bottom().width(buttonWidth);
        row8.add(registerButton2).width(buttonWidth).height(65).bottom().padLeft(30).padRight(30);
        row8.add(exitButton2).bottom().width(buttonWidth);
        registerTable.add(row8);
        registerTable.row();
        registerTable.add(registerWindow).width(registerWindow.getWidth()).height(registerWindow.getHeight());
        stage.addActor(registerTable);

        // forget password :

        // security question :
        securityTable = new Table();
        securityTable.setFillParent(true);
        securityTable.setVisible(false);
        // security question :
        Window securityWindow = new Window("", skin);
        securityWindow.setSize((float) screenWidth / 2, (float) screenHeight / 2);
        securityWindow.setPosition(((float) screenWidth / 2) - securityWindow.getWidth() / 2,
            ((float) screenHeight / 2) - securityWindow.getHeight() / 2);
        signUpButton = new TextButton("Sign Up", skin,"inventoryButtonTop-GREEN24");
        cancelButton = new TextButton("Cancel", skin, "inventoryButtonTop-RED24");
        Label securityLabel = new Label("Please select a security question:", skin, "font-45_PINK");
        securityField = new TextField("", skin);
        securityWindow.add(securityLabel).padBottom(20);
        securityWindow.row();
        ArrayList<String> questions = LoginMenuController.getSecurityQuestions();
        for (String question : questions) {
            CheckBox checkBox = new CheckBox(" " + question, skin, "default2");
            checkBox.setChecked(false);
            securityQuestions.add(checkBox);
            securityWindow.add(checkBox).left().padTop(10);
            securityWindow.row();
        }
        securityQuestions.getFirst().setChecked(true);
        securityWindow.add(securityField).padTop(25).width(textFieldWidth * 2);
        securityWindow.row();

        Table row7 = new Table();
        row7.add(cancelButton).bottom().width(buttonWidth);
        row7.add(signUpButton).width(buttonWidth).height(65).bottom().padLeft(securityWindow.getWidth()/2);
        securityTable.add(row7);
        securityTable.row();
        securityTable.add(securityWindow).width(securityWindow.getWidth()).height(securityWindow.getHeight());

        stage.addActor(securityTable);
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

    public TextButton getSignUpButton() {
        return signUpButton;
    }

    public TextButton getCancelButton() {
        return cancelButton;
    }

    public TextField getSecurityField() {
        return securityField;
    }

    public ArrayList<CheckBox> getSecurityQuestions() {
        return securityQuestions;
    }

    public Table getSecurityTable() {
        return securityTable;
    }

    public TextButton getForgetPassButton() {
        return forgetPassButton;
    }

    public TextButton getLoginButton2() {
        return loginButton2;
    }

    public TextButton getRegisterButton2() {
        return registerButton2;
    }

    public TextField getUsernameField2() {
        return usernameField2;
    }

    public TextField getPasswordField2() {
        return passwordField2;
    }

    public TextField getRePassField() {
        return rePassField;
    }

    public TextField getNicknameField() {
        return nicknameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public CheckBox getmaleCheckBox() {
        return maleCheckBox;
    }

    public CheckBox getfemaleCheckBox() {
        return femaleCheckBox;
    }

    public TextButton getExitButton2() {
        return exitButton2;
    }

    public Table getRegisterTable() {
        return registerTable;
    }

    public Table getLoginTable() {
        return loginTable;
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
            ((float) screenHeight / 4) - (warningLabel.getHeight() * 3)
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
            Result result = LoginMenuController.manageRegisterUser(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
            System.out.println(result);
            if (result.isSuccess()) {
                String input1 = scanner.nextLine();
                Matcher matcher1;
                if ((matcher1 = LoginMenuCommands.pickSecurityQuestion.getMatcher(input1)).find()) {
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
