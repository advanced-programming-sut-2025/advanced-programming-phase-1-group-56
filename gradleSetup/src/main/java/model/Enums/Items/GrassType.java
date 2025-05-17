package model.Enums.Items;

public enum GrassType implements ItemType {
    NormalGrass,
    FiberGrass;

    @Override
    public String getName() {
        return this.toString();
    }
}
