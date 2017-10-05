package smims.networking.model;

import java.util.ArrayList;
import java.util.function.Consumer;

import smims.networking.model.Position.StartingPositionBuilder;

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
	Game(ArrayList<Player> pPlayers, int boardNumberOfPlayers)
	{
		this(pPlayers, boardNumberOfPlayers, new DiceRoller());
	}
	
	Game(ArrayList<Player> pPlayers, int boardNumberOfPlayers, IDiceRoller diceRoller) {
		this.myDice = diceRoller;
		this.myBoard = new Board(pPlayers, boardNumberOfPlayers);
		this.myTurnManager = new TurnManager(pPlayers, myBoard, myDice);
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
	 * @throws NoSuchCharacterException 
	 * @throws Exception
	 */
	@Override
	public void moveCharacter(int pPlayerID, Position Position) throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException
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
	
	@Override
	public int getRemainingRolls() {
		return myTurnManager.getRemainingRolls();
	}

	@Override
	public StartingPositionBuilder getStartingPositionBuilder(int playerId) {
		return myBoard.getStartingPositionBuilderFor(playerId);
	}

	@Override
	public void registerTurnChangedCallback(Consumer<IPlayer> callback) {
		myTurnManager.registerOnNewTurn(callback);
	}
	
}
