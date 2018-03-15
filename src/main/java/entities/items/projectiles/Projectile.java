package entities.items.projectiles;

import java.util.Random;

import core.Map;
import entities.Mob;
import handler.StateHandler;
import handler.Vector;

public class Projectile extends Mob {
	
	private final Vector originPos;
	
	protected double angle;
	protected int speed, range, damage;
	protected Mob mob;
	protected final Random random = new Random();

	public Projectile(Map map, Mob mob, Bullet b, Vector pos, Vector size, String file, double angle) {
		super(map, pos, size, file);
		this.mob = mob;
		this.angle = angle;
		this.speed = b.getSpeed();
		this.range = b.getRange();
		this.damage = b.getDamage();
		
		originPos = pos;
	}

	public Projectile(Map map, Mob mob, Bullet b, Vector pos, String file, double angle) {
		super(map, pos, file);
		this.mob = mob;
		this.angle = angle;
		this.speed = b.getSpeed();
		this.range = b.getRange();
		this.damage = b.getDamage();
		originPos = pos;
	}
	
	protected double distance() {
		return Math.sqrt(Math.abs(((originPos.x - pos.x) * (originPos.x - pos.x)) + ((originPos.y - pos.y) * (originPos.y - pos.y))));
	}
	
	@Override
	public void update() {
		move();
		if (distance() > range) remove();
		if (pos.x < 0 || pos.x > StateHandler.WIDTH || pos.y < 0 || pos.y > StateHandler.HEIGHT) remove();
	}
	
	public Mob getMob() {
		return mob;
	}

}
