package graphics;

import java.awt.Color;
import java.awt.Graphics;

import handler.Vector;

public class GuiBar extends GuiPanel {
    
    private GuiPanel colouredPanel;
    private int width = 0;
    
    public GuiBar(Vector pos, Vector size, Color col, int width) {
        super(pos, size, Color.GRAY);
        this.width = width;
        colouredPanel = new GuiPanel(pos, new Vector(width, size.y), col);
    }
    
    public void setValue(int val) {
        this.width = val;
    }
    
    @Override
    public void update() {
    	super.update();
    	colouredPanel.setWidth(width);
    	colouredPanel.update();
    }
    
    @Override
    public void render(Graphics g) {
    	super.render(g);
    	colouredPanel.render(g);
    }
    
}
