package states.chapters;

import java.awt.Graphics;

import core.Map;
import events.Event;
import states.Game;

public class Chapter1 extends Chapter {

    public Chapter1(Game game) {
        super(new Map(game, 1));
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
