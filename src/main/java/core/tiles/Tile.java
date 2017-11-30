package core.tiles;

import handler.Tools;
import handler.Vector;

import java.awt.Graphics;

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
    
    public void render(Graphics g) {
        g.drawImage(image, (int)pos.x, (int)pos.y, width, height, null);
    }
    
}
