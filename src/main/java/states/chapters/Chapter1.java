package states.chapters;

import java.awt.Graphics;

import core.Map;
import events.Event;

public class Chapter1 extends Chapter {

    public Chapter1() {
        super(new Map(1));
    }

    @Override
    public void onEvent(Event event) {
        map.onEvent(event);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }
}
