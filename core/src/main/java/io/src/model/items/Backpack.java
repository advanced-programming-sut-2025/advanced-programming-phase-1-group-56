package io.src.model.items;

import io.src.model.Enums.BackPackType;

public class Backpack extends Item {
    private BackPackType backPackType;

    public Backpack(BackPackType backPackType) {

        super(backPackType.toString(),1,false,-1);
    }

    public int getCapacity() {
        return backPackType.getCapacity();
    }

    public int getPrice(){
        return -1 ;
    }

    @Override
    public String getAssetName() {
        return backPackType.getAssetName();
    }
}
