package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.Map;
import core.tiles.Tile;
import graphics.Animation;
import handler.Tools;
import handler.Vector;

public abstract class Mob extends Entity {
    
    protected boolean canJump = true;
    protected boolean falling = true;
    protected double health = 200.0;
    protected double gravity;
    protected double maxY;
    protected double maxX;
    protected double speed;
    protected int damage;
    protected int shield;
    protected final int MAX_SHIELD = 100;
    protected final int MAX_HEALTH = 200;
    
    protected Animation animation;
    protected BufferedImage[] left = new BufferedImage[2], right = new BufferedImage[2];
    protected BufferedImage[] images; // 0 - left, 1 - right 
    
    protected Map map;

    public Mob(Map map, Vector pos, Vector size, String file) {
        super(pos, size, file);
        this.map = map;
        
        BufferedImage[] images = Tools.splitImage(image, 4, 1150);
        left[0] = images[0];
        left[1] = images[1];
        right[0] = images[2];
        right[1] = images[3];
        
        animation = new Animation(left, 1);
        
        gravity = 0.04;
        maxY = 3;
        maxX = 1.5;
    }

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
                return true;
            } else falling = true;
            if (getTop().intersects(t.getBottom()) && velocity.y < 0) {
                velocity.y = 0;
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
    	canJump = (velocity.y == 0.04);
        if(health <= 0) {
        	health = 0;
        	remove();
        }
        move();
        fall();
        if (velocity.x == 0) animation.stop();
        else {
        	if (!animation.isAnimating()) animation.animate();
        }
        if (velocity.x > 0) animation.setImages(right);
        else if (velocity.x < 0) animation.setImages(left);
        
        
        animation.update();
        this.image = animation.getCurrentFrame();
    }
    
    @Override
    public void render(Graphics g) {
    	g.drawImage(animation.getCurrentFrame(), (int)(pos.x), (int)pos.y, (int)size.x, (int)size.y, null);
    }
    
    public double getHealth() {
		return health;
	}
    
    public void setHealth(int health) {
		this.health = health;
	}
    
    public void doDamage(int damage) {
    	this.health -= (damage / shield);
    }
    
    public Map getMap() {
		return map;
	}
    
}
