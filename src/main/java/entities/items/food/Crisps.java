/*
This is a food item class. When collected (by running into) this will affect the players stats.
 */
package entities.items.food;

import handler.Vector;

public class Crisps extends Food {
    public Crisps(Vector pos, int ID) {
        super(pos, ID, 0.6, 15.0, 5.0, 5.0);
    }
}
