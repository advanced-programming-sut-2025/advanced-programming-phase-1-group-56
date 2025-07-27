package io.src.view.GameMenus;

import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.src.controller.GameMenuController.InventoryController;
import io.src.model.*;
import io.src.model.Enums.Items.ToolType;
import io.src.model.Enums.Items.TrashcanType;
import io.src.model.Enums.Skills;
import io.src.model.items.Item;
import io.src.model.items.Tool;
import io.src.model.items.Inventory;
import io.src.model.skills.Skill;

import javax.xml.transform.Source;
import java.util.ArrayList;


public class InventoryWindow extends Group {
    private final Group contentGroup;
    private final Image background;
    private final Player player;
    private final DragAndDrop dragAndDrop = new DragAndDrop();

    public InventoryWindow() {
        this.player = new Player(InventoryController.returnUser("Mohsen"));
        player.getSkillByName("Farming").setLevel(2);
        player.getInventory().add(new Tool(ToolType.AXE_WOODEN), 1);
        player.getInventory().add(new Tool(ToolType.PICK_WOODEN), 1);
        setSize(750, 580);
        setPosition(100, 100);

        background = new Image(GameAssetManager.getGameAssetManager().getInventoryBackGround());
        background.setSize(getWidth(), getHeight() + 10);
        addActor(background);
        contentGroup = new Group();
        addActor(contentGroup);

        addActor(createTopTabs());
        addCloseButton();
        showInventoryTab();
    }

