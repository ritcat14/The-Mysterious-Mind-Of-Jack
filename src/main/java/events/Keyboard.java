package events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;

/*
A class acts as listener for keyboard
 */

public class Keyboard implements KeyListener {
  
   public EventListener eventListener = null;
  
   public Keyboard(EventListener listener) { this.eventListener = listener; }

    @Override
    public void keyPressed(KeyEvent e) {
        KeyPressedEvent event = new KeyPressedEvent(e.getKeyCode(), Event.Type.KEY_PRESSED);
        eventListener.onEvent(event);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyReleasedEvent event = new KeyReleasedEvent(e.getKeyCode(), Event.Type.KEY_RELEASED);
        eventListener.onEvent(event);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}