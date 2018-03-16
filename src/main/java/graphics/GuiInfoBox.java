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
        for (String line : lines)
            add(new GuiLabel(new Vector(pos.getX(), pos.getY() + (GuiLabel.standard.getSize())), new Vector(), line, Color.BLACK));
	}

	@Override
	public void update() {
		super.update();
	}
	
}
