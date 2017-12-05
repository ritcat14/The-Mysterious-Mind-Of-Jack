package events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import events.types.MouseDraggedEvent;
import events.types.MouseMovedEvent;
import events.types.MousePressedEvent;
import events.types.MouseReleasedEvent;

public class Mouse implements MouseListener, MouseMotionListener {
	private static int mouseX = -1, mouseY = -1, mouseB = -1;
	public static boolean left = false, right = false;

	public EventListener eventListener = null;

	public Mouse(EventListener listener) {
		this.eventListener = listener;
	}

	public static int getX() { return mouseX; }

	public static int getY() { return mouseY; }

	public static int getButton() { return mouseB; }

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();

		MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
		try {
			eventListener.onEvent(event);
		} catch (Exception ex) {}
	}

	public void mouseReleased(MouseEvent e) {
		mouseB = MouseEvent.NOBUTTON;

		MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
		eventListener.onEvent(event);
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (SwingUtilities.isLeftMouseButton(e)) {
			mouseB = 1;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			mouseB = 3;
		}

		MouseDraggedEvent event = new MouseDraggedEvent(e.getX(), e.getY(), mouseB);
		try {
			eventListener.onEvent(event);
		} catch (Exception ex) {}
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY());
		try {
			eventListener.onEvent(event);
		} catch (Exception ex) {}
	}

}