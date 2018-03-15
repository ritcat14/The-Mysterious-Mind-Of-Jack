package graphics;

import handler.SoundHandler;
import handler.Tools;
import handler.Vector;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javafx.scene.media.MediaPlayer;
import events.Event;
import events.EventDispatcher;
import events.EventHandler;
import events.EventListener;
import events.types.*;

public class GuiButton extends GuiPanel implements EventListener {

	protected String text = "";
	protected Color textColour = Color.BLACK;
	protected Font font = GuiLabel.standard;
	protected MediaPlayer soundPlayer;
	protected boolean playedSound = false;
	protected Vector textOffset = new Vector();

	public GuiButton(Vector pos, Vector size, Color c, String text, Font font) {
		super(pos, size, c);
		this.text = text;
		this.textColour = Color.WHITE;
		this.font = font;
	}

	public GuiButton(Vector pos, BufferedImage image) {
		super(pos, image);
	}

	public GuiButton(Vector pos, Vector size, String file, String text) {
		super(pos, size, Tools.getImage(file));
		this.text = text;
	}

	public GuiButton(Vector pos, Vector size, Color c, String text) {
		super(pos, size, c);
		this.text = text;
	}
	
	public GuiButton setTextOffset(double x, double y) {
	    this.textOffset = new Vector(x, y);
	    return this;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void render(Graphics g) {
    	if (!visible) return;
		super.render(g);
		if (image != null) g.drawImage(image, (int)pos.x, (int)pos.y, (int)size.x, (int)size.y, null);
		g.setColor(textColour);
		g.setFont(font);
		g.drawString(text, (int)(pos.x + textOffset.x), (int)(pos.y + textOffset.y));
	}

	public boolean mousePressed(MousePressedEvent e) {
    	if (!visible) return false;
		return (getBounds().contains(new Point(e.getX(), e.getY())));
	}

	public boolean mouseReleased(MouseReleasedEvent e) {
    	if (!visible) return false;
		return (getBounds().contains(new Point(e.getX(), e.getY())));
	}

	public boolean mouseMoved(MouseMovedEvent e) {
    	if (!visible) return false;
		if (getBounds().contains(new Point(e.getX(), e.getY()))) {
			textColour = new Color(200, 200, 200);
		    if (!playedSound) {
		        soundPlayer = SoundHandler.play("blip");
		        playedSound = true;
	        }
		    return true;
		}
		textColour = Color.WHITE;
		playedSound = false;
		return false;
	}

	public boolean mouseDragged(MouseDraggedEvent e) {
    	if (!visible) return false;
		return (getBounds().contains(new Point(e.getX(), e.getY())));
	}

	public void onEvent(Event event) {
    	if (!visible) return;
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, event13 -> mousePressed((MousePressedEvent) event13));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, event12 -> mouseReleased((MouseReleasedEvent) event12));
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, event1 -> mouseMoved((MouseMovedEvent) event1));
	}
}
