import graphics.GameCanvas;
import handler.StateHandler;
import handler.StateHandler.States;

import java.awt.Toolkit;

import javax.swing.JFrame;

/*
 * The main game class. This is where the application begins.
 * 
 */

public class Main implements Runnable {

    private Thread       t;
    private boolean      running = false;
    private JFrame       frame;
    private GameCanvas   canvas;
    private int          WIDTH   = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int          HEIGHT  = Toolkit.getDefaultToolkit().getScreenSize().height;
    private StateHandler sh;

    public Main() {
        sh = new StateHandler(WIDTH, HEIGHT);
        canvas = new GameCanvas(sh, WIDTH, HEIGHT);
    }

    public synchronized void start() {
        running = true;
        t = new Thread(this, "Main");
        t.start();
        StateHandler.changeState(States.START);
    }

    public synchronized void stop() {
        try {
            t.join();
        } catch (Exception e) {
        }
        running = false;
    }

    public void update() { // Update game logic
        StateHandler.update();
    }

    public void render() { // Draw the game
        canvas.draw();
    }

    @Override
    public void run() {
        long lastTimeU = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double nsu = 1000000000.0 / 120.0;
        double deltaU = 0;
        double deltaF = 0;
        int frames = 0;
        int updates = 0;
        while (running) {
            long nowU = System.nanoTime();
            deltaU += (nowU - lastTimeU) / nsu;
            deltaF += ((nowU - lastTimeU) / nsu)/2;
            lastTimeU = nowU;
            while (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
            
            while (deltaF >= 1) {
                render();
                frames++;
                deltaF --;
            }
            
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle("FPS: " + frames + " , UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        // Main method
        Main m = new Main(); // Create the game object
        m.frame = new JFrame("Game");
        m.frame.setResizable(false);
        m.frame.setUndecorated(true);
        m.frame.add(m.canvas);

        m.frame.pack();
        m.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        m.frame.setLocationRelativeTo(null);
        m.frame.setVisible(true);
        m.frame.requestFocus();
        m.start();
    }

}
