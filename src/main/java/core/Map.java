/*
This class generates, updates, and renders the map, player, entities, and enemies.
 */

package core;

import entities.*;
import entities.items.Item;
import entities.items.armour.Body;
import entities.items.armour.Head;
import entities.items.armour.Legs;
import entities.items.food.*;
import entities.items.other.*;
import entities.items.weapons.Bat;
import entities.items.weapons.Sword;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;
import states.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import events.Event;
import events.Event.Type;
import events.EventDispatcher;
import events.EventListener;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

public class Map implements EventListener {

    private ArrayList<Entity> entities, entitiesToRemove, entitiesToAdd;
    private ArrayList<Enemy> enemies, enemiesToRemove, enemiesToAdd;
    private BufferedImage background;
    private BufferedImage backgroundEgg2;
    private BufferedImage backgroundEgg;
    private Player player;
    private boolean rendering = false;
    private double xPosition = 0;
    private int time = 0;
    private int enemyCount = 0;
    private Boss boss;
    private boolean canSpawn = true;

    public Map(Game game) {
        entities = new ArrayList<>();
        entitiesToRemove = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        enemies = new ArrayList<>();
        enemiesToRemove = new ArrayList<>();
        enemiesToAdd = new ArrayList<>();
        background = Tools.getImage("/chapter/background.png");
        backgroundEgg = Tools.getImage("/chapter/background-egg.png");
        backgroundEgg2 = Tools.getImage("/chapter/background-egg2.png");
        game.setPlayer(player = new Player(this, new Vector(500, StateHandler.HEIGHT - 200)));
        generateItems();
        spawnEnemies();
    }

    public void setXPosition(double x) {
        for (Entity e : entities) {
            if (e instanceof Player) continue;
            e.setPosition(e.getPosition().add(new Vector(x, 0)));
        }
        for (Enemy e : enemies) e.setPosition(e.getPosition().add(new Vector(x, 0)));
        xPosition += x;
    }

    public Boss getBoss() {
        return boss;
    }

    private void spawnEnemies() {
        if (player.hasWheel() && player.hasChair() && player.hasCell()) {
            System.out.println("Boss spawned");
            add(boss = new Boss(this));
            player.spawnCompanion(boss);
            canSpawn = false;
            return;
        }
        if (rendering) return;
        int enemyID = Tools.getRandom(1, 3);
        int x = Tools.getRandom(1000, 2000);
        int y = Mob.FLOOR_HEIGHT - 100;
        for (Enemy e : enemies) {
            if (e.getBounds().contains(x, y)) spawnEnemies();
        }
        enemyCount += 1;
        int healthMult = enemyCount / 3;
        add(new Enemy(this, new Vector(Tools.getRandom(1000, 2000), Mob.FLOOR_HEIGHT - 100), new Vector(48, 96), "/player/backgroundCharacter" + enemyID + ".png", 350, healthMult * 30));
    }

    public void add(Entity e) {
        if (e instanceof Enemy) enemiesToAdd.add((Enemy) e);
        else entitiesToAdd.add(e);
    }

    public void remove(Entity e) {
        if (e instanceof Enemy) enemiesToRemove.add((Enemy) e);
        else entitiesToRemove.add(e);
    }

    public double getX() {
        return xPosition;
    }

    public BufferedImage getImage() {
        return background;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    private void clean() {
        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();
        entitiesToRemove.clear();
        enemies.removeAll(enemiesToRemove);
        enemies.addAll(enemiesToAdd);
        enemiesToAdd.clear();
        enemiesToRemove.clear();
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Entity e : entities) {
            if (e.equals(player)) continue;
            if (e instanceof Item) items.add((Item) e);
        }
        return items;
    }

    private void generateItems() {
        entitiesToAdd.add(new Chest());
        entitiesToAdd.add(new Hawking());
    }

