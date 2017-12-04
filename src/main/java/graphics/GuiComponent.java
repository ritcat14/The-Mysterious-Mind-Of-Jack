package graphics;

import handler.Vector;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class GuiComponent {
    
    protected Vector pos;
    protected Vector size;
    
    protected ArrayList<GuiComponent> components = new ArrayList<GuiComponent>();
    protected ArrayList<GuiComponent> componentsToAdd = new ArrayList<GuiComponent>();
    protected ArrayList<GuiComponent> componentsToRemove = new ArrayList<GuiComponent>();
    
    private boolean isUpdating = false;
    private boolean isRendering = false;
    
    public GuiComponent(Vector pos, Vector size) {
        this.pos = pos;
        this.size = size;
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)pos.x, (int)pos.y, (int)size.x, (int)size.y);
    }
    
    public void add(GuiComponent c) {
        if (isUpdating || isRendering) {
            componentsToAdd.add(c);
        } else components.add(c);
    }
    
    public void remove(GuiComponent c) {
        if (isUpdating || isRendering) {
            componentsToRemove.add(c);
        } else components.remove(c);
    }
    
    public void update() {
        for (GuiComponent c : components) {
            isUpdating = true;
            c.update();
        }
        isUpdating = false;
        if (!isRendering) {
            components.addAll(componentsToAdd);
            componentsToAdd.clear();
            components.removeAll(componentsToRemove);
            componentsToRemove.clear();
        }
    }
    
    public void render(Graphics g) {
        for (GuiComponent c : components) {
            isRendering = true;
            c.render(g);
        }
        isRendering = false;
    }
    
    public void setX(double x) { this.pos.x = x; }
    
    public void setY(double y) { this.pos.y = y; }
    
    public void setWidth(double w) { this.size.x = w; }
    
    public void setHeight(double h) { this.pos.y = h; }
    
}
