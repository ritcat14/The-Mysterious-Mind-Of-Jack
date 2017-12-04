package states;

import java.awt.Color;
import java.awt.Graphics;

import events.Event;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiLabel;
import graphics.GuiPanel;
import handler.StateHandler;
import handler.StateHandler.States;
import handler.Vector;

public class Options extends State {
    
    private GuiPanel mainPanel;
    
    public Options() {
        mainPanel = new GuiPanel(new Vector(100, 100), new Vector(StateHandler.WIDTH - 200, StateHandler.HEIGHT - 200), Color.LIGHT_GRAY);
        mainPanel.add(new GuiButton(new Vector(StateHandler.WIDTH - 450, StateHandler.HEIGHT - 350), new Vector(200, 100), Color.DARK_GRAY, "BACK", GuiLabel.font) {
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
        mainPanel.render(g);
    }
    
    
    
}
