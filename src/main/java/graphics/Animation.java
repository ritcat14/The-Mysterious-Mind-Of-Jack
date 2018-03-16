package graphics;

import java.awt.image.BufferedImage;
/*
A class of Animation to handle animation
 */

public class Animation {
	
	private BufferedImage[] images;
	private int frame = 0;
	private int time = 0;
	private double timeGap = 0;
	private boolean animating = false;

	public Animation(BufferedImage[] images, double timeGap) {
		this.images = images;
		this.timeGap = timeGap;
	}
	
	public void update() {
		if (animating) {
			time++;
			if (time >= Integer.MAX_VALUE - 5) time = 0;
			if (time % timeGap == 0) frame++;
			if (frame == images.length) frame = 0;
		}
	}
	
	public void setImages(BufferedImage[] images) {
		boolean isAnimating = animating; // Take a copy of whether or not we are animating
		stop(); // Stop animating (reset all variables)
		this.images = images; // Change the image array
		if (isAnimating) start(); // If we were previously animating, continue animating now
	}
	
	public void setTimeGap(double timeGap) {
		this.timeGap = timeGap;
	}
	
	public BufferedImage getCurrentFrame() {
		return images[frame];
	}
	
	public boolean isAnimating() {
		return animating;
	}
	
	public void start() {
		animating = true;
	}
	
	public void stop() {
		animating = false;
		frame = 0;
		time = 0;
	}

}
