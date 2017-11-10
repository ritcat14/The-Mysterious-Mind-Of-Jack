package states;

import java.awt.Color;
import java.awt.Graphics;

import events.Event;
import handler.State;
import handler.StateHandler;

public class Game extends State {

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, StateHandler.WIDTH, StateHandler.HEIGHT);
    }

    @Override
    public void onEvent(Event event) {
        
    }
}
