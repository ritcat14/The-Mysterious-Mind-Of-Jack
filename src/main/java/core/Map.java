/*
This class generates, updates, and renders the map, player, entities, and enemies.
 */

package core;

import entities.*;
import entities.items.Item;
import entities.items.food.*;
import entities.items.other.*;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;
import states.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    private final int ENEMY_MAX = 6;
    private int enemyCount = 0;
    private boolean canSpawn = true;

    public Map(Game game) {
        entities = new ArrayList<>();
        entitiesToRemove = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        background = Tools.getImage("/chapter/background.png");
        backgroundEgg = Tools.getImage("/chapter/background-egg.png");
        backgroundEgg2 = Tools.getImage("/chapter/background-egg2.png");
        game.setPlayer(player = new Player(this, new Vector(500, StateHandler.HEIGHT - 200)));
        generateItems();
        spawnEnemies();
    }
    
    public void setXPosition(double x) {
    	for (Entity e : entities) {
    	    if (e instanceof Player) continue;
    	    e.setPosition(e.getPosition().add(new Vector(x, 0)));
        }
    	xPosition += x;
    }

    private void spawnEnemies() {
        System.out.println(enemyCount);
        if (enemyCount > ENEMY_MAX) {
            System.out.println("Boss spawned");
            add(new Boss(this));
            canSpawn = false;
            return;
        }
        enemyCount += 3;
        for (int i = 0; i < 3; i++) {
            int enemyID = Tools.getRandom(1, 3);
            add(new Enemy(this, new Vector(Tools.getRandom(500, 2000), Mob.FLOOR_HEIGHT - 100), new Vector(48, 96), "/player/backgroundCharacter" + enemyID + ".png", 350, (enemyCount / 3) * 100));
        }
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

    public ArrayList<Entity> getEntities() {
        return entities;
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
        entitiesToAdd.add(new Chest());
        entitiesToAdd.add(new Hawking());
    }

    public void spawnItem(Vector pos) {
        Vector spawnPos = pos.add(new Vector(24, -100));
        int ran = Tools.getRandom(1, 5);
        switch(ran) {
            case 1:
                entitiesToAdd.add(new Apple(spawnPos));
                break;
            case 2:
                entitiesToAdd.add(new Banana(spawnPos));
                break;
            case 3:
                entitiesToAdd.add(new Burger(spawnPos));
                break;
            case 4:
                entitiesToAdd.add(new Crisps(spawnPos));
                break;
            case 5:
                entitiesToAdd.add(new Sandwich(spawnPos));
                break;
        }
        ran = Tools.getRandom(1, 10);

    }
    
    public void update() {
        time++;
        player.update();
        for (Entity e : entities) {
        	e.update();
        	if (e.isRemoved()) {
        	    entitiesToRemove.add(e);
            }
        }
        if (time % 600 == 0 && canSpawn) spawnEnemies();
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
            if (pos.getX() + width < 0 || pos.getY() > StateHandler.WIDTH) continue;
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