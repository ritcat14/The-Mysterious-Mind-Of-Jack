/*
 * The main game class. This is where the application begins.
 */

public class Main implements Runnable {
    
    private Thread t;
    private boolean running = false;
    
    public Main() {
        
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
        m.start(); // Start the main thread
    }
    
}
