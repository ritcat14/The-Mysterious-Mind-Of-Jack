/*
This is a food item class. When collected (by running into) this will affect the players stats.
 */
package entities.items.food;

import handler.Vector;

public class Burger extends Food{

    public Burger(Vector pos, int ID) {
        super(pos, ID, 0.7, 25.0, 5.0, 1.0);
    }
}
