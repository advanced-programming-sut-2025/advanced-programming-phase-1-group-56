package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import io.src.StardewValley;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.src.controller.GameMenuController.GameController;
import io.src.model.App;
import io.src.model.Clickable;
import io.src.model.Enums.Direction;
import io.src.model.Enums.FarmPosition;
import io.src.model.Enums.GameLocationType;
import io.src.model.Enums.TileType;
import io.src.model.Game;
import io.src.model.GameObject.GameObject;
import io.src.model.GameObject.SensitiveToPlayer;
import io.src.model.MapModule.Buildings.*;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;
import io.src.model.Player;
import io.src.model.TimeSystem.DateTime;
import io.src.view.GameMenus.ShopMenus.ShopStateWindow;
import org.lwjgl.Sys;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class GameMenuInputAdapter extends InputAdapter {
    private final Game game;
    //    private final GameController gameController;
    private final Set<Integer> keysHeld = new HashSet<>();
    private boolean stopMoving = false;
    private boolean shopCounterHintActive = false;
    private GameObject focusedGameObject = null;
    private final ArrayList<GameObject> nearbyGameObjects = new ArrayList<>();
    //private LocalDateTime LastJClicked = LocalDateTime.now();
    private boolean isCheatWindowOpen = false;

//    public GameMenuInputAdapter(Game game, GameController gameController) {
//        this.game = game;

    /// /        this.gameController = gameController;
//    }
    public GameMenuInputAdapter(Game game) {
        this.game = game;
//        this.gameController = gameController;
    }

    @Override
    public boolean keyDown(int keycode) {

        keysHeld.add(keycode);
        if (keysHeld.contains(Input.Keys.J)) {
//            Tile[][] tiles = App.getMe().getCurrentGameLocation().getTiles();
//            Position pos = App.getMe().getPosition();
//            for (int i = (int) pos.getY() - 1; i <= pos.getY() + 1; i++) {
//                for (int j = (int) pos.getX() - 1; j <= pos.getX() + 1; j++) {
//                    tiles[i][j].setWalkable(true);
//                }
//            }
            //if (LastJClicked.until(LocalDateTime.now(), ChronoUnit.MILLIS) > 100) {
            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().addDay(1);
            //LastJClicked = LocalDateTime.now();
            //}
            return true;
        }
        if (keysHeld.contains(Input.Keys.N)) {
            GameController.manageNextTurn();
            App.getStardewValley().getGameView().updateMap();
            return true;
        }


        if (keycode == Input.Keys.C && !isCheatWindowOpen()) {
            keysHeld.clear();
            CheatWindow cheatWindow = App.getStardewValley().getGameView().getCheatWindow();
            Stage stage = App.getStardewValley().getGameView().getStage();
            stage.setKeyboardFocus(cheatWindow);
            cheatWindow.showWithFocus(stage);
            return true;
        }

        if (keycode == Input.Keys.ENTER && isCheatWindowOpen()) {
            CheatWindow cheatWindow = App.getStardewValley().getGameView().getCheatWindow();
            Stage stage = App.getStardewValley().getGameView().getStage();
            cheatWindow.hideDialog(stage);
            return true;
        }

        if (keycode == Input.Keys.ENTER) {
            App.getStardewValley().getGameView().getWarningWindow().hideDialog();
            return true;
        }
        if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            int selectedSlot = keycode - Input.Keys.NUM_1;
            game.getCurrentPlayer().setSelectedSlot(selectedSlot);
            return true;
        }

        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            return true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keysHeld.remove(keycode);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        int current = game.getCurrentPlayer().getSelectedSlot();
        int size = game.getCurrentPlayer().getCurrentBackpack().getCapacity();
        int next = (current + (amountY > 0 ? 1 : -1) + size) % size;
        game.getCurrentPlayer().setSelectedSlot(next);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            performAction(screenX, screenY);
            return true;
        }
        if (button == Input.Buttons.RIGHT) {
            if (isFacingCounter()) {
                ShopStateWindow shopStateWindow = StardewValley.getGameView().getShopStateWindow();
                shopStateWindow.showDialog();
                return true;
            }
            if (focusedGameObject != null && focusedGameObject instanceof Clickable clickable) {
                return clickable.touchDown(screenX, screenY, pointer, button);
            }
            return true;
        }
        return false;
    }

    private boolean isFacingCounter() {
        if (isNearCounter()) {
            GameLocation currGL = App.getMe().getCurrentGameLocation();
            Class<? extends Store> relatedClazz = currGL.getType().getRelatedClazz();
            Store store = App.getCurrentUser().getCurrentGame().findStoreByClass(
                (Class<? extends Store>) relatedClazz
            );
            float dX = App.getMe().getPosition().getX() - store.getNPCposition().getX();
            float dY = App.getMe().getPosition().getY() - store.getNPCposition().getY();
            return switch (App.getMe().getLastDirection()) {
                case UP -> dY < 0 && Math.abs(dX) < 1.8;
                case DOWN -> dY > 0 && Math.abs(dX) < 1.8;
                case LEFT -> dX > 0 && Math.abs(dY) < 1.8;
                case RIGHT -> dX < 0 && Math.abs(dY) < 1.8;
                default -> false;
            };
        }
        return false;
    }

    private boolean isNearCounter() {
        GameLocation currGL = App.getMe().getCurrentGameLocation();

        Class<?> relatedClazz = currGL.getType().getRelatedClazz();

        if (currGL.getType().isIndoor() && relatedClazz != null && Store.class.isAssignableFrom(relatedClazz)) {
            Store store = App.getCurrentUser().getCurrentGame().findStoreByClass(
                (Class<? extends Store>) relatedClazz
            );
            return store.getNPCposition().isNear(App.getMe().getPosition(), 4);
        }

        return false;
    }


    private boolean zoj = true;

    public void update(float delta) {
        Player player = game.getCurrentPlayer();
        float vx = 0, vy = 0;
        Direction dir = null;
        if (isCheatWindowOpen) {
            player.setMovingDirection(dir);
            float speed = player.getSpeed();
            player.setMovingDirection(dir);
            player.setVelocity(vx * speed, vy * speed);
            player.update(delta);
            return;
        }
        if (keysHeld.contains(Input.Keys.N)) {
            GameController.manageNextTurn();
            StardewValley.getGameView().updateMap();
        }


        if (keysHeld.contains(Input.Keys.W)) {
            vy += 1;
            dir = Direction.UP;
        }
        if (keysHeld.contains(Input.Keys.S)) {
            vy -= 1;
            dir = Direction.DOWN;
        }
        if (keysHeld.contains(Input.Keys.A)) {
            vx -= 1;
            dir = Direction.LEFT;
        }
        if (keysHeld.contains(Input.Keys.D)) {
            vx += 1;
            dir = Direction.RIGHT;
        }

        float ph = 0.8f;
        float pw = 0.8f;
        Position pos = App.getMe().getPosition();

        if (stopMoving) {
            vy = 0;
            vx = 0;
        }

        // RIGHT
        if (vx == 1 &&
            !player.getCurrentGameLocation().getTileByPosition(pos.getX() + pw + (vx / 8), pos.getY()).isWalkable() ||
            !player.getCurrentGameLocation().getTileByPosition(pos.getX() + pw + (vx / 8), pos.getY() + ph).isWalkable()
        ) {
            vx = 0;
        }

        //LEFT
        if (vx == -1 &&
            !player.getCurrentGameLocation().getTileByPosition(pos.getX() + (vx / 8), pos.getY()).isWalkable() ||
            !player.getCurrentGameLocation().getTileByPosition(pos.getX() + (vx / 8), pos.getY() + ph).isWalkable()
        ) {
            vx = 0; //can not move left
        }

        // UP
        if (vy == 1 &&
            !player.getCurrentGameLocation().getTileByPosition(pos.getX() + pw, pos.getY() + ph + (vy / 8)).isWalkable() ||
            !player.getCurrentGameLocation().getTileByPosition(pos.getX(), pos.getY() + ph + (vy / 8)).isWalkable()
        ) {
            vy = 0;//can not move up
        }

        //DOWN
        if (vy == -1 &&
            !player.getCurrentGameLocation().getTileByPosition(pos.getX() + pw, pos.getY() + (vy / 8)).isWalkable() ||
            !player.getCurrentGameLocation().getTileByPosition(pos.getX(), pos.getY() + (vy / 8)).isWalkable()
        ) {
            vy = 0; //can not move down
        }


        if (vx != 0 && vy != 0) {
            float norm = (float) Math.sqrt(vx * vx + vy * vy);
            vx /= norm;
            vy /= norm;
        }

        float speed = player.getSpeed();
        player.setMovingDirection(dir);
        player.setVelocity(vx * speed, vy * speed);
        player.update(delta);
        applyWrapperEffect();
        shopCounterHintActive = isFacingCounter();
        handleFocusedGameObject();
        updateNearbySensitiveObjects();
    }

    private void updateNearbySensitiveObjects() {
        Set<GameObject> newNearbyGameObjects = new HashSet<>();
        for (GameObject gameObject : nearbyGameObjects) {
            SensitiveToPlayer s = (SensitiveToPlayer) gameObject;
            if (gameObject.getPosition().isNear(App.getMe().getPosition(), s.getSensitivityDistance())) {
                newNearbyGameObjects.add(gameObject);
            } else {
                s.onPlayerGetsFar(gameObject.getPosition().distanceTo(App.getMe().getPosition()));
            }
        }
        for (GameObject gameObject : App.getMe().getCurrentGameLocation().getGameObjects()) {
            if (gameObject instanceof SensitiveToPlayer sensitiveObject
                && gameObject.getPosition().isNear(App.getMe().getPosition(), sensitiveObject.getSensitivityDistance()) &&
                !newNearbyGameObjects.contains(gameObject)) {
                if (sensitiveObject.equals(focusedGameObject)) continue;
                newNearbyGameObjects.add(gameObject);
                sensitiveObject.onPlayerGoesNearby(gameObject.getPosition().distanceTo(App.getMe().getPosition()));
            }
        }
        nearbyGameObjects.clear();
        nearbyGameObjects.addAll(newNearbyGameObjects);
    }

    private void handleFocusedGameObject() {
        float pX = App.getMe().getPosition().getX();
        float pY = App.getMe().getPosition().getY();
        GameLocation currGL = App.getMe().getCurrentGameLocation();

        switch (App.getMe().getLastDirection()) {
            case UP -> pY++;
            case DOWN -> pY--;
            case LEFT -> pX--;
            case RIGHT -> pX++;
            default -> {
            }
        }

        pX = Math.min(Math.max(0, pX), currGL.getWidth() - 1);
        pY = Math.min(Math.max(0, pY), currGL.getHeight() - 1);

        GameObject newFocusedGameObject = currGL.getTileByPosition(new Position(pX, pY)).getFixedObject();

        if (!Objects.equals(focusedGameObject, newFocusedGameObject)) {
            if (focusedGameObject instanceof SensitiveToPlayer oldObject)
                oldObject.onPlayerDefocus();
            if (newFocusedGameObject instanceof SensitiveToPlayer newObject)
                newObject.onPlayerFocus();
            focusedGameObject = newFocusedGameObject;
        }
    }


    private void applyWrapperEffect() {

        Position position = App.getMe().getPosition();

        if (App.getMe().getCurrentGameLocation().getTileByPosition(position).getTileType() == TileType.Wrapper &&
            App.getMe().getCurrentGameLocation() instanceof Farm farm
        ) {
            boolean isNearADoor = false;
            for (Building b : farm.getBuildings()) {//FROM A FARM(YOURS OR PARTNERS) TO BUILDING
                if (b.getDoorPosition().isNear(App.getMe().getPosition(), 4)) {
                    isNearADoor = true;
                    if (b instanceof GreenHouse greenHouse && greenHouse.isBroken()) {
                        WarningWindow warning = App.getStardewValley().getGameView().getWarningWindow();
                        warning.showDialog("Warning", "GreenHouse is Broken you can enter as you repair it" +
                            " in carpenter shop", 250);
                        warning.setVisible(true);
                        //TODO dialogue box its broken
                        App.getMe().setPosition(new Position(b.getDoorPosition().getX(), b.getDoorPosition().getY() - 2));
                        App.getMe().setMovingDirection(Direction.UP);
                    } else {
                        StardewValley.getGameView().updateMapWithFade(() -> {
                            App.getMe().setPosition(b.getInitialPosition());//TODO
                            App.getMe().setCurrentGameLocation(b.getIndoor());
                        });
                    }
                }
            }
            if (!isNearADoor) {        //FROM FARM TO TOWN
                StardewValley.getGameView().updateMapWithFade(() -> {
                    App.getMe().setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown());
                    switch (farm.getPosition()) {
                        case LEFT -> App.getMe().setPosition(new Position(3, 56));
                        case RIGHT -> App.getMe().setPosition(new Position(105, 34));
                        case UP -> App.getMe().setPosition(new Position(81, 105));
                        case DOWN -> App.getMe().setPosition(new Position(54, 4));
                        default -> App.getMe().setPosition(new Position(30, 30));
                    }
                });
            }
        } else if (App.getMe().getCurrentGameLocation().getTileByPosition(position).getTileType() == TileType.Wrapper &&
            App.getMe().getCurrentGameLocation() instanceof Town
        ) {
            Player player = App.getMe();
            //From Town to Building
            for (Store store : App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown().getStores()) {
                if (store.getDoorPosition().isNear(App.getMe().getPosition(), 1.5f)) {
                    DateTime now = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime();
                    if (now.getHour() < store.getOpeningHour() || store.getClosingHour() < now.getHour()) {
                        // TODO add warning
                        WarningWindow warning = App.getStardewValley().getGameView().getWarningWindow();
                        warning.showDialog("Warning", "This shop is closed right now.." +
                            " you can enter after " + store.getOpeningHour()
                            + (now.getHour() > store.getClosingHour() ? " AM Tomorrow." : " AM Today.")
                            + "\nShop will be open until " + store.getClosingHour() + " PM", 250);
                        warning.setVisible(true);
                        App.getMe().setPosition(new Position(store.getDoorPosition().getX(), store.getDoorPosition().getY() - 2));
                        App.getMe().setMovingDirection(Direction.UP);
                    } else {
                        StardewValley.getGameView().updateMapWithFade(() -> {
                            App.getMe().setCurrentGameLocation(store.getIndoor());
                            App.getMe().setPosition(store.getInitialPosition());//TODO
                        });
                    }
                }

            }

            //FROM TOWN TO FARM

            if (player.getPosition().isNear(new Position(5, 55), 8)) {
                if (player.getFarmPosition() == FarmPosition.LEFT) {
                    StardewValley.getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                        player.setPosition(new Position(76, 47));
                    });
                } else if (player.getPartner() != null && player.getPartner().getFarmPosition() == FarmPosition.LEFT) {
                    StardewValley.getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                        player.setPosition(new Position(76, 47));
                    });
                } else {
                    //TODO hosdar
                    WarningWindow warning = App.getStardewValley().getGameView().getWarningWindow();
                    warning.showDialog("Warning", "This is not the farm you can enter" +
                        " farm belongs to " + App.getCurrentUser().getCurrentGame().getGameMap().getFarm1().getPlayer().getUser().getName(), 250);
                    player.setPosition(new Position(4, 55));
                }
            } else if (player.getPosition().isNear(new Position(105, 35), 8)) {
                if (player.getFarmPosition() == FarmPosition.RIGHT) {
                    StardewValley.getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                        player.setPosition(new Position(76, 47));
                    });
                } else if (player.getPartner() != null && player.getPartner().getFarmPosition() == FarmPosition.RIGHT) {
                    StardewValley.getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                        player.setPosition(new Position(76, 47));
                    });
                } else {
                    //TODO hoshdar
                    WarningWindow warning = App.getStardewValley().getGameView().getWarningWindow();
                    warning.showDialog("Warning", "This is not the farm you can enter" +
                        " farm belongs to " + App.getCurrentUser().getCurrentGame().getGameMap().getFarm4().getPlayer().getUser().getName(), 250);
                    player.setPosition(new Position(105, 34));
                }
            } else if (player.getPosition().isNear(new Position(81, 107), 10)) {
                if (player.getFarmPosition() == FarmPosition.UP) {
                    StardewValley.getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                        player.setPosition(new Position(41, 63));
                    });
                } else if (player.getPartner() != null && player.getPartner().getFarmPosition() == FarmPosition.UP) {
                    StardewValley.getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                        player.setPosition(new Position(41, 63));
                    });
                } else {
                    //TODO hoshdar
                    WarningWindow warning = App.getStardewValley().getGameView().getWarningWindow();
                    warning.showDialog("Warning", "This is not the farm you can enter" +
                        " farm belongs to " + App.getCurrentUser().getCurrentGame().getGameMap().getFarm2().getPlayer().getUser().getName(), 250);
                    player.setPosition(new Position(81, 104));
                }

            } else if (player.getPosition().isNear(new Position(55, 4), 10)) {
                if (player.getFarmPosition() == FarmPosition.DOWN) {
                    StardewValley.getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                        player.setPosition(new Position(41, 4));
                    });
                } else if (player.getPartner() != null && player.getPartner().getFarmPosition() == FarmPosition.DOWN) {
                    StardewValley.getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                        player.setPosition(new Position(41, 1));
                    });
                } else {
                    //TODO hoshdar
                    WarningWindow warning = App.getStardewValley().getGameView().getWarningWindow();
                    warning.showDialog("Warning", "This is not the farm you can enter.. " +
                        "this farm belongs to: '"
                        + App.getCurrentUser().getCurrentGame().getGameMap().getFarm3().getPlayer().getUser().getName()
                        + "'", 250);
                    player.setPosition(new Position(54, 4));
                }

            }

        }
