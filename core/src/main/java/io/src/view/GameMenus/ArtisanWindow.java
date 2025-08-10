package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.src.controller.GameMenuController.ArtisanController;
import io.src.controller.GameMenuController.CookingController;
import io.src.controller.GameMenuController.InventoryController;
import io.src.model.*;
import io.src.model.Enums.Items.*;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.GameObject.ArtesianMachine;
import io.src.model.items.Food;
import io.src.model.items.Inventory;
import io.src.model.items.Item;

import java.util.ArrayList;
import java.util.List;

import static io.src.controller.GameMenuController.CookingController.*;

public class ArtisanWindow extends Group implements InputProcessor {
    private Group group;
    private Image background;
    private DragAndDrop dragAndDrop = new DragAndDrop();
    private Table table;
    private Label errorLabel;
    private Table infoPanel;
    private List<ArtisanGoodType> all;
    private ArtesianMachine artesianMachine;
    private Label label;

    public ArtisanWindow(ArtesianMachine artesianMachine) {
        this.artesianMachine = artesianMachine;
        all =  artesianMachine.getArtisanMachineType().getProducts();
        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorLabel.setAlignment(Align.center);
        errorLabel.setPosition(375, 30);
        label = new Label("click to get goods!", GameAssetManager.getGameAssetManager().getSkin());
        label.setAlignment(Align.center);
        label.setPosition(500, 245);
        setSize(750, 580);
        setPosition((Gdx.graphics.getWidth() - 750) / 2f, (Gdx.graphics.getHeight() - 580) / 2f);
        group = new Group();

        background = new Image(GameAssetManager.getGameAssetManager().getCraftingBackground());
        background.setSize(getWidth(), getHeight());
        group.addActor(background);

        Group recipesTable = buildRecipesTable();
        recipesTable.setPosition(0, 0);
        recipesTable.setSize(750, 580);
        group.addActor(recipesTable);

        table = createInventoryTable(App.getMe().getInventory());
        table.setPosition(375, 138);
        group.addActor(table);
        group.addActor(errorLabel);
        group.addActor(label);
        addActor(group);
    }

