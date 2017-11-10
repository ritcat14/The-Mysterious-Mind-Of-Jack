package handler;

import java.awt.Graphics;

import states.*;

public class StateHandler {
    
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    
    public static enum States {
        START, GAME, PAUSE;
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
                currentState = new Game();
                break;
            case PAUSE:
                
                break;
        }
    }
    
    public static void update(){
        if (currentState != null) currentState.update();
    }
    
    public static void render(Graphics g){
        if (currentState != null) currentState.render(g);
    }
}
