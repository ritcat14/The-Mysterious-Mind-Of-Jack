package entities;

import core.Map;
import handler.Vector;
/*
A child class called Boss which generates an entity which will be the final enemy.
 */

public class Boss extends Mob {

    public Boss(Map map) {
        super(map, new Vector(500, Mob.FLOOR_HEIGHT), "/player/bully.png");
    }
}
