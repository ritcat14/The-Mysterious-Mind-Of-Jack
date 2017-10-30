import graphics.GameCanvas;

import java.awt.Canvas;
import java.awt.Toolkit;

import javax.swing.JFrame;

/*
 * The main game class. This is where the application begins.
 */

public class Main extends Canvas implements Runnable {
    
    private Thread t;
    private boolean running = false;
    private JFrame frame;
    private GameCanvas canvas;
    private int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    public Main() {
        canvas = new GameCanvas(WIDTH, HEIGHT);
    }
    
    public synchronized void start() {
        running = true;
        t = new Thread(this, "Main");
        t.start();
    }
    
    public synchronized void stop() {
        try {
            t.join();
        } catch (Exception e) {}
        running = false;
    }
    
    public void update() { // Update game logic
        
    }
    
    public void render() { // Draw the game
        canvas.draw();
    }
    
    @Override
    public void run() {
        while(running) { // Game loop
            update();
            render();
        }
    }
    
    public static void main(String[] args) {
        // Main method
        Main m = new Main(); // Create the game object
        m.frame = new JFrame("Game");
        m.frame.setResizable(false);
        m.frame.setUndecorated(true);
        m.frame.add(m);
        
        m.frame.pack();
        m.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        m.frame.setLocationRelativeTo(null);
        m.frame.setVisible(true);
        m.frame.requestFocus();
        m.start();
    }
    
}
