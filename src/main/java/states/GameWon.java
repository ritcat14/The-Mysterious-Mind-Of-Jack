package states;

import events.Event;
import graphics.GuiLabel;
import graphics.GuiPanel;
import handler.StateHandler;
import handler.Tools;
import handler.Vector;

import java.awt.*;
import java.util.ArrayList;

public class GameWon extends State {

    private GuiPanel panel;
    private int time = 0;
    private ArrayList<GuiLabel> credits = new ArrayList<>();
    private ArrayList<GuiLabel> creditsToRemove = new ArrayList<>();
    private boolean rendering = false;

    public GameWon() {
        panel = new GuiPanel(new Vector(0, 0), new Vector(StateHandler.WIDTH, StateHandler.HEIGHT), Color.WHITE);
        String[] text = Tools.getData("/credits.txt");
        for (int i = 0; i < text.length; i++) {
            credits.add(new GuiLabel(new Vector(100, StateHandler.HEIGHT + (50 * i)), new Vector(), text[i], Color.BLACK, new Font("Java New Times", Font.BOLD, 25)));
        }
    }

    @Override
    public void update() {
        time++;
        if (time == Integer.MAX_VALUE) time = 0;
        for (GuiLabel l : credits) {
            l.setY(l.getY() - 1);
            if (l.getY() + l.getHeight() < 0) creditsToRemove.add(l);
        }
        if (!rendering) {
            credits.removeAll(creditsToRemove);
            creditsToRemove.clear();
        }
        if (credits.size() == 0) System.exit(0);
    }

    @Override
    public void render(Graphics g) {
        rendering = true;
        for (GuiLabel l : credits) l.render(g);
        rendering = false;
    }

    @Override
    public void onEvent(Event event) {

    }
}
