package io.src.model.items;

import io.src.model.Enums.Items.EtcType;

public class Etc extends Item {
    private final EtcType etcType;

    public Etc( EtcType etcType) {
        super(etcType.name, 9999, true, etcType.value);
        this.etcType = etcType;
    }


    public EtcType getEtcType() {
        return etcType;
    }

    public int getPrice() {
        return etcType.value;
    }
}
