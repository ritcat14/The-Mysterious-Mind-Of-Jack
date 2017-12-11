package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.sun.javafx.geom.Rectangle;

import entities.Item;
import events.types.MouseDraggedEvent;
import events.types.MouseMovedEvent;
import events.types.MousePressedEvent;
import events.types.MouseReleasedEvent;
import handler.DataHandler;
import handler.StateHandler;
import handler.Tools;
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
	private int time = 0;
	
	private boolean collecting = false;
	private InventButton pickedButton;
	
	private Crafting crafting;
	private String[] itemData;
	private String[] recipeData;
	
	private final int ITEM_MAX = 88;
	
	public Inventory(Vector pos) {
		super(pos, new Vector(20, 30), Color.WHITE, "I", new Font("Times New Java", Font.BOLD, 25));
		setTextOffset(5, 23);
		mainPanel = new GuiPanel(pos, size, Color.LIGHT_GRAY);
		buttons = new ArrayList<GuiComponent>();
		String[] data = null;
		itemData = Tools.getData(Tools.itemFile);
		recipeData = Tools.getData(Tools.recipeFile);
		
		crafting = new Crafting(new Vector(pos.x + 405, pos.y), itemData, recipeData, this);

		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		ArrayList<Integer> buttonIndices = new ArrayList<Integer>();
		
		data = DataHandler.readFile(DataHandler.inventFile);
		for (int i = 0; i < data.length; i++) {
			int ID = Integer.parseInt(data[i].split("-")[0]);
			int amount = Integer.parseInt(data[i].split("-")[1]);
			Item item = new Item(new Vector(), ID);
			items.add(item);
			amounts.add(amount);
			buttonIndices.add(Integer.parseInt(data[i].split("-")[2]));
		}
		int x = 0;
		int y = 0;
		int j = 0;
		for (int i = 0; i < ITEM_MAX; i++) {
			InventButton b;
			if (i >= 0 && i <= 8) b = new InventButton(new Vector((pos.x + (x * 50)) + 10, (pos.y + (y * 50)) + 10), new Color(255, 68, 71), true);
			else if (i >= ITEM_MAX - 3 && i <= ITEM_MAX) b = new InventButton(new Vector((pos.x + (x * 50)) + 10, (pos.y + (y * 50)) + 10), "/gui/armour/" + (j++) + ".png", false);
			else b = new InventButton(new Vector((pos.x + (x * 50)) + 10, (pos.y + (y * 50)) + 10), false);
			buttons.add(b);
			x++;
			if (x == 8) {
				y++;
				x = 0;
			}
		}

		for (int i = 0; i < items.size(); i++) {
			InventButton b = (InventButton) buttons.get(buttonIndices.get(i));
			if (b.getItem() == null) b.setItem(items.get(i), amounts.get(i), crafting.getAmount(items.get(i).getID()));
		}
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
				button.setItem(item, 1, crafting.getAmount(item.getID()));
			}
		}
	}
	
	@Override
	public void update() {
		super.update();
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		mainPanel.update();
		if (open) {
			if (pickedAmount == 0) pickedItem = null;
			if (collecting && pickedButton.getAmount() != 0) {
				pickedButton.removeItem();
				pickedAmount++;
			}
			for (GuiComponent b : buttons) b.update();
			crafting.update();
		}
	}
	
	@Override
	public void render(Graphics g) {
		mainPanel.render(g);
		if (open) {
			for (GuiComponent b : buttons) b.render(g);
			crafting.render(g);
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
		int buttonIndex = 0;
		if (super.mousePressed(e) && !open) {
			open();
			return true;
		} else if (!super.mousePressed(e) && open) {
			close();
			return true;
		} else if (super.mousePressed(e) && open) {
			if (crafting.mousePressed(e)) return true;
			InventButton button = null;
			for (GuiComponent b : buttons) {
				if (((InventButton)b).mousePressed(e)) {
					button = (InventButton)b;
					buttonIndex = buttons.indexOf(button);
					break;
				}
			}
			InventButton input1 = crafting.getInput1();
			InventButton input2 = crafting.getInput2();
			InventButton craftingOutput = crafting.getCraftingOutput();
			if (input1.mousePressed(e)) button = input1;
			if (input2.mousePressed(e)) button = input2;
			if (craftingOutput.mousePressed(e)) button = craftingOutput;
			if (pickedItem != null) {
				switch (index) {
					case 1: // Left
						if (button.getItem() != null) {
							Item i = button.getItem();
							if (i.getID() == pickedItem.getID()) {
								int max = button.getMax();
								int amount = pickedAmount;
								if (button.getAmount() + pickedAmount > max) {
									amount = max - button.getAmount();
									button.addItems(amount);
									pickedAmount -= amount;
								} else {
									button.addItems(pickedAmount);
									pickedItem = null;
									pickedAmount = 0;
								}
								return true;
							} else break;
						} else {
							int max = crafting.getAmount(pickedItem.getID());
							int amount = pickedAmount;
							if (pickedAmount > max) {
								amount = max;
								if (buttonIndex >= ITEM_MAX - 3) {
									addItemToArmour(buttonIndex, amount, button);
								} else {
									button.setItem(pickedItem, amount, crafting.getAmount(pickedItem.getID()));
									pickedAmount -= amount;
								}
							} else {
								if (buttonIndex >= ITEM_MAX - 3) {
									addItemToArmour(buttonIndex, pickedAmount, button);
								} else {
									button.setItem(pickedItem, pickedAmount, crafting.getAmount(pickedItem.getID()));
									pickedAmount = 0;
								}
							}
							return true;
						}
					case 3: // Right
						if (button.getItem() != null) {
							Item i = button.getItem();
							if (i.getID() == pickedItem.getID()) {
								button.removeItem();
								pickedAmount++;
								pickedButton = button;
							} else break;
						} else {
							int max = crafting.getAmount(pickedItem.getID());
							if (button.getAmount() + 1 < max) {
								if (buttonIndex >= ITEM_MAX - 3) {
									addItemToArmour(buttonIndex, pickedAmount, button);
								} else {
									button.setItem(pickedItem, 1, crafting.getAmount(pickedItem.getID()));
									pickedAmount--;
								}
								return true;
							}
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
						} else break;
					case 3:
						if (button.getItem() != null) {
							pickedItem = button.getItem();
							pickedAmount  = 1;
							button.removeItem();
							pickedX = e.getX();
							pickedY = e.getY();
							pickedButton = button;
							return true;
						} else break;						
				}
			}
		}
		return false;
	}
	
	private void addItemToArmour(int buttonIndex, int amount, InventButton button) {
		if (buttonIndex == ITEM_MAX - 3) {
			switch (pickedItem.getID()) {
				case 11: case 14: case 17: case 20: case 23:
					button.setItem(pickedItem, amount, crafting.getAmount(pickedItem.getID()));
					pickedAmount -= amount;
					return;
				default:
					return;
			}
		}
		else if (buttonIndex == ITEM_MAX - 2) {
			switch (pickedItem.getID()) {
			case 12: case 15: case 18: case 21: case 24:
				button.setItem(pickedItem, amount, crafting.getAmount(pickedItem.getID()));
				pickedAmount -= amount;
				return;
			default:
				return;
			}
		} else if (buttonIndex == ITEM_MAX - 1) {
			switch (pickedItem.getID()) {
			case 13: case 16: case 19: case 22: case 25:
				button.setItem(pickedItem, amount, crafting.getAmount(pickedItem.getID()));
				pickedAmount -= amount;
				return;
			default:
				return;
			}
		}
	}
	
	public int getDefense() {
		int defense = 10;
		InventButton head = (InventButton) buttons.get(ITEM_MAX - 3);
		InventButton chest = (InventButton) buttons.get(ITEM_MAX - 2);
		InventButton legs = (InventButton) buttons.get(ITEM_MAX - 1);
		if (head.getItem() != null) {
			int ID = head.getItem().getID();
			for (String s2 : itemData) {
				String[] parts2 = s2.split(":");
				int Id = Integer.parseInt(parts2[0]);
				if (ID == Id) defense += Integer.parseInt(parts2[3]);
			}
		}
		if (chest.getItem() != null) {
			int ID = chest.getItem().getID();
			for (String s2 : itemData) {
				String[] parts2 = s2.split(":");
				int Id = Integer.parseInt(parts2[0]);
				if (ID == Id) defense += Integer.parseInt(parts2[3]);
			}
		}
		if (legs.getItem() != null) {
			int ID = legs.getItem().getID();
			for (String s2 : itemData) {
				String[] parts2 = s2.split(":");
				int Id = Integer.parseInt(parts2[0]);
				if (ID == Id) defense += Integer.parseInt(parts2[3]);
			}
		}
		return defense;
	}
	
	public boolean mouseReleased(MouseReleasedEvent e) {
		switch (e.getButton()) {
			case 3:
				if (super.mouseReleased(e)) {
					if (collecting) collecting = false;
					return true;
				}
				break;
		}
		return false;
	}
	
	public boolean mouseMoved(MouseMovedEvent e) {
		if (pickedItem != null) {
			pickedX = e.getX();
			pickedY = e.getY();
			return true;
		}
		if (!open && new Rectangle((int)pos.x, (int)pos.y, 155, 30).contains(e.getX(), e.getY())) {
			setWidth(160);
			setText("INVENTORY");
			return true;
		} else if (!open) {
			setWidth(20);
			setText("I");
			return false;
		}
		return false;
	}
	
	public boolean mouseDragged(MouseDraggedEvent e) {
		if (super.mouseDragged(e) && open) {
			int index = e.getButton();
			InventButton button = null;
			for (GuiComponent b : buttons) if ((button = ((InventButton)b)).mouseDragged(e)) break;
			InventButton input1 = crafting.getInput1();
			InventButton input2 = crafting.getInput2();
			InventButton craftingOutput = crafting.getCraftingOutput();
			if (input1.mouseDragged(e)) button = input1;
			if (input2.mouseDragged(e)) button = input2;
			if (craftingOutput.mouseDragged(e)) button = craftingOutput;
			if (pickedItem != null) {
				switch (index) {
					case 3: // Right
						if (button.getItem() != null) {
							if (!collecting) {
								collecting = true;
								pickedButton = button;
								pickedX = e.getX();
								pickedY = e.getY();
								return true;
							}
						}
						break;
				}
			} else {
				switch(index) {
					case 3:
						if (button.getItem() != null) {
							collecting = true;
							pickedButton = button;
							return true;
						}
						break;
				}
			}
		}
		return false;
	}
	
	public Object[] getData() {
		Object[] data;
		ArrayList<InventButton> buttonsWithItems = new ArrayList<InventButton>();
		ArrayList<Integer> buttonIndices = new ArrayList<Integer>();
		for (GuiComponent c : buttons) {
			if (((InventButton)c).getItem() != null) {
				buttonsWithItems.add((InventButton)c);
				buttonIndices.add(buttons.indexOf(c));
			}
		}
		data = new Object[buttonsWithItems.size()];
		
		for (int i = 0; i < buttonsWithItems.size(); i++) {
			data[i] = buttonsWithItems.get(i).getItem().getID() + "-" + buttonsWithItems.get(i).getAmount() + "-" + buttonIndices.get(i);
		}
		return data;
	}

	public void open() {
		open = true;
		Game.paused = true;
		setHeight(StateHandler.HEIGHT - pos.y);
		setWidth(415 + crafting.getWidth());
		mainPanel.setHeight(StateHandler.HEIGHT - pos.y);
		mainPanel.setWidth(415 + crafting.getWidth());
	}
	
	public void close() {
		setText("I");
		setHeight(30);
		setWidth(20);
		mainPanel.setHeight(30);
		mainPanel.setWidth(20);
		open = false;
		Game.paused = false;
	}

	public void clearPickedItem() {
		pickedAmount = 0;
		pickedItem = null;
	}
	
	public Crafting getCrafting() { return crafting; }
	
	public boolean isOpen() { return open; }
	
}
