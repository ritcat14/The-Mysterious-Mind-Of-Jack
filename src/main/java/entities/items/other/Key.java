/*
This class is for a key entity which can be picked up by the player and is used to open the chest entity.
 */
package entities.items.other;

import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Key extends Item {

    public Key(Vector pos) {
        super(pos, 50);
    }

    @Override
    public void onEvent(Player player) {
        player.addKey();
        remove();
    }
}
