package entities;

import java.awt.Rectangle;

import core.Map;
import handler.StateHandler;
import handler.Vector;
/*
A child class about enemy of Mob
 */
public class Enemy extends Mob {
	
	protected int damage;

	public Enemy(Map map, Vector pos, Vector size, String file, int spriteWidth) {
		super(map, pos, size, file, spriteWidth);
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
		super.update();
	}

    @Override
    public void remove() {
	    map.spawnItem(pos);
        super.remove();
    }

    public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

}