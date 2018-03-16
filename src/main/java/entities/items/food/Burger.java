/*
This is a food item class. When collected (by running into) this will affect the players stats.
 */
package entities.items.food;

import handler.Vector;

public class Burger extends Food{

    public Burger(Vector pos) {
        super(pos, 6, 0.4, 25.0, 5.0, 1.0);
    }
}