    private Table createTopTabs() {
        Table topTabs = new Table();
        String[] icons = {
            GameAssetManager.getGameAssetManager().getInventoryIcon(), GameAssetManager.getGameAssetManager().getSkillIcon(),
            GameAssetManager.getGameAssetManager().getSocialIcon(), GameAssetManager.getGameAssetManager().getMapIcon(), GameAssetManager.getGameAssetManager().getSettingIcon(), GameAssetManager.getGameAssetManager().getOutIcon()
        };

        final Array<ImageButton> tabButtons = new Array<>();
        final ImageButton[] selectedButton = {null};

        for (int i = 0; i < icons.length; i++) {
            final int index = i;
            Texture icon = new Texture(icons[i]);
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(icon);
            final ImageButton tabBtn = new ImageButton(style);
            tabButtons.add(tabBtn);
            tabBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedButton[0] != tabBtn) {
                        if (selectedButton[0] != null) {
                            selectedButton[0].setPosition(
                                selectedButton[0].getX(),
                                selectedButton[0].getY() + 5
                            );
                        }

                        tabBtn.setPosition(tabBtn.getX(), tabBtn.getY() - 5);
                        selectedButton[0] = tabBtn;

                        contentGroup.clear();
                        switch (index) {
                            case 0:
                                showInventoryTab();
                                break;
                            case 1:
                                showSkillsTab();
                                break;
                            case 2:
                                showSocialTab();
                                break;
                            case 3:
                                showMapTab();
                                break;
                            case 4:
                                showSettingsTab();
                                break;
                            case 5:
                                showExitTab();
                                break;
                        }
                    }
                }
            });
            topTabs.add(tabBtn).pad(0).size(32);
        }
        topTabs.setPosition(120, getHeight() + 10);
        return topTabs;
    }
    private void showSettingsTab(){
        background.setDrawable(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getTmpBackground()));
        //TODO
    }

    private void showInventoryTab() {
        background.setDrawable(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getInventoryBackGround()));
        background.setSize(getWidth(), getHeight() + 10);
        //TODO
        Label statsLabel = new Label("Farm\ncurrent funds: \nDay " + 1 + " of " + "Spring" + " ,Year " + 1, GameAssetManager.getGameAssetManager().getSkin());
        statsLabel.setPosition(360, 100);
        Label nameLabel = new Label("Name: Mohsen", GameAssetManager.getGameAssetManager().getSkin());
        nameLabel.setPosition(75, 44);
        contentGroup.addActor(statsLabel);
        contentGroup.addActor(nameLabel);
        Image trashCan = InventoryController.trashCanImage(player);
        trashCan.setOrigin(Align.center);
        trashCan.setScale(1.2f);
        trashCan.setPosition(750, 270);
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
                player.getInventory().getSlots().remove(fromIndex);
                contentGroup.clear();
                showInventoryTab();
            }
        });
        //TODO
        contentGroup.addActor(trashCan);
        Table table = createInventoryTable(player.getInventory(), dragAndDrop);
        table.setPosition(363, 440);
        contentGroup.addActor(table);
    }


    private void showSkillsTab() {
        background.setDrawable(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getSkillBackground()));
        background.setSize(getWidth(), getHeight());
        int j = -70;
        int countLevel = 0;

        for (int i = 0; i < 4; i++) {
            Skill skill = player.getSkills().get(i);
            countLevel += skill.getLevel();
            Table skillTable = InventoryController.showSkillsLevel(skill.getLevel());
            skillTable.setPosition(508, 520 + j * i);
            contentGroup.addActor(skillTable);
        }
        Label label  = new Label("Level: "+countLevel/4, GameAssetManager.getGameAssetManager().getSkin());
        label.setPosition(280, 50);
        contentGroup.addActor(label);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

        Image hoverArea1 = new Image(drawable);
        Image hoverArea2 = new Image(drawable);
        Image hoverArea3 = new Image(drawable);
        Image hoverArea4 = new Image(drawable);

        hoverArea1.setSize(30, 30);
        hoverArea2.setSize(30, 30);
        hoverArea3.setSize(30, 30);
        hoverArea4.setSize(30, 30);

        hoverArea1.setPosition(282, 505);
        hoverArea2.setPosition(282, 435);
        hoverArea3.setPosition(282, 365);
        hoverArea4.setPosition(282, 295);

        Color baseColor = new Color(1f, 1f, 1f, 0.1f);
        hoverArea1.setColor(baseColor);
        hoverArea2.setColor(baseColor);
        hoverArea3.setColor(baseColor);
        hoverArea4.setColor(baseColor);

        Image farmingHover = new Image(GameAssetManager.getGameAssetManager().getFarmingHover());
        Image miningHover = new Image(GameAssetManager.getGameAssetManager().getMiningHover());
        Image fishingHover = new Image(GameAssetManager.getGameAssetManager().getFishingHover());
        Image foragingHover = new Image(GameAssetManager.getGameAssetManager().getForagingHover());

        farmingHover.setSize(80, 80);
        miningHover.setSize(80, 80);
        fishingHover.setSize(80, 80);
        foragingHover.setSize(80, 80);

        TooltipManager manager = TooltipManager.getInstance();
        manager.initialTime = 0f;
        manager.subsequentTime = 0f;
        manager.resetTime = 0f;
        manager.offsetX = 10f;
        manager.offsetY = 10f;
        Tooltip<Image> tooltip1 = new Tooltip<>(farmingHover);
        Tooltip<Image> tooltip2 = new Tooltip<>(miningHover);
        Tooltip<Image> tooltip3 = new Tooltip<>(fishingHover);
        Tooltip<Image> tooltip4 = new Tooltip<>(foragingHover);

        hoverArea1.addListener(tooltip1);
        hoverArea2.addListener(tooltip2);
        hoverArea3.addListener(tooltip4);
        hoverArea4.addListener(tooltip3);

        InputListener hoverEffect = new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                event.getListenerActor().setColor(1, 1, 1, 0.3f);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                event.getListenerActor().setColor(1, 1, 1, 0.1f);
            }
        };

        hoverArea1.addListener(hoverEffect);
        hoverArea2.addListener(hoverEffect);
        hoverArea3.addListener(hoverEffect);
        hoverArea4.addListener(hoverEffect);

        contentGroup.addActor(hoverArea1);
        contentGroup.addActor(hoverArea2);
        contentGroup.addActor(hoverArea3);
        contentGroup.addActor(hoverArea4);

    }

    private void showSocialTab() {
        background.setDrawable(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getTmpBackground()));
    }

    private void showMapTab() {
        background.setDrawable(new TextureRegionDrawable(new Texture("assets/Inventory/miniMap.png")));
        background.setSize(getWidth(), getHeight());
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            showInventoryTab();
        }
    }

    private void showExitTab() {
        background.setDrawable(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getExitBackground()));
        background.setSize(getWidth(), getHeight());

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        style.imageDown = new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getExitMenuButtonUp());
        style.imageUp = new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getExitMenuButtonDown());
        style.imageOver = new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getExitMenuButtonUp());

        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();

        style2.imageDown = new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getExitToDesktopUp());
        style2.imageUp = new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getExitToDesktopDown());
        style2.imageOver = new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getExitToDesktopUp());

        ImageButton imageButton = new ImageButton(style);
        ImageButton imageButton2 = new ImageButton(style2);

        imageButton.setPosition(230, 334);
        imageButton2.setPosition(205, 199);


        contentGroup.addActor(imageButton);
        contentGroup.addActor(imageButton2);
    }


    private void addCloseButton() {
        Texture closeTex = new Texture("assets/Inventory/exit.png");
        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(closeTex));
        closeButton.setSize(24, 24);
        closeButton.setPosition(getWidth(), getHeight());
        addActor(closeButton);
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

            Image itemImage = null;
            if (i < slots.size()) {
                Slot slot = slots.get(i);
                Item item = slot.getItem();
                int quantity = slot.getQuantity();
                Label label = null;
                stack.setTouchable(Touchable.enabled);

                addDragAndDrop(dragAndDrop, stack, i, inventory);

                if (item != null && quantity > 0) {
                    itemImage = new Image(new Texture("assets/Axe.png"));
                    itemImage.setOrigin(Align.center);
                    itemImage.setScale(0.8f);
                    itemImage.setSize(SLOT_SIZE - 30, SLOT_SIZE - 30);

                    label = new Label(String.valueOf(quantity), GameAssetManager.getGameAssetManager().getSkin());
                    label.setFontScale(0.6f);
                    label.setAlignment(Align.bottomRight);

                    stack.add(itemImage);
                    stack.add(label);
                    stack.setTouchable(Touchable.enabled);

                    Image finalItemImage = itemImage;
                    stack.addListener(new InputListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            if (finalItemImage != null) {
                                finalItemImage.clearActions();
                                finalItemImage.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                            }
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            if (finalItemImage != null) {
                                finalItemImage.clearActions();
                                finalItemImage.addAction(Actions.scaleTo(0.8f, 0.8f, 0.1f));
                            }
                        }
                    });
                }
            }
            if (i < 12) {
                inventoryTable.add(stack).size(SLOT_SIZE).padBottom(25);
            } else {
                inventoryTable.add(stack).size(SLOT_SIZE).padBottom(5);
            }
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
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(index);
                Texture itemTexture = new Texture("assets/Axe.png");
                Image dragImage = new Image(itemTexture);
                dragImage.setSize(50, 50);
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
                    contentGroup.clear();
                    showInventoryTab();
                }
            }
        });
    }



}


