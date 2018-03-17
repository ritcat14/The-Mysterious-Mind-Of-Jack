package entities;

import core.Map;
import entities.items.Item;
import handler.StateHandler;
import handler.Vector;

import java.awt.*;
import java.util.ArrayList;

/*
A child class about enemy of Mob
 */
public class Enemy extends Mob {
	
	protected int damage;
	private int time = 0;
	private boolean hit = false;
	private double incrementalDecrease;
	private double desiredWidth;

	public Enemy(Map map, Vector pos, Vector size, String file, int spriteWidth, double health) {
		super(map, pos, size, file, spriteWidth, health);
		desiredWidth = size.getX();
		speed = 0.01;
		damage = 10;
		incrementalDecrease = desiredWidth / health;
	}
	
	@Override
	public void update() {
		time++;
		if (time == Integer.MAX_VALUE) time = 0;

		if (time % 120 == 0) hit = false;

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
			hit = true;
			super.doDamage(damage);
		}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.GREEN);
		g.fillRect((int)pos.getX(), (int)pos.getY() - 10, (int)(health * incrementalDecrease), 10);
	}

	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}