package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import entities.Player;
import states.chapters.*;
import events.Event;
import events.Event.Type;
import events.EventDispatcher;
import events.EventHandler;
import events.types.KeyPressedEvent;
import handler.StateHandler;

public class Game extends State {
    
    private State currentChapter;
    
    public static boolean acceleration = false;
    public static boolean bloodLevel = false;
    public static boolean paused = false;
    
    private Player player;

    public Game(int chapter) {
        switch(chapter) {
            case 1:
                currentChapter = new Chapter1(this);
                break;
            case 2:
                currentChapter = new Chapter2(this);
                break;
            case 3:
                currentChapter = new Chapter3(this);
                break;
            case 4:
                currentChapter = new Chapter4(this);
                break;
            case 5:
                currentChapter = new Chapter5(this);
                break;
            default:
                currentChapter = new Chapter1(this);
                break;
        }
    }
    
    public Player getPlayer() {
		return player;
	}
    
    public void setPlayer(Player player) {
		this.player = player;
	}

    @Override
    public void update() {
    	if (!paused) currentChapter.update();
    }

    @Override
    public void render(Graphics g) {
    	currentChapter.render(g);
    }

    @Override
    public void onEvent(Event event) {
    	EventDispatcher dispatcher = new EventDispatcher(event);
    	dispatcher.dispatch(Type.KEY_PRESSED, event1 -> keyPressed((KeyPressedEvent) event1));
    	if (!paused) currentChapter.onEvent(event);
    }
    
    public boolean keyPressed(KeyPressedEvent e) {
        switch(e.getKey()) {
            case KeyEvent.VK_ESCAPE:
            	StateHandler.pause();
            	return true;
            case KeyEvent.VK_I:
            	if (player.getInvent().isOpen()) player.getInvent().close();
            	else player.getInvent().open();
            	break;
        }
        return false;
    }
    
}
