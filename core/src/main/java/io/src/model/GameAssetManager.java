package io.src.model;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;




    private GameAssetManager(){

    }

    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }
}
