package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import core.Main;
import events.Event;
import events.EventDispatcher;
import events.Event.Type;
import events.types.KeyPressedEvent;
import events.types.MousePressedEvent;
import graphics.GameCanvas;
import graphics.GuiButton;
import graphics.GuiLabel;
import graphics.GuiPanel;
import handler.StateHandler;
import handler.StateHandler.States;
import handler.Vector;

/*
Pause class to handle pause state
 */

public class Pause extends State {
	
	private GuiPanel mainPanel;
    
    public Pause() {
    	mainPanel = new GuiPanel(new Vector(100, 200), new Vector(400, StateHandler.HEIGHT - 800));
    	//mainPanel.add(new GuiRadioButton(new Vector(100, 100), new Vector(50, 50), "Check me!", GuiLabel.standard));
    	mainPanel.add(new GuiLabel(new Vector(100, 280), new Vector(100, 50), "PAUSE", Color.WHITE, new Font("Times New Java", Font.BOLD, 100)));
    	
    	mainPanel.add(new GuiButton(new Vector(100, 300), new Vector(150, 25), null, "MAIN MENU", new Font("Times New Java", Font.BOLD, 25)) {
    		@Override
    		public boolean mousePressed(MousePressedEvent e) {
    			if (super.mousePressed(e)) {
    				StateHandler.unpause();
    				StateHandler.changeState(States.START);
    				return true;
    			}
    			return false;
    		};
    	}.setTextOffset(2, 20));
    	
    	mainPanel.add(new GuiButton(new Vector(100, 350), new Vector(150, 25), null, "RETURN", new Font("Times New Java", Font.BOLD, 25)) {
    		@Override
    		public boolean mousePressed(MousePressedEvent e) {
    			if (super.mousePressed(e)) {
    				StateHandler.unpause();
    				return true;
    			}
    			return false;
    		};
    	}.setTextOffset(2, 20));
    	mainPanel.add(new GuiButton(new Vector(100, 400), new Vector(150, 25), null, "OPTIONS", new Font("Times New Java", Font.BOLD, 25)) {
    		
    		@Override
    		public boolean mousePressed(MousePressedEvent e) {
    			if (super.mousePressed(e)) {
    				StateHandler.loadOptions();
    				return true;
    			}
    			return false;
    		};
    	}.setTextOffset(2, 20));
    	mainPanel.add(new GuiButton(new Vector(100, 450), new Vector(150, 25), null, "EXIT", new Font("Times New Java", Font.BOLD, 25)) {
    		@Override
    		public boolean mousePressed(MousePressedEvent e) {
    			if (super.mousePressed(e)) {
    				Main.frame.dispatchEvent(new WindowEvent(Main.frame, WindowEvent.WINDOW_CLOSING));
    				return true;
    			}
    			return false;
    		};
    	}.setTextOffset(2, 20));
    }

    @Override
    public void update() {
        mainPanel.update();
    }

    @Override
    public void render(Graphics g) {
    	g.drawImage(GameCanvas.getBlurredFrame(), 0, 0, null);
        mainPanel.render(g);
    }

    @Override
    public void onEvent(Event event) {
    	EventDispatcher dispatcher = new EventDispatcher(event);
    	dispatcher.dispatch(Type.KEY_PRESSED, event1 -> keyPressed((KeyPressedEvent) event1));
    	mainPanel.onEvent(event);
    }
    
    public boolean keyPressed(KeyPressedEvent e) {
        switch(e.getKey()) {
            case KeyEvent.VK_ESCAPE:
            	Game.paused = false;
            	StateHandler.unpause();
            	return true;
        }
        return false;
    }
    
}
