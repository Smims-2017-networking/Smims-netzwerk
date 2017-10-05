package Client;

import java.awt.Button;
import java.awt.Color;

public class gameCharacterOnGUI extends Button{

	private Color characterColor;
	private Character myCharacter;
	private ClientGUI myGUI;
	
	gameCharacterOnGUI(ClientGUI pGUI)
	{
		myGUI = pGUI;
	}
	
	private void changeCharacter(Character pNewCharacter)
	{
		myCharacter = pNewCharacter;
	}
	
	public void positionieren()
	{
		//if(myCharacter)
	}
	
}
