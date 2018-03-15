package handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundHandler {
	
	private static double volume = 0.2;

    private static HashMap<String, Media> sounds = new HashMap<>();
    
    private static ArrayList<MediaPlayer> players = new ArrayList<>();

    public static MediaPlayer play(String fileName) {
        MediaPlayer m = new MediaPlayer(getSound(fileName));
        m.play();
        if (!players.contains(m)) players.add(m);
        return m;
    }

    public static MediaPlayer loop(String fileName) {
        final MediaPlayer m = new MediaPlayer(getSound(fileName));
        m.setOnEndOfMedia(() -> m.seek(Duration.ZERO));
        m.setVolume(volume);
        m.play();
        if (!players.contains(m)) players.add(m);
        return m;
    }
    
    public static void setVolume(double volume) {
    	SoundHandler.volume = volume;
    	for (MediaPlayer m : players) {
    		m.setVolume(volume);
    	}
    }
    
    public static double getVolume() {
		return volume;
	}
    
    public static Media getSound(String file) {
        Media m = null;
        if (!sounds.containsKey(file)) {
            new javafx.embed.swing.JFXPanel();
            URL url = SoundHandler.class.getResource("/sounds/" + file + ".mp3");
            sounds.put(file, new Media(url.toString()));
        }
        m = sounds.get(file);
        return m;
    }
}
