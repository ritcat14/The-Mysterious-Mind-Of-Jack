package handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import core.Main;
import entities.Player;
import events.Event;
import events.EventHandler;
import events.EventListener;
import events.Event.Type;
import events.EventDispatcher;
import events.types.MouseMovedEvent;
import events.types.MousePressedEvent;
import states.*;

public class StateHandler implements EventListener {
    
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    
    private static int chapter = 0;
    private static int cutscene = -1;
    private static int playerProgress = 1;
    
    public static Player player;
    
    public static State pausedGame = null;
    public static State startCopy;
    
    public static enum States {
        START, GAME, PAUSE, OPTIONS, CUTSCENE;        
    }
    
    private static State currentState;
    private static States state;
    
    public StateHandler(int width, int height) {
        StateHandler.WIDTH = width;
        StateHandler.HEIGHT = height;
    }
    
    public static void changeState(States state) {
        switch (state) {
            case START:
            	StateHandler.state = state;
                currentState = new Start();
                break;
            case GAME:
            	StateHandler.state = state;
                currentState = new Game(chapter);
                Game g = (Game)currentState;
                player = g.getPlayer();
                break;
            case CUTSCENE:
            	StateHandler.state = state;
	            currentState = new Cutscene(cutscene);
	            break;
			default:
				break;
        }
    }
    
    public static States getState() {
		return state;
	}
    
    public static void loadOptions() {
    	startCopy = currentState;
    	currentState = new Options();
    }
    
    public static void pause() {
    	player.pause();
    	Game.paused = true;
    	pausedGame = currentState;
    	currentState = new Pause();
    }
    
    public static void unpause() {
    	Game.paused = false;
    	for(int i = 0; i < 20; i++) pausedGame.update();
    	currentState = pausedGame;
    }
    
    public static void nextChapter() {
        chapter = playerProgress;
        changeState(States.GAME);
    }
    
    public static void nextScene() {
        cutscene = playerProgress - 1;
        changeState(States.CUTSCENE);
    }
    
    public static void update() {
        if (currentState != null) {
        	currentState.update();
        	if (player != null) {
        		player.getInvent().update();
        	}
        }
    }
    
    public static void render(Graphics g) {
        if (currentState != null) currentState.render(g);
        g.setFont(new Font("Times New Java", Font.BOLD, 20));
        g.setColor(Color.YELLOW);
        g.drawString("" + Main.FPS, 10, 20);
        if (player != null) player.getInvent().render(g);
    }

    @Override
    public void onEvent(Event event) {
        if (currentState != null) {
        	currentState.onEvent(event);
        	if (player != null) {
        		EventDispatcher dispatcher = new EventDispatcher(event);
                dispatcher.dispatch(Type.MOUSE_PRESSED, new EventHandler() {
        			@Override
        			public boolean onEvent(Event event) {
        				return player.getInvent().mousePressed((MousePressedEvent)event);
        			}
                });
                dispatcher.dispatch(Type.MOUSE_MOVED, new EventHandler() {
        			@Override
        			public boolean onEvent(Event event) {
        				return player.getInvent().mouseMoved((MouseMovedEvent)event);
        			}
                });
        	}
        }
    }
}
