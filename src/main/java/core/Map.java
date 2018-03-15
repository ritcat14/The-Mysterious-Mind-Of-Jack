package core;

import handler.StateHandler;
import handler.Tools;
import handler.Vector;
import states.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Entity;
import entities.Player;
import events.Event;
import events.Event.Type;
import events.EventDispatcher;
import events.EventHandler;
import events.EventListener;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
import core.tiles.Tile;

public class Map implements EventListener {
    
    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesToRemove, entitiesToAdd;
    private BufferedImage background;
    private Player player;
    private boolean rendering = false;
    private double xPosition = 0;
    
    public Map(Game game, int chapter) { // The ID of the chapter is required for accessing the map data
        Decoder decoder = new Decoder();
        decoder.decode(this, chapter);
        tiles = decoder.getTiles();
        player = decoder.getPlayer();
        entities = decoder.getEntities();
        entitiesToRemove = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        background = Tools.getImage("/chapters/chapter" + chapter + "/background.png");
        game.setPlayer(player);
        setXPosition(player.initialOffset);
    }
    
    public void setXPosition(double x) {
    	for (Entity e : entities) e.setPosition(e.getPosition().add(new Vector(x, 0)));
    	for (Tile t : tiles) t.setPosition(t.getPosition().add(new Vector(x, 0)));
    	xPosition += x;
    	System.out.println(xPosition);
    	
    }
    
    public void add(Entity e) {
    	entitiesToAdd.add(e);
    }
    
    public void remove(Entity e) {
    	entitiesToRemove.add(e);
    }
    
    public double getX() {
		return xPosition;
	}
    
    private void clean() {
    	entities.removeAll(entitiesToRemove);
    	entities.addAll(entitiesToAdd);
    	entitiesToAdd.clear();
    	entitiesToRemove.clear();
    }
    
    public void update() {
        player.update();
        for (Tile t : tiles) t.update();
        for (Entity e : entities) {
        	e.update();
        	if (e.isRemoved()) entitiesToRemove.add(e);
        }
        if (!rendering) clean();
    }
    
    public void render(Graphics g) {
    	rendering = true;
        g.drawImage(background, (int)xPosition, 0, null);
        for (Tile t : tiles) {
            Vector pos = t.getPosition();
            double width = t.getWidth();
            if (pos.x + width < 0 || pos.x > StateHandler.WIDTH) continue;
            t.render(g);
        }
        player.render(g);
        for (Entity e : entities) {
            Vector pos = e.getPosition();
            double width = e.getWidth();
            if (pos.x + width < 0 || pos.x > StateHandler.WIDTH) continue;
        	e.render(g);
        }
        rendering = false;
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Type.KEY_PRESSED, event12 -> player.keyPressed((KeyPressedEvent) event12));
        dispatcher.dispatch(Type.KEY_RELEASED, event1 -> player.keyReleased((KeyReleasedEvent) event1));
    }
    
    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    
    public Tile isSolid(Vector position) {
        for (Tile t : tiles) {
            if (t.getBounds().contains(position.getPoint())) {
                return t;
            }
        }
        return null;
    }
    
}
