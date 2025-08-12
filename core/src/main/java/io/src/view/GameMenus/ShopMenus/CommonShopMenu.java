package io.src.view.GameMenus.ShopMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.src.StardewValley;
import io.src.controller.GameMenuController.ShopMenuControllers.BlacksmithMenuController;
import io.src.controller.GameMenuController.ShopMenuControllers.CarpenterMenuController;
import io.src.controller.GameMenuController.ShopMenuControllers.MarniesRanchController;
import io.src.controller.GameMenuController.ShopMenuControllers.ShopController;
import io.src.model.*;
import io.src.model.Enums.Animals.AnimalType;
import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.view.InnerMenus.AnimalNameDialog;
import io.src.view.InnerMenus.BuildingMiniMenu;

import java.util.ArrayList;

public class CommonShopMenu extends Window {
    private Image npcImage;
    private ScrollPane ProductsScrollPane;
    //    private ArrayList<ProductWindow> productsWindows = new ArrayList<>();
    private ArrayList<NpcProduct> products;
    private NpcProduct selectedProduct;
    private Skin skin;

    public CommonShopMenu(Skin skin, String npcImageAssetName, ArrayList<NpcProduct> products, Listener listener) {
        super("", skin, "default4");
        this.skin = skin;
//        npcImage = new Image(new Texture(
//            Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(npcImageAssetName))
//        ));

        this.products = products;

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
            Slot[] items = new Slot[2];
            items[0] = null;
            items[1] = null;

            if (slots != null && slots.length == 2) {
                Item1 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[0].getItem().getAssetName()))));
                item1Name = new Label("x" + slots[0].getQuantity(), skin);
                Item2 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[1].getItem().getAssetName()))));
                item2Name = new Label("x" + slots[1].getQuantity(), skin);
                items[0] = slots[0];
                items[1] = slots[1];
            } else if (slots != null && slots.length == 1) {
                Item1 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[0].getItem().getAssetName()))));
                item1Name = new Label("x" + slots[0].getQuantity(), skin);
                items[0] = slots[0];
            }
            //DEBUG
            System.out.println(product.getName());
            System.out.println(product.Find_ItemNeeded());
            System.out.println(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(product.Find_AssetName()));
            ProductWindow productWindow = new ProductWindow(skin, product,
                new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(product.Find_AssetName())))),
                Item1,
                item1Name,
                items[0],
                Item2,
                item2Name,
                items[1]
            );
            productWindow.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectedProduct = productWindow.getProduct();
                    handleSelectedProduct();
                }
            });
            productsList.add(productWindow).width(800).height(100).padTop(2).padBottom(2).row();

        }

        ProductsScrollPane = new ScrollPane(productsList, skin, "default3");
        ProductsScrollPane.setFadeScrollBars(false);
        ProductsScrollPane.setVariableSizeKnobs(false);
        add(npcImage).width(npcTex.getWidth() * 3).height(npcTex.getHeight() * 3).top().padRight(25);
        add(ProductsScrollPane).width(885).height(450);
        setSize(Gdx.graphics.getWidth() / 1.2f, Gdx.graphics.getHeight() / 2f);
        setPosition(Gdx.graphics.getWidth() / 2f - (this.getWidth() / 2f), Gdx.graphics.getHeight() / 2f - (this.getHeight() / 2f));

        Button exitButton = new Button(skin, "closeButton");
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);
                CommonShopMenu.this.remove();
                App.getMe().setShopState(ShopState.WAIT);
            }
        });
        add(exitButton).padTop(-25).top();

        setMovable(false);
        StardewValley.getGameView().getStage().setScrollFocus(ProductsScrollPane);
        setVisible(false);
    }

    public void handleSelectedProduct() {
        if (selectedProduct.getRemainingStock() == 0)
            return;
        switch (App.getMe().getShopState()) {
            case SHOP: {
                Result result = ShopController.purchaseProductFromList(selectedProduct.getSaleable().getName(), "1", products);
                StardewValley.getGameView().getWarningWindow().showDialog(App.getMe().getCurrentGameLocation().getType().getRelatedClazz().getSimpleName(), result.getMessage(), 180);
                updateProductsShow();
            }
            break;
            case UPGRADE_TOOL: {
                Result result = BlacksmithMenuController.upgradeTools(selectedProduct, products);
                StardewValley.getGameView().getWarningWindow().showDialog(App.getMe().getCurrentGameLocation().getType().getRelatedClazz().getSimpleName(), result.getMessage(), 300);
                StardewValley.getGameView().getInventoryBar().refreshInventory();
                StardewValley.getGameView().getInvWindow().refreshInventory();
                updateProductsShow();
            }
            break;
            case PURCHASE_ANIMAL: {
                AnimalNameDialog dialog = new AnimalNameDialog(
                    SkinManager.getInstance().getSkin("mainSkin/mainSkin.json"),
                    name -> {
                        Result result = MarniesRanchController.buyAnimal(((AnimalType) selectedProduct.getSaleable()).getName(), name);
                        this.setVisible(false);
                        StardewValley.getGameView().getWarningWindow().showDialog(
                            App.getMe().getCurrentGameLocation().getType().getRelatedClazz().getSimpleName(),
                            result.getMessage(),
                            300
                        );
                        updateProductsShow();
                    }
                );
                dialog.showCentered(StardewValley.getGameView().getStage());
            }
            break;
            case BUILD_A_BUILDING: {
                BuildingMiniMenu buildingMiniMenu = new BuildingMiniMenu(skin, (x, y) -> {
                    Result result = CarpenterMenuController.BuildABuilding(( selectedProduct.getSaleable()).getName(),(BuildingType) selectedProduct.getSaleable(), x, y);
                    // show result
                    StardewValley.getGameView().getWarningWindow().showDialog(
                        App.getMe().getCurrentGameLocation().getType().getRelatedClazz().getSimpleName(),
                        result.getMessage(), 300);
                    updateProductsShow();
                    // close parent shop window if needed:
                });
                buildingMiniMenu.showCentered(StardewValley.getGameView().getStage());
            }
            break;
            default: {
                StardewValley.getGameView().getWarningWindow().showDialog(App.getMe().getCurrentGameLocation().getType().getRelatedClazz().getSimpleName(), "bug happened,how do you even get here?", 300);
            }
            break;

        }
    }

    public interface Listener {
        void onProductSelected(int index, NpcProduct product);
    }

    public void showDialog() {
        setVisible(true);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(true);
    }

    public void hideDialog() {
        setVisible(false);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(true);
    }

    public void updateProductsShow() {
        Table productsList = new Table();
        productsList.align(Align.left);
        for (NpcProduct product : products) {
            Slot[] slots = product.Find_ItemNeeded();
            Image Item1 = null;
            Image Item2 = null;
            Label item1Name = null;
            Label item2Name = null;
            Slot[] items = new Slot[2];
            items[0] = null;
            items[1] = null;
            if (slots != null && slots.length == 2) {
                Item1 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[0].getItem().getAssetName()))));
                item1Name = new Label("x" + slots[0].getQuantity(), skin);
                Item2 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[1].getItem().getAssetName()))));
                item2Name = new Label("x" + slots[1].getQuantity(), skin);
                items[0] = slots[0];
                items[1] = slots[1];
            } else if (slots != null && slots.length == 1) {
                Item1 = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(slots[0].getItem().getAssetName()))));
                item1Name = new Label("x" + slots[0].getQuantity(), skin);
                items[0] = slots[0];
            }

            ProductWindow productWindow = new ProductWindow(skin, product,
                new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getAssetsDictionary().get(product.Find_AssetName())))),
                Item1,
                item1Name,
                items[0],
                Item2,
                item2Name,
                items[1]
            );
            productWindow.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectedProduct = productWindow.getProduct();
                    handleSelectedProduct();
                }
            });
            productsList.add(productWindow).width(800).height(100).padTop(2).padBottom(2).row();
        }
        ProductsScrollPane.setWidget(productsList);
        ProductsScrollPane.layout();
    }
}
