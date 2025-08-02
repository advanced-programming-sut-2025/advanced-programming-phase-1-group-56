package io.src.model.Network.Client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.src.model.GameAssetManager;
import io.src.model.Network.Lobby;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import static io.src.controller.Network.ClientController.sendJoinMessageToServer;

public class LobbyClient extends ApplicationAdapter {
    private Stage stage;
    private TCPClient client;
    private Table lobbyItemsTable;
    private Lobby[] lastLobbyList;
    private ArrayList<String> onlineUser = new ArrayList<>();
    private List<String> onlineListUI;
    private Skin skin;
    private Gson gson = new Gson();
    private String username;
    private Label isSuccessfulLabel;
    private Lobby selectedLobby = null;
    private ScrollPane lobbyScrollPane;


    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("assets/Export/menu_Skin_v0.0.1.json"));

        username = "mehdi";

        //background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        skin.add("white", new TextureRegionDrawable(new TextureRegion(whiteTexture)));

        //isSuccessfulCommand
        isSuccessfulLabel = new Label("", skin);
        isSuccessfulLabel.setColor(Color.RED);
        isSuccessfulLabel.setFontScale(1.2f);
        isSuccessfulLabel.setPosition(70, 100);
        isSuccessfulLabel.setVisible(false);

        //background
        Image bgImage = new Image(GameAssetManager.getGameAssetManager().getCoopBackground());
        bgImage.setSize(1920, 1080);
        stage.addActor(bgImage);

        //Main panel
        Table panel = new Table();
        panel.setSize(600, 800);
        panel.setPosition((1920 - 600) / 2f, (1080 - 900) / 2f);

        //OnlineUsers
        onlineListUI = new List<>(skin);
        ScrollPane onlineScrollPane = new ScrollPane(onlineListUI, skin);
        onlineScrollPane.setFadeScrollBars(false);
        Label onlineLabel = new Label("USERS", skin);

        Table onlineTable = new Table();
        onlineTable.top().left();
        onlineTable.add(onlineLabel).row();
        onlineTable.add(onlineScrollPane).width(130).height(500);
        onlineTable.setPosition(1360, 875);

        //lobbies
        lobbyItemsTable = new Table();
        lobbyItemsTable.top().left();

        lobbyScrollPane = new ScrollPane(lobbyItemsTable, skin);
        lobbyScrollPane.setFadeScrollBars(false);
        lobbyScrollPane.setScrollingDisabled(true, false);

        Label lobbyLabel = new Label("LOBBIES", skin);

        Table lobbyTable = new Table();
        lobbyTable.top().left();
        lobbyTable.add(lobbyLabel).padBottom(5).row();
        lobbyTable.add(lobbyScrollPane).width(800).height(500);

        //button
        TextButton createButton = new TextButton("Create", skin);
        TextButton joinButton = new TextButton("Join", skin);
        TextButton refreshButton = new TextButton("Refresh", skin);

        Table buttonTable = new Table();
        buttonTable.add(createButton).pad(10);
        buttonTable.add(joinButton).pad(10);
        buttonTable.add(refreshButton).pad(10);

        //content
        Table content = new Table();
        content.top();
        content.add(lobbyTable).right().pad(10);
        content.row();
        content.add(buttonTable).colspan(3).center().padTop(30);

        panel.add(content).expand().fill();
        stage.addActor(panel);
        stage.addActor(onlineTable);
        stage.addActor(isSuccessfulLabel);

        //passwordfill
        Label idLabel = new Label("Lobby ID:", skin);
        TextField idField = new TextField("", skin);
        idField.setMessageText("Enter Lobby ID");

        Label passLabel = new Label("Password:", skin);
        TextField passField = new TextField("", skin);
        passField.setPasswordMode(true);
        passField.setPasswordCharacter('*');
        passField.setMessageText("Enter Password");

        TextButton joinByIdBtn = new TextButton("Join by ID", skin);

        Table joinByIdTable = new Table();
        joinByIdTable.bottom().left().pad(15);
        joinByIdTable.add(idLabel).padRight(5);
        joinByIdTable.add(idField).width(120).padRight(10);
        joinByIdTable.row();
        joinByIdTable.add(passLabel).padTop(5).padRight(5);
        joinByIdTable.add(passField).width(120).padTop(5).padRight(10);
        joinByIdTable.row();
        joinByIdTable.add(joinByIdBtn).colspan(2).padTop(10).center();
        stage.addActor(joinByIdTable);

        //buttons
        joinByIdBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String lobbyId = idField.getText().trim();
                String password = passField.getText().trim();
                if (lobbyId.isEmpty()) {
                    System.out.println("Lobby ID cannot be empty!");
                    return;
                }
                sendJoinLobby(lobbyId, password);
            }
        });

        createButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                createLobbyDialog();
            }
        });

        joinButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                joinSelectedLobby();
            }
        });

        refreshButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                requestLobbyList();
            }
        });

        connectToServer();
        requestOnlineUsers();
    }


    private void showSuccessMessage(String text) {
        isSuccessfulLabel.setText(text);
        isSuccessfulLabel.setVisible(true);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> isSuccessfulLabel.setVisible(false));
            }
        }, 3);
    }


    private void updateOnlineList(ArrayList<String> users) {
        this.onlineUser = users;

        Gdx.app.postRunnable(() -> {
            onlineListUI.setItems(users.toArray(new String[0]));
        });
    }

    private void connectToServer() {
        try {
            client = new TCPClient();
            client.connect("localhost", 5000);

            new Thread(() -> {
                try {
                    while (true) {
                        String msgStr = client.receive();
                        Message msg1 = gson.fromJson(msgStr, Message.class);
                        System.out.println(msgStr);

                        Object cmdObj = msg1.getFromBody("commandType");
                        if (cmdObj == null) continue;

                        NetworkCommand command;
                        try {
                            command = NetworkCommand.valueOf(cmdObj.toString());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Unknown command type: " + cmdObj);
                            continue;
                        }

                        switch (command) {
                            case list_lobbies -> {
                                ArrayList<?> rawList = (ArrayList<?>) msg1.getFromBody("Lobbies List");
                                ArrayList<Lobby> lobbies = new ArrayList<>();
                                for (Object obj : rawList) {
                                    lobbies.add(gson.fromJson(gson.toJson(obj), Lobby.class));
                                }
                                lastLobbyList = lobbies.toArray(new Lobby[0]);
                                Gdx.app.postRunnable(() -> {
                                    lobbyItemsTable.clear();
                                    lobbyItemsTable.top().left();

                                    ArrayList<Table> lobbyContainers = new ArrayList<>();

                                    for (Lobby lobby : lobbies) {
                                        String lobbyName = "LOBBY'S NAME: " + lobby.getName() + (lobby.isPrivate() ? " (Private)" : "");
                                        Label lobbyLabelItem = new Label(lobbyName, skin);
                                        lobbyLabelItem.setFontScale(1.2f);
                                        lobbyLabelItem.setColor(Color.WHITE);

                                        Pixmap defaultPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                                        defaultPixmap.setColor(Color.BLUE);
                                        defaultPixmap.fill();
                                        Drawable defaultBackground = new TextureRegionDrawable(new TextureRegion(new Texture(defaultPixmap)));

                                        Pixmap selectedPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                                        selectedPixmap.setColor(Color.TAN);
                                        selectedPixmap.fill();
                                        Drawable selectedBackground = new TextureRegionDrawable(new TextureRegion(new Texture(selectedPixmap)));

                                        // Table
                                        Table lobbyItemContainer = new Table(skin);
                                        lobbyItemContainer.setBackground(defaultBackground);
                                        lobbyItemContainer.add(lobbyLabelItem).left().pad(10).expandX().fillX();

                                        lobbyContainers.add(lobbyItemContainer);

                                        // Tooltip
                                        String tooltipText = "Members: " + lobby.getMembers().size() + "\n" +
                                            String.join(", ", lobby.getMembers());
                                        Label.LabelStyle tooltipStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
                                        tooltipStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapFromColor(Color.CORAL))));
                                        Label tooltipLabel = new Label(tooltipText, tooltipStyle);

                                        Tooltip<Label> tooltip = new Tooltip<>(tooltipLabel);
                                        tooltip.setInstant(true);
                                        TooltipManager.getInstance().initialTime = 0.1f;
                                        lobbyLabelItem.addListener(tooltip);

                                        lobbyItemContainer.addListener(new ClickListener() {
                                            @Override
                                            public void clicked(InputEvent event, float x, float y) {
                                                selectedLobby = lobby;

                                                for (Table table : lobbyContainers) {
                                                    table.setBackground(defaultBackground);
                                                }
                                                lobbyItemContainer.setBackground(selectedBackground);
                                            }
                                        });
                                        lobbyItemsTable.add(lobbyItemContainer).padBottom(15).expandX().fillX().row();
                                    }

                                    lobbyItemsTable.invalidateHierarchy();
                                });
                            }

                            case online_Users -> {
                                Object usersObj = msg1.getFromBody("Users List");
                                if (usersObj instanceof ArrayList<?>) {
                                    ArrayList<?> rawList = (ArrayList<?>) usersObj;
                                    ArrayList<String> users = new ArrayList<>();
                                    for (Object obj : rawList) {
                                        if (obj instanceof String s) users.add(s);
                                    }
                                    updateOnlineList(users);
                                }
                            }
                            case create_lobby -> {
                                //TODO
                                if (msg1.getFromBody("isSuccessful").equals(true)) {
                                    showSuccessMessage("Successfully Created Lobby");
                                } else {
                                    System.out.println("yes");
                                    showSuccessMessage("Failed to Create Lobby");
                                }
                            }
                            case join_lobby -> {
                                if (msg1.getFromBody("isSuccessful").equals(true)) {
                                    showSuccessMessage("Successfully Joined Lobby");
                                } else {

                                    showSuccessMessage("Failed to Join Lobby");
                                }
                            }

                            default -> System.out.println("Unhandled command: " + command);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pixmap pixmapFromColor(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }


    private void requestLobbyList() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.list_lobbies);
        Message.Type type = Message.Type.command;
        client.send(gson.toJson(new Message(body, type)));
    }

    private void createLobbyDialog() {
        Dialog dialog = new Dialog("", skin);
        dialog.setColor(Color.CORAL);

        TextField lobbyNameField = new TextField("", skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        CheckBox privateCheck = new CheckBox("Private", skin);
        CheckBox visibleCheck = new CheckBox("Visible", skin);

        dialog.getContentTable().add(new Label("LOBBY NAME: ", skin)).padTop(20).row();
        dialog.getContentTable().add(lobbyNameField).width(300).row();
        dialog.getContentTable().add(new Label("PASSWORD: (if it's private!)", skin)).row();
        dialog.getContentTable().add(passwordField).width(300).row();
        dialog.getContentTable().add(privateCheck).row();
        dialog.getContentTable().add(visibleCheck).padRight(10).row();

        TextButton createBtn = new TextButton("Create", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);
        createBtn.setColor(Color.FOREST);
        cancelBtn.setColor(Color.SCARLET);

        dialog.button(createBtn);
        dialog.button(cancelBtn);

        createBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String lobbyName = lobbyNameField.getText().trim();
                String password = passwordField.getText().trim();
                if (lobbyName.isEmpty()) {
                    System.out.println("Lobby name cannot be empty!");
                    return;
                }
                boolean isPrivate = privateCheck.isChecked();
                HashMap<String, Object> body = new HashMap<>();
                body.put("commandType", NetworkCommand.create_lobby);
                body.put("ownerName", username);
                body.put("lobbyName", lobbyName);
                body.put("password", password);
                body.put("isPrivate", isPrivate);
                body.put("isVisible", visibleCheck.isChecked());
                Message.Type type = Message.Type.command;
                client.send(gson.toJson(new Message(body, type)));
                dialog.hide();
                requestLobbyList();
            }
        });
        cancelBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.show(stage);
        dialog.setSize(500, 420);
        dialog.setPosition(
            (stage.getWidth() - dialog.getWidth()) / 2,
            (stage.getHeight() - dialog.getHeight()) / 2
        );
    }

    private void joinSelectedLobby() {
        if (selectedLobby != null) {
            if (selectedLobby.isPrivate()) {
                askPasswordAndJoin(selectedLobby.getId());
            } else {
                sendJoinLobby(selectedLobby.getId(), null);
            }
        } else {
            System.out.println("No lobby selected.");
        }
    }


    private void askPasswordAndJoin(String lobbyId) {
        Dialog dialog = new Dialog("", skin) {
            TextField passwordField;

            {
                passwordField = new TextField("", skin);
                passwordField.setPasswordMode(true);
                passwordField.setPasswordCharacter('*');

                getContentTable().pad(20);
                getContentTable().add(new Label("Enter Password:", skin)).padBottom(10).row();
                getContentTable().add(passwordField).width(220).padBottom(20).row();

                TextButton joinBtn = new TextButton("Join", skin);
                TextButton cancelBtn = new TextButton("Cancel", skin);

                button(joinBtn, true);
                button(cancelBtn, false);

                joinBtn.setColor(Color.FOREST);
                cancelBtn.setColor(Color.SCARLET);
            }

            @Override
            protected void result(Object object) {
                if (Boolean.TRUE.equals(object)) {
                    String password = passwordField.getText();
                    sendJoinLobby(lobbyId, password);
                }
            }
        };

        dialog.show(stage);
        dialog.setSize(350, 220);
        dialog.setPosition(
            (stage.getWidth() - dialog.getWidth()) / 2,
            (stage.getHeight() - dialog.getHeight()) / 2
        );
    }


    private void sendJoinLobby(String lobbyId, String password) {
        client.send(gson.toJson(sendJoinMessageToServer(lobbyId, password, username)));
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        try {
            client.close();
        } catch (Exception ignored) {
        }
    }

    private void requestOnlineUsers() {

        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.online_Users);
        client.send(gson.toJson(new Message(body, Message.Type.command)));
    }


    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Lobby Client");
        config.setWindowedMode(1920, 1080);
        new Lwjgl3Application(new LobbyClient(), config);
    }
}
