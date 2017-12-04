package graphics;

import java.awt.Color;

import handler.Vector;

public class GuiBar extends GuiPanel {
    
    private GuiPanel colouredPanel;
    
    public GuiBar(Vector pos, Vector size, Color col, int width) {
        super(pos, size, Color.BLACK);
        colouredPanel = new GuiPanel(pos, new Vector(width, size.y), col);
        add(colouredPanel);
    }
    
    public void setValue(int val) {
        colouredPanel.setWidth(val);
    }
    
}
