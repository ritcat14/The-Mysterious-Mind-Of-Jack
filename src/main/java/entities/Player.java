package entities;

import entities.items.Item;
import entities.items.other.Chest;
import entities.items.other.Hawking;
import entities.items.weapons.Weapon;
import graphics.GuiBar;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import core.Map;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
/*
A child class Player from Mob
 */

public class Player extends Mob {
	
    private boolean up, left, right;
    private GuiBar healthBar, sheildBar;
    private final int EDGE_DISTANCE = 100;
    private BufferedImage h, d;

    private Weapon weapon;

    private boolean easterEgg = false;
    private boolean activateButton = false;
    private String message = "";
    private int officeBounds = 300;
    private boolean shootLeft, shootRight;

    private boolean hasKey, hasWheel, hasChair, hasCell = false;
    private BufferedImage chair, cell, wheel;

    private boolean hasHead, hasChest, hasLegs = false;
    private BufferedImage head, chest, legs;

    private boolean hasGun = false;
    private BufferedImage gun;

    private Companion companion;

    public Player(Map map, Vector pos) {
        super(map, pos, new Vector(48, 96), "/player/player.png");
        shield = 5;
        speed = 0.08;
        healthBar = new GuiBar(new Vector(20, 30), new Vector(200, 10), Color.GREEN, (int)health, MAX_HEALTH);
        sheildBar = new GuiBar(new Vector(20, 50), new Vector(200, 10), Color.BLUE, shield, MAX_SHIELD);
        
        h = Tools.getImage("/gui/health.png");
        d = Tools.getImage("/gui/shield.png");

        weapon = new Weapon(map, new Vector(pos.getX() + size.getX() + 10, pos.getY() + (size.getY() / 2)), 9, 0, 30, 0);
    }

    public void setWeapon(Weapon weapon) {
        if (hasGun) return;
        this.weapon = weapon;
        //TODO: set up player sprite change
    }

    public void addHead(BufferedImage head) {
        hasHead = true;
        this.head = head;
    }

    public void addChest(BufferedImage chest) {
        hasChest = true;
        this.chest = chest;
    }

    public void addLegs(BufferedImage legs) {
        hasLegs = true;
        this.legs = legs;
    }

    public void addWheel(BufferedImage wheel) {
        hasWheel = true;
        this.wheel = wheel;
    }

    public void addChair(BufferedImage chair) {
        hasChair = true;
        this.chair = chair;
    }

    public void addCell(BufferedImage cell) {
        hasCell = true;
        this.cell = cell;
    }

    public void addGun(BufferedImage gun) {
        hasGun = true;
        this.gun = gun;
    }

