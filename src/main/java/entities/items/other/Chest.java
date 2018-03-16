/*
This class creates a chest entity on the map which can be opened by collecting the key.
The event method at the bottom checks whether the player has the key.
 */
package entities.items.other;

import entities.Player;
import entities.items.Item;
import entities.items.food.Pill;
import handler.Tools;
import handler.Vector;

public class Chest extends Item {

    private boolean open = false;
    private boolean setImage = false;

    public Chest(Vector pos) {
        super(pos);
        image = Tools.getImage("/items/chest-locked.png");
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public void update() {
        super.update();
        if (open && !setImage) {
            setImage = true;
            image = Tools.getImage("/items/chest-open.png");
        }
    }

    @Override
    public void onEvent(Player player) {
        if (player.hasKey()) open = true;
        player.spawn(new Pill(new Vector(pos.getX(), pos.getY())));
    }
}
