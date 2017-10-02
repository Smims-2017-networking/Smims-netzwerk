package smims.networking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
		if(pCharacter.getPosition().getStatus() == CharacterStatus.BASE)
		{
			pDistance = 0;
		}
		for(ICharacter TestCharacter: charactersOnBoard)
		{
			if(pCharacter.getPosition().getDistanz() + pDistance == TestCharacter.getPosition().getDistanz())
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * 
	 * @param character	Character, der bewegt werden soll
	 * @param distance Distanz, die der Character bewegt werden soll
	 */
	@Override
	public void moveCharacter(ICharacter character, int distance) throws Exception{
		
		int characterSpawnPosition = character.getPlayer().getPlayerId() * this.DistanceBetweenSpawns; 	
		int possibleNewPosition = (character.getPosition().getIndex() + distance) % (DistanceBetweenSpawns * PlayerCount);
		int possibleNewDistanz = character.getPosition().getDistanz() + distance;
		
		if(characterWouldHitTeammate(character, distance))
		{
			throw new MoveNotAllowedException();
		}
		else if(character.getPosition().getStatus() == CharacterStatus.BASE)					// Spieler in der Basis
		{
			if(distance == 6) {
					character.getPosition().setIndex(characterSpawnPosition);
					character.getPosition().setDistanz(character.getPosition().getDistanz()+distance);
				
			} else {
				throw new MoveNotAllowedException();
			}
		}
		else
		{
			if(possibleNewDistanz > (DistanceBetweenSpawns * PlayerCount))		//muss in das Haus rein oder ist im Haus drin.
			{
				if(possibleNewDistanz - (DistanceBetweenSpawns * PlayerCount) > CharactersPerPlayer)	
				{
					throw new MoveNotAllowedException();
				}
				else
				{
					character.getPosition().setDistanz(character.getPosition().getDistanz()+distance);
					character.getPosition().setStatus(CharacterStatus.HOUSE);
					character.getPosition().setIndex(-1);
				}
			}
			else{
				character.getPosition().setDistanz(character.getPosition().getDistanz()+distance);
				character.getPosition().setIndex(possibleNewPosition);
			}
		}
		
		
		
	}

	/**
	 * returns
	 */
	@Override
	public Collection<IReadonlyCharacter> getAllCharachters() {
		Collection<IReadonlyCharacter> characters = new ArrayList<IReadonlyCharacter>();
		characters.addAll(charactersOnBoard);
		return characters;
	}

}
