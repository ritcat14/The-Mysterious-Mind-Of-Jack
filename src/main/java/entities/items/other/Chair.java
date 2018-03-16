package entities.items.other;

import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Chair extends Item {

    public Chair(Vector pos) {
        super(pos, 54);
    }

    @Override
    public void onEvent(Player player) {
        player.addChair(image);
        remove();
    }
}
