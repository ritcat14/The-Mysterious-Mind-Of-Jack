package states;

import java.awt.Graphics;

import states.chapters.*;
import events.Event;

public class Game extends State {
    
    private State currentChapter;

    public Game(int chapter) {
        switch(chapter) {
            case 1:
                currentChapter = new Chapter1();
                break;
            case 2:
                currentChapter = new Chapter2();
                break;
            case 3:
                currentChapter = new Chapter3();
                break;
            case 4:
                currentChapter = new Chapter4();
                break;
            case 5:
                currentChapter = new Chapter5();
                break;
            default:
                currentChapter = new Chapter1();
                break;
        }
    }

    @Override
    public void update() {
        currentChapter.update();
    }

    @Override
    public void render(Graphics g) {
        currentChapter.render(g);
    }

    @Override
    public void onEvent(Event event) {
        currentChapter.onEvent(event);
    }
}
