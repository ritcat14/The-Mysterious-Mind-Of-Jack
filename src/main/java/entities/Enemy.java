package entities;

import java.awt.Rectangle;

import core.Map;
import handler.StateHandler;
import handler.Vector;

public class Enemy extends Mob {
	
	protected int damage;

	public Enemy(Map map, Vector pos, Vector size, String file) {
		super(map, pos, size, file);
		speed = 0.01;
		damage = 10;
	}
	
	@Override
	public void update() {
		Player player = StateHandler.player;
		Vector playerPos = player.getPosition();
		if (!player.getBounds().intersects(this.getBounds())) {
			if (playerPos.x < pos.x) velocity.x -= speed;
			if (playerPos.x > pos.x) velocity.x += speed;
		} else {
			velocity.clear();
			player.doDamage(damage);
		}
		int distance = 80;
		if (getBounds().intersects(new Rectangle((int)(playerPos.x - distance), (int)(playerPos.y - distance), (int)(player.getWidth() + (distance * 2)), StateHandler.HEIGHT))) {
			jump();
		}
		
		super.update();
	}
	
	@Override
	protected void move() {
        if (velocity.x > maxX) velocity.x = maxX;
        if (velocity.x < -maxX) velocity.x = -maxX;
        if (!hasHorizontalCollision()) {
        	pos.add(new Vector(velocity.x, 0));
        } else {
        	jump();
        }
        if (!hasVerticalCollision()) pos.add(new Vector(0, velocity.y));
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

}
