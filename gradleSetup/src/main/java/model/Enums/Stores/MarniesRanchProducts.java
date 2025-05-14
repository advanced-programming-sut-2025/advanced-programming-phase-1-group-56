package model.Enums.Stores;

import model.Enums.Animals.AnimalType;
import model.Enums.Items.EtcType;
import model.Enums.Items.ToolType;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.Animal;
import model.GameObject.NPC.NpcProduct;
import model.items.Etc;
import model.items.Tool;

public enum MarniesRanchProducts {
    // --- General Items ---
    HAY(
            new NpcProduct(
                    "HAY",
                    new Etc(EtcType.HAY),
                    "Dried grass used as animal food.",
                    50,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    MILK_PAIL(
            new NpcProduct(
                    "MILK_PAIL",
                    new Tool(ToolType.MILK_PAIL),
                    "Gather milk from your animals.",
                    1000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    SHEARS(
            new NpcProduct(
                    "SHEARS",
                    new Tool(ToolType.SHEAR),
                    "Use this to collect wool from sheep",
                    1000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),

    // --- Animals ---
    CHICKEN(
            new NpcProduct(
                    "CHICKEN",
                    AnimalType.CHICKEN,
                    "Well cared-for chickens lay eggs every day. Lives in the coop.",
                    800,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    COW(
            new NpcProduct(
                    "COW",
                    AnimalType.COW,
                    "Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn.",
                    1500,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    GOAT(
            new NpcProduct(
                    "GOAT",
                    AnimalType.GOAT,
                    "Happy provide goat milk every other day. A milk pail is required to harvest the milk. Lives in the barn.",
                    4000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    DUCK(
            new NpcProduct(
                    "DUCK",
                    AnimalType.DUCK,
                    "Happy lay duck eggs every other day. Lives in the coop.",
                    1200,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    SHEEP(
            new NpcProduct(
                    "SHEEP",
                    AnimalType.SHEEP,
                    "Can be shorn for wool. A pair of shears is required to harvest the wool. Lives in the barn.",
                    8000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    RABBIT(
            new NpcProduct(
                    "RABBIT",
                    AnimalType.RABBIT,
                    "These are wooly rabbits! They shed precious wool every few days. Lives in the coop.",
                    8000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    DINOSAUR(
            new NpcProduct(
                    "DINOSAUR",
                    AnimalType.DINOSAUR,
                    "The Dinosaur is a farm animal that lives in a Big Coop",
                    14000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    PIG(
            new NpcProduct(
                    "PIG",
                    AnimalType.PIG,
                    "These pigs are trained to find truffles! Lives in the barn.",
                    16000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    );

    private final NpcProduct product;

    MarniesRanchProducts(NpcProduct product) {
        this.product = product;
    }

    public NpcProduct getProduct() {
        return product;
    }
}