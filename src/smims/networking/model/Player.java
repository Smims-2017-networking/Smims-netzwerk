package smims.networking.model;

import java.util.ArrayList;

public class Player {
	
	int playerId;
	DiceRoller myDice ;
	Board myBoard;
	
	
	/**
	 * 
	 * @param pPlayerID ID des Spielers
	 * @param pBoard das verwendete Spielbrett 
	 */
	Player(int pPlayerID, Board pBoard)
	{
		playerId = pPlayerID;
		myDice = new DiceRoller();
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
		ArrayList<ICharacter> allCharacters = myBoard.getAllCharacters();
		
		
		for(ICharacter currentCharacter : allCharacters)
		{
			if(currentCharacter.getPlayer() == this && currentCharacter.getPosition() == posCharacter) {
				myBoard.moveCharacters(currentCharacter, myDice.getResult());
			}
		}
	}
	
	public int charactersCanMove() {
		int ret=0;
		return ret;
		}
	}
}
