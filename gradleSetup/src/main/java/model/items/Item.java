package model.items;

import model.Enums.Items.ItemQuality;

import model.App;
import model.Enums.Items.ItemQuality;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Buildings.*;

public abstract class Item implements Saleable {
    protected boolean Stackable;
    protected int maxStackSize;
    protected String name;
    protected int price;
    protected ItemQuality itemQuality;

    public Item(String name, int maxStackSize, boolean Stackable, int price) {
        this.name = name;
        this.maxStackSize = maxStackSize;
        this.Stackable = Stackable;
        this.price = price;
        this.itemQuality = ItemQuality.Normal;
    }

    public void deleteItem() {
    }

    public void dropItem() {
    }

    public String getName() {
        return name;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getPrice() {
        return price;
    }

    public void setItemQuality(ItemQuality itemQuality) {
        this.itemQuality = itemQuality;
    }

    public ItemQuality getItemQuality() {
        return itemQuality;
    }

    public double getQualityMultiplier() {
        return switch (this.itemQuality) {
            case Normal -> 1.0;
            case Silver -> 1.2;
            case Gold -> 1.5;
            case Iridium -> 2;
        };
    }

    public int getFinalPrice() {
        int basePrice;
        if (price > 0) {
            basePrice = price;
        } else {
            basePrice = -1;

            //1
            NpcProduct product =
                    App.getCurrentUser().getCurrentGame().
                            findStoreByClass(Blacksmith.class).getProductByName(this.name);
            if (product != null) {
                basePrice = product.getPrice()/2;
            }
            //2
            product =
                    App.getCurrentUser().getCurrentGame().
                            findStoreByClass(CarpentersShop.class).getProductByName(this.name);
            if (product != null) {
                basePrice = product.getPrice()/2;
            }
            //3
            product =
                    App.getCurrentUser().getCurrentGame().
                            findStoreByClass(FishShop.class).getProductByName(this.name);
            if (product != null) {
                basePrice = product.getPrice()/2;
            }
            //4
            product =
                    App.getCurrentUser().getCurrentGame().
                            findStoreByClass(PierresGeneralStore.class).getProductByName(this.name);
            if (product != null) {
                basePrice = product.getPrice()/2;
            }
            //5
            product =
                    App.getCurrentUser().getCurrentGame().
                            findStoreByClass(JojaMart.class).getProductByName(this.name);
            if (product != null) {
                basePrice = product.getPrice()/2;
            }
            //6
            product =
                    App.getCurrentUser().getCurrentGame().
                            findStoreByClass(MarniesRanch.class).getProductByName(this.name);
            if (product != null) {
                basePrice = product.getPrice()/2;
            }
            //7
            product =
                    App.getCurrentUser().getCurrentGame().
                            findStoreByClass(TheSaloonStardrop.class).getProductByName(this.name);
            if (product != null) {
                basePrice = product.getPrice()/2;
            }
        }

        return (basePrice == -1) ? -1 : basePrice * getMaxStackSize();
    }

}
