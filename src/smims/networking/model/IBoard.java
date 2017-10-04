package smims.networking.model;

import java.util.Collection;

public interface IBoard {

	/**
	 * Checht ob auf dem Feld ein Character steht
	 * @param pFieldNumber Nummer des zu ueberpruefenden Feldes, Durchnummeriert von dem Spawn mit der PlayerID 0 bis zu dem Feld vor dem Spawn von Player 0
	 * @return true wenn ein Character auf dem Feld ist
	 */
	boolean fieldOccupied(int pFieldNumber);

	/**
	 * 
	 * @param pDistance   
	 * @param pPlayer
	 * @return Es wird der Character zurueckgegeben, der bei der die gesuchte Distanz zurueckgelegt und von dem Player ist.
	 */
	Character getCharacterAt(int pDistance, int pPlayerID);

	/**
	 * 
	 * @param pCharacter
	 * @param pDistance
	 * @return returns true, wenn an der Stelle die der Character nach dem bewegen
	 *         um pDistance auf dem gleichen Feld wie ein Character vom gleichen
	 *         Team ist.
	 */
	boolean characterWouldHitTeammate(Character pCharacter, int pDistance);

	/**
	 * 
	 * @param character,
	 *            der �berpr�ft werden soll
	 * @param pDistance
	 *            Distanz, die der character sich bewegt
	 * @return true wenn er sich bewegen kann (kein Teammate im weg, Wenn in Basis
	 *         eine 6 gew�rfelt und falls er in das Haus geht ist das Haus lang
	 *         genug)
	 */
	boolean characterCanMove(Character character, int pDistance);

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