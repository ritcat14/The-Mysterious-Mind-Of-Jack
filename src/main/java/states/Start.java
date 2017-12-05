package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import events.Event;
import events.types.MouseMovedEvent;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiLabel;
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
        mainPanel = new GuiPanel(new Vector(200, 100), new Vector(StateHandler.WIDTH - 400, StateHandler.HEIGHT - 200), Tools.getImage("/gui/start.png"));
        mainPanel.add(new GuiLabel(new Vector(250, 350), new Vector(150, 60), "MAIN MENU", Color.WHITE, new Font("Times New Java", Font.PLAIN, 50)));
        mainPanel.add(new GuiButton(new Vector(250, 450), new Vector(90, 30), null, "START", new Font("Times New Java", Font.PLAIN, 25)) {
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
            
            
        }.setTextOffset(5, 23));
        mainPanel.add(new GuiButton(new Vector(250, 500), new Vector(120, 30), null, "OPTIONS", new Font("Times New Java", Font.PLAIN, 25)) {
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.loadOptions();
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
            
        }.setTextOffset(5, 23));
        
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
