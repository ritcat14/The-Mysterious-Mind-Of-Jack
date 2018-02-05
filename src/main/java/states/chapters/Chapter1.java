package states.chapters;

import core.Map;
import core.tiles.Tile;
import entities.Enemy;
import handler.Vector;
import states.Game;

public class Chapter1 extends Chapter {

    public Chapter1(Game game) {
        super(new Map(game, 1), new Tile(new Vector().tile(30, 8), 2, 1), new Vector(2,2));
        map.add(new Enemy(map, new Vector(200, 50), new Vector(32, 64), "/player/player.png"));
    }
}
