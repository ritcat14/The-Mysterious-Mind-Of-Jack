package entities.items.projectiles;

import core.Map;
import entities.Mob;
import handler.Vector;

import java.awt.image.BufferedImage;

public class Projectile extends Mob {

	private final Vector originalPosition;
	private double speed, range, damage;
	private double dir = 0;

	public Projectile(Map map, Vector pos, BufferedImage image, int dir, double speed, double range, double damage) {
		super(map, pos, image);
		originalPosition = pos;
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
