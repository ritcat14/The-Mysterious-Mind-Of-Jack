package core.tiles;

import handler.Vector;

public class Floor extends Tile {

    public Floor(Vector pos, int chapterID) {
        super(pos, 1, chapterID);
        solid = true;
    }
}
