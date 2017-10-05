package smims.networking.model;

import java.util.Collection;

public interface IBoard {

	/**
	 * 
	 * @param pDistance   
	 * @param pPlayer
	 * @return Es wird der Character zurueckgegeben, der bei der die gesuchte Distanz zurueckgelegt und von dem Player ist.
	 */
	Character getCharacterAt(int pDistance, int pPlayerID);

	/**
	 * 
	 * @param pPlayerId
	 * @return	true, wenn alle im Haus sind
	 */
	boolean allCharactersInHouse(int pPlayerId);

	/**
	 * 
	 * @param character
	 *            Character, der bewegt werden soll
	 * @param distance
	 *            Distanz, die der Character bewegt werden soll
	 */
	void moveCharacter(Character pCharacter, int distance) throws MoveNotAllowedException;

	/**
	 * returns all Characters in an ArrayList<IReadonlyCharacter>
	 */
	Collection<Character> getAllCharacters();

	IPlayer getWinner();

	boolean playerHasCharactersOnBoard(IPlayer player);

}