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
	private int breite = 18;
	private int hoehe = 18;
	
	gameCharacterOnGUI(ClientGUI pGUI)
	{
		super();
		myGUI = pGUI;
		
		//positionieren();
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				clicked(evt);
			}});
		System.out.println("Button Created");
		this.setVisible(true);
	}
	
	private void clicked(ActionEvent evt)
	{
		myGUI.selectCharacter(myCharacter.getPosition());
	}
		
	public void changeCharacter(Character pNewCharacter, int pPositionInBase)
	{		
		System.out.println("changeCharacters");
		myCharacter = pNewCharacter;
		positionieren(pPositionInBase);
	}
	
	public void positionieren(int pPositionInBasis)
	{
		Punkt tempPunkt = new Punkt(0,0);
		if(myCharacter.isInBase())
		{
			tempPunkt = myGUI.getPunktByBaseAndNumber(myCharacter.getPlayer().getPlayerId(),pPositionInBasis);
			this.setBounds(tempPunkt.getX()-breite/2, tempPunkt.getY()-hoehe/2, breite, hoehe);
			System.out.println("in Base!!!");
		}
		else if(myCharacter.isInHouse())
		{
			tempPunkt = myGUI.getPunktByHouseAndNumber(myCharacter.getPlayer().getPlayerId(), myCharacter.getHouseFieldNumber().get());
			this.setBounds(tempPunkt.getX()-breite/2, tempPunkt.getY()- hoehe/2, breite, hoehe);
		}
		else if(myCharacter.isOnField())
		{
			tempPunkt = myGUI.getPunktByFieldNumber(myCharacter.getFieldNumber().get());
			this.setBounds(tempPunkt.getX()-breite/2, tempPunkt.getY()- hoehe /2, breite, hoehe);
			System.out.println("In Field");
		}
		this.setBackground(myGUI.getColorByPlayerID(myCharacter.getPlayer().getPlayerId()));
		System.out.println("Figur Positioniert: " + tempPunkt.getX() + " , " + tempPunkt.getY());
	}
	
	
}
