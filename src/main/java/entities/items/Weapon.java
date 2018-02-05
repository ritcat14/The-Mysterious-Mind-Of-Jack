package entities.items;

import java.awt.image.BufferedImage;

import entities.Entity;
import entities.Mob;
import graphics.Animation;
import handler.Vector;

public abstract class Weapon extends Entity {
	
	protected boolean hasBullets;
	protected Mob mob;
	protected double range, damage;
	protected Animation attack;

	public Weapon(Vector pos, Vector size, String file) {
		super(pos, size, file);
	}

	public Weapon(Vector pos, Vector size, BufferedImage image) {
		super(pos, size, image);
	}

	public Weapon(Vector pos, BufferedImage image) {
		super(pos, image);
	}

	public Weapon(Vector pos, String file) {
		super(pos, file);
	}
	
	public void useWeapon() {
		attack.start();
	}

}
