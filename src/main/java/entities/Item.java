package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import handler.Tools;
import handler.Vector;

public class Item extends Entity {
	
	private BufferedImage buttonImage;
	private final int ID;
	
	public Item(Vector pos, int ID) {
		super(pos, new Vector(40, 40), Tools.getImage("/items/" + ID + ".png"));
		this.ID = ID;
		buttonImage = image;
	}

	public void action() {
		
	}

	@Override
	public void update() {
		
	}
	
	public int getID() {
		return ID;
	}
	
	@Override
	public BufferedImage getImage() {
		return buttonImage;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
	}

}
