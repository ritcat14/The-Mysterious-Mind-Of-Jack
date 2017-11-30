package core.tiles;

import handler.Tools;
import handler.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import entities.Entity;

public class Tile extends Entity {
    
    protected boolean solid = false;
    
    public Tile(Vector pos, int ID, int chapterID) {
        super(pos, Tools.getImage("/chapters/chapter" + chapterID + "/tiles/" + ID + ".png"));
    }
    
    public boolean isSolid() {
        return solid;
    }
    
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
    
    public void update() {
        
    }
    
    public Rectangle getRight() {
        return new Rectangle((int)(pos.x + (width - 3)), (int)(pos.y + 3), 3, height - 3);
    }
    
    public Rectangle getLeft() {
        return new Rectangle((int)pos.x, (int)pos.y + 3, 3, height - 3);
    }
    
    public Rectangle getBottom() {
        return new Rectangle((int)pos.x, (int)(pos.y + (height - 3)), width, 3);
    }
    
    public Rectangle getTop() {
        return new Rectangle((int)pos.x, (int)pos.y, width, 3);
    }
    
    public void render(Graphics g) {
        g.drawImage(image, (int)pos.x, (int)pos.y, width, height, null);
        g.setColor(Color.BLUE);
        g.drawRect(getRight().x, getRight().y, getRight().width, getRight().height);
        g.drawRect(getLeft().x, getLeft().y, getLeft().width, getLeft().height);
        g.drawRect(getTop().x, getTop().y, getTop().width, getTop().height);
        g.drawRect(getBottom().x, getBottom().y, getBottom().width, getBottom().height);
    }
    
}
