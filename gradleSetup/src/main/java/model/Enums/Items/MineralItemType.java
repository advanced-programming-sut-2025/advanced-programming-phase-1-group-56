package model.Enums.Items;

public enum MineralItemType implements ItemType {
    BIG_STONE("Big Stone",null,0 , "\uD83E\uDEA8"),
    STONE("Stone",null,0 , "\uD83E\uDEA8"),
    QUARTZ("Quartz", "A clear crystal commonly found in caves and mines.", 25 , "\uD83D\uDC8E"),
    EARTH_CRYSTAL("Earth Crystal", "A resinous substance found near the surface.", 50 ,"\uD83D\uDFE4"),
    FROZEN_TEAR("Frozen Tear", "A crystal fabled to be the frozen tears of a yeti.", 75 , "\uD83E\uDDCA"),
    FIRE_QUARTZ("Fire Quartz", "A glowing red crystal commonly found near hot lava.", 100 , "\uD83D\uDFE5"),

    EMERALD("Emerald", "A precious stone with a brilliant green color.", 250 , "\uD83D\uDFE9"),
    AQUAMARINE("Aquamarine", "A shimmery blue-green gem.", 180 , "\uD83D\uDCA0"),
    RUBY("Ruby", "A precious stone that is sought after for its rich color and beautiful luster.", 250 , "\uD83D\uDD34"),
    AMETHYST("Amethyst", "A purple variant of quartz.", 100 , "\uD83D\uDC9C"),
    TOPAZ("Topaz", "Fairly common but still prized for its beauty.", 80 , "\uD83D\uDFE8"),
    JADE("Jade", "A pale green ornamental stone.", 200 , "\uD83D\uDC8E"),
    DIAMOND("Diamond", "A rare and valuable gem.", 750 , "\uD83D\uDFE9"),
    PRISMATIC_SHARD("Prismatic Shard", "A very rare and powerful substance with unknown origins.", 2000 , "\uD83C\uDF08"),

    COPPER_ORE("Copper Ore", "A common ore that can be smelted into bars.", 5 , "\uD83D\uDFE0"),
    IRON_ORE("Iron Ore", "A fairly common ore that can be smelted into bars.", 10 , "\uD83D\uDD34"),
    GOLD_ORE("Gold Ore", "A precious ore that can be smelted into bars.", 25 , "\uD83E\uDE99"),
    IRIDIUM_ORE("Iridium Ore", "An exotic ore with many curious properties. Can be smelted into bars.", 100 , "\uD83D\uDFE2"),
    COAL_ORE("Coal Ore", "A combustible rock that is useful for crafting and smelting.", 15 , "\u26AB"),
    ANY_ORE("Any Ore", "A precious ore that can be smelted into bars.", -1);

    public final String name;
    public final String description;
    public final int sellPrice;
    private final String icon;


    MineralItemType(String name, String description, int sellPrice , String icon) {
        this.name = name;
        this.description = description;
        this.sellPrice = sellPrice;
        this.icon = icon;
    }

    public String getIcon(){
        return icon;
    }

    @Override
    public String getName() {
        return name;
    }
}
