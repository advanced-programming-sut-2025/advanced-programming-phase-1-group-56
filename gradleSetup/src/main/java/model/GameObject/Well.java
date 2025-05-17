package model.GameObject;

import model.Enums.Buildings.BuildingType;
import model.MapModule.Position;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

public class Well extends GameObject implements TimeObserver {
    private final BuildingType type = BuildingType.WELL;

    public Well(Position position) {
        super(false,position);
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {

    }

    public BuildingType getType() {
        return type;
    }
}
