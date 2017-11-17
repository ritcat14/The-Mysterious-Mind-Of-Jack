package graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class GuiComponent {
    
    protected int x, y, width, height;
    
    protected ArrayList<GuiComponent> components = new ArrayList<GuiComponent>();
    protected ArrayList<GuiComponent> componentsToAdd = new ArrayList<GuiComponent>();
    protected ArrayList<GuiComponent> componentsToRemove = new ArrayList<GuiComponent>();
    
    private boolean isUpdating = false;
    private boolean isRendering = false;
    
    public GuiComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
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
    
}
