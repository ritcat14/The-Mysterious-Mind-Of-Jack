package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import events.Event;
import events.EventDispatcher;
import events.EventHandler;
import events.types.MousePressedEvent;
import handler.State;
import handler.StateHandler;
import handler.StateHandler.States;

public class Start extends State {

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, StateHandler.WIDTH, StateHandler.HEIGHT);
        
        g.setColor(Color.BLUE);
        g.fillRect(50, 50, 100, 20);
    }
    
    public boolean mousePressed(MousePressedEvent e) {
        if (new Rectangle(50, 50, 100, 20).contains(e.getX(), e.getY())) {
            StateHandler.changeState(States.GAME);
            return true;
        }
        return false;
    }

    @Override
    public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, new EventHandler() {
			public boolean onEvent(Event event) {
				return mousePressed((MousePressedEvent)event);
			}
		});
    }
}
