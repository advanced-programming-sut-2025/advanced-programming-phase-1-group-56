package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import io.src.model.App;
import io.src.model.GameAssetManager;
import io.src.model.Slot;
import io.src.model.items.Inventory;
import io.src.model.items.Item;

import java.util.ArrayList;

public class InventoryBar extends Group {
    private static InventoryBar instance;
    private Table inventoryTable;
    private static int selectedIndex = 0;
    private static final int VISIBLE_SLOTS = 12;
    private final ArrayList<Image> itemImages = new ArrayList<>();
    private final Label selectedItemLabel;

    public InventoryBar() {
        setPosition((Gdx.graphics.getWidth() - 800) / 2f, 100);

        Image background = new Image(GameAssetManager.getGameAssetManager().getInventoryBarBackground());
        background.setSize(800, 94);
        background.setPosition(0, 0);
        addActor(background);

        inventoryTable = createInventoryTable(App.getMe().getInventory());
        inventoryTable.setPosition(398, 48);
        addActor(inventoryTable);
        selectedItemLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        selectedItemLabel.setFontScale(0.7f);
        selectedItemLabel.setPosition(400, 20); // کنار inventory
        selectedItemLabel.setVisible(false);
        addActor(selectedItemLabel);
    }

    public static InventoryBar getInstance() {
        if (instance == null) {
            instance = new InventoryBar();
        }
        return instance;
    }

    private Table createInventoryTable(Inventory inventory) {
        final float SLOT_SIZE = 64f;
        Table table = new Table();
        int totalSlots = 36;
        int capacity = inventory.getBackPackType().getCapacity();
        ArrayList<Slot> slots = inventory.getSlots();

        for (int i = 0; i < totalSlots; i++) {
            Stack stack = new Stack();
            Image slotImage = (i < capacity)
                ? new Image(GameAssetManager.getGameAssetManager().getEmptySlot())
                : new Image(GameAssetManager.getGameAssetManager().getLockSlot());
            stack.add(slotImage);

            if (i < slots.size()) {
                Slot slot = slots.get(i);
                Item item = slot.getItem();
                if (item != null && slot.getQuantity() > 0) {
                    String assetPath = GameAssetManager.getGameAssetManager()
                        .getAssetsDictionary().get(item.getAssetName());
                    Image itemImage = new Image(new Texture(Gdx.files.internal(assetPath)));
                    itemImage.setOrigin(Align.center);
                    itemImage.setScale(0.8f);
                    itemImage.setSize(SLOT_SIZE - 30, SLOT_SIZE - 30);
                    itemImages.add(itemImage);

                    Label label = new Label(String.valueOf(slot.getQuantity()),
                        GameAssetManager.getGameAssetManager().getSkin());
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
            }

            if (i < VISIBLE_SLOTS) {
                table.add(stack).size(SLOT_SIZE);
            }
        }

        return table;
    }

    public boolean scrolled(float amountX, float amountY) {
        if (amountY > 0) {
            selectedIndex = (selectedIndex + 1) % VISIBLE_SLOTS;
        } else if (amountY < 0) {
            selectedIndex = (selectedIndex - 1 + VISIBLE_SLOTS) % VISIBLE_SLOTS;
        }

        if (selectedIndex >= App.getMe().getInventory().getSlots().size()) {
            selectedIndex = 0;
        }
        if (selectedIndex < 0) {
            selectedIndex = App.getMe().getInventory().getSlots().size() - 1;
        }

        updateCurrentItem();
        return true;
    }

    private void updateCurrentItem() {
        Inventory inventory = App.getMe().getInventory();
        Slot slot = inventory.getSlots().get(selectedIndex);
        App.getMe().setCurrentItem(slot.getItem());
        if (slot.getItem() != null) {
            selectedItemLabel.setText(slot.getItem().getName());
            selectedItemLabel.setColor(1, 1, 1, 1);
            selectedItemLabel.setVisible(true);
            selectedItemLabel.clearActions();
            selectedItemLabel.addAction(
                Actions.sequence(
                    Actions.delay(3f),
                    Actions.fadeOut(0.5f),
                    Actions.visible(false)
                )
            );
        } else {
            selectedItemLabel.setVisible(false);
        }

        System.out.println(App.getMe().getCurrentItem().getName());
    }

    public void refreshInventory() {
        inventoryTable.remove();
        itemImages.clear();
        inventoryTable = createInventoryTable(App.getMe().getInventory());
        inventoryTable.setPosition(398, 48);
        addActor(inventoryTable);
    }
}
