package states.chapters;

import core.Map;
import core.tiles.Tile;
import handler.Vector;
import states.Game;

public class Chapter5 extends Chapter {

    public Chapter5(Game game) {
        super(new Map(game, 5), new Tile(new Vector(65, 200), 2, 1), new Vector(2,2));
    }
}
