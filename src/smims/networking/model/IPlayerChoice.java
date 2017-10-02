package smims.networking.model;

import java.util.Collection;

public interface IPlayerChoice {
	int chooseCharacter();
	void greet();
	void signOff();
	boolean wantsToStartGame();
}
