package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import entities.Item;
import events.types.MouseMovedEvent;
import events.types.MousePressedEvent;
import handler.DataHandler;
import handler.Vector;
import states.Game;

public class Inventory extends GuiButton {

	private ArrayList<GuiComponent> buttons;
	private boolean open = false;
	
	private Item pickedItem;
	private int pickedAmount = 0;
	private int pickedX = 0;
	private int pickedY = 0;
	private GuiPanel mainPanel;
	private InventButton input1, input2, craftingOutput;
	private GuiLabel label;
	private GuiButton craft;
	
	private final int ITEM_MAX = 64;
	
	public Inventory(Vector pos) {
		super(pos, new Vector(50, 50), Color.WHITE, "INVENTORY", new Font("Times New Java", Font.BOLD, 25));
		setTextOffset(5, 23);
		mainPanel = new GuiPanel(pos, size, Color.LIGHT_GRAY);
		buttons = new ArrayList<GuiComponent>();
		String[] data = null;
		
		craftingOutput = new InventButton(new Vector(pos.x + 415, pos.y + 50), new Vector(80, 80));
		input1 = new InventButton(new Vector(pos.x + 410, pos.y + 150));
		input2 = new InventButton(new Vector(pos.x + 460, pos.y + 150));
		label = new GuiLabel(new Vector(pos.x + 415, pos.y + 30), new Vector(80, 20), "CRAFTING", Color.WHITE, new Font("Times New Java", Font.BOLD, 15));
		final Vector invPos = pos;
		craft = new GuiButton(new Vector(invPos.x + 425, invPos.y + 220), new Vector(60, 30), Color.BLACK, "CRAFT", new Font("Times New Java", Font.BOLD, 15)) {
			@Override
			public boolean mousePressed(MousePressedEvent e) {
				if (super.mousePressed(e)) {
					if (input1.getItem() == null || input2.getItem() == null) return false;
					craft();
					return true;
				}
				return false;
			}
		}.setTextOffset(5, 20);

		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		
		data = DataHandler.readFile(DataHandler.inventFile);
		for (int i = 0; i < data.length; i++) {
			int ID = Integer.parseInt(data[i].split("-")[0]);
			int amount = Integer.parseInt(data[i].split("-")[1]);
			Item item = new Item(new Vector(), ID);
			items.add(item);
			amounts.add(amount);
		}
		int x = 0;
		int y = 0;
		for (int i = 0; i < items.size(); i++) {
			InventButton b = new InventButton(new Vector((pos.x + (x * 50)) + 10, (pos.y + (y * 50)) + 10));
			b.setItem(items.get(i), amounts.get(i));
			buttons.add(b);
			x++;
			if (x == 8) {
				y++;
				x = 0;
			}
		}
		for (int i = buttons.size(); i < ITEM_MAX; i++) {
			InventButton b = new InventButton(new Vector((pos.x + (x * 50)) + 10, (pos.y + (y * 50)) + 10));
			buttons.add(b);
			x++;
			if (x == 8) {
				y++;
				x = 0;
			}
		}
	}
	
	public void craft() {
		Item item1 = input1.getItem();
		Item item2 = input2.getItem();
		int amount1 = input1.getAmount();
		int amount2 = input2.getAmount();
		// Open recipe
		String[] data = DataHandler.readFile(DataHandler.recipeFile);
		// Search through data, comparing to our recipe
		int item1ID = 0;
		int item2ID = 0;
		int finalItemID = 0;
		int item1Amount = 0;
		int item2Amount= 0;
		int finalItemAmount = 0;
		for (String s : data) {
			String[] itemData = s.split("-");
			item1ID = Integer.parseInt(itemData[0].split(":")[0]);
			item2ID = Integer.parseInt(itemData[1].split(":")[0]);
			finalItemID = Integer.parseInt(itemData[2].split(":")[0]);
			item1Amount = Integer.parseInt(itemData[0].split(":")[1]);
			item2Amount = Integer.parseInt(itemData[1].split(":")[1]);
			finalItemAmount = Integer.parseInt(itemData[2].split(":")[1]);
		}
		Item finalItem;
		if (item1.getID() == item1ID && item2.getID() == item2ID) {
			// Check we have the right amount of ingredients
			if(item1Amount <= amount1 && item2Amount <= amount2) {
				// If we have the right amount, create the final item
				finalItem = new Item(new Vector(), finalItemID);
				// Remove old items
				input1.remove(item1Amount);
				input2.remove(item2Amount);
				craftingOutput.setItem(finalItem, finalItemAmount);
				// alert the user
				
			}
		}
		// If no match, tell user
	}
	
