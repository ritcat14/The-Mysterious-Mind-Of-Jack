package entities.items.weapons;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import core.Map;
import entities.Enemy;
import entities.Entity;
import entities.Mob;
import entities.items.projectiles.Projectile;
import graphics.Animation;
import handler.Tools;
import handler.Vector;

public class Weapon extends Entity {

	protected double range, damage;
	private  double shotSpeed;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Projectile> projectilesToAdd = new ArrayList<>();
	private ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
	private Map map;
	private BufferedImage projImg;
	private boolean rendering = false;

	public Weapon(Map map, Vector pos, int ID, double range, double damage, double shotSpeed) {
		super(pos, Tools.getImage("/items/" + ID + ".png"));
		this.range = range;
		this.damage = damage;
		this.map = map;
		if (shotSpeed > 0) projImg = Tools.getImage("/items/projectiles/" + ID + ".png");
	}

	private void clean() {
		projectiles.removeAll(projectilesToRemove);
		projectiles.addAll(projectilesToAdd);
		projectilesToRemove.clear();
		projectilesToAdd.clear();
	}

	public void shoot(int dir) {
		if (shotSpeed == 0) return;
		projectiles.add(new Projectile(map, pos, projImg, dir, shotSpeed, range, damage));
	}

	@Override
	public void update() {
		for (Projectile p : projectiles) p.update();
		if (!rendering) clean();

		if (shotSpeed == 0) {
			ArrayList<Entity> entities = map.getEntities();
			for (Entity e : entities) {
				if (e instanceof Enemy) {
					if (e.getBounds().intersects(this.getBounds())) {
						((Enemy) e).doDamage(damage);
					}
				}
			}
		}

	}

	@Override
	public void render(Graphics g) {
		rendering = true;
		super.render(g);
		for (Projectile p : projectiles) p.render(g);
		rendering = false;
	}
}
