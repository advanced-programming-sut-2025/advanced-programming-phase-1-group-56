package model.Locations;

import model.GameObject.Animal.Animal;
import model.GameObject.NPC.NPC;

import java.util.ArrayList;

public class Map {
    private final ArrayList<Farm> farms;
    private final Tile[][] tiles;

    private final ArrayList<NPC> NPCs;
    //private final ArrayList<Animal> animals = new ArrayList<Animal>();

    public Map(ArrayList<Farm> farms,Tile[][] map, ArrayList<NPC> NPCs) {
        this.NPCs = NPCs;
        this.farms = farms;
        tiles = map;
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }
}
