package smims.networking.model;

import java.util.Collection;

public interface IBoard {
	void moveCharacter(ICharacter iCharacter, int distance) throws Exception;
	Collection<IReadonlyCharacter> getAllCharacters();
}
