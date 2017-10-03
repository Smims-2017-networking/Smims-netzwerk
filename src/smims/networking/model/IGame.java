package smims.networking.model;

import java.util.ArrayList;

public interface IGame {	
	void rollDice(int playerID);
	public int getDiceResult();
	public Board getBoard();
	
	public void moveCharacter(int playerID, int characterPos);
	public int whoseTurn();
}
