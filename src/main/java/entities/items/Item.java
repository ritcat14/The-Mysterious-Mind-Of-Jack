package entities.items;

import entities.Entity;
import entities.Player;
import handler.Tools;
import handler.Vector;

import java.awt.image.BufferedImage;

public abstract class Item extends Entity implements ItemEvent {

    private final int ID;
    private double rarity = 1;

    public Item(Vector pos, int ID, double rarity) {
        super(pos, new Vector(40, 40), Tools.getImage("/items/" + ID + ".png"));
        this.ID = ID;
        this.rarity = rarity;
    }

    public Item(Vector pos, int ID) {
        super(pos, new Vector(40, 40), Tools.getImage("/items/" + ID + ".png"));
        this.ID = ID;
    }

    protected Item(Vector pos, Vector size, int ID) {
        super(pos, size);
        this.ID = ID;
    }

    protected Item(Vector pos) {
        super(pos, new Vector(40, 40));
        ID = 0;
    }

    @Override
    public void update() {}

    public double getRarity() {
        return rarity;
    }

    public int getID() {
        return ID;
    }
}
