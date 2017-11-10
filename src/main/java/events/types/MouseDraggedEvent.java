package main.events.types;

import main.events.Event;

public class MouseDraggedEvent extends Event {

	private int x = 0, y = 0;
	private int button = 0;
	
	public MouseDraggedEvent(int x, int y, int button) {
		super(Event.Type.MOUSE_DRAGGED);
		this.x = x;
		this.y = y;
		this.button = button;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getButton() {
		return button;
	}
	
}
