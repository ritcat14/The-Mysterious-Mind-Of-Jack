package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import events.Event;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiPanel;
import handler.SoundHandler;
import handler.StateHandler;
import handler.StateHandler.States;
import handler.Vector;

public class Options extends State {
    private GuiPanel mainPanel;
    
    public Options() {
        mainPanel = new GuiPanel(new Vector(400, 400), new Vector(StateHandler.WIDTH - 800, StateHandler.HEIGHT - 800), Color.LIGHT_GRAY);
        
        mainPanel.add(new GuiButton(new Vector(450, 450), new Vector(100, 50), Color.DARK_GRAY, "BACK", new Font("Times New Java", Font.BOLD, 25)) {
        	
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.changeState(States.START);
                }
                return false;
            }
            
            @Override
            public void update() {
            	setY(300);
            	setTextOffset(0, 20);
            	setHeight(20);
            	setWidth(70);
            	super.update();
            }
            
     }.setTextOffset(0, 20));
        
		mainPanel.add(new GuiButton(new Vector(450, 470), new Vector(0, 0), Color.DARK_GRAY, "Volume", new Font("Times New Java", Font.BOLD, 22)) {
		        	
		            @Override
		            public boolean mousePressed(MousePressedEvent e) {
		                if (super.mousePressed(e)) {
		                    SoundHandler.getVolume();
		                }
		                return false;
		            }
		            
		        });
		    
	mainPanel.add(new GuiButton(new Vector(450, 490), new Vector(0, 0), Color.DARK_GRAY, "Blood Splatter Level", new Font("Times New Java", Font.BOLD, 22)) {
    	
        @Override
        public boolean mousePressed(MousePressedEvent e) {
            if (super.mousePressed(e)) {
                StateHandler.changeState(States.START);
            }
            return false;
        }
        
    });
	mainPanel.add(new GuiButton(new Vector(450, 510), new Vector(0, 0), Color.DARK_GRAY, "GPU Acceleration", new Font("Times New Java", Font.BOLD, 22)) {
	    	
	        @Override
	        public boolean mousePressed(MousePressedEvent e) {
	            if (super.mousePressed(e)) {
	                StateHandler.changeState(States.START);
	            }
	            return false;
	        }
	        
	    });
    }
    @Override
    public void onEvent(Event event) {
        mainPanel.onEvent(event);
    }

    @Override
    public void update() {
        mainPanel.update();
    }

    @Override
    public void render(Graphics g) {
    	StateHandler.startCopy.render(g);
        mainPanel.render(g);
    }
    
    
    
}
