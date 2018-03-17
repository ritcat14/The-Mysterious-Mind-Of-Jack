package entities.items.weapons;

import core.Map;
import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Sword extends Item {

    private double range, damage;
    private double shotSpeed;
    private final int ID;
    private Map map;

    public Sword(Map map, Vector pos, int damage) {
        super(pos, 9);
        this.damage = damage;
        this.range = 0;
        this.shotSpeed = 0;
        this.map = map;
        this.ID = 9;
    }


    @Override
    public void onEvent(Player player) {
        player.setWeapon(new Weapon(map, pos, ID, range, damage, shotSpeed));
        remove();
    }
}
