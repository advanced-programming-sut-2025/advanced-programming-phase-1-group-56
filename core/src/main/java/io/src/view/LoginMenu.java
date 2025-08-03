package io.src.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.src.StardewValley;
import io.src.controller.MenuController.LoginMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.commands.LoginMenuCommands;
import io.src.model.Result;
import io.src.model.SkinManager;
import io.src.model.UI_Models.Cloud;
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
    private final Label passLabel;
    private Label warningLabel;
    private final int screenWidth;
    private final int screenHeight;
    ArrayList<Cloud> clouds;

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

    private final Table forgetTable;
    private final TextField passField;
    private final TextField answerField;
    private final TextButton backButton;
    private final TextButton okButton;
    private final Label securityQuestion;

    // security menu :

    private final ArrayList<CheckBox> securityQuestions = new ArrayList<>();
    private final TextField securityField;
    private final TextButton signUpButton;
    private final TextButton cancelButton;

    // init :
    public LoginMenu() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = SkinManager.getInstance().getSkin("mainSkin/mainSkin.json");
        background = new Texture(Gdx.files.internal("background2.png"));
        int textFieldWidth = 300;
        int buttonWidth = 150;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        System.out.println(screenWidth + " " + screenHeight);

        // login menu

        {
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
        }

        // Register Menu :

        {
            registerTable = new Table();
            registerTable.setFillParent(true);
            registerTable.setVisible(false);
            Window registerWindow = new Window("", skin);
            registerWindow.setSize((float) screenWidth / 2, (float) screenHeight / 2);
            registerWindow.setPosition(((float) screenWidth / 2) - registerWindow.getWidth() / 2,
                ((float) screenHeight / 2) - registerWindow.getHeight() / 2);
            registerWindow.align(2);
            registerWindow.setMovable(false);
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

            registerWindow.add(welcomeLabel2).padTop(10);
            registerWindow.row();
            Table fields = new Table();
            fields.add(usernameLabel2);
            fields.add(usernameField2).width(textFieldWidth).padLeft(10);
            fields.row();
            fields.add(passwordLabel2).padTop(20);
            fields.add(passwordField2).width(textFieldWidth).padTop(20).padLeft(10);
            fields.row();
            fields.add(rePassLabel2).padTop(20);
            fields.add(rePassField).width(textFieldWidth).padTop(20).padLeft(10);
            fields.row();
            fields.add(nicknameLabel2).padTop(20);
            fields.add(nicknameField).width(textFieldWidth).padTop(20).padLeft(10);
            fields.row();
            fields.add(emailLabel2).padTop(20);
            fields.add(emailField).width(textFieldWidth).padTop(20).padLeft(10);
            fields.row();
            fields.add(maleCheckBox).padTop(20);
            fields.add(femaleCheckBox).padTop(20);
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
        }

        // forget password :

        {
            forgetTable = new Table();
            forgetTable.setFillParent(true);
            forgetTable.setVisible(false);
            Window forgetWindow = new Window("", skin);
            forgetWindow.setSize((float) screenWidth / 2, (float) screenHeight / 2);
            forgetWindow.setPosition(((float) screenWidth / 2) - forgetWindow.getWidth() / 2,
                ((float) screenHeight / 2) - forgetWindow.getHeight() / 2);
            forgetWindow.setMovable(false);
            okButton = new TextButton("OK", skin, "inventoryButtonTop-GREEN24");
            backButton = new TextButton("Back", skin, "inventoryButtonTop-RED24");
            answerField = new TextField("", skin);
            passField = new TextField("", skin);
            passField.setAlignment(1);
            passField.setVisible(false);
            passLabel = new Label("please enter new password :", skin);
            passLabel.setVisible(false);
            Label answerQuestion = new Label("answer question", skin, "font-90_PINK");
            securityQuestion = new Label("for test", skin);
            Table row = new Table();
            row.add(backButton).bottom().width(buttonWidth).padRight(forgetWindow.getWidth() / 2);
            row.add(okButton).bottom().width(buttonWidth);
            forgetTable.add(row);
            forgetTable.row();
            forgetWindow.align(2);
            forgetWindow.add(answerQuestion).padTop(10);
            forgetWindow.row();
            forgetWindow.add(securityQuestion).padTop(40);
            forgetWindow.row();
            forgetWindow.add(answerField).width(textFieldWidth * 2).padTop(10);
            forgetWindow.row();
            forgetWindow.add(passLabel).padTop(50);
            forgetWindow.row();
            forgetWindow.add(passField).width(textFieldWidth * 2).padTop(10);
            forgetWindow.row();
            forgetTable.add(forgetWindow).width(forgetWindow.getWidth()).height(forgetWindow.getHeight());

            stage.addActor(forgetTable);
        }

        // security question :

        {
            securityTable = new Table();
            securityTable.setFillParent(true);
            securityTable.setVisible(false);
            Window securityWindow = new Window("", skin);
            securityWindow.setSize((float) screenWidth / 2, (float) screenHeight / 2);
            securityWindow.setPosition(((float) screenWidth / 2) - securityWindow.getWidth() / 2,
                ((float) screenHeight / 2) - securityWindow.getHeight() / 2);
            securityWindow.setMovable(false);
            signUpButton = new TextButton("Sign Up", skin, "inventoryButtonTop-GREEN24");
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
            row7.add(signUpButton).width(buttonWidth).height(65).bottom().padLeft(securityWindow.getWidth() / 2);
            securityTable.add(row7);
            securityTable.row();
            securityTable.add(securityWindow).width(securityWindow.getWidth()).height(securityWindow.getHeight());
            stage.addActor(securityTable);
        }
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(1, 1, 1, 1);
        StardewValley.getBatch().begin();
        StardewValley.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (Cloud c : clouds) {
            c.update(v);
            c.draw(StardewValley.getBatch());
        }
        StardewValley.getBatch().end();
        stage.act(v);
        stage.draw();
    }

    @Override
    public void show() {
        clouds = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            float x = (float) (Math.random() * Gdx.graphics.getWidth());
            float y = 300 + (float) (MathUtils.random(screenHeight / 2 - 400, screenHeight / 2 - 200));
            float speed = 30 + (float) (Math.random() * 5);
            clouds.add(new Cloud(new Texture(Gdx.files.internal("cloud" + ((i % 3) + 1) + ".png")), speed, x, y));
        }
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

    public Label getPassLabel() {
        return passLabel;
    }

    public Table getForgetTable() {
        return forgetTable;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getOkButton() {
        return okButton;
    }

    public TextField getPassField() {
        return passField;
    }

    public TextField getAnswerField() {
        return answerField;
    }

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

    public void showQuestionLabel(String question) {
        securityQuestion.setText(question);
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
            Result result = LoginMenuController.manageRegisterUser(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6).equalsIgnoreCase("male"));
            System.out.println(result);
            if (result.isSuccess()) {
                String input1 = scanner.nextLine();
                Matcher matcher1;
                if ((matcher1 = LoginMenuCommands.pickSecurityQuestion.getMatcher(input1)).find()) {
                    System.out.println(LoginMenuController.peakSecurityQuestion(Integer.parseInt(matcher1.group(1)), matcher1.group(2)));
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
            Result result = LoginMenuController.manageForgotPassword(matcher.group(1));
            System.out.println(result);
            if (result.isSuccess()) {
                Matcher matcher1;
                String input1 = scanner.nextLine();
                if ((matcher1 = LoginMenuCommands.answerSecurityQuestion.getMatcher(input1)).find()) {
                    System.out.println(LoginMenuController.answer(matcher.group(1), matcher1.group(1)));
                    String input2 = scanner.nextLine();
                    System.out.println(LoginMenuController.manageAnswerForgotPassword(input2, matcher.group(1)));
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

