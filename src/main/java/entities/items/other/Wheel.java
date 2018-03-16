package entities.items.other;

import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Wheel extends Item {

    public Wheel(Vector pos) {
        super(pos, 53);
    }

    @Override
    public void onEvent(Player player) {
        player.addWheel(image);
        remove();
    }
}