    public void spawnItem(Vector pos) {
        Vector spawnPos = new Vector(pos.getX(), pos.getY()).add(new Vector(-24, -100));
        int ran = Tools.getRandom(1, 5);
        switch (ran) {
            case 1:
                entitiesToAdd.add(new Apple(spawnPos));
                break;
            case 2:
                entitiesToAdd.add(new Banana(spawnPos));
                break;
            case 3:
                entitiesToAdd.add(new Burger(spawnPos));
                break;
            case 4:
                entitiesToAdd.add(new Crisps(spawnPos));
                break;
            case 5:
                entitiesToAdd.add(new Sandwich(spawnPos));
                break;
        }
        spawnPos.add(new Vector(24, 0));
        int maxChance = 100 - enemyCount;
        if (maxChance < 0) maxChance = 2;
        if (Tools.getRandom(1, maxChance) == 1 && !player.hasHead()) entitiesToAdd.add(new Head(spawnPos));
        else if (Tools.getRandom(1, maxChance) == 1 && !player.hasChest()) entitiesToAdd.add(new Body(spawnPos));
        else if (Tools.getRandom(1, maxChance) == 1 && !player.hasLegs()) entitiesToAdd.add(new Legs(spawnPos));

        spawnPos.add(new Vector(24, 0));
        if (Tools.getRandom(1, 10) == 1 && !player.hasCell()) entitiesToAdd.add(new Cell(spawnPos));
        else if (Tools.getRandom(1, maxChance) == 1 && !player.hasChair()) entitiesToAdd.add(new Chair(spawnPos));
        else if (Tools.getRandom(1, maxChance) == 1 && !player.hasWheel()) entitiesToAdd.add(new Wheel(spawnPos));

        spawnPos.add(new Vector(-24, 40));
        int chance = (Tools.getRandom(1, 100));
        if (chance >= 1 && chance <= 10) entitiesToAdd.add(new Sword(this, spawnPos, enemyCount * 10));
        else {
            chance = (Tools.getRandom(1, 100));
            if (chance >= 1 && chance <= 6) entitiesToAdd.add(new Bat(this, spawnPos, enemyCount * 30));
        }
    }

    private void checkEnemyCollision() {
        for (Enemy e1 : enemies) {
            for (Enemy e2 : enemies) {
                if (e1.equals(e2)) continue;
                if (e1.getRight().intersects(e2.getLeft())) {
                    e1.setX(e2.getX() - 3 - e1.getWidth());
                } else if (e1.getLeft().intersects(e2.getRight())) {
                    e1.setX(e1.getX() + e1.getWidth() + 3);
                } else if (e1.getBounds().intersects(e2.getBounds())) {
                    e1.setX(e2.getX() - 3 - e1.getWidth());
                }
            }
        }
    }

    public void update() {
        time++;
        if (time == Integer.MAX_VALUE) time = 0;
        player.update();
        for (Entity e : entities) {
            e.update();
            if (e.isRemoved()) {
                entitiesToRemove.add(e);
            }
        }
        for (Enemy e : enemies) {
            e.update();
            if (e.isRemoved()) {
                enemiesToRemove.add(e);
            }
        }
        if (time % 180 == 0 && canSpawn) spawnEnemies();
        if (!rendering) {
            checkEnemyCollision();
            clean();
        }
    }

    public void render(Graphics g) {
        rendering = true;
        if (player.isEasterEgg()) {
            time++;
            if (time == Integer.MAX_VALUE - 1) time = 0;
            if (time % Tools.getSecs(1) == 0)
                background = (background.equals(backgroundEgg) ? backgroundEgg2 : backgroundEgg);
        }
        g.drawImage(background, (int) xPosition, StateHandler.HEIGHT - background.getHeight(), null);
        for (Entity e : entities) {
            if (e.isRemoved()) entitiesToRemove.add(e);
            Vector pos = e.getPosition();
            double width = e.getWidth();
            if (pos.getX() + width < 0 || pos.getY() > StateHandler.WIDTH) continue;
            e.render(g);
        }
        for (Enemy e : enemies) {
            if (e.isRemoved()) enemiesToRemove.add(e);
            else e.render(g);
        }
        player.render(g);
        rendering = false;
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Type.KEY_PRESSED, event12 -> player.keyPressed((KeyPressedEvent) event12));
        dispatcher.dispatch(Type.KEY_RELEASED, event1 -> player.keyReleased((KeyReleasedEvent) event1));
    }
}