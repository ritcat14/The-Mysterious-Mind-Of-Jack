package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Item;
import handler.Vector;

public class InventButton extends GuiButton {
	
	private Item item;
	private int amount;
	private boolean tag = false;
	private boolean imageSet = false;
	private BufferedImage setImage;
	
	private int MAX_AMOUNT;

	public InventButton(Vector pos, Vector size, String file, boolean tag) {
		super(pos, size, file, "");
		imageSet = true;
		setImage = this.image;
		this.tag = tag;
	}

	public InventButton(Vector pos, String file, boolean tag) {
		super(pos, new Vector(40, 40), file, "");
		imageSet = true;
		setImage = this.image;
		this.tag = tag;
	}

	public InventButton(Vector pos, Color col, boolean tag) {
		super(pos, new Vector(40, 40), col, "");
		this.tag = tag;
	}

	public InventButton(Vector pos, boolean tag) {
		super(pos, new Vector(40, 40), Color.DARK_GRAY, "");
		this.tag = tag;
	}

	public InventButton(Vector pos, Vector size, boolean tag) {
		super(pos, size, Color.DARK_GRAY, "");
		this.tag = tag;
	}
	
	public void setItem(Item item, int amount, int MAX_AMOUNT) {
		this.item = item;
		this.amount = amount;
		this.MAX_AMOUNT = MAX_AMOUNT;
	}
	
	public int getMax() {
		return MAX_AMOUNT;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void addItem() {
		amount++;
	}
	
	public void addItems(int amount) {
		this.amount += amount;
	}
	
	public void removeItem() {
		if (item != null) amount--;
	}
	
	public void removeAll() {
		amount = 0;
	}
	
	public void remove(int amount) {
		this.amount -= amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	@Override
	public void update() {
		super.update();
		if (amount == 0) {
			item = null;
		}
	}
	
	public void renderTag(Graphics g) {
		if (pos.y == 110) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Java", Font.BOLD, 20));
			g.drawString("" + (int)((pos.x + 40)/50), (int)(pos.x + 10), (int)(pos.y + 10));
		}
	}
	
	@Override
	public void render(Graphics g) {
		if (item != null) this.image = item.getImage();
		else if (item == null && !imageSet) image = null;
		else if (item == null && imageSet) this.image = setImage;
		super.render(g);
		if (amount > 0) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Java", Font.PLAIN, 10));
			g.drawString("" + amount, (int)(pos.x), (int)(pos.y + 8));
		}
		if (tag) renderTag(g);
	}

}
