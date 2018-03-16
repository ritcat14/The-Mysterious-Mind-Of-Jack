/*
This class extends the item class and is a super class to the Apple, Burger, Crisp, and Pill classes.
 */
package entities.items.food;

import entities.Player;
import entities.items.Item;
import handler.Vector;

import java.util.Random;

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
        int multiplier = (new Random().nextBoolean() ? 1 : -1);
        player.adjustHealth(multiplier * healthEffect);
        player.adjustSheild(multiplier * defenseBonus);
        player.adjustAttack(multiplier * attackBonus);
        remove();
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
