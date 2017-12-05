package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import events.Event;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiPanel;
import handler.StateHandler;
import handler.StateHandler.States;
import handler.Vector;

public class Options extends State {
    
    private GuiPanel mainPanel;
    
    public Options() {
        mainPanel = new GuiPanel(new Vector(400, 400), new Vector(StateHandler.WIDTH - 800, StateHandler.HEIGHT - 800), Color.LIGHT_GRAY);
        mainPanel.add(new GuiButton(new Vector(450, 450), new Vector(200, 100), Color.DARK_GRAY, "BACK", new Font("Times New Java", Font.BOLD, 25)) {
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.changeState(States.START);
                }
                return false;
            }
            
            @Override
            public void update() {
            	super.update();
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
    	StateHandler.startBlur.render(g);
        mainPanel.render(g);
    }
    
    
    
}
