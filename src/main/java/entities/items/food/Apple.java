/*
This is a food item class. When collected (by running into) this will affect the players stats.
 */
package entities.items.food;

import handler.Vector;

public class Apple extends Food {
    public Apple(Vector pos) {
        super(pos, 4, 0.7, 50.0, 10.0, 5.0);
    }
}
