package entities;

import core.Map;
import handler.StateHandler;
import handler.Vector;
/*
A child class about enemy of Mob
 */
public class Enemy extends Mob {
	
	protected int damage;
	private int time = 0;
	private boolean hit = false;

	public Enemy(Map map, Vector pos, Vector size, String file, int spriteWidth, double health) {
		super(map, pos, size, file, spriteWidth);
		speed = 0.01;
		damage = 10;
		this.health = health;
	}
	
	@Override
	public void update() {
		time++;
		if (time == Integer.MAX_VALUE) time = 0;

		if (time % 600 == 0) hit = false;

		Player player = StateHandler.player;
		Vector playerPos = player.getPosition();
		if (!player.getBounds().intersects(this.getBounds())) {
			if (playerPos.getX() < pos.getX()) velocity.adjustX(-speed);
			if (playerPos.getX() > pos.getX()) velocity.adjustX(speed);
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

	@Override
	public void doDamage(double damage) {
		if (!hit) {
			super.doDamage(damage);
			hit = true;
		}
	}

	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}