package graphics;

import java.awt.Color;
import java.awt.Graphics;

import handler.Vector;

public class GuiBar extends GuiPanel {
    
    private GuiPanel colouredPanel;
    private int width = 0;
    private final int desiredWidth = 200;
    private int incrementalDecrease;
    
    public GuiBar(Vector pos, Vector size, Color col, int value, int maxValue) {
        super(pos, size, Color.GRAY);
        incrementalDecrease = desiredWidth / maxValue;
        this.width = value * incrementalDecrease;
        colouredPanel = new GuiPanel(pos, new Vector(width, size.getY()), col);
    }
    
    public void setValue(int val) {
        this.width = incrementalDecrease * val;
    }
    
    @Override
    public void update() {
    	if (!visible) return;
    	super.update();
    	colouredPanel.setWidth(width);
    	colouredPanel.update();
    }
    
    @Override
    public void render(Graphics g) {
    	if (!visible) return;
    	super.render(g);
    	colouredPanel.render(g);
    }
    
}
