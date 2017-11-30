package entities;

import java.awt.Rectangle;

import core.Map;
import core.tiles.Tile;
import handler.Vector;

public abstract class Mob extends Entity {
    
    protected boolean canJump = true;
    protected boolean falling = true;
    protected double gravity;
    protected double maxY;
    protected double maxX;
    
    protected Map map;

    public Mob(Map map, Vector pos, String file) {
        super(pos, file);
        this.map = map;
        gravity = 0.02;
        maxY = 3;
        maxX = 3;
    }
    
    protected boolean hasHorizontalCollision() {
        for (int i = 0; i < map.getTiles().size(); i++) {
            Tile t = map.getTiles().get(i);
            if (getBounds().intersects(t.getLeft()) && speed.x < 0) {
                speed.x = 0;
                return true;
            }
            if (getBounds().intersects(t.getRight()) && speed.x > 0) {
                speed.x = 0;
                return true;
            }
        }
        return false;
    }
    
    protected boolean hasVerticalCollision() {
        for (int i = 0; i < map.getTiles().size(); i++) {
            Tile t = map.getTiles().get(i);
            if (getBounds().intersects(t.getTop()) && speed.y > 0) {
                speed.y = 0;
                canJump = true;
                falling = false;
                return true;
            } else falling = true;
            if (getBounds().intersects(t.getBottom()) && speed.y < 0) {
                speed.y = 0;
                canJump = false;
                falling = true;
                return true;
            }
        }
        return false;
    }
    
    protected void move() {
        if (speed.x > maxX) speed.x = maxX;
        if (speed.x < -maxX) speed.x = -maxX;
        if (!hasHorizontalCollision()) pos.add(new Vector(speed.x, 0));
        if (!hasVerticalCollision()) pos.add(new Vector(0, speed.y));
    }
    
    protected void fall() {
        if (falling) {
            speed.y += gravity;
            if (speed.y > maxY) {
                speed.y = maxY;
            }
        }
    }
    
    protected void jump() {
        if (canJump) {
            speed.y -= 3;
            canJump = false;
        }
    }

    @Override
    public void update() {
        move();
        fall();
    }
    
}
