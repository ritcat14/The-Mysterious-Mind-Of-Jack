package entities;

import entities.items.Item;
import entities.items.other.Chest;
import graphics.GuiBar;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import core.Map;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

public class Player extends Mob {
	
    private boolean up, left, right;
    private GuiBar healthBar, sheildBar;
    private final int EDGE_DISTANCE = 100;
    private BufferedImage h, d;
    private boolean easterEgg = false;
    private boolean hasKey = false;
    private boolean activateButton = false;
    private String message = "";

    public Player(Map map, Vector pos) {
        super(map, pos, new Vector(32, 64), "/player/player.png");
        shield = 5;
        speed = 0.08;
        healthBar = new GuiBar(new Vector(20, 30), new Vector(200, 10), Color.GREEN, (int)health, MAX_HEALTH);
        sheildBar = new GuiBar(new Vector(20, 50), new Vector(200, 10), Color.BLUE, shield, MAX_SHIELD);
        
        h = Tools.getImage("/gui/health.png");
        d = Tools.getImage("/gui/shield.png");
    }

    public void addKey() {
        hasKey = true;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public boolean isEasterEgg() {
        return easterEgg;
    }

    public void spawn(Item item) {
        map.add(item);
    }

    public void activateEgg() {
        easterEgg = true;
        MAX_HEALTH = 400;
        MAX_SHIELD = 200;
        health = 400;
        shield = 200;
    }

    @Override
    public void update() {
        healthBar.setValue((int)health);
        sheildBar.setValue(shield);
        if (up) jump();
        
        if (left) velocity.x -= speed;
        else if (velocity.x < 0) velocity.x += speed;
        
        if (right) velocity.x += speed;
        else if (velocity.x > 0) velocity.x -= speed;
        
        if (velocity.x > -speed && velocity.x < speed) velocity.x = 0;
        
        if (pos.x + size.x > StateHandler.WIDTH - EDGE_DISTANCE) {
        	if (map.getX() + (-velocity.x) + map.getImage().getWidth() > StateHandler.WIDTH) {
        	    if (velocity.x > 0) map.setXPosition(-velocity.x);
            }
        }
        
        if (pos.x < EDGE_DISTANCE) {
            if (map.getX() + (-velocity.x) < 0) {
                if (velocity.x < 0) map.setXPosition(-velocity.x);
            }
        }

        super.update();
        healthBar.update();
        sheildBar.update();

        ArrayList<Item> items = map.getItems();
        for (Item i : items) {
            if (getBounds().intersects(i.getBounds())) {
                if (i instanceof Chest) {
                    if (!((Chest)i).isOpen()) message = "Press E (requires key).";
                    if (!activateButton) continue;
                }
                i.onEvent(this);
            }
        }
    }
    
    public void pause() {
    	left = false;
    	right = false;
    	up = false;
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        healthBar.render(g);
        g.drawImage(h, (int)(healthBar.getX() - 15), (int)healthBar.getY(), null);
        sheildBar.render(g);
        g.drawImage(d, (int)(sheildBar.getX() - 15), (int)sheildBar.getY(), null);
        g.setColor(Color.RED);
        g.drawString(message, (int)pos.getX(), (int)pos.getY() - 15);
        message = "";
    }
    
    public void setUp(Boolean up) {
    	this.up = up;
    }
    
    public void setLeft(Boolean left) {
    	this.left = left;
    }
    
    public void setRight(Boolean right) {
    	this.right = right;
    }
    
    public boolean keyReleased(KeyReleasedEvent e) {
        switch(e.getKey()) {
            case KeyEvent.VK_W:
            up = false;
            return true;
            case KeyEvent.VK_SPACE:
            up = false;
            return true;
            case KeyEvent.VK_A:
            left = false;
            return true;
            case KeyEvent.VK_S:
            return true;
            case KeyEvent.VK_D:
            right = false;
            return true;
            case KeyEvent.VK_E:
                activateButton = false;
                return true;
        }
        return false;
    }
    
    public boolean keyPressed(KeyPressedEvent e) {
        switch(e.getKey()) {
            case KeyEvent.VK_W:
	            up = true;
	            return true;
            case KeyEvent.VK_SPACE:
	            up = true;
	            return true;
            case KeyEvent.VK_A:
	            left = true;
	            return true;
            case KeyEvent.VK_S:
	            return true;
            case KeyEvent.VK_D:
	            right = true;
	            return true;
            case KeyEvent.VK_E:
                activateButton = true;
                return true;
        }
        return false;
    }
    
}