//        else if (App.getMe().getCurrentGameLocation() instanceof Town &&
//            App.getMe().getCurrentGameLocation().getTileByPosition(App.getMe().getPosition()).getTileType() == TileType.Wrapper) {
//
//        }
        //Current GameLocation is an Indoor
        else if (!(App.getMe().getCurrentGameLocation() instanceof Town) && !(App.getMe().getCurrentGameLocation() instanceof Farm)
            && App.getMe().getCurrentGameLocation().getTileByPosition(App.getMe().getPosition()).getTileType() == TileType.Wrapper
        ) {
            //state one: player is in Indoor located in Town
            for (Store store : App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown().getStores()) {
                if (App.getMe().getCurrentGameLocation().equals(store.getIndoor())) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        App.getMe().setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown());
                        App.getMe().setPosition(new Position(store.getDoorPosition().getX(), store.getDoorPosition().getY() - 2));//TODO
                    });
                }
            }
            //state two: player is in Indoor located in own Farm
            for (Building building : App.getMe().getPlayerFarm().getBuildings()) {
                if (App.getMe().getCurrentGameLocation().equals(building.getIndoor())) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        App.getMe().setPosition(new Position(building.getDoorPosition().getX(), building.getDoorPosition().getY() - 2));//TODO
                        App.getMe().setCurrentGameLocation(App.getMe().getPlayerFarm());
                    });
                }
            }
            //state three: player is in Indoor located in partner Farm
            if (App.getMe().getPartner() != null) {
                for (Building building : App.getMe().getPartner().getPlayerFarm().getBuildings()) {
                    if (App.getMe().getCurrentGameLocation().equals(building.getIndoor())) {
                        App.getStardewValley().getGameView().updateMapWithFade(() -> {
                            App.getMe().setCurrentGameLocation(App.getMe().getPartner().getPlayerFarm());
                            App.getMe().setPosition(new Position(building.getDoorPosition().getX(), building.getDoorPosition().getY() - 2));//TODO
                        });
                    }
                }
            }
        }
    }

    private void performAction(int screenX, int screenY) {
//        OrthographicCamera camera = game.getCamera();
//        camera.update();
//        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));


//        int tileX = (int) (worldCoordinates.x / 16);
//        int tileY = (int) (worldCoordinates.y / 16);

//        int dx = tileX - Math.round(game.getCurrentPlayer().getPosition().getX());
//        int dy = tileY - Math.round(game.getCurrentPlayer().getPosition().getY());
//
//        if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
//            return;
//        }

//        ItemDescriptionId selectedItem = game.getPlayer().getSelectedItem();
//        if (selectedItem != null) {
//            gameController.useItem(selectedItem, new Point(tileX, tileY), game);
//        }
    }

    public boolean isStopMoving() {
        return stopMoving;
    }

    public void setStopMoving(boolean stopMoving) {
        this.stopMoving = stopMoving;
    }

    public boolean isShopCounterHintActive() {
        return shopCounterHintActive;
    }

    public ArrayList<GameObject> getNearbyGameObjects() {
        return nearbyGameObjects;
    }

    public boolean isCheatWindowOpen() {
        return (isCheatWindowOpen = App.getStardewValley().getGameView().getCheatWindow().isVisible());
    }

    public void setCheatWindowOpen(boolean cheatWindowOpen) {
        isCheatWindowOpen = cheatWindowOpen;
    }
}
