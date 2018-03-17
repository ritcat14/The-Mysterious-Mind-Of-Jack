package entities;

import core.Map;
import handler.Vector;

public class Companion extends Mob {

    private Boss boss;

    public Companion(Map map, Boss boss) {
        super(map, new Vector(map.getX() + 200, Mob.FLOOR_HEIGHT - 100), new Vector(96, 96), "/player/hawking.png", 150, 400);
        this.boss = boss;
    }

    @Override
    public void update() {
        if (!boss.getBounds().intersects(this.getBounds())) {
            if (boss.getX() < pos.getX()) velocity.adjustX(-5);
            if (boss.getX() > pos.getX()) velocity.adjustX(5);
        } else {
            velocity.clear();
            boss.doDamage(200);
        }
        super.update();
    }
}
