package entities;

import core.Map;
import handler.Vector;

public class Boss extends Mob {

    public Boss(Map map) {
        super(map, new Vector(500, Mob.FLOOR_HEIGHT), "/player/bully.png");
    }
}
