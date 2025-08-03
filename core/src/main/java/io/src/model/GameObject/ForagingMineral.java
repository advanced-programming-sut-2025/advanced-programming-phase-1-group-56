package io.src.model.GameObject;

import io.src.model.Enums.Items.MineralItemType;
import io.src.model.MapModule.Position;

public class ForagingMineral extends GameObject {
    public MineralItemType foragingMineralType;

    public ForagingMineral(boolean walkable, Position position, MineralItemType foragingMineralType) {
        super(walkable, position);
        this.foragingMineralType = foragingMineralType;
    }

    public MineralItemType getForagingMineralType() {
        return foragingMineralType;
    }

    @Override
    public String getAssetName() {
        return foragingMineralType.getAssetName();
    }
}
