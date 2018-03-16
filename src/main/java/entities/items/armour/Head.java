package entities.items.armour;

import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Head extends Item {

    public Head(Vector pos) {
        super(pos, 1);
    }

    @Override
    public void onEvent(Player player) {
        player.addHead(image);
        player.adjustSheild(40);
        remove();
    }
}
