package entities.items.armour;

import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Legs extends Item {

    public Legs(Vector pos) {
        super(pos, 3);
    }

    @Override
    public void onEvent(Player player) {
        player.addLegs(image);
        player.adjustSheild(20);
        remove();
    }
}