	public void addItem(Item item) {
		for (GuiComponent b : buttons) {
			InventButton button = (InventButton)b;
			if (button.getItem() != null) { // If the button has an item
				if (button.getItem().getID() == item.getID()) { // If the button's item is the same as ours
					button.addItem(); // increase the amount
					return;
				}
			}
		}
		for (GuiComponent b : buttons) {
			InventButton button = (InventButton)b;
			if (button.getItem() == null) { // If the button has an item
				button.setItem(item, 1);
			}
		}
	}
	
	@Override
	public void update() {
		super.update();
		mainPanel.update();
		if (open) {
			setWidth(505);
			setHeight(410);
			mainPanel.setWidth(505);
			mainPanel.setHeight(410);
			for (GuiComponent b : buttons) b.update();
			craftingOutput.update();
			input1.update();
			input2.update();
			label.update();
			craft.update();
		} else {
			setWidth(155);
			setHeight(30);
			mainPanel.setWidth(155);
			mainPanel.setHeight(30);
		}
	}
	
	@Override
	public void render(Graphics g) {
		mainPanel.render(g);
		if (open) {
			for (GuiComponent b : buttons) {
				b.render(g);
				g.drawLine((int)(pos.x + 405), (int)pos.y, (int)(pos.x + 405), (int)(pos.y + 410));
			}
			craftingOutput.render(g);
			input1.render(g);
			input2.render(g);
			label.render(g);
			craft.render(g);
		} else {
			g.setFont(font);
			g.setColor(colour);
			g.drawString(text, (int)(pos.x + textOffset.x), (int)(pos.y + textOffset.y));
		}
		if (pickedItem != null) {
			g.drawImage(pickedItem.getImage(), pickedX, pickedY, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Java", Font.PLAIN, 10));
			g.drawString("" + pickedAmount, (int)(pickedX + 30), (int)(pickedY + 30));
		}
	}
	
	public boolean mousePressed(MousePressedEvent e) {
		int index = e.getButton();
		if (super.mousePressed(e) && !open) {
			open = true;
			Game.paused = true;
			return true;
		} else if (!super.mousePressed(e) && open) {
			open = false;
			Game.paused = false;
			return true;
		} else if (super.mousePressed(e) && open) {
			InventButton button = null;
			if (craft.mousePressed(e)) return true;
			for (GuiComponent b : buttons) if ((button = ((InventButton)b)).mousePressed(e)) break;
			if (input1.mousePressed(e)) button = input1;
			if (input2.mousePressed(e)) button = input2;
			if (craftingOutput.mousePressed(e)) button = craftingOutput;
			if (pickedItem != null) {
				switch (index) {
					case 1: // Left
						if (button.getItem() != null) {
							Item i = button.getItem();
							if (i.getID() == pickedItem.getID()) {
								button.addItems(pickedAmount);
								pickedItem = null;
								pickedAmount = 0;
								return true;
							} else return false;
						} else {
							button.setItem(pickedItem, pickedAmount);
							pickedAmount = 0;
							pickedItem = null;
							return true;
						}
					case 3: // Right
						if (button.getItem() != null) {
							Item i = button.getItem();
							if (i.getID() == pickedItem.getID()) {
								button.removeItem();
								pickedAmount++;
								return true;
							} else return false;
						} else {
							button.setItem(pickedItem, 1);
							pickedAmount--;
							if (pickedAmount == 0) pickedItem = null;
							return true;
						}
				}
			} else {
				switch(index) {
					case 1:
						if (button.getItem() != null) {
							pickedItem = button.getItem();
							pickedAmount = button.getAmount();
							button.removeAll();
							pickedX = e.getX();
							pickedY = e.getY();
							return true;
						} else return false;
					case 3:
						if (button.getItem() != null) {
							pickedItem = button.getItem();
							pickedAmount  = 1;
							button.removeItem();
							pickedX = e.getX();
							pickedY = e.getY();
							return true;
						} else return false;						
				}
			}
		}
		return false;
	}
	
	public boolean mouseMoved(MouseMovedEvent e) {
		if (super.mouseMoved(e)) {
			if (pickedItem != null) {
				pickedX = e.getX();
				pickedY = e.getY();
				return true;
			}
		}
		return false;
	}
	
	public Object[] getData() {
		Object[] data;
		ArrayList<InventButton> buttonsWithItems = new ArrayList<InventButton>();
		for (GuiComponent c : buttons) if (((InventButton)c).getItem() != null) buttonsWithItems.add((InventButton)c);
		data = new Object[buttonsWithItems.size()];
		
		for (int i = 0; i < buttonsWithItems.size(); i++) {
			data[i] = buttonsWithItems.get(i).getItem().getID() + "-" + buttonsWithItems.get(i).getAmount();
		}
		return data;
	}

	public void open() {
		open = !open;
		Game.paused = !Game.paused;
	}

}
