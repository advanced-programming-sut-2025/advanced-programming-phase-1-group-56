package io.src.view.GameMenus.ShopMenus;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.src.model.GameObject.NPC.NpcProduct;

public class ProductWindow extends Window {
    private final int MAX_CHARACTER = 270;
    private Image productImage;
    private Label productName;
    private Image item1;
    private Image item2;
    private Label item1Name;
    private Label item2Name;
    private Label price;
    private TextTooltip descriptionTooltip;

    public ProductWindow(Skin skin, NpcProduct product, Image productImage, Image item1, Label item1Name, Image item2, Label item2Name) {
        super("", skin, "ProductWindow");
        this.productImage = productImage;
        this.item1 = item1;
        this.item2 = item2;
        this.item1Name = item1Name;
        this.item2Name = item2Name;
        this.productName = new Label(product.getName(), skin);
        this.price = new Label(product.getPrice() + "", skin);

        align(Align.left);

        TooltipManager tooltipManager = TooltipManager.getInstance();
        tooltipManager.initialTime = 0.5f;
        tooltipManager.hideAll();

        descriptionTooltip = new TextTooltip(product.getDescription(), skin);
        this.addListener(descriptionTooltip);
        add(productImage).width(40).height(40).padLeft(27);
        add(productName).width(MAX_CHARACTER).padLeft(35);
        add(item1).width(40).height(40).padLeft(20);
        add(item1Name).padLeft(20);
        add(item2).width(40).height(40).padLeft(20);
        add(item2Name).padLeft(20);
        add(price).padLeft(20).align(Align.right);

        setMovable(false);
    }
}
