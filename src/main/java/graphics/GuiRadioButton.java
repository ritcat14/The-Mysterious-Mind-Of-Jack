package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import events.Event;
import events.Event.Type;
import events.EventDispatcher;
import events.EventHandler;
import events.EventListener;
import events.types.MousePressedEvent;
import handler.Tools;
import handler.Vector;

public class GuiRadioButton extends GuiComponent implements EventListener {
	
	private boolean checked = false;
	private GuiButton button;
	private GuiLabel label;
	private BufferedImage buttonChecked;
	private BufferedImage buttonUnchecked;

	public GuiRadioButton(Vector pos, Vector size, String text, Font font, Color c) {
		super(pos, size);
		buttonUnchecked = Tools.getImage("/gui/buttonUnchecked.png");
		buttonChecked = Tools.getImage("/gui/buttonChecked.png");
		button = new GuiButton(new Vector(pos.x, pos.y - 15), buttonUnchecked) {
			@Override
			public void update() {
				super.update();
			}
		}.setTextOffset(5, 15);
		label = new GuiLabel(pos.add(new Vector(25, 0)), new Vector(), text, c, font);
		add(label);
		add(button);
	}
	
	public boolean mousePressed(MousePressedEvent e) {
		if (button.getBounds().contains(e.getPoint())) {
			checked = !checked;
			return true;
		}
		return false;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public void update() {
		super.update();
		if (checked) button.setImage(buttonChecked);
		else button.setImage(buttonUnchecked);
		button.setX(pos.x - 30);
		button.setY(pos.y - 15);
	}

	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatch = new EventDispatcher(event);
		dispatch.dispatch(Type.MOUSE_PRESSED, new EventHandler() {
			@Override
			public boolean onEvent(Event event) {
				return mousePressed((MousePressedEvent) event);
			}
		});
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
	}

}
