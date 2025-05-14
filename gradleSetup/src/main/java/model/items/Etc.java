package model.items;

import model.Enums.Items.EtcType;
import model.Enums.Items.SeedType;

public class Etc extends Item {
    private EtcType etcType;

    public Etc( EtcType etcType) {
        super(etcType.name, 100, true, etcType.value);
        this.etcType = etcType;
    }


    public EtcType getEtcType() {
        return etcType;
    }

    public int getPrice() {
        return etcType.value;
    }
}
