package core;

import handler.Tools;

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

public class Map implements EventListener {
    
    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entities;
    private BufferedImage background;
    private int xScroll = 0;
    private Player player;
    
    public Map(int chapter) { // The ID of the chapter is required for accessing the map data
        Decoder decoder = new Decoder();
        decoder.decode(chapter);
        tiles = decoder.getTiles();
        player = decoder.getPlayer();
        entities = decoder.getEntities();
        background = Tools.getImage("/chapters/chapter" + chapter + "/background.png");
        entities.add(player);
    }
    
    public void update() {
        this.xScroll = player.getxScroll();
        for (Tile t : tiles) {
            t.update();
        }
        for (Entity e : entities) {
            e.update();
        }
    }
    
    public void render(Graphics g) {
        g.drawImage(background, xScroll, 0, null);
        for (Tile t : tiles) {
            t.render(g);
        }
        for (Entity e : entities) {
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
    
}
