package entities;

import core.Map;
import core.tiles.Tile;
import handler.Vector;

public abstract class Mob extends Entity {
    
    protected boolean canJump = true;
    protected boolean falling = true;
    protected double gravity;
    protected double maxY;
    protected double maxX;
    protected int jumpCount;
    
    protected int health = 200;
    
    protected Map map;

    public Mob(Map map, Vector pos, String file) {
        super(pos, file);
        this.map = map;
        gravity = 0.04;
        maxY = 3;
        maxX = 1.5;
    }
    
    protected boolean hasHorizontalCollision() {
        for (int i = 0; i < map.getTiles().size(); i++) {
            Tile t = map.getTiles().get(i);
            if (!t.isSolid()) continue;
            if (getLeft().intersects(t.getRight()) && velocity.x < 0) {
                velocity.x = 0;
                return true;
            }
            if (getRight().intersects(t.getLeft()) && velocity.x > 0) {
                velocity.x = 0;
                return true;
            }
        }
        return false;
    }
    
    protected boolean hasVerticalCollision() {
        for (int i = 0; i < map.getTiles().size(); i++) {
            Tile t = map.getTiles().get(i);
            if (!t.isSolid()) continue;
            if (getBounds().intersects(t.getTop()) && velocity.y > 0) {
                velocity.y = 0;
                canJump = true;
                falling = false;
                return true;
            } else falling = true;
            if (getTop().intersects(t.getBottom()) && velocity.y < 0) {
                velocity.y = 0;
                canJump = false;
                falling = true;
                return true;
            }
        }
        return false;
    }
    
    protected void move() {
        if (velocity.x > maxX) velocity.x = maxX;
        if (velocity.x < -maxX) velocity.x = -maxX;
        if (!hasHorizontalCollision()) pos.add(new Vector(velocity.x, 0));
        if (!hasVerticalCollision()) pos.add(new Vector(0, velocity.y));
    }
    
    protected void fall() {
        if (falling) {
            velocity.y += gravity;
            if (velocity.y > maxY) {
                velocity.y = maxY;
            }
        }
    }
    
    protected void jump() {
        if (canJump) {
            velocity.y -= 3.5;
            canJump = false;
        }
    }

    @Override
    public void update() {
        if(health < 0) health = 0;
        move();
        fall();
    }
    
    public int getHealth() {
		return health;
	}
    
    public void setHealth(int health) {
		this.health = health;
	}
    
}
