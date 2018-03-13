package entities;

import graphics.GuiBar;
import graphics.Inventory;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import core.Map;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

public class Player extends Mob {
	
    private boolean up, left, right;
    private GuiBar healthBar, sheildBar;
    private final int EDGE_DISTANCE = 100;
    public double initialOffset;
    private BufferedImage h, d;
    
    private Inventory invent;

    public Player(Map map, Vector pos, String file, double initialOffset) {
        super(map, pos, new Vector(32, 64), file);
        this.initialOffset = initialOffset;
        shield = 5;
        speed = 0.08;
        healthBar = new GuiBar(new Vector(20, 30), new Vector(200, 10), Color.GREEN, (int)health, MAX_HEALTH);
        sheildBar = new GuiBar(new Vector(20, 50), new Vector(200, 10), Color.BLUE, shield, MAX_SHIELD);
        
        h = Tools.getImage("/gui/health.png");
        d = Tools.getImage("/gui/shield.png");
        
        invent = new Inventory(new Vector(0, 100));
    }
    
    public void crouch() {
        
    }

    @Override
    public void update() {
    	shield = invent.getDefense();
        healthBar.setValue((int)health);
        sheildBar.setValue(shield);
        if (up) jump();
        
        if (left) velocity.x -= speed;
        else if (velocity.x < 0) velocity.x += speed;
        
        if (right) velocity.x += speed;
        else if (velocity.x > 0) velocity.x -= speed;
        
        if (velocity.x > -speed && velocity.x < speed) velocity.x = 0;
        
        if (pos.x + size.x > StateHandler.WIDTH - EDGE_DISTANCE) {
        	map.setXPosition(-velocity.x);
        	pos.x = StateHandler.WIDTH  - EDGE_DISTANCE - size.x;
        }
        
        if (pos.x < EDGE_DISTANCE) {
        	map.setXPosition(-velocity.x);
        	pos.x = EDGE_DISTANCE;
        }
        
        super.update();
        healthBar.update();
        sheildBar.update();
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
        }
        return false;
    }
    
    public boolean keyPressed(KeyPressedEvent e) {
        switch(e.getKey()) {
            case KeyEvent.VK_W:
            	System.out.println(KeyEvent.VK_W);
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
        }
        return false;
    }
    
    public Inventory getInvent() {
		return invent;
	}
    
}
