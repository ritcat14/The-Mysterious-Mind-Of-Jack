package entities;

import graphics.GuiBar;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import core.Map;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

public class Player extends Mob {
	
    private boolean up, left, right;
    private double speed = 0.08;
    private GuiBar healthBar;
    private final int EDGE_DISTANCE = 100;

    public Player(Map map, Vector pos, String file) {
        super(map, pos, new Vector(32, 64), file);
        healthBar = new GuiBar(new Vector(10, 10), new Vector(200, 10), Color.GREEN, health);
    }
    
    public void crouch() {
        
    }

    @Override
    public void update() {
        healthBar.setValue(health);
        if (up) jump();
        if (left) {
            velocity.x -= speed;
        } else if (velocity.x < 0) velocity.x += speed;
        
        if (right) {
            velocity.x += speed;
        } else if (velocity.x > 0) velocity.x -= speed;
        
        if (velocity.x > 0 && velocity.x < speed) velocity.x = 0;
        
        if (pos.x < EDGE_DISTANCE) {
            xScroll += EDGE_DISTANCE - pos.x;
            pos.x = EDGE_DISTANCE + 1;
        }
        if (pos.x + size.y > StateHandler.WIDTH - EDGE_DISTANCE) {
            xScroll -= ((pos.x + size.y) - (StateHandler.WIDTH - EDGE_DISTANCE));
            pos.x = StateHandler.WIDTH - EDGE_DISTANCE - size.y - 1;
        }
        super.update();
        healthBar.update();
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
    
}