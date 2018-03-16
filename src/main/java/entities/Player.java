package entities;

import entities.items.Item;
import entities.items.other.Chest;
import entities.items.other.Hawking;
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

public class Player extends Mob {
	
    private boolean up, left, right;
    private GuiBar healthBar, sheildBar;
    private final int EDGE_DISTANCE = 100;
    private BufferedImage h, d;

    private boolean easterEgg = false;
    private boolean activateButton = false;
    private String message = "";
    private int officeBounds = 300;

    private boolean hasKey, hasWheel, hasChair, hasCell = false;
    private BufferedImage chair, cell, wheel;

    private boolean hasHead, hasChest, hasLegs = false;
    private BufferedImage head, chest, legs;


    public Player(Map map, Vector pos) {
        super(map, pos, new Vector(48, 96), "/player/player.png");
        shield = 5;
        speed = 0.08;
        healthBar = new GuiBar(new Vector(20, 30), new Vector(200, 10), Color.GREEN, (int)health, MAX_HEALTH);
        sheildBar = new GuiBar(new Vector(20, 50), new Vector(200, 10), Color.BLUE, shield, MAX_SHIELD);
        
        h = Tools.getImage("/gui/health.png");
        d = Tools.getImage("/gui/shield.png");
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

    @Override
    void jump() {
        if (onGround) {
            velocity.y -= 3.5;
            pos.add(new Vector(0, velocity.y));
        }
    }

    @Override
    public boolean hasHorizontalCollision() {
        boolean passed;
        if (pos.x <= officeBounds && !hasKey) {
            message = "Requires Key.";
            pos.x = officeBounds + 1;
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
        if (up) jump();
        
        if (left) velocity.x -= speed;
        else if (velocity.x < 0) velocity.x += speed;
        
        if (right) velocity.x += speed;
        else if (velocity.x > 0) velocity.x -= speed;
        
        if (velocity.x > -speed && velocity.x < speed) velocity.x = 0;
        
        if (pos.x + size.x > StateHandler.WIDTH - EDGE_DISTANCE && velocity.getX() > 0) {
        	if (map.getX() + (-velocity.x) + map.getImage().getWidth() > StateHandler.WIDTH) {
        	    pos.x = StateHandler.WIDTH - EDGE_DISTANCE -size.x - 1;
        	    map.setXPosition(-velocity.getX());
            }
        }
        
        if (pos.x < EDGE_DISTANCE) {
            if (map.getX() + (-velocity.x) < 0) {
                if (velocity.x < 0) {
                    pos.x = EDGE_DISTANCE + 1;
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
    }
    
    public void pause() {
    	left = false;
    	right = false;
    	up = false;
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        healthBar.render(g);
        g.drawImage(h, (int)(healthBar.getX() - 15), (int)healthBar.getY(), null);
        sheildBar.render(g);
        g.drawImage(d, (int)(sheildBar.getX() - 15), (int)sheildBar.getY(), null);
        g.setColor(Color.RED);
        g.drawString(message, (int)pos.getX(), (int)pos.getY() - 15);
        message = "";

        if (hasHead) g.drawImage(head, 80, 200, null);
        if (hasChest) g.drawImage(chest, 80, 260, null);
        if (hasLegs) g.drawImage(legs, 80, 340, null);

        if (hasChair && hasCell && hasWheel) return;
        if (hasCell) g.drawImage(cell, 20, 200, null);
        if (hasChair) g.drawImage(chair, 20, 260, null);
        if (hasWheel) g.drawImage(wheel, 20, 340, null);

    }
    
    public void setUp(Boolean up) {
    	this.up = up;
    }
    
    public void setLeft(Boolean left) {
    	this.left = left;
    }
    
    public void setRight(Boolean right) {
    	this.right = right;
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
        }
        return false;
    }
    
}
