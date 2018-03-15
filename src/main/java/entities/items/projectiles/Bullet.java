package entities.items.projectiles;

import java.awt.image.BufferedImage;

public class Bullet {
	
	protected int speed, range, damage, fireRate;
	protected BufferedImage sprite;

	public Bullet(BufferedImage sprite, int speed, int range, int damage, int rate) {
		this.sprite = sprite;
		this.damage = damage;
		this.speed = speed;
		this.range = range;
		this.fireRate = rate;
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getRange() {
		return range;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getFireRate() {
		return fireRate;
	}

}
