package handler;

import java.awt.Graphics;

public abstract class State {
    
    public State(){
        
    }
    
    public abstract void update();
    
    public abstract void render(Graphics g);
}
