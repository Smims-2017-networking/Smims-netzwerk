package Client;




import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import smims.networking.model.*;
import smims.networking.messages.*;

/**
 * Ein sehr einfacher CLient, der nur die Meldungen des Servers unbearbeitet
 * ausgibt.
 * 
 * Dieser Client erzeugt zusaetzlich einen SenderFrame, der diesen Client zum
 * Senden benutzt.
 * 
 * @version 2.1 vom 8.8.2014
 * @author Klaus Bovermann
 */
public class SpielClient extends Client {

	private ClientGUI myGUI;
	//private AusgabeFrame meinLauscher;

	public SpielClient(String pIPAdresse, int pPortNr) {
		super(pIPAdresse, pPortNr);
		myGUI = new ClientGUI(this);
	//	this.meinLauscher = new AusgabeFrame("Output");
	//	new SenderFrame("Input", this);
	
	}

	public void testFunction()
	{
		System.out.println("testFunciton");
		ArrayList<Player> players= new ArrayList<Player>();
		for(int i = 0; i<=3; i++)
		{
			players.add(new Player(i));
		}
		Board pBoard = new Board(players, 4);
		try {
			pBoard.moveCharacter(pBoard.getAllCharacters().stream().findAny().get(), 6);
		} catch (MoveNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myGUI.updateBoard(pBoard);
	}
	
	
	public void processMessage(String pMessage) {
		String[] tags = pMessage.split(Protokoll.Splitter);
		try {
		switch(tags[0]) {
		
		case Protokoll.SC_Welcome: 
				myGUI.appendChat(tags[1]);
			break;
		
		case Protokoll.SC_Board :
			GsonBuilder myGsonBuilder = new GsonBuilder();
			myGsonBuilder.registerTypeAdapter(BoardDescriptor.class, new BoardDescriptorDeserializer());
			myGsonBuilder.registerTypeAdapter(Board.class, new BoardDeserializer());
			Gson myGson = myGsonBuilder.create();
			myGUI.updateBoard(myGson.fromJson(tags[1], Board.class));
			//Board tempBoard = ;//TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO 
				//myGUI.updateBoard(tempBoard);
			//myBoard = recieved Board NOT YET IMPLEMENTED
			break;
		case Protokoll.SC_DiceResult :
				myGUI.displayDiceResult(tags[1]);
			break;
		case Protokoll.SC_PlayerTurn :
				myGUI.displayCurrentTurnPlayerId(tags[1]);
			break;
		case Protokoll.SC_GameStarts :
				myGUI.appendChat("Spiel faengt an");
				myGUI.bReadyButtonAusblenden();
			break;
		case Protokoll.SC_MoveNotAllowed :
				myGUI.setInfoText("Aktion nicht erlaubt!");
			break;
		case Protokoll.SC_ThrowsLeft :
				myGUI.setInfoText("Du darfst noch " + tags[1] + " mal Wuerfeln.");
			break;
		case Protokoll.SC_OwnPlayerId :
				myGUI.displayOwnPlayerId(tags[1]);
			break;
		case Protokoll.SC_Error :
				myGUI.setInfoText("Falsche Eingabe");
			break;
		case Protokoll.SC_MoveOk :
				myGUI.setInfoText("Gut gemacht!"); 
			break;
		case Protokoll.SC_Exception :
				myGUI.setInfoText("Du kannst das nicht machen");
			break;
		case Protokoll.SC_NotYourTurn :
				myGUI.setInfoText("Du bist nicht dran!");
			break;
		case Protokoll.SC_TurnState :
				myGUI.setInfoText("Runden Status: " + tags[1]);
			break;
		case Protokoll.SC_NoSuchCharacter :
				myGUI.setInfoText("Der Character, den du bewegen wolltest steht da nicht");
			break;
		case Protokoll.SC_Stop :
				myGUI.setInfoText("Server: Bye Bye Bye Bye");
			this.close();
			break;
		}
		}catch (Exception e) {
			myGUI.setInfoText("Fehler: " + e.toString());
		}
		
		
		
		
	}




	
}
