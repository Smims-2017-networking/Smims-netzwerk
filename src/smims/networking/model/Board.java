package smims.networking.model;

import java.util.ArrayList;
import java.util.Collection;

public class Board implements IBoard {
	private static final int PlayerCount = 4;
	private static final int CharactersPerPlayer = 4;
	private static final int DistanceBetweenSpawns = 10;

	private final Collection<ICharacter> charactersOnBoard;

	public Board(ICharacterFactory characterFactory, Collection<Player> players) {
		charactersOnBoard = new ArrayList<ICharacter>();
		for (Player player : players) {
			for (int i = 0; i < CharactersPerPlayer; ++i) {
				charactersOnBoard.add(new Character(player));
			}
		}
	}

	/**
	 * Checht ob auf dem Feld ein Character steht
	 * @param pFieldNumber Nummer des zu ueberpruefenden Feldes
	 * @return true wenn ein Character auf dem Feld ist
	 */
	public boolean fieldOccupied(int pFieldNumber) {
		return charactersOnBoard.stream()
				.anyMatch((character) -> character.isOnField(pFieldNumber));
	}

	/**
	 * 
	 * @param pFieldNumber
	 * @return Character an der Steller auf dem Feld, wenn keiner vorhanden null
	 */
	public ICharacter getCharacterAt(int pFieldNumber) {
		return charactersOnBoard.stream()
				.filter((character) -> character.isOnField(pFieldNumber))
				.findAny()
				.orElse(null);
	}

	/**
	 * 
	 * @param pCharacter
	 * @param pDistance
	 * @return returns true, wenn an der Stelle die der Character nach dem bewegen um pDistance auf dem gleichen Feld wie ein Character vom gleichen Team ist.
	 */
	public boolean characterWouldHitTeammate(ICharacter pCharacter, int pDistance)
	{
		if(pCharacter.isInBase() && pDistance == 6)
		{
			return charactersOnBoard.stream()
				.anyMatch(character -> character.isAtStartingPosition());
		}
		else 
		{
			return charactersOnBoard.stream()
				.anyMatch(character -> characterPositionsAreEqual(pCharacter, pDistance, character));
		}
	}

	boolean characterPositionsAreEqual(ICharacter pCharacter, int pDistance, ICharacter TestCharacter) {
		return pCharacter.getPosition().getDistanz() + pDistance == TestCharacter.getPosition().getDistanz();
	}

	/**
	 * 
	 * @param character, der �berpr�ft werden soll
	 * @param pDistance	Distanz, die der character sich bewegt
	 * @return true wenn er sich bewegen kann (kein Teammate im weg, Wenn in Basis eine 6 gew�rfelt und falls er in das Haus geht ist das Haus lang genug)
	 */
	public boolean characterCanMove(ICharacter character, int pDistance)
	{
		return 
				(!characterWouldHitTeammate(character, pDistance)
				&& !characterWouldMoveOutOfHouse(character, pDistance))
				|| characterCanExitBase(character, pDistance);
	}

	boolean characterWouldMoveOutOfHouse(ICharacter character, int pDistance) {
		return character.getPosition().getDistanz() + pDistance > (DistanceBetweenSpawns * PlayerCount) + CharactersPerPlayer;
	}

	boolean characterCanExitBase(ICharacter character, int pDistance) {
		return character.isInBase() && pDistance == 6;
	}
	
	
	
	/**
	 * 
	 * @param character	Character, der bewegt werden soll
	 * @param distance Distanz, die der Character bewegt werden soll
	 */
	@Override
	public void moveCharacter(ICharacter character, int distance) throws Exception{
		if(characterCanMove(character, distance))
		{
			int characterSpawnPosition = character.getPlayer().getPlayerId() * DistanceBetweenSpawns; 	
			int possibleNewPosition = (character.getPosition().getIndex() + distance) % (DistanceBetweenSpawns * PlayerCount);
			int possibleNewDistanz = character.getPosition().getDistanz() + distance;
			
			if(character.isInBase())			//in Basis
			{
				character.moveOutOfBase(characterSpawnPosition);
			}
			else if(characterWouldBeInHouse(possibleNewDistanz)) 		//geht in das Haus bzw. ist im Haus
			{
				character.moveIntoHouse(possibleNewDistanz);
			}
			else														//normal im feld
			{
				character.move(possibleNewPosition, possibleNewDistanz);
			}
			
			if(fieldOccupied(possibleNewPosition))						//Spieler schlagen, wenn vorhanden
			{
				getCharacterAt(possibleNewPosition).werdeGeschlagen();
			}
			
		}
		else
		{
			throw new MoveNotAllowedException();
		}
		
	}

	boolean characterWouldBeInHouse(int possibleNewDistanz) {
		return possibleNewDistanz > (DistanceBetweenSpawns * PlayerCount);
	}

	/**
	 * returns all Characters in an ArrayList<IReadonlyCharacter>
	 */
	@Override
	public Collection<IReadonlyCharacter> getAllCharachters() {
		Collection<IReadonlyCharacter> characters = new ArrayList<IReadonlyCharacter>();
		characters.addAll(charactersOnBoard);
		return characters;
	}

}
