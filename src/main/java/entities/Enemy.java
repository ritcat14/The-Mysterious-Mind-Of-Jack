package entities;

import core.Map;
import handler.StateHandler;
import handler.Vector;

public class Enemy extends Mob {

	public Enemy(Map map, Vector pos, Vector size, String file) {
		super(map, pos, size, file);
	}
	
	@Override
	public void update() {
		Player player = StateHandler.player;
		if (player.getPosition().x < pos.x) velocity.x += speed;
		if (player.getPosition().x > pos.x) velocity.x -= speed;
		super.update();
	}
	
	@Override
	protected void move() {
        if (velocity.x > maxX) velocity.x = maxX;
        if (velocity.x < -maxX) velocity.x = -maxX;
        if (!hasHorizontalCollision()) {
        	pos.add(new Vector(velocity.x, 0));
        } else {
        	jump();
        }
        if (!hasVerticalCollision()) pos.add(new Vector(0, velocity.y));
	}

}
