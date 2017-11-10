package states;

import java.awt.Color;
import java.awt.Graphics;

import handler.State;
import handler.StateHandler;
import handler.StateHandler.States;

public class Start extends State {

    private int i = 0;

    @Override
    public void update() {
        i++;
        if (i > 1000) StateHandler.changeState(States.GAME);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, StateHandler.WIDTH, StateHandler.HEIGHT);
    }
}
