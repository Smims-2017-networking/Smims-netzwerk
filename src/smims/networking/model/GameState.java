package smims.networking.model;

public class GameState {
	DiceRoller myDice;
	Board myBoard;
	
	/**
	 * 
	 * @param pPlayerID ID des Spielers
	 * @param pBoard das verwendete Spielbrett 
	 */
	GameState(Board pBoard)
	{
		myDice = new DiceRoller();
		myBoard = pBoard;
	}
	
	/**
	 * wuerfelt den wuerfel
	 */
	public void rollDice()
	{
		myDice.rollDice();
	}
	
	public int getDiceResult()
	{
		return myDice.getResult();
	}
	
	public Board getBoard()
	{
		return myBoard;
	}
	
	
	public void MoveCharacter(int PlayerID, int Position) throws Exception
	{
		if(PlayerID == myBoard.getCharacterAt(Position).getPlayer().getPlayerId())
		{	
				myBoard.moveCharacter(myBoard.getCharacterAt(Position), getDiceResult());
		}
		else
		{
			throw new NotYourTurnException();
		}
	}
	
	/*
	 * Gibt die Anzahl der Character des Spielers zur�ck, die sich bewegen lassen
	 */
	public int numberOfMovableCharacters() 
	{
		int ret=0;
		for(Character character: myBoard.getAllCharacters())
		{
			if(myBoard.characterCanMove(character, myDice.getResult()))
			{
				ret++;
			}
		}
		return ret;
	}
}
