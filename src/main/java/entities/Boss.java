package entities;

import core.Map;
import handler.StateHandler;
import handler.Vector;
/*
A child class called Boss which generates an entity which will be the final enemy.
 */

import java.awt.*;

public class Boss extends Enemy {

    public Boss(Map map) {
        super(map, new Vector(500, Mob.FLOOR_HEIGHT - 150), new Vector(64, 128), "/player/bully.png", 350, 5000);
    }

    @Override
    public void update() {
        super.update();
        if (health == 0) StateHandler.changeState(StateHandler.States.GAMEWON);
    }
}
