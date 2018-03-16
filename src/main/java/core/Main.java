/*
 * The main game class. Starts all the essential parts of the code, sets up fps counter, and starts threads.
 *
 */

package core;

import graphics.GameCanvas;
import handler.StateHandler;
import handler.StateHandler.States;
import handler.Tools;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import entities.Player;

public class Main implements Runnable, WindowListener {
	public static JFrame frame;
	private Thread thread;
	private boolean running = false;
	private GameCanvas canvas;
	private int WIDTH = 1200;
	private int HEIGHT = WIDTH / 16 * 9;
	private StateHandler sh;
	private int time = 0;
	private Player player;

	public static int FPS = 0;

	public static Main main;

	public Main() {
		sh = new StateHandler(WIDTH, HEIGHT);
		canvas = new GameCanvas(sh, WIDTH, HEIGHT);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Main");
		thread.start();
		StateHandler.changeState(States.START);
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (Exception e) {
		}
		running = false;
	}

	public void update() { // Update game logic
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		this.player = StateHandler.player;
		StateHandler.update();
	}

	public void render() { // Draw the game
		canvas.draw();
	}

	@Override
	public void run() {
		long lastTimeU = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double nsu = 1000000000.0 / 60.0;
		double deltaU = 0;
		double deltaF = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long nowU = System.nanoTime();
			deltaU += (nowU - lastTimeU) / nsu;
			deltaF += ((nowU - lastTimeU) / nsu);
			lastTimeU = nowU;
			while (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			while (deltaF >= 1) {
				render();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("FPS: " + frames + " , UPS: " + updates);
				FPS = frames;
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}

	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "False");
		// Main method
		Main m = new Main(); // Create the game object
		Main.main = m;
		frame = new JFrame("Game");
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.add(m.canvas);

		frame.pack();
		frame.addWindowListener(m);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		m.start();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		Player player = StateHandler.player;
		if (player != null && StateHandler.getState().equals(States.GAME) || StateHandler.getState().equals(States.PAUSE)) {
			if (StateHandler.getState().equals(States.GAME)) StateHandler.pause();
		}
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

}
