package model.GameObject;

import model.Enums.Items.MineralItemType;
import model.MapModule.Position;

public class ForagingMineral extends  GameObject {
    public MineralItemType foragingMineralType;
    public ForagingMineral(boolean walkable, Position position, MineralItemType foragingMineralType) {
        super(walkable, position);
        this.foragingMineralType = foragingMineralType;
    }
    public MineralItemType getForagingMineralType() {
        return foragingMineralType;
    }



}
