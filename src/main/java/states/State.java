package states;

import java.awt.Graphics;

import events.EventListener;
/*
To render and update state of game
 */

public abstract class State implements EventListener {
    
    public abstract void update();
    
    public abstract void render(Graphics g);
    
}
