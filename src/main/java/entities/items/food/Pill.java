package entities.items.food;

import entities.Player;
import handler.Vector;

public class Pill extends Food {

    public Pill(Vector pos) {
        super(pos, 51, 1, 0, 0, 0);
    }

    @Override
    public void onEvent(Player player) {
        player.activateEgg();
        remove();
    }
}
