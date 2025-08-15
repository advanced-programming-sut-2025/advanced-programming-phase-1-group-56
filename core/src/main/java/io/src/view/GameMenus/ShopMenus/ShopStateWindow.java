package io.src.view.GameMenus.ShopMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import io.src.StardewValley;
import io.src.model.App;
import io.src.model.Enums.GameLocationType;
import io.src.model.Enums.SfxEnum;
import io.src.model.GameAudioManager;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.Buildings.Store;
import io.src.model.SkinManager;
import io.src.view.GameMenus.ScreenTransition;
import io.src.view.GameMenus.ShopMenus.CommonShopMenu.Listener;
import org.junit.runner.Computer;

import java.util.ArrayList;
import java.util.Arrays;

public class ShopStateWindow extends Window {
    private final TextButton button1;
    private final TextButton button2;
    private final TextButton button3;
    private Stage stage;


    public ShopStateWindow(Skin skin) {
        super("", skin);
        this.stage = stage;
        setMovable(false);
        setMovable(true);

        setBounds((float) (Gdx.graphics.getWidth() / 6), 100, (float) (Gdx.graphics.getWidth() * 2) / 3, 180);

        padTop(0);
        padLeft(0);
        padRight(0);
        padBottom(0);
        getTitleTable().clear();
        App.getMe().setShopState(ShopState.WAIT);
        button1 = new TextButton("Shop", skin, "default_BLACK_font30");
        button2 = new TextButton("costumeShop", skin, "default_BLACK_font30");
        button3 = new TextButton("Exit Shop", skin, "default_BLACK_font30");


        add(button1).padTop(15).expand().fill().row();
        add(button2).expand().fill().row();
        add(button3).padBottom(15).expand().fill().row();
        button1.align(Align.left);
        button2.align(Align.left);
        button3.align(Align.left);

        button1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideDialog();
                prepareCommonShop(false);
            }
        });

        button2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideDialog();
                prepareCommonShop(true);
            }
        });

        button3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGameView().getShopStateWindow().setVisible(false);
                hideDialog();
            }
        });

    }

    public void prepareCommonShop(boolean costumeShop) {
        Class<? extends Store> clazz = App.getMe().getCurrentGameLocation().getType().getRelatedClazz();

        if (Store.class.isAssignableFrom(clazz)) {
            Store store = (Store) App.getCurrentUser().getCurrentGame().findStoreByClass(clazz.asSubclass(Store.class));
            ArrayList<NpcProduct> products = new ArrayList<>();
            for (NpcProduct npcProduct : store.getDailyProductList()) {
                if (costumeShop ^ npcProduct.isShopProduct())
                    products.add(npcProduct);
            }
            if (costumeShop) {
                setShopState(switch (App.getMe().getCurrentGameLocation().getType()) {
                    case Blacksmith_Indoor -> ShopState.UPGRADE_TOOL;
                    case MarniesRanch_Indoor -> ShopState.PURCHASE_ANIMAL;
                    case CarpenterShop_Indoor -> ShopState.BUILD_A_BUILDING;
                    default -> ShopState.NOT_SHOP;
                });
            } else {
                setShopState(ShopState.SHOP);
            }
            Open_Common_Shop(products);

        }

    }


    public ShopState getShopState() {
        return App.getMe().getShopState();
    }

    public void setShopState(ShopState shopState) {
        App.getMe().setShopState(shopState);
    }

    public void showOnStage(Stage stage) {
        stage.addActor(this);
        this.setVisible(true);
    }


    public void Open_Common_Shop(ArrayList<NpcProduct> products) {
        CommonShopMenu commonShopMenu = new CommonShopMenu(
            SkinManager.getInstance().getSkin(SkinManager.MAIN_SKIN),
            App.getMe().getCurrentGameLocation().getType().getStoreAvatarName(),
            products, new Listener() {
            @Override
            public void onProductSelected(int index, NpcProduct product) {

            }
        });
        StardewValley.getGameView().getStage().addActor(commonShopMenu);

        this.hideDialog();
        commonShopMenu.showDialog();
    }

    private void hideDialog() {
        this.setVisible(false);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(false);
    }

    public void showDialog() {
        GameAudioManager.getInstance().playSound(GameAudioManager.pickRandom(Arrays.asList(
            SfxEnum.VILLAGER_YES1.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES2.getPath()
            , SfxEnum.VILLAGER_YES3.getPath()
            , SfxEnum.VILLAGER_NO1.getPath()
            , SfxEnum.VILLAGER_NO2.getPath()
            , SfxEnum.VILLAGER_NO3.getPath()
        )), false, GameAudioManager.ambientVolume);
        StardewValley.getGameView().getGameMenuInputAdapter().setInterruptingMenuOpen(true);
        setVisible(true);
        GameLocationType glType = App.getMe().getCurrentGameLocation().getType();
        if (glType != GameLocationType.Blacksmith_Indoor && glType != GameLocationType.MarniesRanch_Indoor && glType != GameLocationType.CarpenterShop_Indoor) {
            prepareCommonShop(false);
        }

        String costumeShop = switch (App.getMe().getCurrentGameLocation().getType()) {
            case Blacksmith_Indoor -> "Upgrade Tool";
            case MarniesRanch_Indoor -> "Buy Animal";
            case CarpenterShop_Indoor -> "Buy A Building";
            default -> "fuck";
        };
        this.button2.setText(costumeShop);
    }
}
