package graphics;

import handler.SoundHandler;
import handler.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javafx.scene.media.MediaPlayer;
import events.Event;
import events.EventDispatcher;
import events.EventHandler;
import events.EventListener;
import events.types.MouseMovedEvent;
import events.types.MousePressedEvent;
import events.types.MouseReleasedEvent;

public class GuiButton extends GuiPanel implements EventListener {

	protected String text = "";
	protected Color textColour = Color.BLACK;
	protected Font font = GuiLabel.font;
	protected MediaPlayer soundPlayer;
	protected boolean playedSound = false;
	protected Vector textOffset = new Vector();

	public GuiButton(Vector pos, Vector size, Color c, String text, Font font, Color... textCol) {
		super(pos, size, c);
		this.text = text;
		this.textColour = textCol[0];
		this.font = font;
	}
	
	public GuiButton setTextOffset(double x, double y) {
	    this.textOffset = new Vector(x, y);
	    return this;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		if (image != null) g.drawImage(image, (int)pos.x, (int)pos.y, (int)size.x, (int)size.y, null);
		g.setColor(textColour);
		g.setFont(font);
		g.drawString(text, (int)(pos.x + textOffset.x), (int)(pos.y + textOffset.y));
	}

	public boolean mousePressed(MousePressedEvent e) {
		return (getBounds().contains(new Point(e.getX(), e.getY())));
	}

	public boolean mouseReleased(MouseReleasedEvent e) {
		return (getBounds().contains(new Point(e.getX(), e.getY())));
	}

	public boolean mouseMoved(MouseMovedEvent e) {
		if (getBounds().contains(new Point(e.getX(), e.getY()))) {
		    if (!playedSound) {
		        soundPlayer = SoundHandler.play("blip");
		        playedSound = true;
	        }
		    return true;
		}
		playedSound = false;
		return false;
	}

	public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, new EventHandler() {
            public boolean onEvent(Event event) {
                return mousePressed((MousePressedEvent)event);
            }
        });
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, new EventHandler() {
            public boolean onEvent(Event event) {
                return mouseReleased((MouseReleasedEvent)event);
            }
        });
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, new EventHandler() {
            public boolean onEvent(Event event) {
                return mouseMoved((MouseMovedEvent)event);
            }
        });
	}
}
