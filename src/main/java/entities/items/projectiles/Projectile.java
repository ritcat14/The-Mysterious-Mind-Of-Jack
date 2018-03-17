package entities.items.projectiles;

import core.Map;
import entities.Mob;
import handler.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Mob {

	private final Vector originalPosition;
	private double speed, range, damage;
	private double dir = 0;

	public Projectile(Map map, Vector pos, Vector size,  BufferedImage image, int dir, double speed, double range, double damage) {
		super(map, pos, size, image);
		originalPosition = pos;
		this.range = range;
		this.speed = speed;
		this.dir = dir;
		this.damage = damage;
	}

	public double getDamage() {
		return damage;
	}

	@Override
	public void update() {
		if (pos.getX() > originalPosition.getX() + range) remove();
		if (pos.getX() < originalPosition.getX() - range) remove();
		if (dir == 0) pos.adjustX(speed);
		if (dir == 1) pos.adjustX(-speed);
	}

}
