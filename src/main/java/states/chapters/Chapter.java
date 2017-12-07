package states.chapters;

import java.awt.Graphics;

import core.Map;
import events.Event;
import states.State;

public abstract class Chapter extends State {
    
    protected final Map map;
    
    public Chapter(Map map) {
        this.map = map;
    }
    
    @Override
    public void update() {
        map.update();
    }
    
    @Override
    public void render(Graphics g) {
        map.render(g);
    }
    
    @Override
    public void onEvent(Event event) {
    	map.onEvent(event);
    }
    
}
