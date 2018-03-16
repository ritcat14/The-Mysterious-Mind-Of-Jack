/*
This is a food item class. When collected (by running into) this will affect the players stats.
 */
package entities.items.food;

import handler.Vector;

public class Crisps extends Food {
    public Crisps(Vector pos) {
        super(pos, 7, 0.4, 15.0, 5.0, 5.0);
    }
}
