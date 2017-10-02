package smims.networking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Board implements IBoard {
	private static final int CharactersPerPlayer = 4;
	
	private final Collection<ICharacter> charactersOnBoard;
	
	public Board(ICharacterFactory characterFactory, Collection<Player> players) {
		charactersOnBoard = new ArrayList<ICharacter>();
		for (Player player : players) {
			for (int i = 0; i < CharactersPerPlayer; ++i) {
				charactersOnBoard.add(characterFactory.createNewCharacter(new Player()));
			}
		}
	}
	
	@Override
	public void moveCharachter(ICharacter character, int distance) {	
	}

	@Override
	public Collection<IReadonlyCharacter> getAllCharachters() {
		Collection<IReadonlyCharacter> characters = new ArrayList<IReadonlyCharacter>();
		characters.addAll(charactersOnBoard);
		return characters;
	}

}
