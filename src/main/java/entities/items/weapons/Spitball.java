package entities.items.weapons;

import core.Map;
import entities.Player;
import entities.items.Item;
import handler.Vector;

public class Spitball extends Item {

    private double range, damage;
    private double shotSpeed;
    private final int ID;
    private Map map;

    public Spitball(Map map, Vector pos, int damage) {
        super(pos, 11);
        this.damage = damage;
        this.range = 5;
        this.shotSpeed = 5;
        this.map = map;
        this.ID = 11;
    }


    @Override
    public void onEvent(Player player) {
        player.setWeapon(new Weapon(map, pos, new Vector(10, 10), ID, range, damage, shotSpeed));
        remove();
    }
}
