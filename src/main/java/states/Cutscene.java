package states;

import handler.SoundHandler;
import handler.StateHandler;
import handler.Tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import events.Event;
import events.EventDispatcher;
import events.EventHandler;
import events.types.KeyPressedEvent;
import javafx.scene.media.MediaPlayer;
/*
Cutscene class to handle loading screen for the game
 */

public class Cutscene extends State {
    
    private BufferedImage[] frames;
    private BufferedImage currentFrame;
    private int time = 0;
    private int timeSec = 0;
    private int[] times;
    private int frame = 0;

    private MediaPlayer player;
    private int soundID = 1;
    private boolean playedSound = false;
    
    private boolean switching = false;
    
    public Cutscene(int scene) {
        String[] lines = Tools.getData("/scenes/sceneData.gme");
        String data = lines[scene];
        String[] parts = data.split(":");
        frames = Tools.splitImage("/scenes/" + parts[0] + ".png", Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        this.times = Tools.StringArrayToIntArray(parts[3].split(","));
    }
    
    private boolean keyPressed(KeyPressedEvent e) {
        switch (e.getKey()) {
            case KeyEvent.VK_SPACE:
            	switching = true;
            	stop();
                StateHandler.changeState(StateHandler.States.GAME);
                return true;
        }
        return false;
    }

    @Override
    public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.KEY_PRESSED, event1 -> keyPressed((KeyPressedEvent) event1));
    }

    @Override
    public void update() {
        if (!playedSound) {
            if (player != null) player.stop();
            player = SoundHandler.play("intro " + soundID);
            soundID++;
            playedSound = true;
        }
    	if (switching) return;
        time++;
        if (time % 60 == 0) timeSec++;
        
        currentFrame = frames[frame];
        
        if (timeSec == times[frame]) {
            if (frame == frames.length - 1) {
            	switching = true;
            	if (player != null) player.stop();
                StateHandler.changeState(StateHandler.States.GAME);
            }
            frame++;
            timeSec = 0;
            playedSound = false;
        }
    }

    public void stop() {
        if (player!= null) player.stop();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentFrame, 0, 0, StateHandler.WIDTH, StateHandler.HEIGHT, null);
        g.setColor(Color.WHITE);
        g.drawString("Press Space to skip", StateHandler.WIDTH - 150, StateHandler.HEIGHT - 50);
    }
    
}
