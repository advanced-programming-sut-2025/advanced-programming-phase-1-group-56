package io.src.view.GameMenus.ShopMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.StardewValley;
import io.src.controller.GameMenuController.ShopMenuControllers.ShopController;
import io.src.controller.GameMenuController.TradeController;
import io.src.model.App;
import io.src.model.GameAssetManager;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.Buildings.Store;
import io.src.model.Result;
import io.src.model.Slot;
import io.src.view.GameMenus.WarningWindow;

import java.util.ArrayList;

public class CommonShopMenu extends Window {
    private Image npcImage;
    private ScrollPane ProductsScrollPane;
    private ArrayList<ProductWindow> productsWindows = new ArrayList<>();
    private ArrayList<NpcProduct> products;
    private NpcProduct selectedProduct;

    public CommonShopMenu(Skin skin, String npcImageAssetName, ArrayList<NpcProduct> products, Listener listener) {
        super("", skin, "default4");
//        npcImage = new Image(new Texture(
//            Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(npcImageAssetName))
//        ));

        this.products = products;

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

                System.out.println(product.getName());
                System.out.println(product.Find_AssetName());

            ProductWindow productWindow = new ProductWindow(skin, product,
                new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(product.Find_AssetName())))),
                Item1,
                new Label("item", skin),
                Item2,
                new Label("item", skin));
            productsWindows.add(productWindow);
            productWindow.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectedProduct = productWindow.getProduct();
                    handleSelectedProduct();
                }
            });
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

    public void handleSelectedProduct() {
        if(selectedProduct.getRemainingStock()==0)
            return;
        switch (StardewValley.getGameView().getShopStateWindow().getShopState()) {
            case SHOP: {
                Result result = ShopController.purchaseProductFromList(selectedProduct.getName(),"1",products);
                StardewValley.getGameView().getWarningWindow().showDialog(App.getMe().getCurrentGameLocation().getType().getRelatedClazz().getSimpleName(),result.getMessage(),100);
            }
            break;
            case UPGRADE_TOOL: {

            }
            break;
            case PURCHASE_ANIMAL: {

            }
            break;
            case BUILD_A_BUILDING: {

            }
            break;
            default: {
            }
            break;

        }
    }

    public interface Listener {
        void onProductSelected(int index, NpcProduct product);
    }
}
