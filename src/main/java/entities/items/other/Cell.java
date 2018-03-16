package entities.items.other;

import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Cell extends Item {

    public Cell(Vector pos) {
        super(pos, 55);
    }

    @Override
    public void onEvent(Player player) {
        player.addCell(image);
        remove();
    }
}
