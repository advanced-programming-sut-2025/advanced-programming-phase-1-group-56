package io.src.view.GameMenus.ShopMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.Slot;

import java.util.ArrayList;

public class CommonShopMenu extends Window {
    private Image npcImage;
    private ScrollPane ProductsScrollPane;
    private ArrayList<ProductWindow> productsWindows = new ArrayList<>();
    private NpcProduct selectedProduct;

    public CommonShopMenu(Skin skin, String npcImageAssetName, ArrayList<NpcProduct> products, Listener listener) {
        super("", skin, "default4");
//        npcImage = new Image(new Texture(
//            Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(npcImageAssetName))
//        ));

        Texture npcTex = new Texture(Gdx.files.internal("AVATAR/final/" + npcImageAssetName + "/1/avatarProfile.png"));
        npcImage = new Image(npcTex);

        Table productsList = new Table();

        for (NpcProduct product : products) {
            Slot[] slots = product.Find_ItemNeeded();
            Image Item1 = null;
            Image Item2 = null;
            if (slots != null && slots.length == 2) {
                Item1 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[0].getItem().getAssetName()))));
                Item2 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[1].getItem().getAssetName()))));
            } else if (slots != null && slots.length == 1)
                Item1 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[0].getItem().getAssetName()))));

            productsWindows.add(new ProductWindow(skin, product,
                new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(product.Find_AssetName())))),
                Item1,
                new Label("item", skin),
                Item2,
                new Label("item", skin)));
            productsList.add(productsWindows.getLast()).width(800).height(100).padTop(2).padBottom(2).row();
        }

        ProductsScrollPane = new ScrollPane(productsList, skin, "default3");
        ProductsScrollPane.setFadeScrollBars(false);
        ProductsScrollPane.setVariableSizeKnobs(false);
        add(npcImage).width(npcTex.getWidth() * 3).height(npcTex.getHeight() * 3);
        add(ProductsScrollPane).width(1000).height(450);
        setSize(Gdx.graphics.getWidth() / 1.2f, Gdx.graphics.getHeight() / 2f);
        setPosition(Gdx.graphics.getWidth() / 2f - (this.getWidth() / 2f), Gdx.graphics.getHeight() / 2f - (this.getHeight() / 2f));

        setMovable(false);
    }

    public interface Listener {
        void onProductSelected(int index, NpcProduct product);
    }
}
