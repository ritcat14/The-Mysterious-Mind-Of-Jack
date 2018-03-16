/*
package entities;

import static org.junit.Assert.*;

import org.junit.Test;

import core.Map;
import events.Event;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
import handler.Vector;
import states.Game;

public class PlayerTest {

	@Test
	public void testUpdate() {
		Player p = new Player(new Map(new Game()), new Vector(100.0, 300.0));
		Double startVel = p.velocity.x;
		Double startX = p.pos.x;
		p.update();
		
		//try moving left
		p.setLeft(true);
		p.update();
		p.setLeft(false);
		assertTrue(p.velocity.x < startVel);
		assertTrue(p.pos.x < startX);
		
		//Left is now false - velocity should be 0 on this next update
		p.update();
		assertTrue(p.velocity.x == 0);
		
		//try moving right
		startVel = p.velocity.x;
		startX = p.pos.x;
		p.setRight(true);
		p.update();
		p.setRight(false);
		assertTrue(p.velocity.x > startVel);
		assertTrue(p.pos.x > startX);
		
		//Move left again after moving right
		startVel = p.velocity.x;
		startX = p.pos.x;
		p.setLeft(true);
		p.update();
		p.setLeft(false);
		assertTrue(p.velocity.x < startVel);
		assertTrue(p.pos.x < startX);
		
		//Do damage to player
		double startHealth = p.getHealth();
		p.doDamage(20.0);
		p.update();
		assertTrue(p.getHealth() < startHealth);
	}
	
	@Test
	public void testKeyPressed() {
		Player p = new Player(new Map(new Game()), new Vector(500.0, 500.0));
		Double startX = p.pos.x;
		Double startY = p.pos.y;
		
		//Valid - 'a' key (left)
		p.keyPressed(new KeyPressedEvent(65, Event.Type.KEY_PRESSED));
		p.update();
		p.keyReleased(new KeyReleasedEvent(65, Event.Type.KEY_RELEASED));
		assertTrue(p.pos.x < startX);
		
		//Valid - 'd' key (right)
		startX = p.pos.x;
		p.keyPressed(new KeyPressedEvent(68, Event.Type.KEY_PRESSED));
		p.update();
		p.keyReleased(new KeyReleasedEvent(68, Event.Type.KEY_RELEASED));
		assertTrue(p.pos.x > startX);
		
		//Valid - 'w' key (jump/up)
		p.keyPressed(new KeyPressedEvent(87, Event.Type.KEY_PRESSED));
		p.update();
		p.keyReleased(new KeyReleasedEvent(87, Event.Type.KEY_RELEASED));
		assertTrue(p.pos.y < startY);
		
		//Valid - 'space' key (jump/up)
		p.keyPressed(new KeyPressedEvent(32, Event.Type.KEY_PRESSED));
		p.update();
		p.keyReleased(new KeyReleasedEvent(32, Event.Type.KEY_RELEASED));
		assertTrue(p.pos.y < startY);
		
		p.update();
		//Invalid 'f' key
		startX = p.pos.x;
		p.keyPressed(new KeyPressedEvent(70, Event.Type.KEY_PRESSED));
		p.update();
		p.keyReleased(new KeyReleasedEvent(70, Event.Type.KEY_RELEASED));
		assertTrue(p.pos.x == startX);
	}
}
*/
