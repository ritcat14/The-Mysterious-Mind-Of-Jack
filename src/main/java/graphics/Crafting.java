package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import entities.Item;
import events.Event;
import events.EventDispatcher;
import events.EventHandler;
import events.EventListener;
import events.Event.Type;
import events.types.MousePressedEvent;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;

public class Crafting extends GuiPanel implements EventListener {

	private InventButton input1, input2, craftingOutput, trash;
	private GuiButton craft;
	
	private ArrayList<GuiLabel> info1 = new ArrayList<GuiLabel>();
	private ArrayList<GuiLabel> info2 = new ArrayList<GuiLabel>();
	
	private GuiLabel errorLabel;
	private int labelTimer = 5;
	private int time = 0;
	
	private String[] itemData;
	private String[] recipeData;

	public Crafting(Vector pos, String[] itemData, String[] recipeData, Inventory inv) {
		super(pos, new Vector(550, 500), Color.WHITE);
		this.itemData = itemData;
		this.recipeData = recipeData;
		//infoPanel = new GuiPanel(new Vector(pos.x + 505, pos.y), new Vector(300, 410), Color.WHITE);
		craftingOutput = new InventButton(new Vector(415, pos.y + 10), new Vector(160, 160), false);
		input1 = new InventButton(new Vector(pos.x + 15, pos.y + 180), new Vector(70, 70), false);
		input2 = new InventButton(new Vector(pos.x + 95, pos.y + 180), new Vector(70, 70), false);
		final Inventory invent = inv;
		trash = new InventButton(new Vector(440, 450), new Vector(100, 100), "/gui/trash.png", false) {
			@Override
			public boolean mousePressed(MousePressedEvent e) {
				if (super.mousePressed(e) ) {
					invent.clearPickedItem();
					return true;
				}
				return false;
			}
		};
		craft = new GuiButton(new Vector(pos.x + 35, pos.y + 280), new Vector(110, 30), Color.BLACK, "CRAFT", new Font("Times New Java", Font.BOLD, 30)) {
			@Override
			public boolean mousePressed(MousePressedEvent e) {
				if (super.mousePressed(e)) {
					if (input1.getItem() == null || input2.getItem() == null) {
						errorLabel.setText("2 Ingredients Required.");
						errorLabel.setVisible(true);
						labelTimer = 5;
						return false;
					}
					craft();
					return true;
				}
				return false;
			}			
		}.setTextOffset(5, 25);
		errorLabel = new GuiLabel(new Vector(415, StateHandler.HEIGHT - 10), new Vector(), "", Color.RED, new Font("Times New Java", Font.BOLD, 10));
		errorLabel.setVisible(false);
		add(errorLabel);
		add(craft);
		add(input1);
		add(input2);
		add(craftingOutput);
		add(trash);
		add(new GuiLabel(new Vector(pos.x + 45, pos.y + 30), new Vector(80, 20), "CRAFTING", Color.WHITE, new Font("Times New Java", Font.BOLD, 15)));
		add(new GuiLabel(new Vector(630, pos.y + 30), new Vector(50, 20), "INFO", Color.BLACK, new Font("Times New Java", Font.BOLD, 30)));
	}
	
