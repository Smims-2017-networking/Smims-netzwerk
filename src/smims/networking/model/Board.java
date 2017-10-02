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
				.anyMatch((character) -> characterOccupiesField(pFieldNumber, character));
	}

	boolean characterOccupiesField(int pFieldNumber, ICharacter currentCharacter) {
		return currentCharacter.getPosition().getIndex() == pFieldNumber
				&& currentCharacter.getPosition().getStatus() == CharacterStatus.FIELD;
	}

	/**
	 * 
	 * @param pFieldNumber
	 * @return Character an der Steller auf dem Feld, wenn keiner vorhanden null
	 */
	public ICharacter getCharacterAt(int pFieldNumber) {
		return charactersOnBoard.stream()
				.filter((character) -> characterOccupiesField(pFieldNumber, character))
				.findFirst()
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
		if(characterIsInBase(pCharacter) && pDistance == 6)
		{
			return charactersOnBoard.stream()
				.anyMatch(character -> characterIsAtStartingPosition(character));
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

	boolean characterIsAtStartingPosition(ICharacter character) {
		return character.getPosition().getDistanz() == 0;
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
		return characterIsInBase(character) && pDistance == 6;
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
			
			if(characterIsInBase(character))			//in Basis
			{
				moveCharacterOutOfBase(character, characterSpawnPosition);
			}
			else if(characterWouldBeInHouse(possibleNewDistanz)) 		//geht in das Haus bzw. ist im Haus
			{
				moveCharacterIntoHouse(character, possibleNewDistanz);
			}
			else														//normal im feld
			{
				moveCharacter(character, possibleNewPosition, possibleNewDistanz);
			}
			
			if(fieldOccupied(possibleNewPosition))						//Spieler schlagen, wenn vorhanden
			{
				characterSchlagen(getCharacterAt(possibleNewPosition));
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

	boolean characterIsInBase(ICharacter character) {
		return character.getPosition().getStatus() == CharacterStatus.BASE;
	}

	void moveCharacterIntoHouse(ICharacter character, int possibleNewDistanz) {
		character.getPosition().setDistanz(possibleNewDistanz);
		character.getPosition().setStatus(CharacterStatus.HOUSE);
		character.getPosition().setIndex(-1);
	}

	void moveCharacter(ICharacter character, int possibleNewPosition, int possibleNewDistanz) {
		character.getPosition().setDistanz(possibleNewDistanz);
		character.getPosition().setIndex(possibleNewPosition);
	}

	void moveCharacterOutOfBase(ICharacter character, int characterSpawnPosition) {
		character.getPosition().setStatus(CharacterStatus.FIELD);
		character.getPosition().setDistanz(0);
		character.getPosition().setIndex(characterSpawnPosition);
	}
	
	/**
	 * 
	 * @param character zur�ck in die Basis setzen (Distanz = -1; Index = -1; Status = BASE)
	 */
	public void characterSchlagen(ICharacter character)
	{
		character.getPosition().setIndex(-1);
		character.getPosition().setStatus(CharacterStatus.BASE);
		character.getPosition().setDistanz(-1);
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
