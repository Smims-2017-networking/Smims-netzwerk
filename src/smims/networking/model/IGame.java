package smims.networking.model;

public interface IGame {	
	void rollDice(int playerID) throws NotYourTurnException, MoveNotAllowedException;
	public int getDiceResult();
	public Board getBoard();
	
	public void moveCharacter(int playerID, int characterPos) throws MoveNotAllowedException, NotYourTurnException;
	public int whoseTurn();
}
