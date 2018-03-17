package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import core.Map;
import events.Event;
import events.types.KeyPressedEvent;
import events.types.KeyReleasedEvent;
import handler.Vector;
import states.Game;

class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void createPlayer() {
        testPlayer = new Player(new Map(new Game()), new Vector(200.0, 200.0));
    }

    @Test
    void testMoveLeft() {
        testPlayer.keyPressed(new KeyPressedEvent(65, Event.Type.KEY_PRESSED));
        assertTrue(testPlayer.getPlayerLeft());
        testPlayer.keyReleased(new KeyReleasedEvent(65, Event.Type.KEY_RELEASED));
        assertTrue(!testPlayer.getPlayerLeft());
    }

    @Test
    void testMoveRight() {
        testPlayer.keyPressed(new KeyPressedEvent(68, Event.Type.KEY_PRESSED));
        assertTrue(testPlayer.getPlayerRight());
        testPlayer.keyReleased(new KeyReleasedEvent(68, Event.Type.KEY_RELEASED));
        assertTrue(!testPlayer.getPlayerRight());
    }
    @Test
    void testJump() {
        testPlayer.keyPressed(new KeyPressedEvent(87, Event.Type.KEY_PRESSED));
        assertTrue(testPlayer.getPlayerUp());
        testPlayer.keyReleased(new KeyReleasedEvent(87, Event.Type.KEY_RELEASED));
        assertTrue(!testPlayer.getPlayerUp());
    }

    @Test
    void testInvalidButton() {
        testPlayer.keyPressed(new KeyPressedEvent(81, Event.Type.KEY_PRESSED));
        assertTrue(!testPlayer.getPlayerRight());
        assertTrue(!testPlayer.getPlayerUp());
        assertTrue(!testPlayer.getPlayerLeft());
        testPlayer.keyReleased(new KeyReleasedEvent(81, Event.Type.KEY_RELEASED));
        assertTrue(!testPlayer.getPlayerRight());
        assertTrue(!testPlayer.getPlayerUp());
        assertTrue(!testPlayer.getPlayerLeft());
    }
}