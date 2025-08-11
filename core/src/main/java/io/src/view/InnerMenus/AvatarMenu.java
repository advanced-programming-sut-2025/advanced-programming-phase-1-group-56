package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.google.gson.Gson;
import io.src.model.App;
import io.src.model.Enums.SfxEnum;
import io.src.model.GameAudioManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AvatarMenu extends Window {

    private final ArrayList<String> avatars;
    private final ArrayList<String> directs;
    private final TextField farmNameField;
    private final TextField nameField;
    private final SelectBox<String> farmPosition;
    private int avatarIndex;
    private Texture avatarTex;
    private final Image avatarImage;
    private Texture avatarProfileTx;
    private final Image avatarProfile;
    private int directIndex;
    private Label avatarName;
    private Container<Label> nameContainer;
    private int avatarStyleIndex;


    public AvatarMenu(Skin skin, AvatarSelectionListener listener) {
        super("", skin);
        align(Align.left | Align.top);
        avatars = new ArrayList<>();
        avatarIndex = App.getCurrentUser().getAvatarIndex();
        directs = new ArrayList<>(Arrays.asList("front", "right", "back", "left"));
        directIndex = 0;
        avatarStyleIndex = App.getCurrentUser().getAvatarStyleIndex();

        File[] avatarsPath = new File("assets/AVATAR/final/").listFiles(File::isDirectory);
        if (avatarsPath != null)
            for (File file : avatarsPath)
                avatars.add("AVATAR/final/" + file.getName() + "/");
        else
            System.out.println("AVATAR/FINAL/AVATAR NOT FOUND");

        Table row1 = new Table();
        Table avatarTable = new Table();
        Button leftDirect = new Button(skin, "leftButton");
        Button rightDirect = new Button(skin, "rightButton");
        Table extraButtonsTable = new Table();
        Button randomAvatar = new Button(skin, "randomButton");
        Button changeStyleButton = new Button(skin, "changeStyleButton");
        extraButtonsTable.add(changeStyleButton).padBottom(20).width(changeStyleButton.getWidth() / 1.5f).height(changeStyleButton.getHeight() / 1.5f).padTop(50).row();
        extraButtonsTable.add(randomAvatar).row();

        avatarTable.add(leftDirect).padRight(-20).bottom();

        // avatar and its background :
        Texture backgroundTex = new Texture("AVATAR/final/avatarBack.png");
        avatarTex = new Texture(avatars.get(avatarIndex) + avatarStyleIndex + "/" + directs.get(directIndex) + ".png");
        Image avatarBackground = new Image(backgroundTex);
        avatarImage = new Image(avatarTex);
        avatarBackground.setScaling(Scaling.stretch);
        avatarImage.setScaling(Scaling.stretch);

        Stack avatarStack = getStack(backgroundTex, avatarBackground);

        avatarTable.add(avatarStack);

        avatarTable.add(rightDirect).padLeft(-20).bottom();
        leftDirect.toFront();
        row1.add(avatarTable).padLeft(70).padTop(50);
        row1.add(extraButtonsTable).padLeft(50);

        Table profileTable = new Table();

        avatarProfileTx = new Texture(avatars.get(avatarIndex) + avatarStyleIndex + "\\avatarProfile.png");
        avatarProfile = new Image(avatarProfileTx);
        Stack profileStack = getStack(skin);

        Button leftDirect1 = new Button(skin, "leftButton");
        Button rightDirect1 = new Button(skin, "rightButton");
        profileTable.add(leftDirect1).padRight(-25);
        profileTable.add(profileStack).width(avatarProfileTx.getWidth() * 3).height(avatarProfileTx.getHeight() * 3);
        leftDirect1.toFront();
        profileTable.add(rightDirect1).padLeft(-20);

        row1.add(profileTable).padLeft(50).padTop(50);

        add(row1).row();

        Table fields = new Table();

        nameField = new TextField("", skin);
        nameField.setAlignment(Align.center);
        Label nameLabel = new Label("Name:", skin, "default-PINK");
        fields.add(nameLabel).padTop(100).padLeft(50).padRight(30);
        fields.add(nameField).padTop(100).width(300).row();

        farmNameField = new TextField("", skin);
        farmNameField.setAlignment(Align.center);
        Label farmNameLabel = new Label("Farm Name:", skin, "default-PINK");
        fields.add(farmNameLabel).padTop(10).padLeft(50).padRight(30);
        fields.add(farmNameField).padTop(10).width(300).row();

        Label farmPositionLabel = new Label("Farm Position: ", skin, "default-PINK");
        farmPosition = new SelectBox<>(skin);
        farmPosition.setItems("Left", "Right", "Up", "Down");
        farmPosition.setSelected("Left");
        fields.add(farmPositionLabel).padTop(10).padLeft(50).padRight(30);
        fields.add(farmPosition).width(300).padTop(10);
        add(fields).padBottom(15);

        Button okButton = new Button(skin, "okButton");
        okButton.setDisabled(true);
        add(okButton).bottom().padRight(10).padBottom(15);

        pack();
        // Set size of window
        pack();
        setPosition(
            (Gdx.graphics.getWidth() - getWidth()) / 2f,
            (Gdx.graphics.getHeight() - getHeight()) / 2f
        );

        leftDirect.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                directIndex = (directIndex + 3) % 4;
                updateAvatarTextures();
            }
        });

        rightDirect.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                directIndex = (directIndex + 1) % 4;
                updateAvatarTextures();
            }
        });

        rightDirect1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                avatarIndex = (avatarIndex + 1) % avatars.size();
                directIndex = 0;
                avatarStyleIndex = 1;
                updateAvatarTextures();
            }
        });

        leftDirect1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                avatarIndex = (avatarIndex + avatars.size() - 1) % avatars.size();
                directIndex = 0;
                avatarStyleIndex = 1;
                updateAvatarTextures();
            }
        });

        changeStyleButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int maxStyles = getMaxStylesForAvatar(avatarIndex);
                avatarStyleIndex++;
                if (avatarStyleIndex > maxStyles) avatarStyleIndex = 1;
                updateAvatarTextures();
            }
        });

        randomAvatar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                avatarIndex = (int) (Math.random() * avatars.size());
                directIndex = 0;
                int maxStyles = getMaxStylesForAvatar(avatarIndex);
                avatarStyleIndex = 1 + (int) (Math.random() * maxStyles);
                updateAvatarTextures();
            }
        });

        okButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (okButton.isDisabled()) return;
                String name = nameField.getText();
                String farm = farmNameField.getText();
                String position = farmPosition.getSelected();
                App.getCurrentUser().setAvatarIndex(avatarIndex);
                App.getCurrentUser().setAvatarStyleIndex(avatarStyleIndex);
                App.saveUsers();
                listener.onAvatarSelected(name, farm, position, avatars.get(avatarIndex), avatarIndex, avatarStyleIndex);
            }
        });

        nameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                okButton.setDisabled(nameField.getText().isEmpty() || farmNameField.getText().isEmpty());
            }
        });

        farmNameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                okButton.setDisabled(nameField.getText().isEmpty() || farmNameField.getText().isEmpty());
            }
        });

        setMovable(false);
        setModal(true);
    }

    private int getMaxStylesForAvatar(int avatarIndex) {
        File avatarDir = new File("assets\\" + avatars.get(avatarIndex));
        if (!avatarDir.exists() || !avatarDir.isDirectory()) return 1;
        int count = 0;
        for (File file : avatarDir.listFiles(File::isDirectory)) {
            try {
                Integer.parseInt(file.getName());
                count++;
            } catch (NumberFormatException ignored) {
            }
        }
        return Math.max(count, 1);
    }

    private void updateAvatarTextures() {
        GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, 1f);
        Texture oldTex = avatarTex;
        avatarTex = new Texture(avatars.get(avatarIndex) + avatarStyleIndex + "/" + directs.get(directIndex) + ".png");
        avatarImage.setDrawable(new TextureRegionDrawable(new TextureRegion(avatarTex)));
        oldTex.dispose();

        oldTex = avatarProfileTx;
        avatarProfileTx = new Texture(avatars.get(avatarIndex) + avatarStyleIndex + "/avatarProfile.png");
        avatarProfile.setDrawable(new TextureRegionDrawable(new TextureRegion(avatarProfileTx)));
        oldTex.dispose();

        avatarName.setText(avatars.get(avatarIndex)
            .substring(avatars.get(avatarIndex).lastIndexOf("final/") + 6,
                avatars.get(avatarIndex).lastIndexOf('/')));
        avatarName.pack();
        nameContainer.pack();
        avatarName.invalidateHierarchy();
    }

    private Stack getStack(Skin skin) {
        Container<Image> profileContainer = new Container<>(avatarProfile);
        profileContainer.size(avatarProfileTx.getWidth() * 3, avatarProfileTx.getHeight() * 3);
        avatarName = new Label(avatars.get(avatarIndex).substring(avatars.get(avatarIndex).lastIndexOf("final/") + 6, avatars.get(avatarIndex).lastIndexOf('/')), skin, "labelBack-24_ZERESHK");
        nameContainer = new Container<>(avatarName);
        nameContainer.align(Align.bottom);
        nameContainer.padBottom(-5);
        Stack profileStack = new Stack();
        profileStack.add(profileContainer);
        profileStack.add(nameContainer);
        return profileStack;
    }

    private Stack getStack(Texture backgroundTex, Image avatarBackground) {
        float backgroundWidth = backgroundTex.getWidth();
        float backgroundHeight = backgroundTex.getHeight();
        Container<Image> backgroundContainer = new Container<>(avatarBackground);
        backgroundContainer.size(backgroundWidth, backgroundHeight);
        Container<Image> avatarContainer = new Container<>(avatarImage);
        avatarContainer.size(avatarTex.getWidth() * 4, avatarTex.getHeight() * 4);
        avatarContainer.align(Align.center);
        Stack avatarStack = new Stack();
        avatarStack.add(backgroundContainer);
        avatarStack.add(avatarContainer);
        return avatarStack;
    }

    public int getAvatarIndex() {
        return avatarIndex;
    }

    public int getAvatarStyleIndex() {
        return avatarStyleIndex;
    }

    public interface AvatarSelectionListener {
        void onAvatarSelected(String name, String farmName, String farmPosition, String avatar, int AvatarIndex, int AvatarStyleIndex);
    }
}
