package events.types;

import java.awt.Point;

import events.Event;
/*
A child class to detect mouse button
 */

public class MouseButtonEvent extends Event { 

	protected int button = -1;  // 1 - left, 2 - middle, 3 - right
	protected int x = 0, y = 0;
	
	protected MouseButtonEvent(int button, int x, int y, Type type) {
		super(type);
		this.button = button;
		this.x = x;
		this.y = y;
	}

	public int getButton() {
		return button;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Point getPoint() {
		return new Point(x, y);
	}
	
}
