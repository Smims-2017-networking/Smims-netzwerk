package smims.networking.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
		Character[] characters = new Character[] { new Character(player), new Character(player) };
		return Arrays.asList(characters);
	}

	@Test
	public void allowsThreeThrowsWhenAllCharactersAreInTheBase() throws Exception {
		Turn turn = new Turn(player, new PermissiveBoard(characters, true), new LambdaDiceRoller(prev -> 3));
		
		turn.rollDice();
		turn.rollDice();
		turn.rollDice();
		
		exception.expect(MoveNotAllowedException.class);
		turn.rollDice();
	}
}
