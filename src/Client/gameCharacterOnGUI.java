package Client;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import smims.networking.model.*;
import smims.networking.model.Character;

public class gameCharacterOnGUI extends Button{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5083706558909688339L;
	
	private Color characterColor;
	private Character myCharacter;
	private ClientGUI myGUI;
	private int positionInBasis = 0;
	private int breite = 20;
	private int hoehe = 20;
	
	gameCharacterOnGUI(ClientGUI pGUI, Character pCharacter)
	{
		myGUI = pGUI;
		myCharacter = pCharacter;
		positionieren();
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				clicked(evt);
			}});
	}
	
	private void clicked(ActionEvent evt)
	{
		myGUI.selectCharacter(myCharacter.getPosition());
	}
		
	private void changeCharacter(Character pNewCharacter)
	{		
		myCharacter = pNewCharacter;
	}
	
	public void positionieren()
	{
		if(myCharacter.isInBase())
		{
			this.setBounds(myGUI.getPunktByBaseAndNumber(myCharacter.getPlayer().getPlayerId(),positionInBasis).getX(), myGUI.getPunktByBaseAndNumber(myCharacter.getPlayer().getPlayerId(),positionInBasis).getY(), breite, hoehe);
		}
		else if(myCharacter.isInHouse())
		{
			Punkt tempPunkt = myGUI.getPunktByHouseAndNumber(myCharacter.getPlayer().getPlayerId(), myCharacter.getHouseFieldNumber().get());
			this.setBounds(tempPunkt.getX(), tempPunkt.getY(), breite, hoehe);
		}
		else if(myCharacter.isOnField())
		{
			Punkt tempPunkt = myGUI.getPunktByFieldNumber(myCharacter.getFieldNumber().get());
			this.setBounds(tempPunkt.getX(), tempPunkt.getY(), breite, hoehe);
		}
	}
	
	
}
