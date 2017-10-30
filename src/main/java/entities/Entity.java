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

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    protected int x, y, width, height;
    protected BufferedImage image;
    
    public Entity(int x, int y, int width, int height, String file) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Entity(int x, int y, int width, int height, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }
    
    public Entity(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.image = image;
    }
    
    public int getWidth() { return width; }
    
    public int getHeight() { return height; }
    
    public int getX() { return x; }
    
    public int getY() { return y; }
    
    public BufferedImage getImage() { return image; }
    
    public void setX(int x) { this.x = x; }
    
    public void setY(int y) { this.y = y; }
    
    public Rectangle getBounds() { return new Rectangle(x, y, width, height); } // Used primarily for collision detection
    
}
