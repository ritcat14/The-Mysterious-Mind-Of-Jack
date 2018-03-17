package entities;

import java.awt.image.BufferedImage;

import core.Map;
import graphics.Animation;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;
/*
A child class Mob from Entity
 */
public abstract class Mob extends Entity {

    protected boolean onGround = false;

    protected double health = 200.0;
    protected double gravity;
    protected double maxY;
    protected double maxX;
    protected double speed;
    protected int damage;
    protected int shield = 1;
    protected int MAX_SHIELD = 100;
    protected int MAX_HEALTH = 200;
    public static int FLOOR_HEIGHT;
    
    protected Animation animation;
    protected BufferedImage[] left = new BufferedImage[2], right = new BufferedImage[2];
    protected BufferedImage[] images; // 0 - left, 1 - right

    protected int dir = 1;
    
    protected Map map;
    
    private boolean isLeft = true;

    public Mob(Map map, Vector pos, Vector size, String file, int spriteWidth, double health) {
        super(pos, size, file);
        this.map = map;
        this.health = health;
        this.MAX_HEALTH = (int)health;
        FLOOR_HEIGHT = (StateHandler.HEIGHT - 20);

        images = Tools.splitImage(image, 4, spriteWidth);
        left[0] = images[1];
        left[1] = images[0];
        right[0] = images[2];
        right[1] = images[3];

        animation = new Animation(left, 8);

        gravity = 0.04;
        maxY = 3;
        maxX = 1.5;
    }

    public Mob(Map map, Vector pos, Vector size, String file) {
        super(pos, size, file);
        this.map = map;
        FLOOR_HEIGHT = (StateHandler.HEIGHT - 20);
        
        images = Tools.splitImage(image, 4, 1250);
        left[0] = images[1];
        left[1] = images[0];
        right[0] = images[2];
        right[1] = images[3];
        
        animation = new Animation(left, 8);
        
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
        FLOOR_HEIGHT = (StateHandler.HEIGHT - 20);
    }

    public Mob(Map map, Vector pos, Vector size, BufferedImage image) {
        super(pos, size, image);
        this.map = map;
        gravity = 0.04;
        maxY = 3;
        maxX = 1.5;
        FLOOR_HEIGHT = (StateHandler.HEIGHT - 20);
    }

    public boolean hasHorizontalCollision() {
        boolean passed = false;
        if (pos.getX() < 0) {
            pos.setX(0);
            passed = true;
        }
        if (pos.getX() >= StateHandler.WIDTH) {
            pos.setX(StateHandler.WIDTH);
            passed = true;
        }
        return passed;
    }
    
    public boolean hasVerticalCollision() {
        boolean passed = false;
        if (pos.getY() < 0) {
            pos.setY(0);
            passed = true;
        }
        if (onGround) passed = true;
        return passed;
    }

    public Map getMap() {
        return map;
    }

    protected void move() {
        if (velocity.getX() > maxX) velocity.setX(maxX);
        if (velocity.getX() < -maxX) velocity.setX(-maxX);
        if (!hasHorizontalCollision()) pos.add(new Vector(velocity.getX(), 0));
        if (!hasVerticalCollision()) pos.add(new Vector(0, velocity.getY()));
    }
    
    private void fall() {
    	if (!onGround) {
	    	velocity.adjustY(gravity);
	    	if (velocity.getY() > maxY) {
	    		velocity.setY(maxY);
	    	}
    	}
    }
    
    void jump() {
        if (onGround) {
            velocity.adjustY(-2);
            pos.add(new Vector(0, velocity.getY()));
        }
    }

    @Override
    public void update() {
        onGround = pos.getY() + size.getY() >= FLOOR_HEIGHT;
        if(health <= 0) {
        	health = 0;
        	remove();
        }
        if (health > MAX_HEALTH) health = MAX_HEALTH;
        if (shield > MAX_SHIELD) shield = MAX_SHIELD;
        move();
        fall();
        if (!animation.isAnimating() && velocity.getX() != 0) animation.start();
        else if (velocity.getX() == 0 && animation.isAnimating() || !onGround) animation.stop();
        if (velocity.getX() > 0 && isLeft) {
        	isLeft = false;
        	animation.setImages(right);
        	dir = 0;
        } else if (velocity.getX() < 0 && !isLeft) {
        	isLeft = true;
        	animation.setImages(left);
        	dir = 1;
        }
        animation.update();
        this.image = animation.getCurrentFrame();
    }

    public void adjustSheild(double sheild) {
        this.shield += shield;
    }

    public void adjustHealth(double health) {
        this.health += health;
    }

    public void adjustAttack(double attack) {
        this.damage += attack;
    }

    public double getHealth() {
		return health;
	}
    
    public void setHealth(double health) {
		this.health = health;
	}
    
    public void doDamage(double damage) {
    	this.health -= damage / shield;
    }

}
