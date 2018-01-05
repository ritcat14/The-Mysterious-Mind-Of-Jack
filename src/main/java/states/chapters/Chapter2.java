package states.chapters;

import core.Map;
import core.tiles.Tile;
import handler.Vector;
import states.Game;

public class Chapter2 extends Chapter {

    public Chapter2(Game game) {
        super(new Map(game, 2), new Tile(new Vector(65, 200), 2, 2), new Vector(2,2));
    }
}
