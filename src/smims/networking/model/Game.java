package smims.networking.model;

import java.util.ArrayList;

public class Game {
	
	private DiceRoller myDice ;
	private Board myBoard;
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
	 */
	public void rollDice(int pPlayerID) throws Exception
	{
		myTurnManager.rollDice(pPlayerID);
	}
	
	public int getDiceResult()
	{
		return myDice.getResult();
	}
	
	public Board getBoard()
	{
		return myBoard;
	}
	
	/**
	 * Bewegt den Character an der Position, wenn er zu dem Player gehoert.
	 * @param pPlayerID
	 * @param Position
	 * @throws Exception
	 */
	public void moveCharacter(int pPlayerID, int Position) throws Exception
	{
		myTurnManager.moveCharacter(pPlayerID, Position);
	}
	
	/**
	 * @return PlayerID des Spielers, der dran ist
	 */
	public int whoseTurn()
	{
		return myTurnManager.whoseTurn();
	}
	
}
