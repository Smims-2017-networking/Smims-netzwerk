package smims.networking.model;

import java.util.ArrayList;

public class Game implements IGame {
	
	private IDiceRoller myDice ;
	private IBoard myBoard;
	private ArrayList<Player> myPlayers;
	private final TurnManager myTurnManager;
		
	/**
	 * 
	 * @param pPlayerID ID des Spielers
	 * @param pBoard das verwendete Spielbrett 
	 */
	Game(ArrayList<Player> pPlayers, int BoardNumberOfPlayers)
	{
		myPlayers = pPlayers;
		myDice = new DiceRoller();
		myBoard = new Board(pPlayers, BoardNumberOfPlayers);
		myTurnManager = new TurnManager(pPlayers,myBoard, myDice);
	}
	
	/**
	 * wuerfelt den wuerfel
	 * @throws MoveNotAllowedException 
	 * @throws NotYourTurnException 
	 */
	@Override
	public void rollDice(int pPlayerID) throws NotYourTurnException, MoveNotAllowedException
	{
		myTurnManager.rollDice(pPlayerID);
	}
	
	@Override
	public int getDiceResult()
	{
		return myDice.getResult();
	}
	
	@Override
	public IBoard getBoard()
	{
		return myBoard;
	}
	
	/**
	 * Bewegt den Character an der Position, wenn er zu dem Player gehoert.
	 * @param pPlayerID
	 * @param Position
	 * @throws Exception
	 */
	@Override
	public void moveCharacter(int pPlayerID, int Position) throws MoveNotAllowedException, NotYourTurnException
	{
		myTurnManager.moveCharacter(pPlayerID, Position);
	}
	
	/**
	 * @return PlayerID des Spielers, der dran ist
	 */
	@Override
	public int whoseTurn()
	{
		return myTurnManager.whoseTurn();
	}

	@Override
	public TurnState getCurrentTurnState() {
		return myTurnManager.getCurrentTurnState();
	}

	@Override
	public IPlayer getWinner() {
		return myBoard.getWinner();
	}
	
}
