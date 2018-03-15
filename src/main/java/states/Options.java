package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import events.Event;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiLabel;
import graphics.GuiPanel;
import graphics.GuiRadioButton;
import handler.SoundHandler;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;

public class Options extends State {
    private GuiPanel mainPanel;
    private GuiLabel volumeLabel;
    private GuiRadioButton acceleration, blood;
    
    public Options() {
    	init();
        mainPanel = new GuiPanel(new Vector(400, 400), new Vector(StateHandler.WIDTH - 800, StateHandler.HEIGHT - 800), Color.LIGHT_GRAY);
        
        mainPanel.add(new GuiButton(new Vector(400, 550), new Vector(85, 20), Color.DARK_GRAY, "CLOSE", new Font("Times New Java", Font.BOLD, 25)) {
        	
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                	Object[] data = {"" + SoundHandler.getVolume() + ":" + Game.bloodLevel + ":" + Game.acceleration};
                	Tools.writeToFile(Tools.propertiesFile, data);
            	    System.setProperty("sun.java2d.opengl", "" + acceleration);
                    StateHandler.unloadOptions();
                }
                return false;
            }            
        }.setTextOffset(0, 20));
        
		// Volume
        mainPanel.add(volumeLabel = new GuiLabel(new Vector(450, 370), new Vector(), "VOLUME", Color.WHITE, new Font("Times New Java", Font.BOLD, 15)));
        
        mainPanel.add(new GuiButton(new Vector(400, 320), Tools.getImage("/gui/upButtonWhite.png")) {
        	@Override
        	public boolean mousePressed(MousePressedEvent e) {
        		if (super.mousePressed(e)) {
        			if (SoundHandler.getVolume() < 1) {
        				SoundHandler.setVolume(Tools.round((SoundHandler.getVolume() + 0.2), 1, false));
        			}
        			return true;
        		}
        		return false;
        	}
        });
        
        mainPanel.add(new GuiButton(new Vector(400, 380), Tools.getImage("/gui/downButtonWhite.png")) {
        	@Override
        	public boolean mousePressed(MousePressedEvent e) {
        		if (super.mousePressed(e)) {
        			if (SoundHandler.getVolume() > 0.1) {
        				SoundHandler.setVolume(Tools.round((SoundHandler.getVolume()) - 0.2, 1, false));
        			}
        			return true;
        		}
        		return false;
        	}
        });
		    
		// Blood spatter
        
        mainPanel.add(acceleration = new GuiRadioButton(new Vector(430, 450), new Vector(80, 20), "Blood Spatter", GuiLabel.standard, Color.WHITE));
        
		// GPU acceleration
        
        mainPanel.add(blood = new GuiRadioButton(new Vector(430, 500), new Vector(80, 20), "GPU Acceleration", GuiLabel.standard, Color.WHITE));
    	acceleration.setChecked(Game.acceleration);
    	blood.setChecked(Game.bloodLevel);
    }
    
    public void init() {
    	/*String[] data = Tools.readFile(Tools.propertiesFile)[0].split(":");
    	SoundHandler.setVolume(Double.parseDouble(data[0]));
    	Game.bloodLevel = Boolean.parseBoolean(data[1]);
    	Game.acceleration = Boolean.parseBoolean(data[2]);*/
    }
    
    @Override
    public void onEvent(Event event) {
        mainPanel.onEvent(event);
    }

    @Override
    public void update() {
        mainPanel.update();
        volumeLabel.setText("VOLUME: " + SoundHandler.getVolume());
        Game.bloodLevel = blood.isChecked();
    	Game.acceleration = acceleration.isChecked();
    }

    @Override
    public void render(Graphics g) {
    	StateHandler.startCopy.render(g);
        mainPanel.render(g);
    }
    
    
    
}
