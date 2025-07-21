package io.src.view.GameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class InventoryWindow extends Window {
    private Table tabs;
    private Stack contentStack;
    private TextButton itemsTab, equipTab;
    public InventoryWindow(Skin skin) {
        super("Inventory", skin);
        itemsTab = new TextButton("Items", skin);
        equipTab = new TextButton("Equipment", skin);
        ButtonGroup<TextButton> bg = new ButtonGroup<>(itemsTab, equipTab);
        bg.setMinCheckCount(1); bg.setMaxCheckCount(1);
        tabs = new Table(skin);
        tabs.add(itemsTab).pad(2);
        tabs.add(equipTab).pad(2).row();

        Table itemsTable = new Table(skin);
        Table equipTable = new Table(skin);
        // TODO: پر کردن جدول‌ها از موجودی بازیکن
        contentStack = new Stack(itemsTable, equipTable);
        equipTable.setVisible(false);

//        bg.addChangeListener((e, btn) -> {
//            itemsTable.setVisible(itemsTab.isChecked());
//            equipTable.setVisible(equipTab.isChecked());
//        });

        add(tabs).colspan(2).row();
        add(contentStack).expand().fill();
        setSize(400, 300);
        setPosition((Gdx.graphics.getWidth() - getWidth())/2, (Gdx.graphics.getHeight() - getHeight())/2);
        setVisible(false);
    }
}
