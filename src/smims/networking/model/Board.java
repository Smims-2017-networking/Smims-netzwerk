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

	public boolean fieldOccupied(int pFieldNumber) {
		for (ICharacter currentCharacter : charactersOnBoard) {
			if (currentCharacter.getPosition().getIndex() == pFieldNumber
					&& currentCharacter.getPosition().getStatus() == CharacterStatus.FIELD) {
				return true;
			}
		}
		return false;
	}

	public ICharacter getCharacterAt(int pFieldNumber) {
		for (ICharacter currentCharacter : charactersOnBoard) {
			if (currentCharacter.getPosition().getIndex() == pFieldNumber
					&& currentCharacter.getPosition().getStatus() == CharacterStatus.FIELD) {
				return currentCharacter;
			}
		}
		return null;
	}

	@Override
	public void moveCharacter(ICharacter character, int distance) throws Exception{
		
		if(character.getPosition().getStatus() == CharacterStatus.BASE)					// Spieler in der Basis
		{
			if(distance == 6) {
				int characterSpawnPosition = character.getPlayer().getPlayerId() * this.DistanceBetweenSpawns; 	
			
				if(fieldOccupied(characterSpawnPosition)){
					if(getCharacterAt(characterSpawnPosition).getPlayer().getPlayerId() == character.getPlayer().getPlayerId()){
					throw new MoveNotAllowedException();
					} else {
						character.getPosition().setIndex(characterSpawnPosition);
						character.getPosition().setDistanz(character.getPosition().getDistanz()+distance);
					}
			
				} else {
					character.getPosition().setIndex(characterSpawnPosition);
					character.getPosition().setDistanz(character.getPosition().getDistanz()+distance);
				}
			} else {
				throw new MoveNotAllowedException();
			}
		}
		else
		{
			int possibleNewDistanz = character.getPosition().getDistanz() + distance;
			int possibleNewPosition = (character.getPosition().getIndex() + distance) % (DistanceBetweenSpawns * PlayerCount);
			
			if(possibleNewDistanz > (DistanceBetweenSpawns * PlayerCount))		//muss in das Haus rein oder ist im Haus drin.
			{
				if(possibleNewDistanz - (DistanceBetweenSpawns * PlayerCount) > CharactersPerPlayer || )	//Platz an der Stelle im Haus?
				{
					throw new MoveNotAllowedException();
				}
			}
		}
		
		
		
	}

	@Override
	public Collection<IReadonlyCharacter> getAllCharachters() {
		Collection<IReadonlyCharacter> characters = new ArrayList<IReadonlyCharacter>();
		characters.addAll(charactersOnBoard);
		return characters;
	}

}
