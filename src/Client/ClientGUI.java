package Client;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

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
  
  private ArrayList<gameCharacterOnGUI> gameCharacters = new ArrayList<gameCharacterOnGUI>();
  
  private static final int FieldStartX = 50;
  private static final int FieldStartY = 150;
  private static final int distanceBetweenFields = 25;
  private static final int circleSize = 20;
  
  
  private Container GameField;
  private Container MainContainer;
  private Container Controlls;
  
  private BoardDescriptor myBoardDescriptor;
  private Board myBoard;
  
  
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
  private JButton bReady = new JButton();
  private JButton bTest = new JButton();
  
  private JTextField textFieldChatEingabe = new JTextField();
  private JButton bSenden = new JButton();
 
  
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
    
    MainContainer = getContentPane();
    
    GameField = new JPanel();
    Controlls = new JPanel();
    
    GameField.setLayout(null);
    Controlls.setLayout(null);
   MainContainer.setLayout(null);
    
    GameField.setBounds(0, 0, frameWidth, 300);
    Controlls.setBounds(0, 300,frameWidth, frameHeight );
    
    
    
    
    Controlls.add(GameField);
    // Anfang Komponenten

    bReady.setBounds(20,130,120,60);
    bReady.setText("Bereit!");
    bReady.setMargin(new Insets(2, 2, 2, 2));
    bReady.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            bReady_ActionPerformed(evt);
          }
        });
    
    Controlls.add(bReady);
    
    bSenden.setBounds(900, 300, 75, 25);
    bSenden.setText("senden");
    bSenden.setMargin(new Insets(2, 2, 2, 2));
    bSenden.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bSenden_ActionPerformed(evt);
      }
    });

    Controlls.add(bSenden);
    
    bTest.setBounds(40,200,120,60);
    bTest.setText("Testen!");
    bTest.setMargin(new Insets(2, 2, 2, 2));
    bTest.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            bTest_ActionPerformed(evt);
          }
        });
    
    Controlls.add(bTest);
    
    bWuerfeln.setBounds(290, 0, 120, 60);
    bWuerfeln.setText("würfeln");
    bWuerfeln.setMargin(new Insets(2, 2, 2, 2));
    bWuerfeln.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        bWuerfeln_ActionPerformed(evt);
      }
    });
    Controlls.add(bWuerfeln);
    
    textFieldChatEingabe.setBounds(900, 250, 280, 33);
    textFieldChatEingabe.setToolTipText("Hier die Chateingabe");
    Controlls.add(textFieldChatEingabe);
    
    labelEigeneSpielerID.setBounds(160, 0, 110, 20);
    labelEigeneSpielerID.setText("eigeneSpielerID");
    Controlls.add(labelEigeneSpielerID);
    
    lEigeneSpielerID.setBounds(20, 0, 110, 20);
    lEigeneSpielerID.setText("Eigene Spieler ID:");
    Controlls.add(lEigeneSpielerID);
    
    lAnderReiheist.setBounds(20, 40, 110, 20);
    lAnderReiheist.setText("An der Reihe ist :");
    Controlls.add(lAnderReiheist);
    
    labelAktuelleSpielerID.setBounds(160, 40, 110, 20);
    labelAktuelleSpielerID.setText("aktuelleSpielerID");
    Controlls.add(labelAktuelleSpielerID);
    
    lGewurfelt.setBounds(20, 80, 110, 20);
    lGewurfelt.setText("gewürfelt:");
    Controlls.add(lGewurfelt);
    
    labelWuerfelergebniss.setBounds(160, 80, 110, 20);
    labelWuerfelergebniss.setText("wuerfelergebniss");
    Controlls.add(labelWuerfelergebniss);
    
    textAreaChatScrollPane.setBounds(900, 0, 280, 200);
    Controlls.add(textAreaChatScrollPane);
    
    textAreaInfoScrollPane.setBounds(500, 0, 280, 200);
    Controlls.add(textAreaInfoScrollPane);
    
    bAktuallisieren.setBounds(290, 70, 120, 25);
    bAktuallisieren.setText("aktuallisieren");
    bAktuallisieren.setMargin(new Insets(2, 2, 2, 2));
    bAktuallisieren.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        bAktuallisieren_ActionPerformed(evt);
      }
    });
    Controlls.add(bAktuallisieren);
    
    // Ende Komponenten

    MainContainer.add(GameField);
    MainContainer.add(Controlls);
    
    GameField.setVisible(true);
    Controlls.setVisible(true);
    
    setVisible(true);
    myGraphic = GameField.getGraphics();
    
  } // end of public ClientSpielGUI
  
   public void paint(Graphics g) {	   
	 //System.out.println("paint");
     //getContentPane().repaint();
     if(myBoardDescriptor != null && myBoard != null) {
       drawBoard();
     }
      Controlls.repaint();
   }
  
  private void drawBoard()
  {
    myGraphic = GameField.getGraphics();
	  
    for(int i = 0; i< myBoardDescriptor.getBoardSize(); i++)
    {
      malFeld(getPunktByFieldNumber(i));
    }
    for(int j = 0; j < myBoardDescriptor.getBoardSize() / myBoard.getBoardSectionSize(); j++)
    {
      for(int a = 0; a< myBoardDescriptor.getHouseSize(); a++)
      {
        myGraphic.setColor(getColorByPlayerID(j));
        malFeld(getPunktByBaseAndNumber(j, a));
        malFeld(getPunktByHouseAndNumber(j, a));
      }
    }
    myGraphic.setColor(Color.BLACK);
  }

  public void updateBoard(Board pBoard)
  {
    
    myBoard = pBoard;
    myBoardDescriptor = pBoard.getBoardDescriptor();
    System.out.println("myBoardDescriptor.getBoardSize(): " + myBoardDescriptor.getBoardSize() + " ;myBoardDescriptor.getHouseSize(): " + myBoardDescriptor.getHouseSize() + "; myBoard..getBoardSectionSize(): " + myBoard.getBoardSectionSize());
    this.repaint();
    drawBoard();
    if(gameCharacters.isEmpty())
    {
      placeCharactersOnBoard(pBoard.getAllCharacters());
    }
    updateCharactersOnBoard(pBoard.getAllCharacters());
    
    
  }
  
  
  private void updateCharactersOnBoard(Collection<Character> pCharacters)
  {
    
    Character[] pCharacterArray =  pCharacters.toArray(new Character[pCharacters.size()]);
    gameCharacterOnGUI[] gameCharactersArray =  gameCharacters.toArray(new gameCharacterOnGUI[gameCharacters.size()]);
    if(gameCharactersArray.length == pCharacterArray.length)
    {
      for(int i = 0; i<pCharacterArray.length;i++)
      {
        gameCharactersArray[i].changeCharacter(pCharacterArray[i]);
      } 
    }
  }
  
  private void placeCharactersOnBoard(Collection<Character> pCharacters)
  {
    Container cp = getContentPane();
    for(int i = 0; i< pCharacters.size(); i++)
    {
      gameCharacterOnGUI newCharacterButton = new gameCharacterOnGUI(this);
      gameCharacters.add(newCharacterButton);
      GameField.add(newCharacterButton);
    }
  }
  
  /**
   * 
   * @param pPunkt Punkt ist der MittelPunkt des Feldes
   */
  private void malFeld(Punkt pPunkt)
  {
    myGraphic.drawOval(pPunkt.getX()-circleSize/2, pPunkt.getY()-circleSize/2, circleSize, circleSize);

  }
  
  public Punkt getPunktByFieldNumber(int pFieldNumber)
  {
    return new Punkt(FieldStartX + distanceBetweenFields * pFieldNumber, FieldStartY);
  }
  
  /**
   * 
   * @param pHouse    von welchem Spieler das Haus
   * @param pHouseFieldNumber das wievielete haus feld
   * @return
   */
  public Punkt getPunktByHouseAndNumber(int pHouse, int pHouseFieldNumber)
  {
    if(pHouse == 0)   //haus von Spieler 0 muss ganz rechts sein
    {
      return new Punkt(FieldStartX + distanceBetweenFields * (myBoardDescriptor.getBoardSize() - 1), FieldStartY + distanceBetweenFields * (pHouseFieldNumber + 1));
    }
    return new Punkt(FieldStartX - distanceBetweenFields + distanceBetweenFields * myBoard.getBoardSectionSize() * pHouse, FieldStartY + distanceBetweenFields * (pHouseFieldNumber + 1));
  }
  
  public Punkt getPunktByBaseAndNumber(int pBase, int pBaseFieldNumber)
  {
    return new Punkt(FieldStartX + distanceBetweenFields *  myBoard.getBoardSectionSize() * pBase, FieldStartY - distanceBetweenFields * (pBaseFieldNumber+ 2));
  }
  
  
  public Color getColorByPlayerID(int pPlayerId)
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
  
  
  public void selectCharacter(Position pPosition)
  {
	
	  if(pPosition.isInBase())
	  {
		  myClient.send(Protokoll.CS_MoveCharacter + Protokoll.Splitter + Protokoll.Base + Protokoll.Splitter + 0);
	  }
	  else if(pPosition.isInHouse())
	  {
		  myClient.send(Protokoll.CS_MoveCharacter + Protokoll.Splitter + Protokoll.House + Protokoll.Splitter + pPosition.getHouseFieldNumber().get());
	  }
	  else if(pPosition.isOnField() && !pPosition.isAtStartingPosition())
	  {
		  myClient.send(Protokoll.CS_MoveCharacter + Protokoll.Splitter + Protokoll.Board + Protokoll.Splitter + pPosition.getFieldNumber().get());
	  }
	  else if(pPosition.isOnField() && pPosition.isAtStartingPosition())
	  {
		  myClient.send(Protokoll.CS_MoveCharacter + Protokoll.Splitter + Protokoll.Starting + Protokoll.Splitter + "0");
	  }
	  System.out.println("Move Character");
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
    //myClient.testFunction();
    myClient.send(Protokoll.CS_RollDice);
  } // end of bWuerfeln_ActionPerformed

  public void bReady_ActionPerformed(ActionEvent evt) {
	myClient.send(Protokoll.CS_Ready);  
  
  }
  
  public void bReadyButtonAusblenden()
  {
	  bReady.setVisible(false);
  }
  
  public void bTest_ActionPerformed(ActionEvent evt) {
	    myClient.testFunction();
  }
  
  public void bSenden_ActionPerformed(ActionEvent evt) {
	  myClient.send(Protokoll.CS_Chat + Protokoll.Splitter + textFieldChatEingabe.getText());
	  textFieldChatEingabe.setText("");
  }
  
  public void bAktuallisieren_ActionPerformed(ActionEvent evt) {
    try {
      myClient.send(Protokoll.CS_GetBoard);
      TimeUnit.MILLISECONDS.sleep(10);
      myClient.send(Protokoll.CS_GetDiceResult);
      TimeUnit.MILLISECONDS.sleep(10);
      myClient.send(Protokoll.CS_GetOwnPlayerId);
      TimeUnit.MILLISECONDS.sleep(10);
      myClient.send(Protokoll.CS_WhoseTurn);
      TimeUnit.MILLISECONDS.sleep(10);
      myClient.send(Protokoll.CS_GetTurnState);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

  } // end of bAktuallisieren_ActionPerformed

  // Ende Methoden
} // end of class ClientSpielGUI


