package states;

import events.Event;
import events.types.MousePressedEvent;
import graphics.GuiButton;
import graphics.GuiLabel;
import graphics.GuiPanel;
import handler.StateHandler;
import handler.Vector;

import java.awt.*;

public class GameOver extends State {

    private GuiPanel panel;

    public GameOver() {
        panel = new GuiPanel(new Vector(0, 0), new Vector(StateHandler.WIDTH, StateHandler.HEIGHT), Color.BLACK);

        panel.add(new GuiLabel(new Vector(150, (StateHandler.HEIGHT/2) - 200), new Vector(600, 400), "GAME OVER", Color.WHITE, new Font("Java New Times", Font.BOLD, 150)));
        panel.add(new GuiButton(new Vector((StateHandler.WIDTH/2) - 100, StateHandler.HEIGHT / 2), new Vector(70, 20), Color.BLACK, "RETRY", new Font("Java New Times", Font.BOLD, 20)){
            @Override
            public boolean mousePressed(MousePressedEvent e) {
                if (super.mousePressed(e)) {
                    StateHandler.changeState(StateHandler.States.START);
                    return true;
                }
                return false;
            }
        }.setTextOffset(0, 20));
    }

    @Override
    public void update() {
        panel.update();
    }

    @Override
    public void render(Graphics g) {
        panel.render(g);
    }

    @Override
    public void onEvent(Event event) {
        panel.onEvent(event);
    }
}
