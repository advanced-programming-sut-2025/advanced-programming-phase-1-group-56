package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import io.src.model.App;
import io.src.model.Enums.Direction;
import io.src.model.Enums.TileType;
import io.src.model.Game;
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
        applyPositionEffect();
    }


    private void applyPositionEffect() {
        Position position = App.getMe().getPosition();
        if (App.getMe().getCurrentGameLocation().getTileByPosition(position).getTileType() == TileType.Wrapper&&
            App.getMe().getCurrentGameLocation() instanceof Farm
        ) {
            App.getMe().setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown());
            App.getStardewValley().getGameView().updateMap();
            App.getMe().setPosition(new Position(30,30));
        }else if (App.getMe().getCurrentGameLocation().getTileByPosition(position).getTileType() == TileType.Wrapper&&
            App.getMe().getCurrentGameLocation() instanceof Town
        ) {
            App.getMe().setCurrentGameLocation(App.getMe().getPlayerFarm());
            App.getStardewValley().getGameView().updateMap();
            App.getMe().setPosition(new Position(30,30));
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
}
