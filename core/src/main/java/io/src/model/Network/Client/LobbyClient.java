package io.src.model.Network.Client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.*;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyClient extends ApplicationAdapter {
    private Stage stage;
    private TCPClient client;
    private List<String> lobbyListUI;
    private LobbyData[] lastLobbyList;
    private ArrayList<String> onlineUser = new ArrayList<>();
    private Skin skin;
    private Gson gson = new Gson();
    private String username;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("assets/Export/menu_Skin_v0.0.1.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        username = "User_" + System.currentTimeMillis();
        lobbyListUI = new List<>(skin);
        ScrollPane scrollPane = new ScrollPane(lobbyListUI, skin);

        TextButton createLobbyBtn = new TextButton("Create Lobby", skin);
        TextButton joinLobbyBtn = new TextButton("Join Selected", skin);
        TextButton refreshBtn = new TextButton("Refresh", skin);

        table.add(new Label("Lobbies", skin)).padBottom(10).row();
        table.add(scrollPane).width(300).height(200).padBottom(10).row();
        table.add(createLobbyBtn).padBottom(5).row();
        table.add(joinLobbyBtn).padBottom(5).row();
        table.add(refreshBtn);

        // Events
        createLobbyBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                createLobbyDialog();
            }
        });

        joinLobbyBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                joinSelectedLobby();
            }
        });

        refreshBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                requestLobbyList();
            }
        });

        connectToServer();
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

                        if (msg1.getFromBody("commandType").equals(NetworkCommand.list_lobbies)) {
                            lastLobbyList = gson.fromJson(msg1.getFromBody("Lobbies List").toString(), LobbyData[].class);
                            String[] names = new String[lastLobbyList.length];
                            for (int i = 0; i < lastLobbyList.length; i++) {
                                names[i] = lastLobbyList[i].getName() + (lastLobbyList[i].isPrivate() ? " (Private)" : "");
                            }
                            Gdx.app.postRunnable(() -> lobbyListUI.setItems(names));
                        } else if (msg1.getFromBody("commandType").equals(NetworkCommand.online_Users)) {
                            ArrayList<String> users = gson.fromJson(msg1.getFromBody("Users List").toString(), ArrayList.class);
                            Gdx.app.postRunnable(() -> onlineUser = users);
                        }
                    }
                } catch (Exception ignored) {}
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestLobbyList() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.list_lobbies);
        Message.Type type = Message.Type.command;
        client.send(gson.toJson(new Message(body,type)));
    }

    private void createLobbyDialog() {
        Dialog dialog = new Dialog("Create Lobby", skin);

        TextField lobbyNameField = new TextField("", skin);
        CheckBox privateCheck = new CheckBox("Private", skin);
        CheckBox visibleCheck = new CheckBox("Visible", skin);


        dialog.getContentTable().add(new Label("Lobby Name:", skin));
        dialog.getContentTable().row();
        dialog.getContentTable().add(lobbyNameField).width(200).row();
        dialog.getContentTable().add(privateCheck).row();
        dialog.getContentTable().add(visibleCheck).row();

        TextButton createBtn = new TextButton("Create", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);

        dialog.button(createBtn);
        dialog.button(cancelBtn);

        createBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String lobbyName = lobbyNameField.getText().trim();
                if (lobbyName.isEmpty()) {
                    System.out.println("Lobby name cannot be empty!");
                    return;
                }
                boolean isPrivate = privateCheck.isChecked();
                HashMap<String, Object> body = new HashMap<>();
                body.put("commandType", NetworkCommand.create_lobby);
                body.put("ownerName", username);
                body.put("lobbyName", lobbyName);
                //TODO
                body.put("password", 7);
                //
                body.put("isPrivate", isPrivate);
                body.put("isVisible", visibleCheck.isChecked());

                Message.Type type = Message.Type.command;
                client.send(gson.toJson(new Message(body,type)));
                String msgStr = null;
                try {
                    msgStr = client.receive();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Message msg1 = gson.fromJson(msgStr, Message.class);
                if(msg1.getFromBody("commandType").equals(NetworkCommand.create_lobby)) {
                    if(msg1.getFromBody("isSuccessful").equals("yes")) {
                        System.out.println("have successfully created lobby!");
                    }
                }
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
    }

    private void joinSelectedLobby() {
        String selectedName = lobbyListUI.getSelected();
        if (selectedName != null) {
            for (LobbyData lobby : lastLobbyList) {
                String nameWithPrivacy = lobby.getName() + (lobby.isPrivate() ? " (Private)" : "");
                if (nameWithPrivacy.equals(selectedName)) {
                    if (lobby.isPrivate()) {
                        askPasswordAndJoin(lobby.getId());
                    } else {
                        sendJoinLobby(lobby.getId(), null);
                    }

                    break;
                }
            }
        }
    }

    private void askPasswordAndJoin(String lobbyId) {
        Dialog dialog = new Dialog("Enter Password", skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        dialog.text("Password:");
        dialog.getContentTable().add(passwordField).width(200);

        dialog.button("Join", true);
        dialog.button("Cancel", false);

        dialog.result(result -> {
            if ((Boolean) result) {
                String password = passwordField.getText();
                sendJoinLobby(lobbyId, password);
            }
        });

        dialog.show(stage);
    }

    private void sendJoinLobby(String lobbyId, String password) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.join_lobby);
        body.put("username", username);
        body.put("lobbyId", lobbyId);
        body.put("password", password);
        Message.Type type = Message.Type.command;
        client.send(gson.toJson(new Message(body,type)));
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
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Lobby Client");
        config.setWindowedMode(1000, 800);
        new Lwjgl3Application(new LobbyClient(), config);
    }


    public static class LobbyData {
        private String id;
        private String name;
        private boolean isPrivate;

        public String getId() { return id; }
        public String getName() { return name; }
        public boolean isPrivate() { return isPrivate; }
    }

}
