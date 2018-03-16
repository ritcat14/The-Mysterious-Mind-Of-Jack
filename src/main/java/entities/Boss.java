package entities;

import core.Map;
import handler.StateHandler;
import handler.Vector;
/*
A child class called Boss which generates an entity which will be the final enemy.
 */

import java.awt.*;

public class Boss extends Enemy {

    private int desiredWidth = 128;
    private int maxValue = 5000000;

    public Boss(Map map) {
        super(map, new Vector(500, Mob.FLOOR_HEIGHT - 150), new Vector(64, 128), "/player/bully.png", 350, 5000000);
    }

    @Override
    public void remove() {
        super.remove();
        StateHandler.changeState(StateHandler.States.GAMEOVER);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.GREEN);
        int incrementalDecrease = desiredWidth / maxValue;
        int width = (int)(health * incrementalDecrease);
        g.drawRect((int)pos.getX(), (int)(pos.getY() - 20), width, 10);
    }
}
