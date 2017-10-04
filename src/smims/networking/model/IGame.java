package smims.networking.model;

public interface IGame {	
	void rollDice(int playerID) throws NotYourTurnException, MoveNotAllowedException;
	public int getDiceResult();
	public IBoard getBoard();
	public TurnState getCurrentTurnState();
	public void moveCharacter(int playerID, int characterPos) throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException;
	public int whoseTurn();
	public IPlayer getWinner();
}
