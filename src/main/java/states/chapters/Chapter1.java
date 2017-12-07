package states.chapters;

import core.Map;
import entities.Enemy;
import handler.Vector;
import states.Game;

public class Chapter1 extends Chapter {

    public Chapter1(Game game) {
        super(new Map(game, 1));
        map.add(new Enemy(map, new Vector(200, 50), new Vector(32, 64), "/player.png"));
    }
}
