package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.Item;
import handler.Vector;

public class InventButton extends GuiButton {
	
	private Item item;
	private int amount;

	public InventButton(Vector pos) {
		super(pos, new Vector(40, 40), Color.DARK_GRAY, "");
	}

	public InventButton(Vector pos, Vector size) {
		super(pos, size, Color.DARK_GRAY, "");
	}
	
	public void setItem(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void addItem() {
		if (item != null) amount++;
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
	
	@Override
	public void render(Graphics g) {
		if (item != null) this.image = item.getImage();
		else {
			this.colour = Color.DARK_GRAY;
			image = null;
		}
		super.render(g);
		
		if (amount > 0) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Java", Font.PLAIN, 10));
			g.drawString("" + amount, (int)(pos.x + 30), (int)(pos.y + 30));
		}
		if (pos.y == 110) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Java", Font.BOLD, 20));
			g.drawString("" + (int)((pos.x + 40)/50), (int)(pos.x + 10), (int)(pos.y + 10));
		}
	}

}
