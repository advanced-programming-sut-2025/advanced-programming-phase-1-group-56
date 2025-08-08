package io.src.view.GameMenus.ShopMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.src.controller.GameMenuController.ShopMenuControllers.ProductWindow;
import io.src.model.App;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.NPC.NpcProduct;

import java.util.ArrayList;

public class CommonShopMenu extends Window {
    private Image npcImage;
    private ScrollPane ProductsScrollPane;
    private ArrayList<ProductWindow> productsWindows = new ArrayList<>();
    private NpcProduct selectedProduct;

    public CommonShopMenu(Skin skin, String npcImageAssetName, ArrayList<NpcProduct> products) {
        super("", skin, "default4");
        npcImage = new Image(new Texture(
            Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(npcImageAssetName))
        ));

    }

    public ArrayList<Window> getProductsWindows() {
        return productsWindows;
    }

    public void setProductsWindows(ArrayList<Window> productsWindows) {
        this.productsWindows = productsWindows;
    }
}
