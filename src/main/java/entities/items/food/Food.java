package entities.items.food;

import entities.items.Item;
import handler.Vector;

public class Food extends Item {

    private double healthEffect;
    private double attackBonus;
    private double defenseBonus;

    public Food(Vector pos, int ID, double rarity, double health, double attack, double defense) {
        super(pos, ID, rarity);
        healthEffect = health;
        attackBonus = attack;
        defenseBonus  = defense;
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