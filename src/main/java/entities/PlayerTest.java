package entities;

import static org.junit.Assert.*;

import org.junit.Test;

import core.Main;
import core.Map;
import handler.Vector;
import states.Game;

public class PlayerTest {

	@Test
	public void testUpdate() {
		Player p = new Player(new Map(new Game(1), 1), new Vector(500.0, 500.0), "/player/player.png", 0);
		Double startVel = p.velocity.x;
		Double startX = p.pos.x;
		System.out.println("Start x value = " + startX);
		System.out.println("Start x velocity = " + startVel);
		
		//try moving left
		p.setLeft(true);
		p.update();
		System.out.println("Now x value = " + p.pos.x);
		System.out.println("Now x velocity = " + p.velocity.x);
		assertTrue(p.velocity.x < startVel);
		assertTrue(p.pos.x < startX);
		p.setLeft(false);
	}

}
