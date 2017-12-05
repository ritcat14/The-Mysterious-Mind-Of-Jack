package core;

import handler.Tools;
import handler.Vector;
import states.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Entity;
import entities.Player;
import events.Event;
import events.Event.Type;
import events.EventDispatcher;
import events.EventHandler;
import events.EventListener;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
import core.tiles.Tile;

public class Map implements EventListener {
    
    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entities;
    private BufferedImage background;
    private Player player;
    
    public Map(Game game, int chapter) { // The ID of the chapter is required for accessing the map data
        Decoder decoder = new Decoder();
        decoder.decode(this, chapter);
        tiles = decoder.getTiles();
        player = decoder.getPlayer();
        entities = decoder.getEntities();
        background = Tools.getImage("/chapters/chapter" + chapter + "/background.png");
        game.setPlayer(player);
    }
    
    public void update() {
        player.update();
        for (Tile t : tiles) {
            t.update();
        }
        for (Entity e : entities) {
            e.update();
        }
    }
    
    public void render(Graphics g) {
        g.drawImage(background, player.getXScroll(), 0, null);
        for (Tile t : tiles) {
            t.setScroll(player.getXScroll(), 0);
            t.render(g);
        }
        player.render(g);
        for (Entity e : entities) {
            e.setScroll(player.getXScroll(), 0);
            e.render(g);
        }
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Type.KEY_PRESSED, new EventHandler() {
            @Override
            public boolean onEvent(Event event) {
                return player.keyPressed((KeyPressedEvent)event);
            }
        });
        dispatcher.dispatch(Type.KEY_RELEASED, new EventHandler() {
            @Override
            public boolean onEvent(Event event) {
                return player.keyReleased((KeyReleasedEvent)event);
            }
        });
    }
    
    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    
    public Tile isSolid(Vector position) {
        for (Tile t : tiles) {
            if (t.getBounds().contains(position.getPoint())) {
                return t;
            }
        }
        return null;
    }
    
}
