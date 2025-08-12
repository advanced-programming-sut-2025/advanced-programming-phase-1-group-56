package io.src.model.Network.DTO;

public class GameMapDTO {
    public TownDTO getPelikanTown() {
        return pelikanTown;
    }

    public void setPelikanTown(TownDTO pelikanTown) {
        this.pelikanTown = pelikanTown;
    }

    public FarmDTO getFarm1() {
        return farm1;
    }

    public void setFarm1(FarmDTO farm1) {
        this.farm1 = farm1;
    }

    public FarmDTO getFarm2() {
        return farm2;
    }

    public void setFarm2(FarmDTO farm2) {
        this.farm2 = farm2;
    }

    public FarmDTO getFarm3() {
        return farm3;
    }

    public void setFarm3(FarmDTO farm3) {
        this.farm3 = farm3;
    }

    public FarmDTO getFarm4() {
        return farm4;
    }

    public void setFarm4(FarmDTO farm4) {
        this.farm4 = farm4;
    }

    private TownDTO pelikanTown;
    private FarmDTO farm1;
    private FarmDTO farm2;
    private FarmDTO farm3;
    private FarmDTO farm4;

    // getters & setters
}
