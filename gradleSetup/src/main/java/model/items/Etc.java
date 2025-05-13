package model.items;

import model.Enums.Items.EtcType;
import model.Enums.Items.SeedType;

public class Etc extends Item {
    private EtcType etcType;

    public Etc(int maxStackSize, boolean Stackable,  EtcType etcType) {
        super(etcType.name, maxStackSize, Stackable);
        this.etcType = etcType;
    }


    public EtcType getEtcType() {
        return etcType;
    }

    @Override
    public int getPrice() {
        return etcType.value;
    }
}
