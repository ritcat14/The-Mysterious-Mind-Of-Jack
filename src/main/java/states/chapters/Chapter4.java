package states.chapters;

import java.awt.Graphics;

import core.Map;
import core.tiles.Tile;
import events.Event;
import handler.Vector;
import states.Game;

public class Chapter4 extends Chapter {

    public Chapter4(Game game) {
        super(new Map(game, 4), new Tile(new Vector(65, 200), 2, 4), new Vector(2,2));
    }
}
