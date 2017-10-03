package smims.networking.model;

public class GameState {
	
	int playerId;
	DiceRoller myDice ;
	Board myBoard;
	
	
	/**
	 * 
	 * @param pPlayerID ID des Spielers
	 * @param pBoard das verwendete Spielbrett 
	 */
	GameState(int pPlayerID, Board pBoard)
	{
		playerId = pPlayerID;
		myDice = new DiceRoller();
		myBoard = pBoard;
	}
	
	public int getPlayerId()
	{
		return this.playerId;
	}
	/**
	 * wuerfelt den wuerfel
	 */
	public void rollDice()
	{
		myDice.rollDice();
	}
	
	/**
	 * bewegt die Spielfigur and der gewaehlten position.
	 * @param posCharacter Position der Spielfigur, die bewegt werden soll.
	 * @throws Exception
	 */
	public void moveCharacters(int posCharacter) throws Exception
	{
		if(myBoard.fieldOccupied(posCharacter))
		{
			ICharacter currentCharacter = myBoard.getCharacterAt(posCharacter);
			if(currentCharacter.getPlayer().getPlayerId() == this.playerId)
			{
				myBoard.moveCharacter(currentCharacter, myDice.getResult());
			}
		}
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
