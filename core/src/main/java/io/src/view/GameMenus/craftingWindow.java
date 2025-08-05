package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.model.App;
import io.src.model.Enums.Recepies.CraftingRecipesList;
import io.src.model.GameAssetManager;
import io.src.model.Player;
import io.src.model.Slot;

import java.util.ArrayList;

import static io.src.controller.GameMenuController.CraftingController.*;

public class craftingWindow extends Group {
    private Group group;
    private Image background;
    private Player player;

    public craftingWindow(Player player) {
        this.player = player;

        setSize(750, 580);
        setPosition((Gdx.graphics.getWidth() - 750) / 2f, (Gdx.graphics.getHeight() - 580) / 2f);

        player.addToolRecipes(CraftingRecipesList.BeeHouse);
        player.addToolRecipes(CraftingRecipesList.Bomb);

        group = new Group();

        background = new Image(GameAssetManager.getGameAssetManager().getCraftingBackground());
        background.setSize(getWidth(), getHeight());
        group.addActor(background);

        Table recipesTable = buildRecipesTable();
        recipesTable.setPosition(0, 0);
        recipesTable.setSize(750, 580);
        group.addActor(recipesTable);

        addActor(group);
    }

    private Table buildRecipesTable() {
        ArrayList<CraftingRecipesList> unlocked = haveCraftingRecipes();
        ArrayList<CraftingRecipesList> canCraft = havenotCraftingRecipes();
        ArrayList<CraftingRecipesList> all = returnCraftingRecipes();

        Table table = new Table();
        table.defaults().pad(10f);
        table.defaults().padRight(60f);

        int numCols = 5;

        for (int i = 0; i < all.size(); i++) {
            CraftingRecipesList recipe = all.get(i);
            String assetName = recipe.getAssetName();
            System.out.println(assetName);
            Texture itemTexture = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName)));
            Image icon = new Image(itemTexture);
            icon.setSize(70, 100);

            Stack stack = new Stack();
            stack.add(icon);

            if (unlocked.contains(recipe)) {
                stack.setTouchable(Touchable.enabled);
                stack.addListener(createClickListener(recipe));
            } else if (canCraft.contains(recipe)) {
                Image overlay = new Image(getBlurOverlay(0.5f));
                overlay.setSize(20, 50);
                stack.add(overlay);
                stack.addListener(createClickListener(recipe));
            } else {
                Image overlay = new Image(getBlurOverlay(0.8f));
                overlay.setSize(50, 50);
                stack.add(overlay);
            }

            table.add(stack).size(70, 90);

            if ((i + 1) % numCols == 0) {
                table.row();
            }
        }

        return table;
    }

    private Texture getBlurOverlay(float alpha) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, alpha));
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private ClickListener createClickListener(CraftingRecipesList recipe) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showRecipeIngredients(recipe);
            }
        };
    }

    private void showRecipeIngredients(CraftingRecipesList recipe) {
        if (!havaIngredient(recipe)) {
            System.out.println("You don't have enough ingredients!");
        } else {
            System.out.println(12);
            StringBuilder ingredients = new StringBuilder("Ingredients:\n");
            for (Slot ingredient : recipe.ingredients) {
                ingredients.append(ingredient.getItem().getName())
                    .append(": ").append(ingredient.getQuantity()).append("\n");
            }
            System.out.println(ingredients.toString());
        }
    }
}
