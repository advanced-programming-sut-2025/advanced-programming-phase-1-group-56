package io.src.model.GameObject;

import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.MapModule.Position;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;

public class Well extends GameObject implements TimeObserver {
    private final BuildingType type = BuildingType.WELL;

    public Well(Position position) {
        super(false, position);
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {

    }

    public BuildingType getType() {
        return type;
    }

    public String getAssetName() {
        return "well";
    }
}
