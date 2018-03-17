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
	private Vector projSize;
	private boolean canShoot = true;
	private int time = 0;

	public Weapon(Map map, Vector pos, Vector projSize, int ID, double range, double damage, double shotSpeed) {
		super(pos, Tools.getImage("/items/" + ID + ".png"));
		this.range = range;
		this.damage = damage;
		this.map = map;
		this.shotSpeed = shotSpeed;
		this.projSize = projSize;
		if (shotSpeed > 0) projImg = Tools.getImage("/items/projectiles/" + ID + ".png");
	}

    public Weapon(Map map, Vector pos, int ID, double range, double damage, double shotSpeed) {
        super(pos, Tools.getImage("/items/" + ID + ".png"));
        this.range = range;
        this.damage = damage;
        this.map = map;
        this.shotSpeed = shotSpeed;
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
		if (canShoot) {
		    projectilesToAdd.add(new Projectile(map, pos, projSize, projImg, dir, shotSpeed, range, damage));
		    canShoot = false;
        }
	}

	@Override
	public void update() {
	    if (!canShoot) {
	        time++;
	        if (time % 60 == 0) {
	            canShoot = true;
	            time = 0;
            }
        }
		for (Projectile p : projectiles) p.update();

        ArrayList<Enemy> enemies = map.getEnemies();
		if (shotSpeed == 0) {
			for (Enemy e : enemies) {
				if (e.getBounds().intersects(this.getBounds())) ((Enemy) e).doDamage(damage);
			}
		} else if (!rendering) {
            for (Enemy e : enemies) {
                for (Projectile p : projectiles) {
                    if (e.getBounds().intersects(p.getBounds())) {
                        projectilesToRemove.add(p);
                        e.doDamage(p.getDamage());
                    }
                }
            }
            clean();
        }
	}

	@Override
	public void render(Graphics g) {
		rendering = true;
		for (Projectile p : projectiles) p.render(g);
		rendering = false;
	}
}
