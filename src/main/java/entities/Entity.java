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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {
    
    protected int width, height;
    protected BufferedImage image;
    protected Vector pos;
    protected Vector velocity = new Vector();
    
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
    
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(getRight().x, getRight().y, getRight().width, getRight().height);
        g.drawRect(getLeft().x, getLeft().y, getLeft().width, getLeft().height);
        g.drawRect(getTop().x, getTop().y, getTop().width, getTop().height);
        g.drawRect(getBottom().x, getBottom().y, getBottom().width, getBottom().height);
    }
    
    public int getWidth() { return width; }
    
    public int getHeight() { return height; }
    
    public Vector getPosition() { return pos; }
    
    public BufferedImage getImage() { return image; }
    
    public void setPosition(Vector pos) { this.pos = pos; }
    
    public Rectangle getBounds() { return new Rectangle((int)pos.x, (int)pos.y, width, height); } // Used primarily for collision detection
    
    public Rectangle getRight() { return new Rectangle((int)(pos.x + (width - 3)), (int)(pos.y + 3), 3, height - 6);}
    public Rectangle getLeft() { return new Rectangle((int)pos.x, (int)pos.y + 3, 3, height - 6);}
    
    public Rectangle getBottom() { return new Rectangle((int)pos.x + 3, (int)(pos.y + (height - 3)), width - 6, 3);}
    
    public Rectangle getTop() { return new Rectangle((int)pos.x + 3, (int)pos.y, width - 6, 3);}
    
}
