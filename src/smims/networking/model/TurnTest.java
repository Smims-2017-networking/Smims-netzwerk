package smims.networking.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import smims.networking.testing.*;

public class TurnTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private static final int DefaultPlayerId = 0;
	private final IPlayer player = generateDefaultPlayer();
	private final Collection<Character> characters = generateDefaultCharacters(player);

	private IPlayer generateDefaultPlayer() {
		return new Player(DefaultPlayerId);
	}

	private Collection<Character> generateDefaultCharacters(IPlayer player) {
		BoardDescriptor boardDescriptor = new BoardDescriptor(4, 40);
		Character[] characters = new Character[] { new Character(player, boardDescriptor, 0), new Character(player, boardDescriptor, 0) };
		return Arrays.asList(characters);
	}

	@Test
	public void allowsThreeThrowsWhenAllCharactersAreInTheBase() throws Exception {
		Turn turn = new Turn(player, new PermissiveBoard(characters, false, false), new LambdaDiceRoller(prev -> 3));
		
		turn.rollDice();
		turn.rollDice();
		turn.rollDice();
		
		exception.expect(MoveNotAllowedException.class);
		turn.rollDice();
	}
	
	@Test
	public void allowsOneThrowWhenNotAllCharactersAreInTheBase() throws Exception {
		Turn turn = new Turn(player, new PermissiveBoard(characters, false, true), new LambdaDiceRoller(prev -> 3));
		
		turn.rollDice();
		
		exception.expect(MoveNotAllowedException.class);
		turn.rollDice();
	}
	
	@Test
	public void allowsTwoRollsWhenRolling6() throws Exception {
		Turn turn = new Turn(player, new PermissiveBoard(characters, false, false), new SequentialDiceRoller(Arrays.stream(new int[] {5,6})));
		
		turn.rollDice();
		turn.rollDice();
		
		exception.expect(MoveNotAllowedException.class);
		turn.rollDice();
	}
	
	@Test
	public void allowsCharacterToMoveAfterRolling() throws Exception {
		Turn turn = new Turn(player, new PermissiveBoard(characters, false, true), new LambdaDiceRoller(x -> 3));
		
		turn.rollDice();
		turn.moveCharacter(0, DefaultPlayerId);

		assertEquals(turn.getCurrentTurnState(), TurnState.Finished);
	}
	
	@Test
	public void allowsToRollAgainAfter6() throws Exception {
		Turn turn = new Turn(player, new PermissiveBoard(characters, false, true), new LambdaDiceRoller(x -> 6));
		
		turn.rollDice();
		turn.moveCharacter(0, DefaultPlayerId);
		
		assertEquals(turn.getCurrentTurnState(), TurnState.ExpectRoll);
		turn.rollDice();
	}
}
