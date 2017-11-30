package handler;

import java.io.File;
import java.util.LinkedList;

import javax.print.attribute.standard.Media;

public class SoundHandler {
    public static void playMP3(String fileName) {
        new javafx.embed.swing.JFXPanel();
        String uriString = new File(fileName).toURI().toString();
        new MediaPlayer(new Media(uriString)).play();
    }
}
