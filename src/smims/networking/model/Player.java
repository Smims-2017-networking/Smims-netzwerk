package smims.networking.model;

public class Player {
	
	int playerId;
	Dice dice ;
	Board myBoard;
	
	
	/**
	 * 
	 * @param pPlayerID ID des Spielers
	 * @param pBoard das verwendete Spielbrett
	 */
	Player(int pPlayerID, Board pBoard)
	{
		playerId = pPlayerID;
		dice = new Dice();
		myBoard = pBoard;
	}
	
	/**
	 * wuerfelt den wuerfel
	 */
	public void rollDice()
	{
		dice.diceRoll();
	}
	
	/**
	 * bewegt die Spielfigur and der gewaehlten position.
	 * @param posCharacter Position der Spielfigur, die bewegt werden soll.
	 */
	public void moveCharacters(int posCharacter)
	{
		allCharacters = myBoard.getAllCharacters();
		
		
		for(int i = 0; i< allCharacters ;i++)
		board.moveCharacters(selectedCharacter, dice.getResult());
	}
	
	
}
