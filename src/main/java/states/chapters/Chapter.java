package states.chapters;

import java.awt.Graphics;

import core.Decoder;
import core.Map;
import core.tiles.Tile;
import entities.Player;
import events.Event;
import handler.StateHandler;
import handler.Vector;
import states.State;

public abstract class Chapter extends State {
	
    protected final Tile endTile;
    protected final Map map;
    protected boolean started = false;
    protected Vector playerPos;
    
    public Chapter(Map map, Tile endTile, Vector playerPos) {
        this.map = map;
        this.endTile = endTile;
        map.add(endTile);
        this.playerPos = playerPos;
    }
    
    @Override
    public void update() {
    	if (!started) {
    		if (StateHandler.player != null) {
    	        StateHandler.player.setPosition(new Vector(Decoder.TILE_SIZE, Decoder.TILE_SIZE).multiply(playerPos));
    	        started = true;
    		}
    	}
        map.update();
        Player p = StateHandler.player;
        if (p.getBounds().intersects(endTile.getBounds())) {
        	StateHandler.nextChapter(false);
        }
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
