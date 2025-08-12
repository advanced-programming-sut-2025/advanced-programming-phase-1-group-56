package io.src.view.InnerMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.Animal;

public class AnimalMenu extends Window {
    public AnimalMenu(Skin skin, Animal animal) {
        super("", skin);

        // fields
        System.out.println(animal.getAssetName());
        System.out.println(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(animal.getAssetName()));
        Image image = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(animal.getAssetName()))));
        Table animalProfileTable = new Table(skin);
        animalProfileTable.add(image);
        animalProfileTable.add(new Label(animal.getName(), skin, "labelBack-24_ZERESHK")).bottom();
        add(animalProfileTable);

        TextButton feedButton = new TextButton("FEED", skin, "button1-2_font30GREEN");
        TextButton petButton = new TextButton("PET", skin, "button1-2_font30GREEN");
        TextButton homeButton = new TextButton("HOME", skin, "button1-2_font30GREEN");
        TextButton freeButton = new TextButton("FREE", skin, "button1-2_font30");
        TextButton sellButton = new TextButton("SELL", skin, "button1-2_font30");

        Table home_freeTable = new Table();
        home_freeTable.add(homeButton);
        home_freeTable.add(freeButton);
        freeButton.setVisible(false);

        Table buttonTable = new Table();
        buttonTable.add(feedButton);
        buttonTable.add(petButton).row();
        buttonTable.add(home_freeTable);
        buttonTable.add(sellButton);

    }
}
