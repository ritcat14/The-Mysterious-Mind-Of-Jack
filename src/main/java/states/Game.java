package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import core.Map;
import entities.Player;
import handler.Vector;
import events.Event;
import events.Event.Type;
import events.EventDispatcher;
import events.types.KeyPressedEvent;
import handler.StateHandler;

public class Game extends State {
    
    public static boolean acceleration = false;
    public static boolean bloodLevel = false;
    public static boolean paused = false;

    private Map map;
    protected Vector playerPos;
    
    private Player player;

    public Game() {
        map = new Map(this);
    }
    
    public Player getPlayer() {
		return player;
	}
    
    public void setPlayer(Player player) {
		this.player = player;
	}

    @Override
    public void update() {
    	if (!paused) map.update();
    }

    @Override
    public void render(Graphics g) {
    	map.render(g);
    }

    @Override
    public void onEvent(Event event) {
    	EventDispatcher dispatcher = new EventDispatcher(event);
    	dispatcher.dispatch(Type.KEY_PRESSED, event1 -> keyPressed((KeyPressedEvent) event1));
    	if (!paused) map.onEvent(event);
    }
    
    public boolean keyPressed(KeyPressedEvent e) {
        switch(e.getKey()) {
            case KeyEvent.VK_ESCAPE:
            	StateHandler.pause();
            	return true;
        }
        return false;
    }
    
}
