/*
 * The Entity class. Used for defining in game objects.
 * 
 * @param x - the x location of the entity
 * @param y - the y location of the entity
 * @param width - the width of the entity
 * @param height - the height of the entity
 * @param image - the image (skin) of the entitiy
 * @param file - the file location of the image of the entity
 * 
 */

package entities;

import handler.Tools;
import handler.Vector;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {
    
    protected int width, height;
    protected BufferedImage image;
    protected Vector pos;
    
    public Entity(Vector pos, int width, int height, String file) {
        this.width = width;
        this.height = height;
        this.image = Tools.getImage(file);
        this.pos = pos;
    }

    public Entity(Vector pos, int width, int height, BufferedImage image) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.image = image;
    }
    
    public Entity(Vector pos, BufferedImage image) {
        this.pos = pos;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.image = image;
    }
    
    public Entity(Vector pos, String file) {
        this.image = Tools.getImage(file);
        this.pos = pos;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    
    public abstract void update();
    
    public abstract void render(Graphics g);
    
    public int getWidth() { return width; }
    
    public int getHeight() { return height; }
    
    public Vector getPosition() { return pos; }
    
    public BufferedImage getImage() { return image; }
    
    public void setPosition(Vector pos) { this.pos = pos; }
    
    public Rectangle getBounds() { return new Rectangle((int)pos.x, (int)pos.y, width, height); } // Used primarily for collision detection
    
}
