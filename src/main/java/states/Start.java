package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import events.Event;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiLabel;
import graphics.GuiPanel;
import handler.SoundHandler;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;

/*
Start class to handle start state
 */

public class Start extends State {
    
    private GuiPanel mainPanel;
    
    public Start() {
        //SoundHandler.loop("intro");
        mainPanel = new GuiPanel(new Vector(100, 50), new Vector(StateHandler.WIDTH - 200, StateHandler.HEIGHT - 100), Tools.getImage("/gui/start.png"));
        mainPanel.add(new GuiLabel(new Vector(150, 200), new Vector(150, 50), "MAIN MENU", Color.WHITE, new Font("Times New Java", Font.PLAIN, 50)));
        mainPanel.add(new GuiButton(new Vector(230, 300), new Vector(90, 25), null, "START", new Font("Times New Java", Font.PLAIN, 25)) {
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.nextScene();
                    return true;
                }
                return false;
            }
            
        }.setTextOffset(5, 23));
        
        mainPanel.add(new GuiButton(new Vector(215, 350), new Vector(120, 25),  null, "OPTIONS", new Font("Times New Java", Font.PLAIN, 25)) {
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.loadOptions();
                    return true;
                }
                return false;
            }
            
        }.setTextOffset(5, 23));
        
        GuiButton exit = new GuiButton(new Vector(240, 400), new Vector(65, 25),  null, "EXIT", new Font("Times New Java", Font.PLAIN, 25)) {
        	@Override
        	public boolean mousePressed(MousePressedEvent e) {
        		if (super.mousePressed(e)) {
        			System.exit(0);
            		return true;
        		}
        		return false;
        	}
        	
        }.setTextOffset(5, 23);
        
        mainPanel.add(exit);
        
    }
    
    @Override
    public void update() {
        mainPanel.update();
    }

    @Override
    public void render(Graphics g) {
        mainPanel.render(g);
    }

    @Override
    public void onEvent(Event event) {
        mainPanel.onEvent(event);
    }
}
