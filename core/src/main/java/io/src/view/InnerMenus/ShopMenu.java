package io.src.view.InnerMenus;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import io.src.model.GameObject.NPC.NpcProduct;

import java.util.ArrayList;

public class ShopMenu extends Window {
    private Image npcImage;
    private ScrollPane productsScrollPane;
    private ArrayList<NpcProduct> npcProducts;

    public ShopMenu(Skin skin, String npcImageAssetName, ArrayList<NpcProduct> products ) {
        super("", skin, "default4");

        setMovable(false);
    }
}
