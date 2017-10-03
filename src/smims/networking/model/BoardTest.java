package smims.networking.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {
	private final Board board = new Board((player) -> new Character(player), new ArrayList<Player>() {
		
	});

	@Test
	public void testCreatesCorrectNumberOfCharacters() {
		assertEquals(4*4, board.getAllCharacters().size());
	}

}
