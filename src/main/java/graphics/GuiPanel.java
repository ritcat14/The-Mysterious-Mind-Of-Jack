package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import events.Event;
import events.EventListener;

public class GuiPanel extends GuiComponent implements EventListener {

    protected Color colour = Color.BLACK;
    protected BufferedImage image;
    
    public GuiPanel(int x, int y, int width, int height, Color col) {
        super(x, y, width, height);
        this.colour = col;
    }
    
    public GuiPanel(int x, int y, int width, int height, BufferedImage img) {
        super(x, y, width, height);
        this.image = img;
    }
    
    public GuiPanel(int x, int y, BufferedImage img) {
        super(x, y, img.getWidth(), img.getHeight());
        this.image = img;
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(colour);
        g.fillRect(x, y, width, height);
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
        super.render(g);
    }

    @Override
    public void onEvent(Event event) {
        for (GuiComponent c : components) {
            if (c instanceof EventListener) {
                ((EventListener)c).onEvent(event);
            }
        }
    }
    
}

