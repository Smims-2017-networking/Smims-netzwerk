package smims.networking.testing;

import java.util.Collection;

import smims.networking.model.Character;
import smims.networking.model.IBoard;
import smims.networking.model.IPlayer;
import smims.networking.model.MoveNotAllowedException;

/**
 * This Board implementation is configured to allow as many operations as 
 * possible. It does not manage multiple players.
 */
public class PermissiveBoard implements IBoard {
	private final Collection<Character> allCharacters;
	private boolean allCharactersInHouse;
	private boolean playerHasCharactersOnBoard;
	
	public PermissiveBoard(Collection<Character> allCharacters, boolean allCharactersInHouse, boolean playerHasCharactersOnBoard) {
		if (allCharacters == null) {
			throw new IllegalArgumentException();
		}
		
		this.allCharacters = allCharacters;
		this.allCharactersInHouse = allCharactersInHouse;
		this.playerHasCharactersOnBoard = playerHasCharactersOnBoard;
	}

	@Override
	public boolean fieldOccupied(int pFieldNumber) {
		return false;
	}

	@Override
	public Character getCharacterAt(int pDistance, int pPlayerID) {
		return null;
	}

	@Override
	public boolean characterWouldHitTeammate(Character pCharacter, int pDistance) {
		return false;
	}

	@Override
	public boolean characterCanMove(Character character, int pDistance) {
		return true;
	}

	@Override
	public boolean allCharactersInHouse(int pPlayerId) {
		return allCharactersInHouse;
	}
	
	public void setAllCharactersInHouse(boolean value) {
		allCharactersInHouse = value;
	}

	@Override
	public void moveCharacter(Character pCharacter, int distance) throws MoveNotAllowedException {		
	}

	@Override
	public Collection<Character> getAllCharacters() {
		return allCharacters;
	}

	@Override
	public IPlayer getWinner() {
		return null;
	}

	@Override
	public boolean playerHasCharactersOnBoard(IPlayer player) {
		return playerHasCharactersOnBoard;
	}
	
	public void setPlayerHasCharactersOnBoard(boolean value) {
		playerHasCharactersOnBoard = value;
	}

}
