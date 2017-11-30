package entities;

import handler.StateHandler;
import handler.Vector;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import core.Map;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

public class Player extends Mob {
    
    private boolean up, down, left, right;
    private int xScroll = 0;
    private double speed = 0.04;

    public Player(Map map, Vector pos, String file) {
        super(map, pos, file);
    }
    
    public void crouch() {
        
    }
    
    public int getxScroll() {
        return xScroll;
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
        
        if (pos.x < 20) {
            xScroll += 20 - pos.x;
            pos.x = 20;
        }
        if (pos.x + width > StateHandler.WIDTH - 20) {
            xScroll -= ((pos.x + width) - (StateHandler.WIDTH - 20));
            pos.x = StateHandler.WIDTH - 20 - width;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, (int)(pos.x), (int)(pos.y), null);
        super.render(g);
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
