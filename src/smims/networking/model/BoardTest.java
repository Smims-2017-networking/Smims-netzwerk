package smims.networking.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public class BoardTest {
	private final Board board = new Board(Arrays.asList(new Player[]{
		new Player(0),
		new Player(1),
		new Player(2),
		new Player(3),
	}), 4);
	
	
	@Test
	public void test9DoesntReset() throws Exception {
		Collection<Character> characters = board.getAllCharacters();
		Character firstPlayerChar = characters.stream().filter(c -> c.getPlayer().getPlayerId() == 0)
				.findAny().get();
		
		board.moveCharacter(firstPlayerChar, 6);
		board.moveCharacter(firstPlayerChar, 6);
		board.moveCharacter(firstPlayerChar, 3);
		
		assertEquals(firstPlayerChar, board.getCharacterAt(9, 0));
	}

}
