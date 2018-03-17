package entities.items.other;

import entities.Mob;
import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Hawking extends Item {

    public Hawking() {
        super(new Vector(790, 440), new Vector(71, 99), 52);
    }

    @Override
    public void onEvent(Player player) {
        player.getMap().add(new Key(new Vector(600, Mob.FLOOR_HEIGHT - 40)));
        remove();
    }
}
