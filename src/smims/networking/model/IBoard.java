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

	Character getCharacterAt(Position givenPosition);

	BoardDescriptor getBoardDescriptor();

	int getBoardSectionSize();
	
	default Position.StartingPositionBuilder getStartingPositionBuilderFor(IPlayer player) {
		return getStartingPositionBuilderFor(player.getPlayerId());
	}
	
	Position.StartingPositionBuilder getStartingPositionBuilderFor(int playerId);

	boolean canMoveByDistance(Character c, int result);

	boolean anyCharacterInHouseCanMove(IPlayer player);

}