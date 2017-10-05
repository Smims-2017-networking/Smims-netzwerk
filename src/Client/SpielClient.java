package Client;




import smims.networking.model.*;

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
	private AusgabeFrame meinLauscher;

	public SpielClient(String pIPAdresse, int pPortNr) {
		super(pIPAdresse, pPortNr);
		myGUI = new ClientGUI(this);
	//	this.meinLauscher = new AusgabeFrame("Output");
	//	new SenderFrame("Input", this);
	
	}

	public void processMessage(String pMessage) {
		String[] tags = pMessage.split(Protokoll.Splitter);
		switch(tags[0]) {
		case Protokoll.SC_Welcome: 
				myGUI.appendChat(tags[1]);
			break;
		
		case Protokoll.SC_Board :
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
				myGUI.setInfoText("Unbekannter fehler aufgetaucht. Hier die nachricht vom Server: " + pMessage);
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
		ausgabe("Empfangen :<" + pMessage + ">");
	}

	public void ausgabe(String pText) {
		if (this.meinLauscher != null && this.meinLauscher.getInhaltLabel() != null) {
			this.meinLauscher.getInhaltLabel().setText(pText);
		}
	}
	
	
}
