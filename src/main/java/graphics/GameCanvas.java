package graphics;

import handler.StateHandler;
import handler.Tools;
import states.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import events.Keyboard;
import events.Mouse;

public class GameCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static BufferedImage currentFrame;
	private static Graphics g;
	private boolean isBlurred = false;
	private static BufferedImage blurred;
	
	public GameCanvas(StateHandler sh, int width, int height) {
        currentFrame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
		setPreferredSize(new Dimension(width, height));
		
		Mouse m = new Mouse(sh);
		
		addMouseListener(m);
		addMouseMotionListener(m);
		addKeyListener(new Keyboard(sh));		
		
		requestFocus();
	}
	
	public static BufferedImage getBlurredFrame() {
		return blurred;
	}
	
	public static Graphics getG() {
		return g;
	}
	
	public void draw() {
		requestFocus();
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = currentFrame.getGraphics();
        GameCanvas.g = g;
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        if (Game.paused) {
        	if (!isBlurred) {
	        	if (StateHandler.pausedGame != null) {
		        	StateHandler.pausedGame.render(g);
		        	blurred = Tools.blur(currentFrame);
		        	isBlurred = true;
	        	}
        	}
        } else isBlurred = false;
        
        StateHandler.render(g);
        
        bs.getDrawGraphics().drawImage(currentFrame, 0, 0, StateHandler.WIDTH, StateHandler.HEIGHT, null);
        
        g.dispose();
        bs.show();
	}

}