    private Group buildRecipesTable() {
        Group recipesTable = new Group();
        infoPanel = new Table();
        infoPanel.setBackground(new TextureRegionDrawable(new TextureRegion(
            GameAssetManager.getGameAssetManager().getToolTipBackground()
        )));
        infoPanel.setVisible(false);
        infoPanel.setSize(180, 220);
        infoPanel.setPosition(getWidth() + 10, getHeight() - 400);
        group.addActor(infoPanel);

        float[][] positions = new float[][]{
            {100, 450}, {120, 370}, {230, 380}, {330, 460}, {430, 330},
            {520, 470}, {200,300}
        };

        for (int i = 0; i < all.size(); i++) {
            ArtisanGoodType recipe = all.get(i);
            String assetName = recipe.getAssetName();
            Texture itemTexture = new Texture(Gdx.files.internal(
                GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName)
            ));

            Image icon = new Image(itemTexture);
            Stack stack = new Stack();
            stack.setSize(60, 84);
            stack.add(icon);

            // Hover → نمایش infoPanel
            stack.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    updateInfoPanel(recipe);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    infoPanel.setVisible(false);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (button == Input.Buttons.LEFT) {
                        System.out.println("yes");
                        Result result = tryMakeArtisanGoodWithResult(recipe);
                        showErrorLabel(result.getMessage());
                    }
                    return false;
                }
            });

            if (i < positions.length) {
                stack.setPosition(positions[i][0], positions[i][1]);
            }

            recipesTable.addActor(stack);
        }

        return recipesTable;
    }

    private void updateInfoPanel(ArtisanGoodType recipe) {
        infoPanel.clear();

        Label nameLabel = new Label(recipe.getName(), GameAssetManager.getGameAssetManager().getSkin());
        nameLabel.setFontScale(1f);
        nameLabel.setColor(Color.GOLD);

        Label descriptionLabel = new Label(recipe.getDescription(), GameAssetManager.getGameAssetManager().getSkin());
        descriptionLabel.setFontScale(0.7f);
        StringBuilder ingText = new StringBuilder();

        descriptionLabel.setColor(Color.WHITE);
        if(recipe.getIngredients() == null){
            ingText.append("No ingredients!");
        } else{
            if (recipe.getIngredients() != null) {
                for (Slot slot : recipe.getIngredients()) {
                    ingText.append(slot.getQuantity())
                        .append("x ")
                        .append(slot.getItem().getName())
                        .append("\n");
                }
            }
        }

        Label ingredientsLabel = new Label(ingText.toString(), GameAssetManager.getGameAssetManager().getSkin());
        ingredientsLabel.setFontScale(0.7f);
        ingredientsLabel.setColor(Color.WHITE);


        Texture previewTexture = new Texture(Gdx.files.internal(
            GameAssetManager.getGameAssetManager().getAssetsDictionary().get(recipe.getAssetName())
        ));
        Image previewImage = new Image(previewTexture);
        previewImage.setSize(48, 48);

        infoPanel.add(previewImage).pad(5).row();
        infoPanel.add(nameLabel).pad(2).row();
        infoPanel.add(descriptionLabel).pad(3).row();
        infoPanel.add(ingredientsLabel).pad(5).row();

        infoPanel.pack();
        infoPanel.setVisible(true);
    }




    private void showErrorLabel(String message) {
        errorLabel.setText(message);
        errorLabel.setColor(Color.RED);
        errorLabel.setVisible(true);
        errorLabel.getColor().a = 1f;

        errorLabel.clearActions();
        errorLabel.addAction(Actions.sequence(
            Actions.delay(2f),
            Actions.fadeOut(0.5f),
            Actions.run(() -> {
                errorLabel.setVisible(false);
                errorLabel.getColor().a = 1f;
            })
        ));
    }

    private Table createInventoryTable(Inventory inventory) {
        final float SLOT_SIZE = 54f;
        Table inventoryTable = new Table();
        inventoryTable.defaults().padRight(1.5f);
        int totalSlots = 36;
        int capacity = inventory.getBackPackType().getCapacity();
        ArrayList<Slot> slots = inventory.getSlots();

        for (int i = 0; i < totalSlots; i++) {
            Stack stack = new Stack();

            Image slotImage = (i < capacity)
                ? new Image(GameAssetManager.getGameAssetManager().getEmptySlot())
                : new Image(GameAssetManager.getGameAssetManager().getLockSlot());
            stack.add(slotImage);

            while (slots.size() <= i && i < capacity) {
                slots.add(new Slot(null, 0));
            }

            Slot slot = null;
            Item item = null;
            int quantity = 0;
            if(i<capacity){
                slot = slots.get(i);
                item = slot.getItem();
                quantity = slot.getQuantity();
            }

            if (item != null && quantity > 0) {
                Image itemImage = new Image(new Texture(Gdx.files.internal(
                    GameAssetManager.getGameAssetManager().getAssetsDictionary().get(item.getAssetName())
                )));
                itemImage.setOrigin(Align.center);
                itemImage.setScale(0.8f);
                itemImage.setSize(SLOT_SIZE - 30, SLOT_SIZE - 30);

                Label label = new Label(String.valueOf(quantity), GameAssetManager.getGameAssetManager().getSkin());
                label.setFontScale(0.6f);
                label.setAlignment(Align.bottomRight);

                stack.add(itemImage);
                stack.add(label);

                stack.addListener(new InputListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        itemImage.clearActions();
                        itemImage.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        itemImage.clearActions();
                        itemImage.addAction(Actions.scaleTo(0.8f, 0.8f, 0.1f));
                    }
                });
            }

            inventoryTable.add(stack).size(SLOT_SIZE).padBottom(5);
            if ((i + 1) % 12 == 0) inventoryTable.row();
        }

        return inventoryTable;
    }


    public void refreshInventory() {
        table.clear();
        table = createInventoryTable(App.getMe().getInventory());
        table.setPosition(375, 135);
        group.addActor(table);
    }

    private Result tryMakeArtisanGoodWithResult(ArtisanGoodType product) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();

        // Check if all required ingredients exist in player's inventory
        if (product.getIngredients() != null) {
            for (Slot ingredient : product.getIngredients()) {
                String ingredientName = ingredient.getItem().getName();
                boolean foundIngredient = false;

                if (ingredientName.equals("Any Fish")) {
                    // Check if player has any fish with enough quantity
                    boolean anyFishFound = false;
                    for (Slot playerSlot : player.getInventory().getSlots()) {
                        if (playerSlot.getItem() != null
                            && FishType.fromName(playerSlot.getItem().getName()) != null
                            && playerSlot.getQuantity() >= ingredient.getQuantity()) {
                            anyFishFound = true;
                            break;
                        }
                    }
                    if (!anyFishFound) return new Result(false, "Not enough fish in inventory.");
                    else foundIngredient = true;

                } else if (ingredientName.equals("Any Fruit")) {
                    // Check if player has any fruit with enough quantity
                    boolean anyFruitFound = false;
                    for (Slot playerSlot : player.getInventory().getSlots()) {
                        if (playerSlot.getItem() != null
                            && FruitType.fromName(playerSlot.getItem().getName()) != null
                            && playerSlot.getQuantity() >= ingredient.getQuantity()) {
                            anyFruitFound = true;
                            break;
                        }
                    }
                    if (!anyFruitFound) return new Result(false, "Not enough fruit in inventory.");
                    else foundIngredient = true;

                } else if (ingredientName.equals("Any Ore")) {
                    // Check if player has any ore with enough quantity
                    ArrayList<String> ores = new ArrayList<>();
                    ores.add(MineralItemType.COPPER_ORE.getName());
                    ores.add(MineralItemType.IRON_ORE.getName());
                    ores.add(MineralItemType.GOLD_ORE.getName());
                    ores.add(MineralItemType.IRIDIUM_ORE.getName());

                    boolean anyOreFound = false;
                    for (String oreName : ores) {
                        if (player.getInventory().countItemByName(oreName, ingredient.getQuantity())) {
                            anyOreFound = true;
                            break;
                        }
                    }
                    if (!anyOreFound) return new Result(false, "Not enough ore in inventory.");
                    else foundIngredient = true;
                }

                if (!foundIngredient) {
                    // Check if player has specific item with enough quantity
                    if (!player.getInventory().countItemByName(ingredient.getItem().getName(), ingredient.getQuantity())) {
                        return new Result(false, "Not enough " + ingredientName + " in inventory.");
                    }
                }
            }


            // All ingredients exist, remove them now
            for (Slot ingredient : product.getIngredients()) {
                String ingredientName = ingredient.getItem().getName();

                if (ingredientName.equals("Any Fish")) {
                    for (Slot playerSlot : player.getInventory().getSlots()) {
                        if (playerSlot.getItem() != null
                            && FishType.fromName(playerSlot.getItem().getName()) != null
                            && playerSlot.getQuantity() >= ingredient.getQuantity()) {
                            player.getInventory().remove(playerSlot.getItem(), ingredient.getQuantity());
                            break;
                        }
                    }
                } else if (ingredientName.equals("Any Fruit")) {
                    for (Slot playerSlot : player.getInventory().getSlots()) {
                        if (playerSlot.getItem() != null
                            && FruitType.fromName(playerSlot.getItem().getName()) != null
                            && playerSlot.getQuantity() >= ingredient.getQuantity()) {
                            player.getInventory().remove(playerSlot.getItem(), ingredient.getQuantity());
                            break;
                        }
                    }
                } else if (ingredientName.equals("Any Ore")) {
                    ArrayList<String> ores = new ArrayList<>();
                    ores.add(MineralItemType.COPPER_ORE.getName());
                    ores.add(MineralItemType.IRON_ORE.getName());
                    ores.add(MineralItemType.GOLD_ORE.getName());
                    ores.add(MineralItemType.IRIDIUM_ORE.getName());

                    for (String oreName : ores) {
                        if (player.getInventory().countItemByName(oreName, ingredient.getQuantity())) {
                            Item oreItem = player.getInventory().findItemByName(oreName);
                            player.getInventory().remove(oreItem, ingredient.getQuantity());
                            break;
                        }
                    }
                } else {
                    player.getInventory().remove(ingredient.getItem(), ingredient.getQuantity());
                }
            }


        }
        if(artesianMachine.getProcessTime()==0){
            artesianMachine.startMakeArtisanGood(product);
            player.subtractEnergy(product.getEnergy());
            return new Result(true, "Started crafting artisan product.");
        } else{
            return new Result(true, "we are in progress!");
        }

    }



    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.U) {
            if(GameView.artisanWindow().isVisible()) {
                Gdx.input.setInputProcessor(GameView.getGameMenuInputAdapter());
            }
            GameView.artisanWindow().setVisible(!GameView.artisanWindow().isVisible());
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
