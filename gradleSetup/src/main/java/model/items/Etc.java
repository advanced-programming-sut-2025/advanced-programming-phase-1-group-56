package model.items;

import model.Enums.Items.EtcType;

public class Etc extends Item {
    private EtcType etcType;

    public Etc(int maxStackSize, boolean Stackable,  EtcType etcType) {
        super(etcType.name, maxStackSize, Stackable);
        this.etcType = etcType;
    }


    public EtcType getEtcType() {
        return etcType;
    }
}
