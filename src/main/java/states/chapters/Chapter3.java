package states.chapters;

import core.Map;
import core.tiles.Tile;
import handler.Vector;
import states.Game;

public class Chapter3 extends Chapter {

    public Chapter3(Game game) {
        super(new Map(game, 3), new Tile(new Vector(65, 200), 2, 3), new Vector(2,2));
    }
}