    public void addKey() {
        hasKey = true;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public boolean isEasterEgg() {
        return easterEgg;
    }

    public void spawn(Item item) {
        map.add(item);
    }

    public void activateEgg() {
        easterEgg = true;
        MAX_HEALTH = 400;
        MAX_SHIELD = 200;
        health = 400;
        shield = 200;
    }

    public void spawnCompanion(Boss boss) {
        if (!hasKey) return;
        companion = new Companion(map, boss);
        map.add(companion);
    }

    @Override
    public boolean hasHorizontalCollision() {
        boolean passed;
        if (pos.getX() <= officeBounds && !hasKey) {
            message = "Requires Key.";
            pos.setX(officeBounds + 1);
            passed = true;
        }
        if (pos.getY() < StateHandler.HEIGHT - 430) {
            velocity.setY(-velocity.getY());
            passed = true;
        }
        if (pos.getX() + size.getX() > StateHandler.WIDTH) {
            pos.setX(StateHandler.WIDTH - 1);
            passed = true;
        }
        passed = super.hasHorizontalCollision();
        return passed;
    }

    @Override
    public void update() {
        officeBounds = (int)(map.getX() + 300);
        healthBar.setValue((int)health);
        sheildBar.setValue(shield);

       if (map.getBoss() != null && companion == null) {
            if (!hasKey) message = "Activate Poster";
            else spawnCompanion(map.getBoss());
        }

        if ((shootLeft || shootRight)) weapon.shoot(shootLeft ? 1 : 0);
        if (dir == 0) weapon.setPosition(new Vector(pos.getX() + size.getX() - 20, (pos.getY() + (size.getY() / 2)) - 20));
        else weapon.setPosition(new Vector(pos.getX() - 10, (pos.getY() + (size.getY() / 2)) - 10));
        weapon.update();

        if (up) jump();
        
        if (left) velocity.adjustX(-speed);
        else if (velocity.getX() < 0) velocity.adjustX(speed);
        
        if (right) velocity.adjustX(speed);
        else if (velocity.getX() > 0) velocity.adjustX(-speed);
        
        if (velocity.getX() > -speed && velocity.getX() < speed) velocity.setX(0);
        
        if (pos.getX() + size.getX() > StateHandler.WIDTH - EDGE_DISTANCE && velocity.getX() > 0) {
        	if (map.getX() + (-velocity.getX()) + map.getImage().getWidth() > StateHandler.WIDTH) {
        	    pos.setX(StateHandler.WIDTH - EDGE_DISTANCE -size.getX() - 1);
        	    map.setXPosition(-velocity.getX());
            }
        }
        
        if (pos.getX() < EDGE_DISTANCE) {
            if (map.getX() + (-velocity.getX()) < 0) {
                if (velocity.getX() < 0) {
                    pos.setX(EDGE_DISTANCE + 1);
                    map.setXPosition(-velocity.getX());
                }
            }
        }
        super.update();
        healthBar.update();
        sheildBar.update();

        ArrayList<Item> items = map.getItems();
        for (Item i : items) {
            if (getBounds().intersects(i.getBounds())) {
                if (i instanceof Chest) {
                    if (!((Chest)i).isOpen()) message = "Press E (requires key).";
                    if (!activateButton) continue;
                }
                if (i instanceof Hawking && !activateButton) continue;
                i.onEvent(this);
            }
        }
        if (health == 0) StateHandler.changeState(StateHandler.States.GAMEOVER);
    }
    
    public void pause() {
    	left = false;
    	right = false;
    	up = false;
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        weapon.render(g);
        healthBar.render(g);
        g.drawImage(h, (int)(healthBar.getX() - 15), (int)healthBar.getY(), null);
        sheildBar.render(g);
        g.drawImage(d, (int)(sheildBar.getX() - 15), (int)sheildBar.getY(), null);
        g.setColor(Color.RED);
        g.drawString(message, (int)pos.getX(), (int)pos.getY() - 15);
        message = "";

        if (easterEgg) g.drawImage(Tools.getImage("/items/51.png"), 10, 100, null);
        if (hasGun) g.drawImage(gun, 60, 100, null);

        if (hasHead) g.drawImage(head, 60, 150, null);
        if (hasChest) g.drawImage(chest, 60, 200, null);
        if (hasLegs) g.drawImage(legs, 60, 250, null);

        if (hasCell) g.drawImage(cell, 10, 150, null);
        if (hasChair) g.drawImage(chair, 10, 200, null);
        if (hasWheel) g.drawImage(wheel, 10, 250, null);

    }
    
    public void setUp(Boolean up) {
    	this.up = up;
    }

    public boolean getPlayerLeft() { return this.left;}

    public boolean getPlayerRight() { return this.right;}

    public boolean getPlayerUp() { return this.up;}

    public boolean hasChest() {
        return hasChest;
    }

    public boolean hasHead() {
        return hasHead;
    }

    public boolean hasLegs() {
        return hasLegs;
    }

    public boolean hasCell() {
        return hasCell;
    }

    public boolean hasChair() {
        return hasChair;
    }

    public boolean hasWheel() {
        return hasWheel;
    }

    public boolean hasGun() {
        return hasGun;
    }

    public boolean keyReleased(KeyReleasedEvent e) {
        switch(e.getKey()) {
            case KeyEvent.VK_W:
            up = false;
            return true;
            case KeyEvent.VK_SPACE:
            up = false;
            return true;
            case KeyEvent.VK_A:
            left = false;
            return true;
            case KeyEvent.VK_S:
            return true;
            case KeyEvent.VK_D:
            right = false;
            return true;
            case KeyEvent.VK_E:
                activateButton = false;
                return true;
            case KeyEvent.VK_LEFT:
                shootLeft = false;
                return true;
            case KeyEvent.VK_RIGHT:
                shootRight = false;
                return true;
        }
        return false;
    }
    
    public boolean keyPressed(KeyPressedEvent e) {
        switch(e.getKey()) {
            case KeyEvent.VK_W:
	            up = true;
	            return true;
            case KeyEvent.VK_SPACE:
	            up = true;
	            return true;
            case KeyEvent.VK_A:
	            left = true;
	            return true;
            case KeyEvent.VK_S:
	            return true;
            case KeyEvent.VK_D:
	            right = true;
	            return true;
            case KeyEvent.VK_E:
                activateButton = true;
                return true;
            case KeyEvent.VK_LEFT:
                shootLeft = true;
                return true;
            case KeyEvent.VK_RIGHT:
                shootRight = true;
                return true;
        }
        return false;
    }
    
}
