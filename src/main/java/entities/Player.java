package entities;

import graphics.GuiBar;
import handler.StateHandler;
import handler.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import core.Map;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

public class Player extends Mob {
    
    private boolean up, down, left, right;
    private double speed = 0.04;
    private GuiBar healthBar;
    
    private final int EDGE_DISTANCE = 100;

    public Player(Map map, Vector pos, String file) {
        super(map, pos, file);
        healthBar = new GuiBar(new Vector(10, 10), new Vector(200, 30), Color.GREEN, 200);
    }
    
    public void crouch() {
        
    }

    @Override
    public void update() {
        super.update();
        if (up) jump();
        if (left) {
            velocity.x -= speed;
        } else if (velocity.x < 0) velocity.x += speed;
        
        if (right) {
            velocity.x += speed;
        }else if (velocity.x > 0) velocity.x -= speed;
        
        if (pos.x < EDGE_DISTANCE) {
            xScroll += EDGE_DISTANCE - pos.x;
            pos.x = EDGE_DISTANCE + 1;
        }
        if (pos.x + width > StateHandler.WIDTH - EDGE_DISTANCE) {
            xScroll -= ((pos.x + width) - (StateHandler.WIDTH - EDGE_DISTANCE));
            pos.x = StateHandler.WIDTH - EDGE_DISTANCE - width - 1;
        }
        healthBar.update();
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
            down = false;
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
            down = true;
            return true;
            case KeyEvent.VK_D:
            right = true;
            return true;
        }
        return false;
    }
    
}
