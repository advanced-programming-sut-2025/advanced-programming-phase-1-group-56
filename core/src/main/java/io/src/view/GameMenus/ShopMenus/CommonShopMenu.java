package io.src.view.GameMenus.ShopMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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
        productsList.align(Align.left);
        for (NpcProduct product : products) {
            Slot[] slots = product.Find_ItemNeeded();
            Image Item1 = null;
            Image Item2 = null;
            Label item1Name = null;
            Label item2Name = null;
            if (slots != null && slots.length == 2) {
                Item1 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[0].getItem().getAssetName()))));
                item1Name = new Label(slots[0].getItem().getName(), skin);
                Item2 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[1].getItem().getAssetName()))));
                item2Name = new Label(slots[1].getItem().getName(), skin);
            } else if (slots != null && slots.length == 1) {
                Item1 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[0].getItem().getAssetName()))));
                item1Name = new Label(slots[0].getItem().getName(), skin);
            }

            productsWindows.add(new ProductWindow(skin, product,
                new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(product.Find_AssetName())))),
                Item1,
                item1Name,
                Item2,
                item2Name));
            productsList.add(productsWindows.getLast()).width(800).height(100).padTop(2).padBottom(2).padLeft(-1).row();
        }

        ProductsScrollPane = new ScrollPane(productsList, skin, "default3");
        ProductsScrollPane.setFadeScrollBars(false);
        ProductsScrollPane.setVariableSizeKnobs(false);
        add(npcImage).width(npcTex.getWidth() * 3).height(npcTex.getHeight() * 3).top();
        add(ProductsScrollPane).width(885).height(450);
        setSize(Gdx.graphics.getWidth() / 1.2f, Gdx.graphics.getHeight() / 2f);
        setPosition(Gdx.graphics.getWidth() / 2f - (this.getWidth() / 2f), Gdx.graphics.getHeight() / 2f - (this.getHeight() / 2f));

        Button exitButton = new Button(skin, "closeButton");
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                CommonShopMenu.this.remove();
            }
        });
        add(exitButton).padTop(-25).top();

        setMovable(false);
    }

    public interface Listener {
        void onProductSelected(int index, NpcProduct product);
    }
}
