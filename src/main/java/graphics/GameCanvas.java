package graphics;

import handler.StateHandler;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import events.Keyboard;
import events.Mouse;

public class GameCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static BufferedImage currentFrame;
	private int[] pixels;
	
	public GameCanvas(StateHandler sh, int width, int height) {
	    System.setProperty("sun.java2d.opengl", "true");
        currentFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)currentFrame.getRaster().getDataBuffer()).getData();
        
		setPreferredSize(new Dimension(width, height));
		
		Mouse m = new Mouse(sh);
		
		addMouseListener(m);
		addMouseMotionListener(m);
		addKeyListener(new Keyboard(sh));		
		
		requestFocus();
	}
	
	public static BufferedImage getFrame() {
		return currentFrame;
	}
	
	public void draw() {
		requestFocus();
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        for (int i = 0; i < getWidth() * getHeight(); i++)
            pixels[i] = 0;

        Graphics g = currentFrame.getGraphics();
        
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        StateHandler.render(g);
        
        bs.getDrawGraphics().drawImage(currentFrame, 0, 0, getWidth(), getHeight(), null);
        
        g.dispose();
        bs.show();
	}

}