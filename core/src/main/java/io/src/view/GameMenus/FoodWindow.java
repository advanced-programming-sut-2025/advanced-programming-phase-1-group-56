package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import io.src.controller.GameMenuController.CookingController;
import io.src.controller.GameMenuController.CraftingController;
import io.src.controller.GameMenuController.InventoryController;
import io.src.model.App;
import io.src.model.Enums.Items.FoodType;
import io.src.model.Enums.Recepies.CraftingRecipesList;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.GameAssetManager;
import io.src.model.Player;
import io.src.model.Slot;
import io.src.model.items.CraftingTool;
import io.src.model.items.Food;
import io.src.model.items.Inventory;
import io.src.model.items.Item;

import java.time.Clock;
import java.util.ArrayList;

import static io.src.controller.GameMenuController.CookingController.*;
import static io.src.controller.GameMenuController.CraftingController.*;

public class FoodWindow extends Group implements InputProcessor {
    private Group group;
    private Image background;
    private Player player;
    private DragAndDrop dragAndDrop = new DragAndDrop();
    private Table table;
    private Label errorLabel;
    private Table infoPanel;
    private ArrayList<FoodRecipesList> unlocked = haveFoodRecipes();
    private ArrayList<FoodRecipesList> canCraft = foodNotCraftingRecipes();
    private ArrayList<FoodRecipesList> all = returnFoodRecipes();

    public FoodWindow(Player player) {
        this.player = player;
        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorLabel.setAlignment(Align.center);
        errorLabel.setPosition(375, 30);
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

        table = createInventoryTable(App.getMe().getInventory(), dragAndDrop);
        table.setPosition(375, 130);
        group.addActor(table);
        group.addActor(errorLabel);
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
            {10, 450},{25,450}, {120, 470}, {230, 440}, {330, 460}, {430, 440},
            {520, 470}, {620, 450}, {80, 370}, {180, 350},{210,350}, {270, 340},
            {370, 380}, {470, 350}, {570, 350}, {670, 360}, {50, 270},
            {150, 270}, {250, 270}, {350, 280}, {450, 270}, {550, 260},
            {650, 260},
        };

        for (int i = 0; i < all.size(); i++) {
            FoodRecipesList recipe = all.get(i);
            String assetName = recipe.getAssetName();
            Texture itemTexture = assetName.equals("Triple_shot_Espresso")
                ? GameAssetManager.getGameAssetManager().getTripleShotEspresso()
                : new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName)));

            Image icon = new Image(itemTexture);
            Stack stack = new Stack();
            stack.setSize(60, 84);
            stack.setTransform(true);
            stack.add(icon);

            stack.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    updateInfoPanel(recipe);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    infoPanel.setVisible(false);
                }
            });

            addRecipeDragAndDrop(stack, recipe);

//            if (!unlocked.contains(recipe) && canCraft.contains(recipe)) {
//                stack.addListener(new ClickListener() {
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//                        System.out.println("You don't have enough ingredients!");
//                    }
//                });
//            }


            if (i < positions.length) {
                float x = positions[i][0];
                float y = positions[i][1];
                stack.setPosition(x, y);
            }

            recipesTable.addActor(stack);
        }

        return recipesTable;
    }
    private void updateInfoPanel(FoodRecipesList recipe) {
        infoPanel.clear();
        Label nameLabel = new Label(recipe.name(), GameAssetManager.getGameAssetManager().getSkin());
        nameLabel.setFontScale(1f);
        nameLabel.setColor(Color.GOLD);

        Label ingredientsLabel = new Label(CookingController.Ingredients(recipe), GameAssetManager.getGameAssetManager().getSkin());
        ingredientsLabel.setFontScale(0.8f);
        ingredientsLabel.setColor(Color.WHITE);

        String assetName = recipe.getAssetName();
        Texture previewTexture = assetName.equals("Triple_shot_Espresso")
            ? GameAssetManager.getGameAssetManager().getTripleShotEspresso()
            : new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName)));
        Image previewImage = new Image(previewTexture);
        previewImage.setSize(48, 48);

        infoPanel.add(previewImage).pad(5).row();
        infoPanel.add(nameLabel).pad(2).row();
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

    private Table createInventoryTable(Inventory inventory, DragAndDrop dragAndDrop) {
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
                addDragAndDrop(dragAndDrop, stack, i, inventory);
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

    private void addDragAndDrop(DragAndDrop dragAndDrop, Actor actor, int index, Inventory inventory) {
        dragAndDrop.addTarget(new DragAndDrop.Target(actor) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (payload.getObject() instanceof Integer) {
                    int fromIndex = (int) payload.getObject();
                    int toIndex = index;

                    if (fromIndex != toIndex) {
                        Slot fromSlot = inventory.getSlots().get(fromIndex);
                        Slot toSlot = inventory.getSlots().get(toIndex);

                        if (toSlot.getItem() == null || toSlot.getQuantity() == 0) {
                            toSlot.setItem(fromSlot.getItem());
                            toSlot.setQuantity(fromSlot.getQuantity());
                            fromSlot.setItem(null);
                            fromSlot.setQuantity(0);
                        } else {
                            InventoryController.swapSlots(inventory, fromIndex, toIndex);
                        }

                        refreshInventory();
                    }

                } else if (payload.getObject() instanceof FoodRecipesList) {
                    FoodRecipesList recipe = (FoodRecipesList) payload.getObject();
                    if (!unlocked.contains(recipe) && !canCraft.contains(recipe)) {
                        showErrorLabel("it's not open for you!");
                    } else if (unlocked.contains(recipe)) {
                        FoodType foodType = FoodType.fromName(recipe.getName());
                        Item food = new Food(foodType);
                        if (inventory.add(food, 1)) {
                            App.getCurrentUser().
                                getCurrentGame()
                                .getCurrentPlayer()
                                .subtractEnergy(3);
                            showErrorLabel("Crafted: " + food.getAssetName());
                            refreshInventory();
                        } else {
                            showErrorLabel("Inventory is full!");
                        }
                    } else {
                        showErrorLabel("Not enough ingredients!");
                    }
                }

            }
        });
        dragAndDrop.addSource(new DragAndDrop.Source(actor) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                Slot slot = inventory.getSlots().get(index);
                Item item = slot.getItem();
                int quantity = slot.getQuantity();

                if (item == null || quantity <= 0) return null;

                Texture texture = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(item.getAssetName())));
                Image dragImage = new Image(texture);
                dragImage.setSize(50, 50);

                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(index);
                payload.setDragActor(dragImage);

                return payload;
            }
        });

    }

    private void addRecipeDragAndDrop(Stack stack, FoodRecipesList recipe) {
        dragAndDrop.addSource(new DragAndDrop.Source(stack) {
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                System.out.println("Start drag: " + recipe.name());
                Texture texture = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(recipe.getAssetName())));
                Image dragImage = new Image(texture);
                dragImage.setSize(50, 50);
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(recipe);
                payload.setDragActor(dragImage);
                return payload;
            }
        });

    }


    public void refreshInventory() {
        table.clear();
        table = createInventoryTable(App.getMe().getInventory(), dragAndDrop);
        table.setPosition(375, 120);
        group.addActor(table);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.F) {
            if(GameView.foodWindow().isVisible()) {
                Gdx.input.setInputProcessor(GameView.getGameMenuInputAdapter());
            }
            GameView.foodWindow().setVisible(!GameView.foodWindow().isVisible());
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
