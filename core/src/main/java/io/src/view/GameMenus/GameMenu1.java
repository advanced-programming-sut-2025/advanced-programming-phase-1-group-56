package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import io.src.controller.GameMenuController.GameController;
import io.src.model.Game;

public class GameMenu1 implements Screen {
    private GameView gameView;
    public Game myGame ;
    private GameMenuInputAdapter gameMenuInputAdapter;
    private GameController gameController;


    public GameMenu1(GameController gameController) {
        myGame = gameController.getGame().getGame();
        this.gameController = gameController;
        initializeGame();
    }

    private void initializeGame() {

//        TimeSystem timeSystem = new TimeSystem(1, 9);
////        myGame.setTimeSystem(timeSystem);// 1/4 set
//
//
//        ArrayList<Player> playersToPlay = new ArrayList<>();
//
//        String mohsen = "mohsen";
//        String  wolf = "wolf";
//        Player player = new Player(new User(mohsen,mohsen,mohsen,mohsen,mohsen,2,mohsen,true));
//        Player player2 = new Player(new User(wolf,wolf,wolf,wolf,wolf,3,wolf,false));
//        ArrayList<Player> players = new ArrayList<>();
//        players.add(player);
//        players.add(player2);
//
//
//        WeatherState weatherState = new WeatherState();
////        myGame.setWeatherState(weatherState);// 2/4 set
////        myGame.setPlayers(players);
//        GameMap gameMap = new GameMap();
////        myGame.setGameMap(gameMap);
////        myGame.setCurrentPlayer(player);
//        setCursor();
//        myGame = new Game(players,gameMap,timeSystem,weatherState);
        gameView = new GameView(myGame);
        gameMenuInputAdapter = new GameMenuInputAdapter(myGame, gameController);
        Gdx.input.setInputProcessor(gameMenuInputAdapter);
    }
    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        myGame.update(delta);
        gameView.render(delta);
        gameMenuInputAdapter.update(delta);
    }

    @Override
    public void resize(int width, int height) {

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

    }

    private void setCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("cursors/cusor1.png"));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, 1, 1);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
    }
}
