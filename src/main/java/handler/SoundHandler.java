package handler;

import java.io.File;
import java.util.LinkedList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.HashMap;

public class SoundHandler {
    
    private static HashMap<String, Media> sounds = new HashMap<String, Media>();
    
    public static MediaPlayer play(String fileName) {
        if (!sounds.containsKey(fileName)) {
            new javafx.embed.swing.JFXPanel();
            URL url = SoundHandler.class.getResource("/sounds/" + fileName);
            sounds.put(fileName, new Media(url.toString()));
        }
        return new MediaPlayer(sounds.get(fileName)).play();
    }
}
