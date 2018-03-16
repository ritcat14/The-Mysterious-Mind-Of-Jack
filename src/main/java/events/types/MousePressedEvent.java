package events.types;

import events.Event;

/*
A child class to detect when mouse is pressed
 */

public class MousePressedEvent extends MouseButtonEvent {

	public MousePressedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_PRESSED);
	}

}
