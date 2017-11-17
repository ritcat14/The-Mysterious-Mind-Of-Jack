package handler;

import java.awt.Graphics;

import events.Event;
import events.EventListener;
import states.*;

public class StateHandler implements EventListener {
    
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    
    private static int chapter = 0;
    private static int cutscene = -1;
    
    public static enum States {
        START, GAME, PAUSE, OPTIONS, CUTSCENE;        
    }
    
    private static State currentState;
    
    public StateHandler(int width, int height) {
        StateHandler.WIDTH = width;
        StateHandler.HEIGHT = height;
    }
    
    public static void changeState(States state) {
        switch (state) {
            case START:
                currentState = new Start();
                break;
            case GAME:
                currentState = new Game(chapter);
                break;
            case PAUSE:
                currentState = new Pause();
                break;
            case OPTIONS:
                currentState = new Options();
                break;
            case CUTSCENE:
            currentState = new Cutscene(cutscene);
            break;
        }
    }
    
    public static void nextChapter() {
        chapter++;
        changeState(States.GAME);
    }
    
    public static void nextScene() {
        cutscene++;
        changeState(States.CUTSCENE);
    }
    
    public static void update(){
        if (currentState != null) currentState.update();
    }
    
    public static void render(Graphics g){
        if (currentState != null) currentState.render(g);
    }

    @Override
    public void onEvent(Event event) {
        if (currentState != null) currentState.onEvent(event);
    }
}
