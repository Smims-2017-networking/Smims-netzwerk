package Client;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import smims.networking.model.*;
import smims.networking.model.Character;
/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 04.10.2017
 * @author
 */

public class ClientGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8982056986521808025L;

	// Anfang Attribute
	private SpielClient myClient;
	private Graphics myGraphic;
	
	private final Color[] Colors = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.CYAN, Color.MAGENTA};
	
	private static final int FieldStartX = 50;
	private static final int FieldStartY = 300;
	private static final int distanceBetweenFields = 30;
	private static final int circleSize = 20;
	
	
	private int SpaceBetweenPlayers = 10;
	private int PlayerAmountOnField = 4;
	private int HouseSize = 4;
	
	
	private JButton bWuerfeln = new JButton();
	private JLabel labelEigeneSpielerID = new JLabel();
	private JLabel lEigeneSpielerID = new JLabel();
	private JLabel lAnderReiheist = new JLabel();
	private JLabel labelAktuelleSpielerID = new JLabel();
	private JLabel lGewurfelt = new JLabel();
	private JLabel labelWuerfelergebniss = new JLabel();
	private JTextArea textAreaChat = new JTextArea("");
	private JScrollPane textAreaChatScrollPane = new JScrollPane(textAreaChat);
	private JTextArea textAreaInfo = new JTextArea("");
	private JScrollPane textAreaInfoScrollPane = new JScrollPane(textAreaInfo);
	private JButton bAktuallisieren = new JButton();
	// Ende Attribute

	public ClientGUI(SpielClient pClient) {
		
		// Frame-Initialisierung
		super();
		myClient = pClient;
		
		
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int frameWidth = 1219;
		int frameHeight = 741;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		setTitle("ClientSpielGUI");
		setResizable(true);
		Container cp = getContentPane();
		cp.setLayout(null);
		// Anfang Komponenten

		bWuerfeln.setBounds(888, 96, 251, 73);
		bWuerfeln.setText("würfeln");
		bWuerfeln.setMargin(new Insets(2, 2, 2, 2));
		bWuerfeln.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bWuerfeln_ActionPerformed(evt);
			}
		});
		cp.add(bWuerfeln);
		labelEigeneSpielerID.setBounds(1024, 16, 110, 20);
		labelEigeneSpielerID.setText("eigeneSpielerID");
		cp.add(labelEigeneSpielerID);
		lEigeneSpielerID.setBounds(888, 16, 110, 20);
		lEigeneSpielerID.setText("Eigene Spieler ID:");
		cp.add(lEigeneSpielerID);
		lAnderReiheist.setBounds(888, 56, 110, 20);
		lAnderReiheist.setText("An der Reihe ist :");
		cp.add(lAnderReiheist);
		labelAktuelleSpielerID.setBounds(1024, 56, 110, 20);
		labelAktuelleSpielerID.setText("aktuelleSpielerID");
		cp.add(labelAktuelleSpielerID);
		lGewurfelt.setBounds(888, 184, 110, 20);
		lGewurfelt.setText("gewürfelt:");
		cp.add(lGewurfelt);
		labelWuerfelergebniss.setBounds(1016, 184, 110, 20);
		labelWuerfelergebniss.setText("wuerfelergebniss");
		cp.add(labelWuerfelergebniss);
		textAreaChatScrollPane.setBounds(888, 232, 281, 225);
		cp.add(textAreaChatScrollPane);
		textAreaInfoScrollPane.setBounds(888, 480, 281, 209);
		cp.add(textAreaInfoScrollPane);
		bAktuallisieren.setBounds(696, 648, 123, 25);
		bAktuallisieren.setText("aktuallisieren");
		bAktuallisieren.setMargin(new Insets(2, 2, 2, 2));
		bAktuallisieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bAktuallisieren_ActionPerformed(evt);
			}
		});
		cp.add(bAktuallisieren);
		// Ende Komponenten

		setVisible(true);
		
		
	} // end of public ClientSpielGUI
	
	 public void paint(Graphics g) {
		 myGraphic = g;
		 drawBoard();
	 }
	
	private void drawBoard()
	{
		Graphics g = this.getGraphics();
		for(int i = 0; i< SpaceBetweenPlayers * PlayerAmountOnField; i++)
		{
			malFeld(getPunktByFieldNumber(i));
		}
		for(int j = 0; j < PlayerAmountOnField; j++)
		{
			for(int a = 0; a< HouseSize; a++)
			{
				myGraphic.setColor(getColorByPlayerID(j));
				malFeld(getPunktByBaseAndNumber(j, a));
				malFeld(getPunktByHouseAndNumber(j, a));
			}
		}
		myGraphic.setColor(Color.BLACK);
	}

	private void malFeld(Punkt pPunkt)
	{
		myGraphic.drawOval(pPunkt.getX(), pPunkt.getY(), circleSize, circleSize);
	}
	
	private Punkt getPunktByFieldNumber(int pFieldNumber)
	{
		return new Punkt(FieldStartX + distanceBetweenFields * pFieldNumber, FieldStartY);
	}
	
	/**
	 * 
	 * @param pHouse		von welchem Spieler das Haus
	 * @param pHouseFieldNumber	das wievielete haus feld
	 * @return
	 */
	private Punkt getPunktByHouseAndNumber(int pHouse, int pHouseFieldNumber)
	{
		if(pHouse == 0)		//haus von Spieler 0 muss ganz rechts sein
		{
			return new Punkt(FieldStartX + distanceBetweenFields * (SpaceBetweenPlayers * PlayerAmountOnField - 1), FieldStartY + distanceBetweenFields * (pHouseFieldNumber + 1));
		}
		return new Punkt(FieldStartX - distanceBetweenFields + distanceBetweenFields * SpaceBetweenPlayers * pHouse, FieldStartY + distanceBetweenFields * (pHouseFieldNumber + 1));
	}
	
	private Punkt getPunktByBaseAndNumber(int pBase, int pBaseFieldNumber)
	{
		return new Punkt(FieldStartX + distanceBetweenFields * SpaceBetweenPlayers * pBase, FieldStartY - distanceBetweenFields * (pBaseFieldNumber+ 2));
	}
	
	private void placeCharactersOnBoard(ArrayList<Character> pCharacters)
	{
		for(Character character: pCharacters)
		{
			
		}
	}
	
	private Color getColorByPlayerID(int pPlayerId)
	{
		return Colors[pPlayerId%Colors.length];
	}
	
	
	public void appendChat(String pText)
	{
		textAreaChat.append(pText);
	}
	
	public void setInfoText(String pText)
	{
		textAreaInfo.setText(pText);
	}
	
	public void showBoard(Board pBoard)
	{
		//TO DO
	}
	
	
	public void displayDiceResult(String pDiceResult)
	{
		labelWuerfelergebniss.setText(pDiceResult);
	}
	
	public void displayOwnPlayerId(String pId)
	{
		labelEigeneSpielerID.setText(pId);
	}
	
	public void displayCurrentTurnPlayerId(String pId)
	{
		labelAktuelleSpielerID.setText(pId);
	}
	
	public void bWuerfeln_ActionPerformed(ActionEvent evt) {
		// TODO hier Quelltext einfügen

	} // end of bWuerfeln_ActionPerformed

	public void bAktuallisieren_ActionPerformed(ActionEvent evt) {
		// TODO hier Quelltext einfügen

	} // end of bAktuallisieren_ActionPerformed

	// Ende Methoden
} // end of class ClientSpielGUI


