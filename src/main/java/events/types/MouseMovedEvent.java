package main.events.types;

import main.events.Event;

public class MouseMovedEvent extends Event {

	private int x = 0, y = 0;
	
	public MouseMovedEvent(int x, int y) {
		super(Event.Type.MOUSE_MOVED);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
