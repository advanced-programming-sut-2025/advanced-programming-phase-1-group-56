package io.src.model.Network.Client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import io.src.StardewValley;
import io.src.controller.Network.ClientController;
import io.src.controller.Network.ServerController;
import io.src.model.*;
import io.src.model.Activities.Friendship;
import io.src.model.Enums.Direction;
import io.src.model.Enums.FarmPosition;
import io.src.model.Enums.Items.ToolType;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.GameMap;
import io.src.model.Network.DTO.GameDTO;
import io.src.model.Network.DTO.GameMapper;
import io.src.model.Network.Lobby;
import io.src.model.Network.Message;
import io.src.model.Network.NetworkCommand;
import io.src.model.TimeSystem.LocalDateTimeAdapter;
import io.src.model.items.Tool;
import io.src.view.GameMenus.GameMenuInputAdapter;
import io.src.view.GameMenus.GameView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static io.src.controller.Network.ClientController.sendJoinMessageToServer;
import static io.src.model.MapModule.newFarmLoader.loadTheLocation;

public class LobbyClient implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private static TCPClient client;
    private Table lobbyItemsTable;
    private Lobby[] lastLobbyList;
    private ArrayList<String> onlineUser = new ArrayList<>();
    private List<String> onlineListUI;
    private Skin skin;
    private Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .create();
    private String username;
    private Label isSuccessfulLabel;
    private Lobby selectedLobby = null;
    private ScrollPane lobbyScrollPane;


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
            ClientController.sendUserNameToServer(username, client, gson);

            new Thread(() -> {
                try {
                    while (true) {
                        String msgStr = null;
                        Message msg1 = null;

                        try {
                            msgStr = client.receive();
                            msg1 = gson.fromJson(msgStr, Message.class);
                        } catch (Exception e) {
                            System.out.println("Lost connection. Trying to reconnect...");
                            long start = System.currentTimeMillis();
                            boolean connected = false;
                            while (System.currentTimeMillis() - start < 120_000 && !connected) {
                                try {
                                    Thread.sleep(2000);
                                    client.connect("localhost", 5000);
                                    ClientController.sendUserNameToServer(username, client, gson);
                                    connected = true;
                                    System.out.println("Reconnected successfully!");
                                } catch (Exception ex) {
                                    System.out.println("Reconnect failed, retrying...");
                                }
                            }
                            continue;
                        }

                        if (msg1 == null) continue;

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
                            case updateEmote -> {
                                for (Player player : App.getCurrentUser().getCurrentGame().getPlayers()) {
                                    if (player.getUserName().equals(msg1.getFromBody("username"))) {
                                        ArrayList<Integer> emoteId = msg1.getFromBody2(
                                            "allEmote",
                                            new TypeToken<ArrayList<Integer>>() {
                                            }.getType()
                                        );
                                        player.setEmotes(emoteId);
                                    }
                                }
                            }
                            case emote -> {
                                for (Player player : App.getCurrentUser().getCurrentGame().getPlayers()) {
                                    if (player.getUserName().equals(msg1.getFromBody("username"))) {
                                        double emoteId = (double)msg1.getFromBody("emoteId");
                                        int emoteCount = (int)emoteId;
                                        player.setShowEmote(emoteCount);
                                    }
                                }
                            }
                            case ready_for_state -> {
                                HashMap<String, Object> body = new HashMap<>();
                                body.put("commandType", NetworkCommand.request_game_state);
                                body.put("username", username);
                                Message.Type type = Message.Type.command;
                                client.send(gson.toJson(new Message(body, type)));
                            }
                            case OWNERtoDC -> {
                                GameDTO gameDTO = msg1.getFromBody1("gameDTO", GameDTO.class);
                                Game game = GameMapper.fromDTO(gameDTO);
                                ArrayList<User> users = new ArrayList<>();
                                for (Player member : game.getPlayers()) {
                                    users.add(ServerController.getUserByUsername(member.getUserName()));
                                }
                                setGame(game, users);
                            }
                            case DCtoOWNER -> {
                                String targetUsername = msg1.getFromBody("username");
                                System.out.println(App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown().getTownmapPath());
                                GameDTO gameDTO = GameMapper.toDTO(App.getCurrentUser().getCurrentGame());
                                System.out.println(gameDTO.getGameMap().getPelikanTown().getTownmapPath() + "abc");
                                HashMap<String, Object> body = new HashMap<>();
                                body.put("commandType", NetworkCommand.OWNERtoDC);
                                body.put("username", targetUsername);
                                body.put("gameDTO", gameDTO);
                                Message.Type type = Message.Type.command;
                                client.send(gson.toJson(new Message(body, type)));
                            }
                            case load -> {
                                String gameId = msg1.getFromBody("gameId");
                                GameDTO gameDTO = io.src.model.Network.Utils.GameSaveManager.loadGame(gameId);
                                Game loadedGame = null;
                                if (gameDTO != null) {
                                    loadedGame = GameMapper.fromDTO(gameDTO);
                                }
                                ArrayList<User> users = msg1.getFromBody2(
                                    "users",
                                    new TypeToken<ArrayList<User>>() {
                                    }.getType()
                                );
                                setGame(loadedGame, users);
                            }
                            case updatePlayer -> {
                                String username = msg1.getFromBody("username");
                                float x = Float.parseFloat(msg1.getFromBody("x").toString());
                                float y = Float.parseFloat(msg1.getFromBody("y").toString());
                                Direction dir = Direction.valueOf(msg1.getFromBody("Dir").toString());
                                Player targetPlayer = null;
                                Game game = App.getCurrentUser().getCurrentGame();
                                for (Player player : game.getPlayers()) {
                                    if (player.getUserName().equals(username)) {
                                        targetPlayer = player;
                                    }
                                }
                                if (targetPlayer != null) {
                                    targetPlayer.getPosition().setX(x);
                                    targetPlayer.getPosition().setY(y);
                                    targetPlayer.setMovingDirection(dir);
                                }

                                double locDouble = (double) msg1.getFromBody("GameLocation");
                                int gameLocation = (int) locDouble;

                                if (gameLocation == 0) {
                                    targetPlayer.setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown());
                                } else if (gameLocation == 1) {
                                    targetPlayer.setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getFarm1());
                                } else if (gameLocation == 2) {
                                    targetPlayer.setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getFarm2());
                                } else if (gameLocation == 3) {
                                    targetPlayer.setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getFarm3());
                                } else if (gameLocation == 4) {
                                    targetPlayer.setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getFarm4());
                                }
                            }
                            case Game -> {
                                Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
                                GameDTO gamedto = msg1.getFromBody1("Game", GameDTO.class);

                                ArrayList<User> users = msg1.getFromBody2(
                                    "users",
                                    new TypeToken<ArrayList<User>>() {
                                    }.getType()
                                );

                                Game game = GameMapper.fromDTO(gamedto);
                                System.out.println(game.getGameMap().getPelikanTown().getTownmapPath() + "12151515151");
                                setGame(game, users);
                            }
                            case start -> showSuccessMessage(msg1.getFromBody("isSuccessful"));
                            case list_lobbies -> {
                                listLobby(msg1);
                            }
                            case toggle_ready -> {
                                showSuccessMessage(msg1.getFromBody("Ready"));

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
                            case leave_lobby -> {
                                System.out.println("sdsdfd");
                                showSuccessMessage(msg1.getFromBody("isSuccessful"));
                                requestLobbyList();
                            }

                            default -> requestLobbyList();
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

    private void listLobby(Message msg1) {
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
                        if (lobby.getOwner().equals(username)) {
                            // Owner
                            showOwnerLobbyOptions(lobby);
                        } else if (lobby.getMembers().contains(username)) {
                            showMemberLobbyOptions(lobby);
                        }

                    }
                });

                lobbyItemsTable.add(lobbyItemContainer).padBottom(15).expandX().fillX().row();
            }

            lobbyItemsTable.invalidateHierarchy();
        });
    }

    private void setGame(Game newGame, ArrayList<User> usersToPlay) {

        for (int i = 0; i < newGame.getPlayers().size(); i++) {
            if (i == 0) {
                newGame.getPlayers().get(i).setPlayerFarm(newGame.getGameMap().getFarm1());
                newGame.getPlayers().get(i).setCurrentGameLocation(newGame.getGameMap().getFarm1());

            } else if (i == 1) {
                newGame.getPlayers().get(i).setPlayerFarm(newGame.getGameMap().getFarm2());
                newGame.getPlayers().get(i).setCurrentGameLocation(newGame.getGameMap().getFarm2());

            } else if (i == 2) {
                newGame.getPlayers().get(i).setPlayerFarm(newGame.getGameMap().getFarm3());
                newGame.getPlayers().get(i).setCurrentGameLocation(newGame.getGameMap().getFarm3());

            } else if (i == 3) {
                newGame.getPlayers().get(i).setCurrentGameLocation(newGame.getGameMap().getFarm4());
                newGame.getPlayers().get(i).setPlayerFarm(newGame.getGameMap().getFarm4());

            }
        }


        for (Player player1 : newGame.getPlayers()) {
            for (Player player2 : newGame.getPlayers()) {
                if (player2.equals(player1))
                    continue;
                player1.getFriendShips().add(new Friendship(player2));
            }
        }
        App.getCurrentUser().setCurrentGame(newGame);
        App.getCurrentUser().setGameId(newGame.getGameId());
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(newGame.getWeatherState());
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(newGame.getGameMap().getPelikanTown());
        App.getCurrentUser().setNumOfGames(App.getCurrentUser().getNumOfGames() + 1);

        GivePlayersInitialItem(newGame);
        newGame.setCurrentPlayer(newGame.getPlayerByUser(App.getCurrentUser()));
        newGame.setStarterPlayer(newGame.getPlayerByUser(App.getCurrentUser()));

        for (User user : usersToPlay) {
            user.setGameId(newGame.getGameId());
            user.setCurrentGame(newGame);
            user.setNumOfGames(user.getNumOfGames() + 1);
        }

        Gdx.app.postRunnable(() -> {
            GameMap map = newGame.getGameMap();
            map.getPelikanTown().setTownmapPath("assets\\gameLocations\\Town4");

            map.setPelikanTown((Town) loadTheLocation(map.getPelikanTown().getTownmapPath()));
            Town town = map.getPelikanTown();

            for (NPC npc : town.getNPCs()) {
                npc.initializePaths(town);
            }

            if (map.getFarm1() != null) {
                map.getFarm1().setFarnmapPath("assets\\gameLocations\\Farm1");

                map.setFarm1((Farm) loadTheLocation(map.getFarm1().getFarnmapPath()));
                map.getFarm1().setPosition(FarmPosition.LEFT);
                App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(map.getFarm1());
            }
            if (map.getFarm2() != null) {
                map.getFarm2().setFarnmapPath("assets\\gameLocations\\Farm1");

                map.setFarm2((Farm) loadTheLocation(map.getFarm2().getFarnmapPath()));
                map.getFarm2().setPosition(FarmPosition.UP);
                App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(map.getFarm2());
            }
            if (map.getFarm3() != null) {
                map.getFarm3().setFarnmapPath("assets\\gameLocations\\Farm1");

                map.setFarm3((Farm) loadTheLocation(map.getFarm3().getFarnmapPath()));
                map.getFarm3().setPosition(FarmPosition.DOWN);
                App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(map.getFarm3());
            }
            if (map.getFarm4() != null) {
                map.getFarm4().setFarnmapPath("assets\\gameLocations\\Farm2");

                map.setFarm4((Farm) loadTheLocation(map.getFarm4().getFarnmapPath()));
                map.getFarm4().setPosition(FarmPosition.RIGHT);
                App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(map.getFarm4());
            }

            for (int i = 0; i < newGame.getPlayers().size(); i++) {
                Player p = newGame.getPlayers().get(i);
                switch (i) {
                    case 0 -> p.setPlayerFarm(map.getFarm1());
                    case 1 -> p.setPlayerFarm(map.getFarm2());
                    case 2 -> p.setPlayerFarm(map.getFarm3());
                    case 3 -> p.setPlayerFarm(map.getFarm4());
                }
                p.setCurrentGameLocation(p.getPlayerFarm());
                p.setDefaultHome(p.getPlayerFarm().getDefaultHome());
            }

            System.out.println("moshefnsesfsssefsefse");
            StardewValley.setGame(newGame);
            GameView gameView = new GameView(newGame);
            App.setStardewValley(StardewValley.getStardewValley());
            StardewValley.getStardewValley().setGameView(gameView);
            App.getStardewValley().setScreen(gameView);
            newGame.setStarted(true);
        });


    }


    private static void GivePlayersInitialItem(Game newGame) {
        for (Player player : newGame.getPlayers()) {
            player.getInventory().add(new Tool(ToolType.AXE_WOODEN), 1);
            player.getInventory().add(new Tool(ToolType.PICK_WOODEN), 1);
            player.getInventory().add(new Tool(ToolType.SCYTHE_BASIC), 1);
            player.getInventory().add(new Tool(ToolType.HOE_WOODEN), 1);
            player.getInventory().add(new Tool(ToolType.CAN_WOODEN), 1);
            player.addGold(100);
            player.setDefaultHome(player.getPlayerFarm().getDefaultHome());
            player.addFoodRecipes(FoodRecipesList.FRIED_EGG);
            player.addFoodRecipes(FoodRecipesList.BAKED_FISH);
            player.addFoodRecipes(FoodRecipesList.SALAD);
            player.setCurrentItem(player.getInventory().findItemByName(ToolType.AXE_WOODEN.getName()));
        }
    }


    private void showOwnerLobbyOptions(Lobby lobby) {
        Dialog dialog = new Dialog("", skin);

        TextButton removeBtn = new TextButton("Remove Player", skin);
        TextButton loadBtn = new TextButton("Load Game", skin);
        TextButton startBtn = new TextButton("Start Game", skin);
        TextButton leaveBtn = new TextButton("Leave Lobby", skin);
        TextButton closeBtn = new TextButton("Close", skin);
        removeBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                showRemovePlayerDialog(lobby);
            }
        });

        //TODO
        startBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("commandType", NetworkCommand.start);
                body.put("lobbyId", lobby.getId());
                client.send(gson.toJson(new Message(body, Message.Type.command)));
                dialog.hide();
            }
        });

        loadBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("commandType", NetworkCommand.load);
                body.put("lobbyId", lobby.getId());
                client.send(gson.toJson(new Message(body, Message.Type.command)));
                dialog.hide();
            }

        });

        // Leave lobby
        leaveBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                sendLeaveLobby(lobby.getId());
                dialog.hide();
            }
        });

        closeBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.getContentTable().pad(20);
        dialog.getContentTable().add(removeBtn).pad(5).row();
        dialog.getContentTable().add(startBtn).pad(5).row();
        dialog.getContentTable().add(leaveBtn).pad(5).row();
        dialog.getContentTable().add(closeBtn).pad(5).row();

        dialog.show(stage);
    }

    private void showMemberLobbyOptions(Lobby lobby) {
        Dialog dialog = new Dialog("", skin);

        TextButton readyBtn = new TextButton("Ready / Unready", skin);
        TextButton leaveBtn = new TextButton("Leave Lobby", skin);
        TextButton closeBtn = new TextButton("Close", skin);

        readyBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("commandType", NetworkCommand.toggle_ready);
                body.put("lobbyId", lobby.getId());
                body.put("username", username);
                client.send(gson.toJson(new Message(body, Message.Type.command)));
                dialog.hide();

            }
        });

        leaveBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                sendLeaveLobby(lobby.getId());
                dialog.hide();
            }
        });

        closeBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.getContentTable().pad(20);
        dialog.getContentTable().add(readyBtn).pad(5).row();
        dialog.getContentTable().add(leaveBtn).pad(5).row();
        dialog.getContentTable().add(closeBtn).pad(5).row();

        dialog.show(stage);
    }

    private void showRemovePlayerDialog(Lobby lobby) {
        Dialog dialog = new Dialog("", skin);
        List<String> membersList = new List<>(skin);

        ArrayList<String> members = new ArrayList<>(lobby.getMembers());
        members.remove(username);
        membersList.setItems(members.toArray(new String[0]));

        ScrollPane scrollPane = new ScrollPane(membersList, skin);

        TextButton removeBtn = new TextButton("Remove", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);

        removeBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String selectedUser = membersList.getSelected();
                if (selectedUser != null) {
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("commandType", NetworkCommand.kick_user);
                    body.put("lobbyId", lobby.getId());
                    body.put("username", selectedUser);
                    client.send(gson.toJson(new Message(body, Message.Type.command)));
                    dialog.hide();
                }
            }
        });

        cancelBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.getContentTable().pad(20);
        dialog.getContentTable().add(scrollPane).width(250).height(200).row();
        dialog.getContentTable().add(removeBtn).padTop(10).row();
        dialog.getContentTable().add(cancelBtn).padTop(5).row();

        dialog.show(stage);
    }


    private void sendLeaveLobby(String lobbyId) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.leave_lobby);
        body.put("lobbyId", lobbyId);
        body.put("username", username);
        client.send(gson.toJson(new Message(body, Message.Type.command)));

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
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("assets/Export/menu_Skin_v0.0.1.json"));

        username = App.getCurrentUser().getUsername();

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
        isSuccessfulLabel.setPosition(30, 1000);
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        batch.dispose();
    }


    private void requestOnlineUsers() {

        HashMap<String, Object> body = new HashMap<>();
        body.put("commandType", NetworkCommand.online_Users);
        client.send(gson.toJson(new Message(body, Message.Type.command)));
    }

    public static TCPClient getClient() {
        return client;
    }
}
