package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import io.src.controller.GameMenuController.GameController;
import io.src.model.App;
import io.src.model.Game;
import io.src.model.Player;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GameMenuInputAdapter extends InputAdapter {
    private final Game game;
//    private final GameController gameController;
    private final Set<Integer> keysHeld = new HashSet<>();

//    public GameMenuInputAdapter(Game game, GameController gameController) {
//        this.game = game;
////        this.gameController = gameController;
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
        int dir = 0;

        if (keysHeld.contains(Input.Keys.W)) {
            vy += 1;
            dir = 3;
        }
        if (keysHeld.contains(Input.Keys.S)) {
            vy -= 1;
            dir = 1;
        }
        if (keysHeld.contains(Input.Keys.A)) {
            vx -= 1;
            dir = 4;
        }
        if (keysHeld.contains(Input.Keys.D)) {
            vx += 1;
            dir = 2;
        }

        if (!App.getMe().getCurrentGameLocation().getTileByPosition(player.getPosition().getX()+vx , player.getPosition().getY()+vy).isWalkable()){
            vx = 0;
            vy = 0;
        }
        System.out.println(vx + " " + vy);


        if(vx !=0 && vy !=0) {
            vx/=(float) Math.sqrt(2);
            vy/=(float) Math.sqrt(2);
        }



        System.out.println(player.getPosition().getX() + "," + player.getPosition().getY());
        player.setMovingDirection(dir);

        float speed = player.getSpeed();

        player.setVelocity(vx * speed, vy * speed);

        player.update(delta);
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
