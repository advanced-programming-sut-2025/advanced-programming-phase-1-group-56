package io.src.model.items;

import io.src.model.Enums.Animals.AnimalProductQuality;
import io.src.model.Enums.Items.EtcType;

public class AnimalProduct extends Item{
    private final EtcType etcType;
//    private AnimalProductQuality quality;

    public AnimalProduct(EtcType etcType) {
        super(etcType.name, 9999, true, etcType.value);
//        this.quality = quality;
        this.etcType = etcType;
    }


    public EtcType getEtcType(){
        return this.etcType;
    }


    @Override
    public String getAssetName() {
        return etcType.getAssetName();
    }
}