	private void craft() {
		int itemID1 = input1.getItem().getID();
		int itemID2 = input2.getItem().getID();
		int amount1 = input1.getAmount();
		int amount2 = input2.getAmount();
		for (String s : recipeData) {
			String[] parts = s.split("-");
			int ID1 = Integer.parseInt(parts[0].split(":")[0]);
			int ID2 = Integer.parseInt(parts[1].split(":")[0]);
			int finalID = Integer.parseInt(parts[2].split(":")[0]);
			int required1 = Integer.parseInt(parts[0].split(":")[1]);
			int required2 = Integer.parseInt(parts[1].split(":")[1]);
			int finalRequired = Integer.parseInt(parts[2].split(":")[1]);
			
			if ((itemID1 == ID1 && itemID2 == ID2) || (itemID1 == ID2 && itemID2 == ID1)) {
				if ((amount1 == required1 && amount2 == required2) || (amount1 == required2 && amount2 == required1)) {
					// Create new item
					Item finalItem = new Item(new Vector(), finalID);
					if (craftingOutput.getItem() != null) {
						if (craftingOutput.getItem().getID() == finalID) craftingOutput.addItem();
						else {
							errorLabel.setText("Please remove crafted item first.");
							labelTimer = 5;
							errorLabel.setVisible(true);
							return;
						}
					} else {
						craftingOutput.setItem(finalItem, finalRequired, getAmount(finalID));
					}
					input1.removeAll();
					input2.removeAll();
					return;
				}
			}
		}
		errorLabel.setText("No recipe found. Please check you have the exact amount.");
		errorLabel.setVisible(true);
		labelTimer = 5;
	}
	
	public int getAmount(int Id) {
		int MAX_AMOUNT = 0;
		for (String s2 : itemData) {
			String[] parts2 = s2.split(":");
			int ID = Integer.parseInt(parts2[0]);
			if (ID == Id) MAX_AMOUNT = Integer.parseInt(parts2[1]);
		}
		return MAX_AMOUNT;
	}
	
	private void createLabel(String inf, ArrayList<GuiLabel> list, int ID) {
		int y = 150;
		if (ID == 2) y = 400;
		String[] parts = inf.split(":");
		int itemID = Integer.parseInt(parts[0]);
		int index = 3;
		if (itemID > 10 && itemID < 26) index = 4;
		list.add(new GuiLabel(new Vector(580, y), new Vector(), parts[2] + " - " + parts[index], Color.BLACK,new Font("Times New Java", Font.BOLD, 15)));
		if (parts.length > 4) {
			String[] recipes;
			if (itemID > 10 && itemID < 26) recipes = parts[5].split(",");
			else recipes = parts[4].split(",");
			for (int i = 0; i < recipes.length; i++) {
				final int k = i;
				list.add(new GuiLabel(new Vector(580, (y + 20) + (k * 15)), new Vector(), recipes[i], Color.BLACK,new Font("Times New Java", Font.PLAIN, 15)));
			}
		}
	}
	
	@Override
	public void update() {
		setHeight(StateHandler.HEIGHT - pos.y);
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		super.update();
		if (errorLabel.isVisible()) {
			if (time % Tools.getSecs(1) == 0) { // If 1 second has passed
				labelTimer--;
				if (labelTimer == 0) errorLabel.setVisible(false);
			}
		}
		if (input1.getItem() != null) {
			if (info1.isEmpty()) {
				String[] data = Tools.getData(Tools.itemFile);
				for (String s : data) {
					int ID = Integer.parseInt(s.split(":")[0]);
					if (input1.getItem().getID() == ID) createLabel(s, info1, 1);
				}
			}
		} else if (input1.getItem() == null) {
			if (!info1.isEmpty()) info1.clear();
		}
		
		if (input2.getItem() != null) {
			if (info2.isEmpty()) {
				String[] data = Tools.getData(Tools.itemFile);
				for (String s : data) {
					int ID = Integer.parseInt(s.split(":")[0]);
					if (input2.getItem().getID() == ID) createLabel(s, info2, 2);
				}
			}
		} else if (input2.getItem() == null) {
			if (!info2.isEmpty()) info2.clear();
		}
		for (GuiLabel l : info1) l.update();
		for (GuiLabel l : info2) l.update();
	}
	
	public boolean mousePressed(MousePressedEvent e) {
		if (craft.mousePressed(e)) return true;
		if (trash.mousePressed(e)) return true;
		return false;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		for (GuiLabel l : info1) l.render(g);
		for (GuiLabel l : info2) l.render(g);
	}
	
	public InventButton getInput1() {
		return input1;
	}
	
	public InventButton getInput2() {
		return input2;
	}
	
	public InventButton getCraftingOutput() {
		return craftingOutput;
	}

}
