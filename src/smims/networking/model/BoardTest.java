package smims.networking.model;

import static org.junit.Assert.*;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public class BoardTest {	
	@Test
	public void test9DoesntReset() throws Exception {
		IBoard board = new Board(Arrays.asList(new Player[]{
				new Player(0),
				new Player(1),
				new Player(2),
				new Player(3),
			}), 4);
		
		Collection<Character> characters = board.getAllCharacters();
		Character firstPlayerChar = characters.stream().filter(c -> c.getPlayer().getPlayerId() == 0)
				.findAny().get();
		
		board.moveCharacter(firstPlayerChar, 6);
		board.moveCharacter(firstPlayerChar, 6);
		board.moveCharacter(firstPlayerChar, 3);
		
		assertEquals(firstPlayerChar, board.getCharacterAt(9, 0));
	}

	@Test
	public void testThrowingOutCharacter() throws Exception {
		BoardDescriptor boardDescriptor = new BoardDescriptor(1, 2);
		IPlayer player1 = new Player(0);
		Character char1 = new Character(player1, boardDescriptor, 0);
		IPlayer player2 = new Player(1);
		Character char2 = new Character(player2, boardDescriptor, 1);
		IBoard board = new Board(
				boardDescriptor,
				Arrays.asList(new Character[] {
					new Character(player1, boardDescriptor, 0),
					new Character(player2, boardDescriptor, 1),
				}),
				2
		);
		
		assertFalse(char1.getFieldNumber().isPresent());
		assertTrue(char1.isInBase());
		
		board.moveCharacter(char1, 6);

		assertEquals(new Integer(0), char1.getFieldNumber().get());
		assertTrue(char1.isOnField());
		
		board.moveCharacter(char2, 6);
		
		assertEquals(new Integer(1), char2.getFieldNumber().get());
		assertTrue(char2.isOnField());
		
		board.moveCharacter(char1, 1);
		
		assertTrue(char2.isInBase());
		assertTrue(char1.isOnField());
		assertEquals(new Integer(1), char1.getFieldNumber().get());
				
	}
}
