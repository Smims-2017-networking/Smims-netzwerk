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
		for (ICharacter currentCharacter : charactersOnBoard) {
			if (currentCharacter.getPosition().getIndex() == pFieldNumber
					&& currentCharacter.getPosition().getStatus() == CharacterStatus.FIELD) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param pFieldNumber
	 * @return Character an der Steller auf dem Feld, wenn keiner vorhanden null
	 */
	public ICharacter getCharacterAt(int pFieldNumber) {
		for (ICharacter currentCharacter : charactersOnBoard) {
			if (currentCharacter.getPosition().getIndex() == pFieldNumber
					&& currentCharacter.getPosition().getStatus() == CharacterStatus.FIELD) {
				return currentCharacter;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param pCharacter
	 * @param pDistance
	 * @return returns true, wenn an der Stelle die der Character nach dem bewegen um pDistance auf dem gleichen Feld wie ein Character vom gleichen Team ist.
	 */
	public boolean characterWouldHitTeammate(ICharacter pCharacter, int pDistance)
	{
		if(pCharacter.getPosition().getStatus() == CharacterStatus.BASE && pDistance == 6)
		{
			for(ICharacter TestCharacter: charactersOnBoard)
			{
				if(0 == TestCharacter.getPosition().getDistanz())
				{
					return true;
				}
			}
		}
		else 
		{
			for(ICharacter TestCharacter: charactersOnBoard)
			{
				if(pCharacter.getPosition().getDistanz() + pDistance == TestCharacter.getPosition().getDistanz())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param character, der überprüft werden soll
	 * @param pDistance	Distanz, die der character sich bewegt
	 * @return true wenn er sich bewegen kann (kein Teammate im weg, Wenn in Basis eine 6 gewürfelt und falls er in das Haus geht ist das Haus lang genug)
	 */
	public boolean characterCanMove(ICharacter character, int pDistance)
	{
		if(characterWouldHitTeammate(character, pDistance))			//Würde teammate treffen
		{
			return false;
		}
		if(character.getPosition().getStatus() == CharacterStatus.BASE && pDistance != 6)		//In Basis und keine 6
		{
			return false;
		}
		if(character.getPosition().getDistanz() + pDistance > (DistanceBetweenSpawns * PlayerCount) + CharactersPerPlayer)		//Haus nicht lang genug
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param character zurück in die Basis setzen (Distanz = -1; Index = -1; Status = BASE)
	 */
	public void characterSchlagen(ICharacter character)
	{
		character.getPosition().setIndex(-1);
		character.getPosition().setStatus(CharacterStatus.BASE);
		character.getPosition().setDistanz(-1);
	}
	
	
	/**
	 * 
	 * @param character	Character, der bewegt werden soll
	 * @param distance Distanz, die der Character bewegt werden soll
	 */
	@Override
	public void moveCharacter(ICharacter character, int distance) throws Exception{
		
		int characterSpawnPosition = character.getPlayer().getPlayerId() * DistanceBetweenSpawns; 	
		int possibleNewPosition = (character.getPosition().getIndex() + distance) % (DistanceBetweenSpawns * PlayerCount);
		int possibleNewDistanz = character.getPosition().getDistanz() + distance;
		
		if(characterCanMove(character, distance))
		{
			if(character.getPosition().getStatus() == CharacterStatus.BASE)			//in Basis
			{
				character.getPosition().setStatus(CharacterStatus.FIELD);
				character.getPosition().setDistanz(0);
				character.getPosition().setIndex(characterSpawnPosition);
			}
			else if(possibleNewDistanz > (DistanceBetweenSpawns * PlayerCount)) 		//geht in das Haus bzw. ist im Haus
			{
				character.getPosition().setDistanz(possibleNewDistanz);
				character.getPosition().setStatus(CharacterStatus.HOUSE);
				character.getPosition().setIndex(-1);
			}
			else														//normal im feld
			{
				character.getPosition().setDistanz(possibleNewDistanz);
				character.getPosition().setIndex(possibleNewPosition);
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
