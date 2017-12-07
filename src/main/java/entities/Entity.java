/*
 * The Entity class. Used for defining in game objects.
 * 
 * @param pos - the position (Vector) of the entity
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
    
    protected BufferedImage image;
    protected Vector pos, size;
    protected Vector velocity = new Vector();
    protected boolean removed = false;
    
    public Entity(Vector pos, Vector size, String file) {
        this.size = size;
        this.image = Tools.getImage(file);
        this.pos = pos;
    }

    public Entity(Vector pos, Vector size, BufferedImage image) {
        this.pos = pos;
        this.size = size;
        this.image = image;
    }
    
    public Entity(Vector pos, BufferedImage image) {
        this.pos = pos;
        this.size = new Vector(image.getWidth(), image.getHeight());
        this.image = image;
    }
    
    public Entity(Vector pos, String file) {
        this.image = Tools.getImage(file);
        this.pos = pos;
        this.size = new Vector(image.getWidth(), image.getHeight());
    }
    
    public abstract void update();
    
    public void render(Graphics g) {
        g.drawImage(image, (int)(pos.x), (int)(pos.y), (int)size.x, (int)size.y, null);
    }
    
    public double getWidth() { return size.x; }
    
    public double getHeight() { return size.y; }
    
    public Vector getPosition() { return pos; }
    
    public BufferedImage getImage() { return image; }
    
    public void setPosition(Vector pos) { this.pos = pos; }
    
    public Rectangle getBounds() { return new Rectangle((int)pos.x, (int)pos.y, (int)size.x, (int)size.y); } // Used primarily for collision detection
    
    public Rectangle getRight() { return new Rectangle((int)(pos.x + (size.x - 3)), (int)(pos.y + 3), 3, (int)(size.y - 6));}
    
    public Rectangle getLeft() { return new Rectangle((int)pos.x, (int)pos.y + 3, 3, (int)(size.y - 6));}
    
    public Rectangle getBottom() { return new Rectangle((int)pos.x + 3, (int)(pos.y + (size.y - 3)), (int)(size.y - 6), 3);}
    
    public Rectangle getTop() { return new Rectangle((int)pos.x + 3, (int)pos.y, (int)(size.y - 6), 3);}
    
    public boolean isRemoved() { return removed; }
    
    public void remove() { removed = true; }
    
}
