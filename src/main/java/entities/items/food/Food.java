/*
This class extends the item class and is a super class to the Apple, Burger, Crisp, and Pill classes.
 */
package entities.items.food;

import entities.Player;
import entities.items.Item;
import handler.Vector;

public abstract class Food extends Item {

    private double healthEffect;
    private double attackBonus;
    private double defenseBonus;

    public Food(Vector pos, int ID, double rarity, double health, double attack, double defense) {
        super(pos, ID, rarity);
        healthEffect = health;
        attackBonus = attack;
        defenseBonus  = defense;
    }

    @Override
    public void onEvent(Player player) {
        player.adjustHealth(healthEffect);
        player.adjustSheild(defenseBonus);
        player.adjustAttack(attackBonus);
    }

    public double getHealthEffect() {
        return healthEffect;
    }

    public double getAttackBonus() {
        return attackBonus;
    }

    public double getDefenseBonus() {
        return defenseBonus;
    }
}
