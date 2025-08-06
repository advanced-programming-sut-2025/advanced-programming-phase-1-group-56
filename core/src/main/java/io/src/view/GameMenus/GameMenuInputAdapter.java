package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.src.model.App;
import io.src.model.Enums.Direction;
import io.src.model.Enums.FarmPosition;
import io.src.model.Enums.TileType;
import io.src.model.Game;
import io.src.model.MapModule.Buildings.*;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;
import io.src.model.Player;
import java.util.HashSet;
import java.util.Set;


public class GameMenuInputAdapter extends InputAdapter {
    private final Game game;
    //    private final GameController gameController;
    private final Set<Integer> keysHeld = new HashSet<>();
    private boolean stopMoving = false;
    private static GameMenuInputAdapter gameMenuInputAdapter;
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
        if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            int selectedSlot = keycode - Input.Keys.NUM_1;
            game.getCurrentPlayer().setSelectedSlot(selectedSlot);
            return true;
        }

        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            return true;
        }

        if(keycode == Input.Keys.B) {
            if(GameView.getInvWindow().isVisible()) {
                Gdx.input.setInputProcessor(this);
            } else{
                Gdx.input.setInputProcessor(GameView.getStage());
            }
            GameView.getCraftingWindow().setVisible(!GameView.getCraftingWindow().isVisible());
        }

        if(keycode == Input.Keys.E) {
            if(GameView.getInvWindow().isVisible()) {
                Gdx.input.setInputProcessor(this);
            } else{
                GameView.getInvWindow().refreshInventory();
                Gdx.input.setInputProcessor(GameView.getStage());
            }
            GameView.getInvWindow().setVisible(!GameView.getInvWindow().isVisible());
        }

        if(keycode == Input.Keys.F) {
            if(GameView.foodWindow().isVisible()) {
                Gdx.input.setInputProcessor(this);
            } else{
                Gdx.input.setInputProcessor(GameView.getStage());
            }
            GameView.foodWindow().setVisible(!GameView.getInvWindow().isVisible());
        }

//        if (keycode == Input.Keys.N) {
//            gameController.advanceToNextDay();
//        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keysHeld.remove(keycode);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        InventoryBar.getInstance().scrolled(amountX, amountY);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            performAction(screenX, screenY);
            return true;
        }
        return false;
    }

    public void update(float delta) {
        Player player = game.getCurrentPlayer();
        float vx = 0, vy = 0;
        Direction dir = null;

        if (keysHeld.contains(Input.Keys.J)) {
            Tile[][] tiles = App.getMe().getCurrentGameLocation().getTiles();
            Position pos = App.getMe().getPosition();
            for (int i = (int) pos.getY() - 1; i <= pos.getY() + 1; i++) {
                for (int j = (int) pos.getX() - 1; j <= pos.getX() + 1; j++) {
                    tiles[i][j].setWalkable(true);
                }
            }
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
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        App.getMe().setPosition(b.getInitialPosition());//TODO
                        App.getMe().setCurrentGameLocation(b.getIndoor());
                    });
                }
            }
            if (!isNearADoor) {        //FROM FARM TO TOWN
                App.getStardewValley().getGameView().updateMapWithFade(() -> {
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
        }
        else if (App.getMe().getCurrentGameLocation().getTileByPosition(position).getTileType() == TileType.Wrapper &&
            App.getMe().getCurrentGameLocation() instanceof Town
        ) {
            Player player = App.getMe();
            //From Town to Building
            System.out.println("player position : " +App.getMe().getPosition().getX() + " " + App.getMe().getPosition().getY());
            for (Store store : App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown().getStores()) {
                System.out.println(store.getName() + " door position :" + store.getDoorPosition().getX() + " " + store.getDoorPosition().getY());
                if (store.getDoorPosition().isNear(App.getMe().getPosition(), 3)) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        App.getMe().setCurrentGameLocation(store.getIndoor());
                        App.getMe().setPosition(store.getInitialPosition());//TODO
                    });
                }

            }

            //FROM TOWN TO FARM

            if (player.getPosition().isNear(new Position(5, 55), 8)) {
                if (player.getFarmPosition() == FarmPosition.LEFT) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                        player.setPosition(new Position(76, 47));
                    });
                } else if (player.getPartner() != null && player.getPartner().getFarmPosition() == FarmPosition.LEFT) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                        player.setPosition(new Position(76, 47));
                    });
                } else {
                    //TODO hosdar
                    player.setPosition(new Position(4, 55));
                }
            } else if (player.getPosition().isNear(new Position(105, 35), 8)) {
                if (player.getFarmPosition() == FarmPosition.RIGHT) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                        player.setPosition(new Position(76, 47));
                    });
                } else if (player.getPartner() != null && player.getPartner().getFarmPosition() == FarmPosition.RIGHT) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                        player.setPosition(new Position(76, 47));
                    });
                } else {
                    //TODO hoshdar
                    player.setPosition(new Position(105, 34));
                }
            } else if (player.getPosition().isNear(new Position(81, 107), 10)) {
                if (player.getFarmPosition() == FarmPosition.UP) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                        player.setPosition(new Position(41, 63));
                    });
                } else if (player.getPartner() != null && player.getPartner().getFarmPosition() == FarmPosition.UP) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                        player.setPosition(new Position(41, 63));
                    });
                } else {
                    //TODO hoshdar
                    player.setPosition(new Position(81, 104));
                }

            } else if (player.getPosition().isNear(new Position(55, 4), 10)) {
                if (player.getFarmPosition() == FarmPosition.DOWN) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                        player.setPosition(new Position(41, 4));
                    });
                } else if (player.getPartner() != null && player.getPartner().getFarmPosition() == FarmPosition.DOWN) {
                    App.getStardewValley().getGameView().updateMapWithFade(() -> {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                        player.setPosition(new Position(41, 1));
                    });
                } else {
                    //TODO hoshdar
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
}
