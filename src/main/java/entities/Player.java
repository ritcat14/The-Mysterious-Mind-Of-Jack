package entities;

import handler.StateHandler;
import handler.Vector;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

public class Player extends Entity {
    
    private boolean up, down, left, right;
    private int xScroll = 0;
    private boolean jumping = false;
    private Vector speed = new Vector(2, 2);
    private long t0 = 0;
    private Vector pos0;
    private Vector speed0;
    private double speedJump = 3;
    private double g = -9.81;

    public Player(Vector pos, String file) {
        super(pos, file);
    }
    
    public void jump() { // implementation of gravity
        if (!jumping) {
            t0 = currentTime();
            pos0=pos;
            speed0 = speed;
            speed0.y += speedJump;
            jumping = true;
        }
    }
    
    private long currentTime() {
        return System.currentTimeMillis() / 1000;
    }
    
    public void crouch() {
        
    }
    
    public int getxScroll() {
        return xScroll;
    }

    @Override
    public void update() {
        if (left) pos.x -= speed.x;
        if (right) pos.x += speed.x; 
        long t;
        if (jumping) {
            t = currentTime()-t0;
            pos.y = pos0.y + speed0.y*t - g*t*t;
            pos.x = pos0.x + speed0.x*t;
        
            // And test that the character is not on the ground again.
            if (pos.y + 32 > StateHandler.HEIGHT - 64) {
                pos.y = StateHandler.HEIGHT - 96;
                jumping = false;
            }
        }
        
        if (pos.x < 20) {
            xScroll += 20 - pos.x;
            pos.x = 20;
        }
        if (pos.x > StateHandler.WIDTH - 20) {
            xScroll -= (pos.x - (StateHandler.WIDTH - 20));
            pos.x = StateHandler.WIDTH - 20;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, (int)(pos.x), (int)(pos.y), null);
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
