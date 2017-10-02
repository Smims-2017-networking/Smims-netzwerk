package smims.networking.model;

import java.util.Collection;

public interface IBoard {
	void moveCharachter(ICharacter iCharacter, int distance);
	Collection<IReadonlyCharacter> getAllCharachters();
}
