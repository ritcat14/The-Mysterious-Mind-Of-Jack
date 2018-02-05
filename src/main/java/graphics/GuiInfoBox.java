package graphics;

import java.awt.Color;

import handler.Vector;

public class GuiInfoBox extends GuiPanel {

	public GuiInfoBox(Vector pos, Vector size, Color col, String text) {
		super(pos, size, col);
		setText(text);
	}
	
	public void setText(String text) {
		components.clear();
		String[] lines = text.split("`");
		for (int i = 0; i < lines.length; i++) add(new GuiLabel(new Vector(pos.x, pos.y + (GuiLabel.standard.getSize())), new Vector(), lines[i], Color.BLACK));
	}

	@Override
	public void update() {
		super.update();
	}
	
}
