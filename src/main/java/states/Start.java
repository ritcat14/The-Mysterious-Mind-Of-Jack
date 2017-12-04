package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import events.Event;
import events.types.MouseMovedEvent;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiPanel;
import handler.SoundHandler;
import handler.StateHandler;
import handler.StateHandler.States;
import handler.Tools;
import handler.Vector;

public class Start extends State {
    
    private GuiPanel mainPanel;
    
    public Start() {
        SoundHandler.loop("intro");
        mainPanel = new GuiPanel(new Vector(50, 50), new Vector(StateHandler.WIDTH - 100, StateHandler.HEIGHT - 100), Tools.getImage("/gui/start.png"));
        mainPanel.
        mainPanel.add(new GuiButton(new Vector(100, 400), new Vector(90, 30), Color.BLACK, "START", new Font("Times New Java", Font.PLAIN, 25), Color.WHITE) {
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.nextScene();
                    return true;
                }
                return false;
            }
            
            @Override
            public boolean mouseMoved(MouseMovedEvent e) {
                if (super.mouseMoved(e)) {
                    textColour = new Color(200, 200, 200);
                    return true;
                } else textColour = Color.WHITE;
                return false;
            }
            
        }.setTextOffset(5, 20));
        mainPanel.add(new GuiButton(new Vector(100, 450), new Vector(110, 30), Color.BLACK, "OPTIONS", new Font("Times New Java", Font.PLAIN, 25), Color.WHITE) {
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.changeState(States.OPTIONS);
                    return true;
                }
                return false;
            }
            
            @Override
            public boolean mouseMoved(MouseMovedEvent e) {
                if (super.mouseMoved(e)) {
                    textColour = new Color(200, 200, 200);
                    return true;
                } else textColour = Color.WHITE;
                return false;
            }
        }.setTextOffset(5, 20));
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
