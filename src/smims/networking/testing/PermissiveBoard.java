package smims.networking.testing;

import java.util.Collection;

import smims.networking.model.BoardDescriptor;
import smims.networking.model.Character;
import smims.networking.model.IBoard;
import smims.networking.model.IPlayer;
import smims.networking.model.MoveNotAllowedException;
import smims.networking.model.Position;

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
	public Character getCharacterAt(int pDistance, int pPlayerID) {
		return allCharacters.stream().findAny().get();
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

	@Override
	public Character getCharacterAt(Position givenPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardDescriptor getBoardDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBoardSectionSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
