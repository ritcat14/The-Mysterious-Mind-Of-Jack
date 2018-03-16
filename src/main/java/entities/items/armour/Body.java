package entities.items.armour;

import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Body extends Item {

    public Body(Vector pos) {
        super(pos, 2);
    }

    @Override
    public void onEvent(Player player) {
        player.addChest(image);
        remove();
        player.adjustSheild(40);
    }
}
