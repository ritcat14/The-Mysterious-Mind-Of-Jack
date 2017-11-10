package events.types;

import events.Event;

public class MouseButtonEvent extends Event { 

	protected int button = -1;
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
	
}
