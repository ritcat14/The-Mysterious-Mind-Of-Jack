package graphics;

import handler.Vector;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
/*
A class consisted of Gui components
 */

public class GuiComponent {
    
    protected Vector pos;
    protected Vector size;
    
    protected ArrayList<GuiComponent> components = new ArrayList<>();
    protected ArrayList<GuiComponent> componentsToAdd = new ArrayList<>();
    protected ArrayList<GuiComponent> componentsToRemove = new ArrayList<>();
    
    protected boolean visible = true;
    
    private boolean isRendering = false;
    
    public GuiComponent(Vector pos, Vector size) {
        this.pos = pos;
        this.size = size;
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)pos.getX(), (int)pos.getY(), (int)size.getX(), (int)size.getY());
    }
    
    public void addAll(ArrayList<GuiComponent> c) {
        if (isRendering) {
            componentsToAdd.addAll(c);
        } else components.addAll(c);
    }
    
    public void removeAll(ArrayList<GuiComponent> c) {
        if (isRendering) {
            componentsToRemove.addAll(c);
        } else components.removeAll(c);
    }
    
    public void add(GuiComponent c) {
        if (isRendering) {
            componentsToAdd.add(c);
        } else components.add(c);
    }
    
    public void remove(GuiComponent c) {
        if (isRendering) {
            componentsToRemove.add(c);
        } else components.remove(c);
    }
    
    public void update() {
    	if (!visible) return;
        for (GuiComponent c : components) {
            c.update();
        }
        if (!isRendering) {
            components.addAll(componentsToAdd);
            componentsToAdd.clear();
            components.removeAll(componentsToRemove);
            componentsToRemove.clear();
        }
    }
    
    public void render(Graphics g) {
    	if (!visible) return;
        for (GuiComponent c : components) {
            isRendering = true;
            c.render(g);
        }
        isRendering = false;
    }
    
    public boolean isVisible() { return visible; }
    
    public double getX() { return pos.getX(); }
    
    public double getY() { return pos.getY(); }
    
    public double getWidth() { return size.getX(); }
    
    public double getHeight() { return size.getY(); }
    
    public void setX(double x) { this.pos.setX(x); }
    
    public void setY(double y) { this.pos.setY(y); }
    
    public void setWidth(double w) { this.size.setX(w); }
    
    public void setHeight(double h) { this.size.setY(h); }
    
    public void setVisible(boolean visible) { this.visible = visible; }
    
}
