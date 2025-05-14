package model.items;

import model.Enums.Animals.AnimalProductQuality;
import model.Enums.Items.EtcType;

public class AnimalProduct extends Item{
    private final EtcType etcType;
    private final AnimalProductQuality quality;

    public AnimalProduct(EtcType etcType, AnimalProductQuality quality) {
        super(etcType.name, 100, true, etcType.value);
        this.quality = quality;
        this.etcType = etcType;
    }


    public EtcType getEtcType(){
        return this.etcType;
    }

    public AnimalProductQuality getQuality() {
        return quality;
    }

    public int getPrice(){
        return price;
    }
}
