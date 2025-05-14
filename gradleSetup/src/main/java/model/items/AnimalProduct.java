package model.items;

import model.Enums.Animals.AnimalProductQuality;
import model.Enums.Items.EtcType;

public class AnimalProduct extends Item{
    private final AnimalProductQuality quality;
    private final EtcType etcType;

    public AnimalProduct(EtcType etcType, AnimalProductQuality quality) {
        super(etcType.name, 100, true,etcType.value);
        this.quality = quality;
        this.etcType = etcType;
    }


    public String getName() {
        return this.etcType.name;
    }

    public AnimalProductQuality getQuality() {
        return this.quality;
    }

    @Override
    public int getPrice(){
        return this.etcType.value;
    }
}
