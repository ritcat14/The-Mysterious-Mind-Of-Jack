package graphics;

import handler.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import events.Event;
import events.EventListener;

public class GuiPanel extends GuiComponent implements EventListener {

    protected Color colour = Color.BLACK;
    protected BufferedImage image;
    
    public GuiPanel(Vector pos, Vector size, Color col) {
        super(pos, size);
        this.colour = col;
    }
    
    public GuiPanel(Vector pos, Vector size) {
        super(pos, size);
        this.colour = null;
    }
    
    public GuiPanel(Vector pos, Vector size, BufferedImage img) {
        super(pos, size);
        this.image = img;
    }
    
    public GuiPanel(Vector pos, BufferedImage img) {
        super(pos, new Vector(img.getWidth(), img.getHeight()));
        this.image = img;
    }
    
    @Override
    public void render(Graphics g) {
    	if (!visible) return;
        if (colour != null) {
            g.setColor(colour);
            g.fillRect((int)pos.x, (int)pos.y, (int)size.x, (int)size.y);
        }
        if (image != null) {
            g.drawImage(image, (int)pos.x, (int)pos.y, (int)size.x, (int)size.y, null);
        }
        super.render(g);
    }
    
    public void setImage(BufferedImage image) {
		this.image = image;
	}

    @Override
    public void onEvent(Event event) {
    	if (!visible) return;
        for (GuiComponent c : components) {
            if (c instanceof EventListener) {
                ((EventListener)c).onEvent(event);
            }
        }
    }
    
}

