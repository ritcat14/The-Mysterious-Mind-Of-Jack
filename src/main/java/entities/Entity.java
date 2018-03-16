/*
 * The Entity class. Used for defining in game objects.
 * 
 * @param pos - the position (Vector) of the entity
 * @param width - the width of the entity
 * @param height - the height of the entity
 * @param image - the image (skin) of the entitiy
 * @param file - the file location of the image of the entity
 */

package entities;

import handler.Tools;
import handler.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    
    protected BufferedImage image;
    protected Vector pos, size;
    protected Vector velocity = new Vector();
    protected boolean removed = false;
    protected Color c;
    
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

    protected Entity(Vector pos, Vector size) {
        this.pos = pos;
        this.size = size;
    }
    
    public abstract void update();
    
    public void render(Graphics g) {
        if (image != null) g.drawImage(image, (int)(pos.getX()), (int)(pos.getY()), (int)size.getX(), (int)size.getY(), null);
        else {
            g.setColor(c);
            g.drawRect((int)(pos.getX()), (int)(pos.getY()), (int)size.getY(), (int)size.getY());
        }
    }
    
    public double getWidth() { return size.getX(); }
    
    public double getHeight() { return size.getY(); }
    
    public Vector getPosition() { return pos; }
    
    public BufferedImage getImage() { return image; }
    
    public void setPosition(Vector pos) { this.pos = pos; }
    
    public Rectangle getBounds() { return new Rectangle((int)pos.getX(), (int)pos.getY(), (int)size.getX(), (int)size.getY()); } // Used primarily for collision detection
    
    public Rectangle getRight() { return new Rectangle((int)(pos.getX() + (size.getX() - 3)), (int)(pos.getY() + 3), 3, (int)(size.getY() - 6));}
    
    public Rectangle getLeft() { return new Rectangle((int)pos.getX(), (int)pos.getY() + 3, 3, (int)(size.getY() - 6));}
    
    public Rectangle getBottom() { return new Rectangle((int)pos.getX() + 3, (int)(pos.getY() + (size.getY() - 3)), (int)(size.getX() - 6), 3);}
    
    public Rectangle getTop() { return new Rectangle((int)pos.getX() + 3, (int)pos.getY(), (int)(size.getX() - 6), 3);}
    
    public boolean isRemoved() { return removed; }
    
    public void remove() { removed = true; }

    public int getX() {
        return (int) pos.getX();
    }

    public int getY() {
        return (int) pos.getY();
    }
    
}