package smims.networking.model;

import smims.networking.model.Position.StartingPositionBuilder;

public interface IGame {	
	void rollDice(int playerID) throws NotYourTurnException, MoveNotAllowedException;
	public int getDiceResult();
	public IBoard getBoard();
	public TurnState getCurrentTurnState();
	public void moveCharacter(int playerID, Position characterPos) throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException;
	public int whoseTurn();
	public IPlayer getWinner();
	public int getRemainingRolls();
	public default StartingPositionBuilder getStartingPositionBuilder(IPlayer player) {
		return getStartingPositionBuilder(player.getPlayerId());
	}
	public StartingPositionBuilder getStartingPositionBuilder(int playerId);
}
