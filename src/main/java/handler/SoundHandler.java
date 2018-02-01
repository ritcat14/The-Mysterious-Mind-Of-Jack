package handler;

import java.net.URL;
import java.util.HashMap;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundHandler {

    private static HashMap<String, Media> sounds = new HashMap<String, Media>();

    public static MediaPlayer play(String fileName) {
        MediaPlayer m = new MediaPlayer(getSound(fileName));
        m.play();
        return m;
    }

    public static MediaPlayer loop(String fileName) {
        final MediaPlayer m = new MediaPlayer(getSound(fileName));
        m.setOnEndOfMedia(new Runnable() {
            public void run() {
                m.seek(Duration.ZERO);
            }
        });
        m.play();
        return m;
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
   public final static double getVolume() {
	   return getVolume();
   }
}
