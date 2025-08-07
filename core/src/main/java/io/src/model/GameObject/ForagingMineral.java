package io.src.model.GameObject;

import io.src.model.Enums.GameObjects.ForagingGameObjectType;
import io.src.model.Enums.Items.MineralItemType;
import io.src.model.MapModule.Position;

public class ForagingMineral extends GameObject {
    public ForagingGameObjectType foragingGameObjectType;

    public ForagingMineral(boolean walkable, Position position, ForagingGameObjectType foragingGameObjectType) {
        super(walkable, position);
        this.foragingGameObjectType = foragingGameObjectType;
    }

    public ForagingGameObjectType getForagingMineralType() {
        return foragingGameObjectType;
    }

    @Override
    public String getAssetName() {
        return foragingGameObjectType.getAssetName();
    }
}
