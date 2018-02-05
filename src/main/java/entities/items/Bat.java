package entities.items;

import java.awt.image.BufferedImage;

import handler.Vector;

public class Bat extends Weapon {

	public Bat(Vector pos, Vector size, String file) {
		super(pos, size, file);
	}

	public Bat(Vector pos, Vector size, BufferedImage image) {
		super(pos, size, image);
	}

	public Bat(Vector pos, BufferedImage image) {
		super(pos, image);
	}

	public Bat(Vector pos, String file) {
		super(pos, file);
	}

	@Override
	public void update() {

	}

}
