package Client;




import smims.networking.model.Protokoll;

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
public class StartClient extends Client {

	private AusgabeFrame meinLauscher;

	public StartClient(String pIPAdresse, int pPortNr) {
		super(pIPAdresse, pPortNr);
		this.meinLauscher = new AusgabeFrame("Output");
		new SenderFrame("Input", this);
	}

	public void processMessage(String pMessage) {
		String[] tags = pMessage.split(Protokoll.Splitter);
		/*if (tags[0].equals(PROTOKOLL.SC_STOP)) {
			this.close();
		}*/
		ausgabe("Empfangen :<" + pMessage + ">");
	}

	public void ausgabe(String pText) {
		if (this.meinLauscher != null && this.meinLauscher.getInhaltLabel() != null) {
			this.meinLauscher.getInhaltLabel().setText(pText);
		}
	}

}
