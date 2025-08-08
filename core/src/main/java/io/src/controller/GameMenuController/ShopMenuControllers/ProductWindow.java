package io.src.controller.GameMenuController.ShopMenuControllers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class ProductWindow extends Window {
    private Image productImage;
    private Label productName;
    private Image item1;
    private Image item2;
    private Label item1Name;
    private Label item2Name;
    private Label price;
    private Window descriptionTooltip;

    public ProductWindow(String title, Skin skin) {
        super(title, skin);
    }

}
