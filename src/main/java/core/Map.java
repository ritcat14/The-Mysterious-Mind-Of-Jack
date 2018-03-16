package core;

import entities.Mob;
import entities.items.Item;
import entities.items.other.Chest;
import entities.items.other.Key;
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
import events.EventListener;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

public class Map implements EventListener {

    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesToRemove, entitiesToAdd;
    private BufferedImage background;
    private BufferedImage backgroundEgg2;
    private BufferedImage backgroundEgg;
    private Player player;
    private boolean rendering = false;
    private double xPosition = 0;
    private int time = 0;

    private int width;
    
    public Map(Game game) { // The ID of the chapter is required for accessing the map data
        entities = new ArrayList<>();
        entitiesToRemove = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        background = Tools.getImage("/chapter/background.png");
        backgroundEgg = Tools.getImage("/chapter/background-egg.png");
        backgroundEgg2 = Tools.getImage("/chapter/background-egg2.png");
        game.setPlayer(player = new Player(this, new Vector(500, StateHandler.HEIGHT - 100)));
        width = background.getWidth();
        generateItems();
    }
    
    public void setXPosition(double x) {
    	for (Entity e : entities) e.setPosition(e.getPosition().add(new Vector(x, 0)));
    	xPosition += x;
    	
    }
    
    public void add(Entity e) { entitiesToAdd.add(e); }
    
    public void remove(Entity e) {
    	entitiesToRemove.add(e);
    }
    
    public double getX() {
		return xPosition;
	}

    public BufferedImage getImage() {
        return background;
    }

    private void clean() {
    	entities.removeAll(entitiesToRemove);
    	entities.addAll(entitiesToAdd);
    	entitiesToAdd.clear();
    	entitiesToRemove.clear();
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Entity e : entities) {
            if (e.equals(player)) continue;
            if (e instanceof Item) items.add((Item)e);
        }
        return items;
    }

    private void generateItems() {
        entities.add(new Chest(new Vector(200, Mob.FLOOR_HEIGHT)));
        entities.add(new Key(new Vector(500, Mob.FLOOR_HEIGHT)));
    }
    
    public void update() {
        player.update();
        for (Entity e : entities) {
        	e.update();
        	if (e.isRemoved()) entitiesToRemove.add(e);
        }
        if (!rendering) clean();
    }
    
    public void render(Graphics g) {
    	rendering = true;
    	if (player.isEasterEgg()) {
    	    time++;
    	    if (time == Integer.MAX_VALUE - 1) time = 0;
            if (time % Tools.getSecs(1) == 0) background = (background.equals(backgroundEgg) ? backgroundEgg2 : backgroundEgg);
        }
        g.drawImage(background, (int)xPosition, StateHandler.HEIGHT - background.getHeight(), null);
        for (Entity e : entities) {
            if (e.isRemoved()) entitiesToRemove.add(e);
            Vector pos = e.getPosition();
            double width = e.getWidth();
            if (pos.x + width < 0 || pos.x > StateHandler.WIDTH) continue;
        	e.render(g);
        }
        player.render(g);
        rendering = false;
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Type.KEY_PRESSED, event12 -> player.keyPressed((KeyPressedEvent) event12));
        dispatcher.dispatch(Type.KEY_RELEASED, event1 -> player.keyReleased((KeyReleasedEvent) event1));
    }
    
}
