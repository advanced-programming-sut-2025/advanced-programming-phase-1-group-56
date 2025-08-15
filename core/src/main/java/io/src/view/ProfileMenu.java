package io.src.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.src.controller.MenuController.MainMenuController;
import io.src.controller.MenuController.ProfileMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.SfxEnum;
import io.src.model.Enums.commands.ProfileMenuCommands;
import io.src.model.GameAudioManager;
import io.src.model.Result;
import io.src.model.SkinManager;
import io.src.view.InnerMenus.AvatarMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Window implements AppMenu {

    private Button editNickName;
    private Button editUsername;
    private Button editEmail;
    private Button editPassword;
    private Window profileWin;

    public ProfileMenu() {
        super("", SkinManager.getInstance().getSkin(SkinManager.MAIN_SKIN));
    }

    private Label warningLabel;
    private ArrayList<String> avatars;
    private Image avatar;
    private TextField nickNameField;
    private TextField usernameField;
    private TextField emailField;
    private TextField passwordField;
    private MainMenuController controller;

    private Stage stage;

    public ProfileMenu(Skin skin) {
        super("", skin, "noWindow");
        align(Align.left | Align.top);
        setFillParent(true);
        // initialize avatars :
        avatars = new ArrayList<>();
        File[] avatarsPath = new File("assets/AVATAR/final/").listFiles(File::isDirectory);
        if (avatarsPath != null)
            for (File file : avatarsPath)
                avatars.add("AVATAR/final/" + file.getName() + "/");
        else
            System.out.println("AVATAR/FINAL/AVATAR NOT FOUND");

        // exit button

        Button closeButton = new Button(skin, "closeButton");

        // avatar :
        Texture avatarTex = new Texture(avatars.get(App.getCurrentUser().getAvatarIndex()) + "\\" + App.getCurrentUser().getAvatarStyleIndex() + "\\avatarProfile.png");
        avatar = new Image(avatarTex);

        // nickName :
        editNickName = new Button(skin, "editingButton");
        Label nickNameLabel = new Label("NickName:", skin);
        nickNameField = new TextField(App.getCurrentUser().getName(), skin);

        // username :
        editUsername = new Button(skin, "editingButton");
        Label usernameLabel = new Label("Username:", skin);
        usernameField = new TextField(App.getCurrentUser().getUsername(), skin);

        // email :
        editEmail = new Button(skin, "editingButton");
        Label emailLabel = new Label("Email:", skin);
        emailField = new TextField(App.getCurrentUser().getEmail(), skin);

        // password :
        editPassword = new Button(skin, "editingButton");
        Label passwordLabel = new Label("Password:", skin);
        passwordField = new TextField(App.getCurrentUser().getPassword(), skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        // games
        Label gameLabel = new Label("Games played:", skin);
        Label gamesField = new Label(App.getCurrentUser().getNumOfGames() + "", skin, "default-PINK");

        // high score
        Label highScoreLabel = new Label("High Score:", skin);
        Label highScoreField = new Label(App.getCurrentUser().getHighScore() + "", skin, "default-PINK");

        // buttons :
        TextButton saveButton = new TextButton("SAVE", skin, "bottomButton-GREEN24");
        TextButton cancelButton = new TextButton("CANCEL", skin, "bottomButton-RED24");

        // profile window

        profileWin = new Window("", skin);
        profileWin.align(Align.left | Align.top);

        Table row1 = new Table();
        Table column = new Table();
        row1.add(avatar).width(avatarTex.getWidth() * 3).height(avatarTex.getHeight() * 3)
            .left().padLeft(70).padTop(50);
        column.add(nickNameLabel).bottom().left().padBottom(18).padLeft(3).row();
        column.add(nickNameField).bottom().width(250).padBottom(10);
        column.add(editNickName).width(closeButton.getWidth() / 2).height(closeButton.getHeight() / 2).padLeft(10)
            .padBottom(10);
        row1.add(column).padRight(70).bottom().padLeft(23);

        Table row2 = new Table();
        row2.add(usernameLabel).padRight(20).left();
        row2.add(usernameField).width(350);
        row2.add(editUsername).padLeft(10).width(closeButton.getWidth() / 2).height(closeButton.getHeight() / 2).row();
        row2.add(emailLabel).padTop(25).padRight(20).left();
        row2.add(emailField).padTop(25).width(350);
        row2.add(editEmail).padLeft(10).width(closeButton.getWidth() / 2).height(closeButton.getHeight() / 2)
            .padTop(25).row();
        row2.add(passwordLabel).padTop(25).padRight(20).left();
        row2.add(passwordField).padTop(25).width(350);
        row2.add(editPassword).padLeft(10).width(closeButton.getWidth() / 2).height(closeButton.getHeight() / 2)
            .padTop(25).row();

        Table row3 = new Table();
        row3.add(gameLabel).padRight(200).left();
        row3.add(gamesField).row();
        row3.add(highScoreLabel).padTop(25).padRight(200).left();
        row3.add(highScoreField).padTop(25).row();

        profileWin.add(row1).row();
        profileWin.add(row2).padTop(50).row();
        profileWin.add(row3).padBottom(25).padTop(45).row();

        setEditingMode(false);
        profileWin.setMovable(false);
        profileWin.pack();

        Table buttonTable = new Table();
        buttonTable.add(cancelButton).left().padLeft(10);
        buttonTable.add(saveButton).expandX().right().padRight(10);
        buttonTable.setVisible(false);

        // add to main Window :
        add(closeButton)
            .padTop(Gdx.graphics.getHeight() / 2f - profileWin.getHeight() / 2f - saveButton.getHeight())
            .right().row();

        add(profileWin).width(profileWin.getWidth()).height(profileWin.getHeight())
            .padLeft(Gdx.graphics.getWidth() / 2f - profileWin.getWidth() / 2f)
            .row();

        Dialog askPasswordDialog = new Dialog("", skin, "askWindow");
        askPasswordDialog.text(new Label("Enter your old password:", skin)).row();
        TextField oldPasswordField = new TextField("", skin);
        oldPasswordField.setPasswordCharacter('*');
        oldPasswordField.setPasswordMode(true);
        askPasswordDialog.getContentTable().add(oldPasswordField).width(300).row();
        Button okbutton = new Button(skin, "okButton");
        askPasswordDialog.button(okbutton);

        add(buttonTable).width(profileWin.getWidth())
            .padLeft(Gdx.graphics.getWidth() / 2f - profileWin.getWidth() / 2f).row();

        setMovable(false);

        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
                ProfileMenu.this.setVisible(false);
                controller.hideMainMenu(true);
            }
        });

        editNickName.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.setKeyboardFocus(nickNameField);
                nickNameField.selectAll();
                nickNameField.setDisabled(false);
                editingMode(false);
                editNickName.setVisible(true);
                editNickName.setDisabled(true);
                buttonTable.setVisible(true);
                closeButton.setVisible(false);
            }
        });

        cancelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.UI_LOOM_SELECT_PATTERN4.getPath(), false, GameAudioManager.sfxVolume);
                setEditingMode(false);
                editingMode(true);
                oldPasswordField.setText("");
                buttonTable.setVisible(false);
                passwordField.setText(App.getCurrentUser().getPassword());
                usernameField.setText(App.getCurrentUser().getUsername());
                emailField.setText(App.getCurrentUser().getEmail());
                nickNameField.setText(App.getCurrentUser().getName());
                passwordField.setPasswordMode(true);
                closeButton.setVisible(true);
            }
        });

        saveButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.UI_LOOM_TAKE_RESULT1.getPath(), false, GameAudioManager.sfxVolume);
                stage.setKeyboardFocus(editNickName);
                Result result = null;

                if (editNickName.isVisible())
                    result = ProfileMenuController.manageChangeNickName(nickNameField.getText().trim());
                else if (editUsername.isVisible())
                    result = ProfileMenuController.manageChangeUsername(usernameField.getText().trim());
                else if (editEmail.isVisible())
                    result = ProfileMenuController.manageChangeEmail(emailField.getText().trim());
                else if (editPassword.isVisible())
                    result = ProfileMenuController.manageChangePassword(passwordField.getText(), oldPasswordField.getText().trim());


                if (result != null && result.isSuccess())
                    App.saveUsers();
                else if (result != null)
                    showWarningLabel(result.getMessage());

                oldPasswordField.setText("");
                editingMode(true);
                setEditingMode(false);
                buttonTable.setVisible(false);
                passwordField.setText(App.getCurrentUser().getPassword());
                usernameField.setText(App.getCurrentUser().getUsername());
                emailField.setText(App.getCurrentUser().getEmail());
                nickNameField.setText(App.getCurrentUser().getName());
                passwordField.setPasswordMode(true);
                closeButton.setVisible(true);
            }
        });

        avatar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                AvatarMenu avatarMenu = new AvatarMenu(skin, (name, farmName, farmPosition, avatar, AvatarIndex, AvatarStyleIndex) -> {
                    updateAvatar();
                    System.out.println(AvatarIndex + " : " + AvatarStyleIndex);
                    App.getCurrentUser().setAvatarIndex(AvatarIndex);
                    App.getCurrentUser().setAvatarStyleIndex(AvatarStyleIndex);
                    App.saveUsers();
                });
                avatarMenu.setAvatarMode();
                avatarMenu.show(stage);
            }
        });

        editUsername.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.setKeyboardFocus(usernameField);
                usernameField.selectAll();
                usernameField.setDisabled(false);
                editingMode(false);
                editUsername.setVisible(true);
                editUsername.setDisabled(true);
                buttonTable.setVisible(true);
                closeButton.setVisible(false);
            }
        });

        editEmail.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.setKeyboardFocus(emailField);
                emailField.selectAll();
                emailField.setDisabled(false);
                editingMode(false);
                editEmail.setVisible(true);
                editEmail.setDisabled(true);
                buttonTable.setVisible(true);
                closeButton.setVisible(false);
            }
        });

        editPassword.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                askPasswordDialog.show(stage);
                stage.setKeyboardFocus(passwordField);
                passwordField.setDisabled(false);
                editingMode(false);
                editPassword.setVisible(true);
                editPassword.setDisabled(true);
                passwordField.setText("");
                passwordField.setPasswordMode(false);
                buttonTable.setVisible(true);
                closeButton.setVisible(false);
            }
        });
    }

    public void updateAvatar() {
        Texture newAvatarTex = new Texture(
            avatars.get(App.getCurrentUser().getAvatarIndex()) +
                "/" + App.getCurrentUser().getAvatarStyleIndex() +
                "/avatarProfile.png"
        );
        avatar.setDrawable(new Image(newAvatarTex).getDrawable());
    }

    public void setEditingMode(boolean editingMode) {
        editingMode = !editingMode;
        nickNameField.setDisabled(editingMode);
        usernameField.setDisabled(editingMode);
        emailField.setDisabled(editingMode);
        passwordField.setDisabled(editingMode);
    }

    public void setController(MainMenuController controller) {
        this.controller = controller;
    }

    @Override
    public Result check(Scanner scanner, String cmd) {
        Matcher matcher;
        if (ProfileMenuCommands.ShowCurrentMenu.getMatcher(cmd) != null) {
            return new Result(true, "you are in profile menu!");
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

    public void showWarningLabel(String message) {
        if (warningLabel == null) {
            warningLabel = new Label("", SkinManager.getInstance().getSkin(SkinManager.MAIN_SKIN),
                "default-GREEN_warning");
            warningLabel.pack();
            add(warningLabel).padTop(10).padLeft(Gdx.graphics.getWidth() / 2f - profileWin.getWidth() / 2f);
        }
        warningLabel.setText(" " + message);
        warningLabel.pack();

        warningLabel.getColor().a = 1f;
        warningLabel.addAction(
            Actions.sequence(
                Actions.delay(2f),
                Actions.fadeOut(0.5f)
            )
        );
    }

    private void editingMode(boolean state) {
        editEmail.setVisible(state);
        editNickName.setVisible(state);
        editUsername.setVisible(state);
        editPassword.setVisible(state);
        editEmail.setDisabled(!state);
        editNickName.setDisabled(!state);
        editUsername.setDisabled(!state);
        editPassword.setDisabled(!state);
    }

    public void adStage(Stage stage) {
        this.stage = stage;
    }
}
