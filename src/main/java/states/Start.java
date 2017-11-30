package states;

import java.awt.Color;
import java.awt.Graphics;

import events.Event;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiPanel;
import handler.SoundHandler;
import handler.StateHandler;
import handler.StateHandler.States;

public class Start extends State {
    
    private GuiPanel mainPanel;
    
    public Start() {
        SoundHandler.playMP3("intro.mp3");
        mainPanel = new GuiPanel(50, 50, StateHandler.WIDTH - 100, StateHandler.HEIGHT - 100, Color.GRAY);
        mainPanel.add(new GuiButton((StateHandler.WIDTH/2) - 100, 300, 200, 100, Color.DARK_GRAY, "START") {
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.nextScene();
                    return true;
                }
                return false;
            }
        });
        mainPanel.add(new GuiButton((StateHandler.WIDTH/2) - 100, 500, 200, 100, Color.DARK_GRAY, "OPTIONS") {
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.changeState(States.OPTIONS);
                    return true;
                }
                return false;
            }
        });
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
