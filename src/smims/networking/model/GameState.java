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
	
	/*
	 * Gibt die Anzahl der Character des Spielers zur�ck, die sich bewegen lassen
	 */
	public int numberOfMovableCharacters() 
	{
		int ret=0;
		for(IReadonlyCharacter character: myBoard.getAllCharacters())
		{
			if(myBoard.characterCanMove(character, myDice.getResult()))
			{
				ret++;
			}
		}
		return ret;
	}
}
