package graphics;

import java.awt.image.BufferedImage;

import handler.Tools;

public class Animation {
	
	private BufferedImage[] images;
	private int frame = 0;
	private int time = 0;
	private int timeSecs = 0;
	private int timeGap = 0;
	private boolean animating = false;

	public Animation(BufferedImage[] images, int timeGap) {
		this.images = images;
		this.timeGap = timeGap;
		
	}
	
	public void update() {
		if (animating) {
			time++;
			if (time >= Integer.MAX_VALUE) time = 0;
			if (time % Tools.getSecs(1) == 0) timeSecs++;
			if (timeSecs == timeGap) frame++;
			if (frame == images.length) frame = 0;
		}
	}
	
	public void setImages(BufferedImage[] images) {
		boolean isAnimating = animating; // Take a copy of whether or not we are animating
		stop(); // Stop animating (reset all variables)
		this.images = images; // Change the image array
		if (isAnimating) animate(); // If we were previously animating, continue animating now
	}
	
	public BufferedImage getCurrentFrame() {
		return images[frame];
	}
	
	public boolean isAnimating() {
		return animating;
	}
	
	public void animate() {
		animating = true;
	}
	
	public void stop() {
		animating = false;
		frame = 0;
		time = 0;
		timeSecs = 0;
	}

}
