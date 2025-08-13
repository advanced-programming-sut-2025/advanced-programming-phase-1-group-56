package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import io.src.StardewValley;
import io.src.controller.GameMenuController.InventoryController;
import io.src.controller.GameMenuController.TradeController;
import io.src.model.App;
import io.src.model.GameAssetManager;
import io.src.model.SkinManager;
import io.src.model.Slot;
import io.src.model.items.Food;
import io.src.model.items.Inventory;
import io.src.model.items.Item;

import java.util.ArrayList;

public class ShippingBarWindow extends Group implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.T) {
            if (StardewValley.getGameView().getShippingBarWindow().isVisible()) {
                Gdx.input.setInputProcessor(StardewValley.getGameView().getMultiplexer());
            }
            StardewValley.getGameView().getShippingBarWindow().setVisible(!StardewValley.getGameView().getShippingBarWindow().isVisible());
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

    private Group group;
    private Image background;
    private Label errorLabel;
    private DragAndDrop dragAndDrop = new DragAndDrop();
    private Table playerTable;

    ShippingBarWindow() {
        group = new Group();
        errorLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        errorLabel.setAlignment(Align.center);
        errorLabel.setPosition(470, 36);

        setSize(940, 640);
        setPosition((Gdx.graphics.getWidth() - 940) / 2f, (Gdx.graphics.getHeight() - 640) / 2f);
        Inventory playerInven = App.getMe().getInventory();


        playerTable = createInventoryTable(playerInven, dragAndDrop);
        playerTable.setPosition(410, 100);
        group.addActor(playerTable);
        group.addActor(errorLabel);
        createTrash();
        addActor(group);

        // Exit button
        Button exitButton = new Button(SkinManager.getInstance().getSkin(SkinManager.MAIN_SKIN), "closeButton");
        exitButton.setPosition(getWidth() - 120, getHeight() - 500);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setVisible(false);
                Gdx.input.setInputProcessor(StardewValley.getGameView().getMultiplexer());
            }
        });

        addActor(exitButton);
    }

    private void createTrash() {
        Image trashCan = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get("Shipping_Bin_Opened"))));
        ;
        trashCan.setOrigin(Align.center);
        trashCan.setScale(1.2f);
        trashCan.setPosition(950, 270);
        trashCan.setSize(50, 80);
        Image finalTrashCan = trashCan;
        trashCan.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                finalTrashCan.addAction(Actions.scaleTo(1.5f, 1.5f, 0.15f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                finalTrashCan.addAction(Actions.scaleTo(1.2f, 1.2f, 0.15f));
            }
        });
        Image finalTrashCan1 = trashCan;
        dragAndDrop.addTarget(new DragAndDrop.Target(finalTrashCan1) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                finalTrashCan1.setColor(Color.RED);
                return true;
            }

            @Override
            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                finalTrashCan1.setColor(Color.WHITE);
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                int fromIndex = (int) payload.getObject();
                if (App.getMe().getInventory().getSlots().get(fromIndex) == null) return;
                if (App.getMe().getInventory().getSlots().get(fromIndex).getItem() == null) return;
                TradeController.sellProducts(App.getMe().getInventory().getSlots().get(fromIndex).getItem().getName(), String.valueOf(1));

                refreshInventory();
            }
        });
        addActor(finalTrashCan1);
    }

    private Table createInventoryTable(Inventory inventory, DragAndDrop dragAndDrop) {
        final float SLOT_SIZE = 60f;
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
            if (i < capacity) {
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
        dragAndDrop.addSource(new DragAndDrop.Source(actor) {
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                System.out.println("drag started");
                Slot slot = inventory.getSlots().get(index);
                Item item = slot.getItem();
                if (item == null) return null;
                String assetName = item.getAssetName();
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(index);
                Texture itemTexture = new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(assetName)));
                Image dragImage = new Image(itemTexture);
                dragImage.setSize(50, 50);

                dragImage.setOrigin(Align.center);
                dragImage.setPosition(100, 100);
                payload.setDragActor(dragImage);
                return payload;
            }

        });

        dragAndDrop.addTarget(new DragAndDrop.Target(actor) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                int fromIndex = (int) payload.getObject();
                int toIndex = index;

                if (fromIndex != toIndex) {
                    InventoryController.swapSlots(inventory, fromIndex, toIndex);
                    refreshInventory();
                }
            }
        });
    }


    public void refreshInventory() {
        playerTable.clear();
        playerTable = createInventoryTable(App.getMe().getInventory(), dragAndDrop);
        playerTable.setPosition(422, 120);
        group.addActor(playerTable);
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
}
